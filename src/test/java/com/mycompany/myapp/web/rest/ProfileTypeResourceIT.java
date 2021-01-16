package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.ProfileType;
import com.mycompany.myapp.repository.ProfileTypeRepository;
import com.mycompany.myapp.service.ProfileTypeService;
import com.mycompany.myapp.service.dto.ProfileTypeDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeMapper;
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
 * Integration tests for the {@link ProfileTypeResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ProfileTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProfileTypeRepository profileTypeRepository;

    @Autowired
    private ProfileTypeMapper profileTypeMapper;

    @Autowired
    private ProfileTypeService profileTypeService;

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

    private MockMvc restProfileTypeMockMvc;

    private ProfileType profileType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileTypeResource profileTypeResource = new ProfileTypeResource(profileTypeService);
        this.restProfileTypeMockMvc = MockMvcBuilders.standaloneSetup(profileTypeResource)
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
    public static ProfileType createEntity(EntityManager em) {
        ProfileType profileType = new ProfileType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return profileType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileType createUpdatedEntity(EntityManager em) {
        ProfileType profileType = new ProfileType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return profileType;
    }

    @BeforeEach
    public void initTest() {
        profileType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileType() throws Exception {
        int databaseSizeBeforeCreate = profileTypeRepository.findAll().size();

        // Create the ProfileType
        ProfileTypeDTO profileTypeDTO = profileTypeMapper.toDto(profileType);
        restProfileTypeMockMvc.perform(post("/api/profile-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileType in the database
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();
        assertThat(profileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileType testProfileType = profileTypeList.get(profileTypeList.size() - 1);
        assertThat(testProfileType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProfileTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileTypeRepository.findAll().size();

        // Create the ProfileType with an existing ID
        profileType.setId(1L);
        ProfileTypeDTO profileTypeDTO = profileTypeMapper.toDto(profileType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileTypeMockMvc.perform(post("/api/profile-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileType in the database
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();
        assertThat(profileTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfileTypes() throws Exception {
        // Initialize the database
        profileTypeRepository.saveAndFlush(profileType);

        // Get all the profileTypeList
        restProfileTypeMockMvc.perform(get("/api/profile-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProfileType() throws Exception {
        // Initialize the database
        profileTypeRepository.saveAndFlush(profileType);

        // Get the profileType
        restProfileTypeMockMvc.perform(get("/api/profile-types/{id}", profileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingProfileType() throws Exception {
        // Get the profileType
        restProfileTypeMockMvc.perform(get("/api/profile-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileType() throws Exception {
        // Initialize the database
        profileTypeRepository.saveAndFlush(profileType);

        int databaseSizeBeforeUpdate = profileTypeRepository.findAll().size();

        // Update the profileType
        ProfileType updatedProfileType = profileTypeRepository.findById(profileType.getId()).get();
        // Disconnect from session so that the updates on updatedProfileType are not directly saved in db
        em.detach(updatedProfileType);
        updatedProfileType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ProfileTypeDTO profileTypeDTO = profileTypeMapper.toDto(updatedProfileType);

        restProfileTypeMockMvc.perform(put("/api/profile-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileType in the database
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();
        assertThat(profileTypeList).hasSize(databaseSizeBeforeUpdate);
        ProfileType testProfileType = profileTypeList.get(profileTypeList.size() - 1);
        assertThat(testProfileType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileType() throws Exception {
        int databaseSizeBeforeUpdate = profileTypeRepository.findAll().size();

        // Create the ProfileType
        ProfileTypeDTO profileTypeDTO = profileTypeMapper.toDto(profileType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileTypeMockMvc.perform(put("/api/profile-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileType in the database
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();
        assertThat(profileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileType() throws Exception {
        // Initialize the database
        profileTypeRepository.saveAndFlush(profileType);

        int databaseSizeBeforeDelete = profileTypeRepository.findAll().size();

        // Delete the profileType
        restProfileTypeMockMvc.perform(delete("/api/profile-types/{id}", profileType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();
        assertThat(profileTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
