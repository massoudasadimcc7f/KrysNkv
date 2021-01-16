package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.Profile;
import com.mycompany.myapp.repository.ProfileRepository;
import com.mycompany.myapp.service.ProfileService;
import com.mycompany.myapp.service.dto.ProfileDTO;
import com.mycompany.myapp.service.mapper.ProfileMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ProfileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ME = false;
    private static final Boolean UPDATED_ME = true;

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Autowired
    private ProfileMapper profileMapper;

    @Mock
    private ProfileService profileServiceMock;

    @Autowired
    private ProfileService profileService;

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

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .name(DEFAULT_NAME)
            .jobTitle(DEFAULT_JOB_TITLE)
            .notes(DEFAULT_NOTES)
            .me(DEFAULT_ME)
            .relation(DEFAULT_RELATION);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .name(UPDATED_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .notes(UPDATED_NOTES)
            .me(UPDATED_ME)
            .relation(UPDATED_RELATION);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfile.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testProfile.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testProfile.isMe()).isEqualTo(DEFAULT_ME);
        assertThat(testProfile.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].me").value(hasItem(DEFAULT_ME.booleanValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        ProfileResource profileResource = new ProfileResource(profileServiceMock);
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfileMockMvc.perform(get("/api/profiles?eagerload=true"))
        .andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProfileResource profileResource = new ProfileResource(profileServiceMock);
            when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfileMockMvc.perform(get("/api/profiles?eagerload=true"))
        .andExpect(status().isOk());

            verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.me").value(DEFAULT_ME.booleanValue()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .name(UPDATED_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .notes(UPDATED_NOTES)
            .me(UPDATED_ME)
            .relation(UPDATED_RELATION);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testProfile.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testProfile.isMe()).isEqualTo(UPDATED_ME);
        assertThat(testProfile.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
