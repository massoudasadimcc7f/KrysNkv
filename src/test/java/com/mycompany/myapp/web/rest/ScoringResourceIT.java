package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.Scoring;
import com.mycompany.myapp.repository.ScoringRepository;
import com.mycompany.myapp.service.ScoringService;
import com.mycompany.myapp.service.dto.ScoringDTO;
import com.mycompany.myapp.service.mapper.ScoringMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScoringResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ScoringResourceIT {

    private static final Integer DEFAULT_SCORE_1 = 1;
    private static final Integer UPDATED_SCORE_1 = 2;

    private static final Integer DEFAULT_SCORE_2 = 1;
    private static final Integer UPDATED_SCORE_2 = 2;

    @Autowired
    private ScoringRepository scoringRepository;

    @Autowired
    private ScoringMapper scoringMapper;

    @Autowired
    private ScoringService scoringService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restScoringMockMvc;

    private Scoring scoring;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScoringResource scoringResource = new ScoringResource(scoringService);
        this.restScoringMockMvc = MockMvcBuilders.standaloneSetup(scoringResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoring createEntity(EntityManager em) {
        Scoring scoring = new Scoring()
            .score1(DEFAULT_SCORE_1)
            .score2(DEFAULT_SCORE_2);
        return scoring;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoring createUpdatedEntity(EntityManager em) {
        Scoring scoring = new Scoring()
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2);
        return scoring;
    }

    @BeforeEach
    public void initTest() {
        scoring = createEntity(em);
    }

    @Test
    @Transactional
    public void createScoring() throws Exception {
        int databaseSizeBeforeCreate = scoringRepository.findAll().size();

        // Create the Scoring
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);
        restScoringMockMvc.perform(post("/api/scorings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isCreated());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeCreate + 1);
        Scoring testScoring = scoringList.get(scoringList.size() - 1);
        assertThat(testScoring.getScore1()).isEqualTo(DEFAULT_SCORE_1);
        assertThat(testScoring.getScore2()).isEqualTo(DEFAULT_SCORE_2);
    }

    @Test
    @Transactional
    public void createScoringWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoringRepository.findAll().size();

        // Create the Scoring with an existing ID
        scoring.setId(1L);
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoringMockMvc.perform(post("/api/scorings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScorings() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        // Get all the scoringList
        restScoringMockMvc.perform(get("/api/scorings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scoring.getId().intValue())))
            .andExpect(jsonPath("$.[*].score1").value(hasItem(DEFAULT_SCORE_1)))
            .andExpect(jsonPath("$.[*].score2").value(hasItem(DEFAULT_SCORE_2)));
    }
    
    @Test
    @Transactional
    public void getScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        // Get the scoring
        restScoringMockMvc.perform(get("/api/scorings/{id}", scoring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scoring.getId().intValue()))
            .andExpect(jsonPath("$.score1").value(DEFAULT_SCORE_1))
            .andExpect(jsonPath("$.score2").value(DEFAULT_SCORE_2));
    }

    @Test
    @Transactional
    public void getNonExistingScoring() throws Exception {
        // Get the scoring
        restScoringMockMvc.perform(get("/api/scorings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        int databaseSizeBeforeUpdate = scoringRepository.findAll().size();

        // Update the scoring
        Scoring updatedScoring = scoringRepository.findById(scoring.getId()).get();
        // Disconnect from session so that the updates on updatedScoring are not directly saved in db
        em.detach(updatedScoring);
        updatedScoring
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2);
        ScoringDTO scoringDTO = scoringMapper.toDto(updatedScoring);

        restScoringMockMvc.perform(put("/api/scorings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isOk());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeUpdate);
        Scoring testScoring = scoringList.get(scoringList.size() - 1);
        assertThat(testScoring.getScore1()).isEqualTo(UPDATED_SCORE_1);
        assertThat(testScoring.getScore2()).isEqualTo(UPDATED_SCORE_2);
    }

    @Test
    @Transactional
    public void updateNonExistingScoring() throws Exception {
        int databaseSizeBeforeUpdate = scoringRepository.findAll().size();

        // Create the Scoring
        ScoringDTO scoringDTO = scoringMapper.toDto(scoring);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoringMockMvc.perform(put("/api/scorings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scoring in the database
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScoring() throws Exception {
        // Initialize the database
        scoringRepository.saveAndFlush(scoring);

        int databaseSizeBeforeDelete = scoringRepository.findAll().size();

        // Delete the scoring
        restScoringMockMvc.perform(delete("/api/scorings/{id}", scoring.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scoring> scoringList = scoringRepository.findAll();
        assertThat(scoringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
