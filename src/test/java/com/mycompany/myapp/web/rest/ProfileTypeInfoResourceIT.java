package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KmptDemoApp;
import com.mycompany.myapp.domain.ProfileTypeInfo;
import com.mycompany.myapp.repository.ProfileTypeInfoRepository;
import com.mycompany.myapp.service.ProfileTypeInfoService;
import com.mycompany.myapp.service.dto.ProfileTypeInfoDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeInfoMapper;
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
 * Integration tests for the {@link ProfileTypeInfoResource} REST controller.
 */
@SpringBootTest(classes = KmptDemoApp.class)
public class ProfileTypeInfoResourceIT {

    private static final String DEFAULT_CHAPTER = "AAAAAAAAAA";
    private static final String UPDATED_CHAPTER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    private static final String DEFAULT_H_1 = "AAAAAAAAAA";
    private static final String UPDATED_H_1 = "BBBBBBBBBB";

    private static final String DEFAULT_H_2 = "AAAAAAAAAA";
    private static final String UPDATED_H_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ProfileTypeInfoRepository profileTypeInfoRepository;

    @Autowired
    private ProfileTypeInfoMapper profileTypeInfoMapper;

    @Autowired
    private ProfileTypeInfoService profileTypeInfoService;

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

    private MockMvc restProfileTypeInfoMockMvc;

