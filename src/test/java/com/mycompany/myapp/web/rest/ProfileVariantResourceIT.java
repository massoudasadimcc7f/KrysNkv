package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.ProfileVariant;
import com.mycompany.myapp.repository.ProfileVariantRepository;
import com.mycompany.myapp.service.ProfileVariantService;
import com.mycompany.myapp.service.dto.ProfileVariantDTO;
import com.mycompany.myapp.service.mapper.ProfileVariantMapper;
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
 * Integration tests for the {@link ProfileVariantResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ProfileVariantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private ProfileVariantRepository profileVariantRepository;

    @Autowired
    private ProfileVariantMapper profileVariantMapper;

    @Autowired
    private ProfileVariantService profileVariantService;

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

    private MockMvc restProfileVariantMockMvc;

    private ProfileVariant profileVariant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileVariantResource profileVariantResource = new ProfileVariantResource(profileVariantService);
        this.restProfileVariantMockMvc = MockMvcBuilders.standaloneSetup(profileVariantResource)
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
    public static ProfileVariant createEntity(EntityManager em) {
        ProfileVariant profileVariant = new ProfileVariant()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR);
        return profileVariant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileVariant createUpdatedEntity(EntityManager em) {
        ProfileVariant profileVariant = new ProfileVariant()
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR);
        return profileVariant;
    }

    @BeforeEach
    public void initTest() {
        profileVariant = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileVariant() throws Exception {
        int databaseSizeBeforeCreate = profileVariantRepository.findAll().size();

        // Create the ProfileVariant
        ProfileVariantDTO profileVariantDTO = profileVariantMapper.toDto(profileVariant);
        restProfileVariantMockMvc.perform(post("/api/profile-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileVariantDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileVariant in the database
        List<ProfileVariant> profileVariantList = profileVariantRepository.findAll();
        assertThat(profileVariantList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileVariant testProfileVariant = profileVariantList.get(profileVariantList.size() - 1);
        assertThat(testProfileVariant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfileVariant.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createProfileVariantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileVariantRepository.findAll().size();

        // Create the ProfileVariant with an existing ID
        profileVariant.setId(1L);
        ProfileVariantDTO profileVariantDTO = profileVariantMapper.toDto(profileVariant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileVariantMockMvc.perform(post("/api/profile-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileVariantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileVariant in the database
        List<ProfileVariant> profileVariantList = profileVariantRepository.findAll();
        assertThat(profileVariantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfileVariants() throws Exception {
        // Initialize the database
        profileVariantRepository.saveAndFlush(profileVariant);

        // Get all the profileVariantList
        restProfileVariantMockMvc.perform(get("/api/profile-variants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileVariant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));
    }
    
    @Test
    @Transactional
    public void getProfileVariant() throws Exception {
        // Initialize the database
        profileVariantRepository.saveAndFlush(profileVariant);

        // Get the profileVariant
        restProfileVariantMockMvc.perform(get("/api/profile-variants/{id}", profileVariant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileVariant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR));
    }

    @Test
    @Transactional
    public void getNonExistingProfileVariant() throws Exception {
        // Get the profileVariant
        restProfileVariantMockMvc.perform(get("/api/profile-variants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileVariant() throws Exception {
        // Initialize the database
        profileVariantRepository.saveAndFlush(profileVariant);

        int databaseSizeBeforeUpdate = profileVariantRepository.findAll().size();

        // Update the profileVariant
        ProfileVariant updatedProfileVariant = profileVariantRepository.findById(profileVariant.getId()).get();
        // Disconnect from session so that the updates on updatedProfileVariant are not directly saved in db
        em.detach(updatedProfileVariant);
        updatedProfileVariant
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR);
        ProfileVariantDTO profileVariantDTO = profileVariantMapper.toDto(updatedProfileVariant);

        restProfileVariantMockMvc.perform(put("/api/profile-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileVariantDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileVariant in the database
        List<ProfileVariant> profileVariantList = profileVariantRepository.findAll();
        assertThat(profileVariantList).hasSize(databaseSizeBeforeUpdate);
        ProfileVariant testProfileVariant = profileVariantList.get(profileVariantList.size() - 1);
        assertThat(testProfileVariant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfileVariant.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileVariant() throws Exception {
        int databaseSizeBeforeUpdate = profileVariantRepository.findAll().size();

        // Create the ProfileVariant
        ProfileVariantDTO profileVariantDTO = profileVariantMapper.toDto(profileVariant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileVariantMockMvc.perform(put("/api/profile-variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileVariantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileVariant in the database
        List<ProfileVariant> profileVariantList = profileVariantRepository.findAll();
        assertThat(profileVariantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileVariant() throws Exception {
        // Initialize the database
        profileVariantRepository.saveAndFlush(profileVariant);

        int databaseSizeBeforeDelete = profileVariantRepository.findAll().size();

        // Delete the profileVariant
        restProfileVariantMockMvc.perform(delete("/api/profile-variants/{id}", profileVariant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileVariant> profileVariantList = profileVariantRepository.findAll();
        assertThat(profileVariantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
