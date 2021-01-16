package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProfileTypeInfo;
import com.mycompany.myapp.repository.ProfileTypeInfoRepository;
import com.mycompany.myapp.service.dto.ProfileTypeInfoDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfileTypeInfo}.
 */
@Service
@Transactional
public class ProfileTypeInfoService {

    private final Logger log = LoggerFactory.getLogger(ProfileTypeInfoService.class);

    private final ProfileTypeInfoRepository profileTypeInfoRepository;

    private final ProfileTypeInfoMapper profileTypeInfoMapper;

    public ProfileTypeInfoService(ProfileTypeInfoRepository profileTypeInfoRepository, ProfileTypeInfoMapper profileTypeInfoMapper) {
        this.profileTypeInfoRepository = profileTypeInfoRepository;
        this.profileTypeInfoMapper = profileTypeInfoMapper;
    }

    /**
     * Save a profileTypeInfo.
     *
     * @param profileTypeInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileTypeInfoDTO save(ProfileTypeInfoDTO profileTypeInfoDTO) {
        log.debug("Request to save ProfileTypeInfo : {}", profileTypeInfoDTO);
        ProfileTypeInfo profileTypeInfo = profileTypeInfoMapper.toEntity(profileTypeInfoDTO);
        profileTypeInfo = profileTypeInfoRepository.save(profileTypeInfo);
        return profileTypeInfoMapper.toDto(profileTypeInfo);
    }

    /**
     * Get all the profileTypeInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileTypeInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileTypeInfos");
        return profileTypeInfoRepository.findAll(pageable)
            .map(profileTypeInfoMapper::toDto);
    }


    /**
     * Get one profileTypeInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileTypeInfoDTO> findOne(Long id) {
        log.debug("Request to get ProfileTypeInfo : {}", id);
        return profileTypeInfoRepository.findById(id)
            .map(profileTypeInfoMapper::toDto);
    }

    /**
     * Delete the profileTypeInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProfileTypeInfo : {}", id);
        profileTypeInfoRepository.deleteById(id);
    }
}