    private ProfileTypeInfo profileTypeInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileTypeInfoResource profileTypeInfoResource = new ProfileTypeInfoResource(profileTypeInfoService);
        this.restProfileTypeInfoMockMvc = MockMvcBuilders.standaloneSetup(profileTypeInfoResource)
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
    public static ProfileTypeInfo createEntity(EntityManager em) {
        ProfileTypeInfo profileTypeInfo = new ProfileTypeInfo()
            .chapter(DEFAULT_CHAPTER)
            .rank(DEFAULT_RANK)
            .h1(DEFAULT_H_1)
            .h2(DEFAULT_H_2)
            .content(DEFAULT_CONTENT);
        return profileTypeInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileTypeInfo createUpdatedEntity(EntityManager em) {
        ProfileTypeInfo profileTypeInfo = new ProfileTypeInfo()
            .chapter(UPDATED_CHAPTER)
            .rank(UPDATED_RANK)
            .h1(UPDATED_H_1)
            .h2(UPDATED_H_2)
            .content(UPDATED_CONTENT);
        return profileTypeInfo;
    }

    @BeforeEach
    public void initTest() {
        profileTypeInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileTypeInfo() throws Exception {
        int databaseSizeBeforeCreate = profileTypeInfoRepository.findAll().size();

        // Create the ProfileTypeInfo
        ProfileTypeInfoDTO profileTypeInfoDTO = profileTypeInfoMapper.toDto(profileTypeInfo);
        restProfileTypeInfoMockMvc.perform(post("/api/profile-type-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileTypeInfo in the database
        List<ProfileTypeInfo> profileTypeInfoList = profileTypeInfoRepository.findAll();
        assertThat(profileTypeInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileTypeInfo testProfileTypeInfo = profileTypeInfoList.get(profileTypeInfoList.size() - 1);
        assertThat(testProfileTypeInfo.getChapter()).isEqualTo(DEFAULT_CHAPTER);
        assertThat(testProfileTypeInfo.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testProfileTypeInfo.geth1()).isEqualTo(DEFAULT_H_1);
        assertThat(testProfileTypeInfo.geth2()).isEqualTo(DEFAULT_H_2);
        assertThat(testProfileTypeInfo.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createProfileTypeInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileTypeInfoRepository.findAll().size();

        // Create the ProfileTypeInfo with an existing ID
        profileTypeInfo.setId(1L);
        ProfileTypeInfoDTO profileTypeInfoDTO = profileTypeInfoMapper.toDto(profileTypeInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileTypeInfoMockMvc.perform(post("/api/profile-type-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileTypeInfo in the database
        List<ProfileTypeInfo> profileTypeInfoList = profileTypeInfoRepository.findAll();
        assertThat(profileTypeInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfileTypeInfos() throws Exception {
        // Initialize the database
        profileTypeInfoRepository.saveAndFlush(profileTypeInfo);

        // Get all the profileTypeInfoList
        restProfileTypeInfoMockMvc.perform(get("/api/profile-type-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileTypeInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].chapter").value(hasItem(DEFAULT_CHAPTER)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].h1").value(hasItem(DEFAULT_H_1)))
            .andExpect(jsonPath("$.[*].h2").value(hasItem(DEFAULT_H_2)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getProfileTypeInfo() throws Exception {
        // Initialize the database
        profileTypeInfoRepository.saveAndFlush(profileTypeInfo);

        // Get the profileTypeInfo
        restProfileTypeInfoMockMvc.perform(get("/api/profile-type-infos/{id}", profileTypeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileTypeInfo.getId().intValue()))
            .andExpect(jsonPath("$.chapter").value(DEFAULT_CHAPTER))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.h1").value(DEFAULT_H_1))
            .andExpect(jsonPath("$.h2").value(DEFAULT_H_2))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingProfileTypeInfo() throws Exception {
        // Get the profileTypeInfo
        restProfileTypeInfoMockMvc.perform(get("/api/profile-type-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileTypeInfo() throws Exception {
        // Initialize the database
        profileTypeInfoRepository.saveAndFlush(profileTypeInfo);

        int databaseSizeBeforeUpdate = profileTypeInfoRepository.findAll().size();

        // Update the profileTypeInfo
        ProfileTypeInfo updatedProfileTypeInfo = profileTypeInfoRepository.findById(profileTypeInfo.getId()).get();
        // Disconnect from session so that the updates on updatedProfileTypeInfo are not directly saved in db
        em.detach(updatedProfileTypeInfo);
        updatedProfileTypeInfo
            .chapter(UPDATED_CHAPTER)
            .rank(UPDATED_RANK)
            .h1(UPDATED_H_1)
            .h2(UPDATED_H_2)
            .content(UPDATED_CONTENT);
        ProfileTypeInfoDTO profileTypeInfoDTO = profileTypeInfoMapper.toDto(updatedProfileTypeInfo);

        restProfileTypeInfoMockMvc.perform(put("/api/profile-type-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileTypeInfo in the database
        List<ProfileTypeInfo> profileTypeInfoList = profileTypeInfoRepository.findAll();
        assertThat(profileTypeInfoList).hasSize(databaseSizeBeforeUpdate);
        ProfileTypeInfo testProfileTypeInfo = profileTypeInfoList.get(profileTypeInfoList.size() - 1);
        assertThat(testProfileTypeInfo.getChapter()).isEqualTo(UPDATED_CHAPTER);
        assertThat(testProfileTypeInfo.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testProfileTypeInfo.geth1()).isEqualTo(UPDATED_H_1);
        assertThat(testProfileTypeInfo.geth2()).isEqualTo(UPDATED_H_2);
        assertThat(testProfileTypeInfo.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileTypeInfo() throws Exception {
        int databaseSizeBeforeUpdate = profileTypeInfoRepository.findAll().size();

        // Create the ProfileTypeInfo
        ProfileTypeInfoDTO profileTypeInfoDTO = profileTypeInfoMapper.toDto(profileTypeInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileTypeInfoMockMvc.perform(put("/api/profile-type-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileTypeInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileTypeInfo in the database
        List<ProfileTypeInfo> profileTypeInfoList = profileTypeInfoRepository.findAll();
        assertThat(profileTypeInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileTypeInfo() throws Exception {
        // Initialize the database
        profileTypeInfoRepository.saveAndFlush(profileTypeInfo);

        int databaseSizeBeforeDelete = profileTypeInfoRepository.findAll().size();

        // Delete the profileTypeInfo
        restProfileTypeInfoMockMvc.perform(delete("/api/profile-type-infos/{id}", profileTypeInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileTypeInfo> profileTypeInfoList = profileTypeInfoRepository.findAll();
        assertThat(profileTypeInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
