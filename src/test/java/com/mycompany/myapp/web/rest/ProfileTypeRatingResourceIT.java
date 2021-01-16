package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.ProfileTypeRating;
import com.mycompany.myapp.repository.ProfileTypeRatingRepository;
import com.mycompany.myapp.service.ProfileTypeRatingService;
import com.mycompany.myapp.service.dto.ProfileTypeRatingDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeRatingMapper;
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
 * Integration tests for the {@link ProfileTypeRatingResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ProfileTypeRatingResourceIT {

    private static final String DEFAULT_CHARACTERISTIC = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTERISTIC = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    @Autowired
    private ProfileTypeRatingRepository profileTypeRatingRepository;

    @Autowired
    private ProfileTypeRatingMapper profileTypeRatingMapper;

    @Autowired
    private ProfileTypeRatingService profileTypeRatingService;

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

    private MockMvc restProfileTypeRatingMockMvc;

    private ProfileTypeRating profileTypeRating;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileTypeRatingResource profileTypeRatingResource = new ProfileTypeRatingResource(profileTypeRatingService);
        this.restProfileTypeRatingMockMvc = MockMvcBuilders.standaloneSetup(profileTypeRatingResource)
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
    public static ProfileTypeRating createEntity(EntityManager em) {
        ProfileTypeRating profileTypeRating = new ProfileTypeRating()
            .characteristic(DEFAULT_CHARACTERISTIC)
            .rating(DEFAULT_RATING);
        return profileTypeRating;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileTypeRating createUpdatedEntity(EntityManager em) {
        ProfileTypeRating profileTypeRating = new ProfileTypeRating()
            .characteristic(UPDATED_CHARACTERISTIC)
            .rating(UPDATED_RATING);
        return profileTypeRating;
    }

    @BeforeEach
    public void initTest() {
        profileTypeRating = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileTypeRating() throws Exception {
        int databaseSizeBeforeCreate = profileTypeRatingRepository.findAll().size();

        // Create the ProfileTypeRating
        ProfileTypeRatingDTO profileTypeRatingDTO = profileTypeRatingMapper.toDto(profileTypeRating);
        restProfileTypeRatingMockMvc.perform(post("/api/profile-type-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeRatingDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileTypeRating in the database
        List<ProfileTypeRating> profileTypeRatingList = profileTypeRatingRepository.findAll();
        assertThat(profileTypeRatingList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileTypeRating testProfileTypeRating = profileTypeRatingList.get(profileTypeRatingList.size() - 1);
        assertThat(testProfileTypeRating.getCharacteristic()).isEqualTo(DEFAULT_CHARACTERISTIC);
        assertThat(testProfileTypeRating.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createProfileTypeRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileTypeRatingRepository.findAll().size();

        // Create the ProfileTypeRating with an existing ID
        profileTypeRating.setId(1L);
        ProfileTypeRatingDTO profileTypeRatingDTO = profileTypeRatingMapper.toDto(profileTypeRating);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileTypeRatingMockMvc.perform(post("/api/profile-type-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeRatingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileTypeRating in the database
        List<ProfileTypeRating> profileTypeRatingList = profileTypeRatingRepository.findAll();
        assertThat(profileTypeRatingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfileTypeRatings() throws Exception {
        // Initialize the database
        profileTypeRatingRepository.saveAndFlush(profileTypeRating);

        // Get all the profileTypeRatingList
        restProfileTypeRatingMockMvc.perform(get("/api/profile-type-ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileTypeRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].characteristic").value(hasItem(DEFAULT_CHARACTERISTIC)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }
    
    @Test
    @Transactional
    public void getProfileTypeRating() throws Exception {
        // Initialize the database
        profileTypeRatingRepository.saveAndFlush(profileTypeRating);

        // Get the profileTypeRating
        restProfileTypeRatingMockMvc.perform(get("/api/profile-type-ratings/{id}", profileTypeRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileTypeRating.getId().intValue()))
            .andExpect(jsonPath("$.characteristic").value(DEFAULT_CHARACTERISTIC))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }

    @Test
    @Transactional
    public void getNonExistingProfileTypeRating() throws Exception {
        // Get the profileTypeRating
        restProfileTypeRatingMockMvc.perform(get("/api/profile-type-ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileTypeRating() throws Exception {
        // Initialize the database
        profileTypeRatingRepository.saveAndFlush(profileTypeRating);

        int databaseSizeBeforeUpdate = profileTypeRatingRepository.findAll().size();

        // Update the profileTypeRating
        ProfileTypeRating updatedProfileTypeRating = profileTypeRatingRepository.findById(profileTypeRating.getId()).get();
        // Disconnect from session so that the updates on updatedProfileTypeRating are not directly saved in db
        em.detach(updatedProfileTypeRating);
        updatedProfileTypeRating
            .characteristic(UPDATED_CHARACTERISTIC)
            .rating(UPDATED_RATING);
        ProfileTypeRatingDTO profileTypeRatingDTO = profileTypeRatingMapper.toDto(updatedProfileTypeRating);

        restProfileTypeRatingMockMvc.perform(put("/api/profile-type-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeRatingDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileTypeRating in the database
        List<ProfileTypeRating> profileTypeRatingList = profileTypeRatingRepository.findAll();
        assertThat(profileTypeRatingList).hasSize(databaseSizeBeforeUpdate);
        ProfileTypeRating testProfileTypeRating = profileTypeRatingList.get(profileTypeRatingList.size() - 1);
        assertThat(testProfileTypeRating.getCharacteristic()).isEqualTo(UPDATED_CHARACTERISTIC);
        assertThat(testProfileTypeRating.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileTypeRating() throws Exception {
        int databaseSizeBeforeUpdate = profileTypeRatingRepository.findAll().size();

        // Create the ProfileTypeRating
        ProfileTypeRatingDTO profileTypeRatingDTO = profileTypeRatingMapper.toDto(profileTypeRating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileTypeRatingMockMvc.perform(put("/api/profile-type-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeRatingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileTypeRating in the database
        List<ProfileTypeRating> profileTypeRatingList = profileTypeRatingRepository.findAll();
        assertThat(profileTypeRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileTypeRating() throws Exception {
        // Initialize the database
        profileTypeRatingRepository.saveAndFlush(profileTypeRating);

        int databaseSizeBeforeDelete = profileTypeRatingRepository.findAll().size();

        // Delete the profileTypeRating
        restProfileTypeRatingMockMvc.perform(delete("/api/profile-type-ratings/{id}", profileTypeRating.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileTypeRating> profileTypeRatingList = profileTypeRatingRepository.findAll();
        assertThat(profileTypeRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
