package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProfileType;
import com.mycompany.myapp.repository.ProfileTypeRepository;
import com.mycompany.myapp.service.dto.ProfileTypeDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfileType}.
 */
@Service
@Transactional
public class ProfileTypeService {

    private final Logger log = LoggerFactory.getLogger(ProfileTypeService.class);

    private final ProfileTypeRepository profileTypeRepository;

    private final ProfileTypeMapper profileTypeMapper;

    public ProfileTypeService(ProfileTypeRepository profileTypeRepository, ProfileTypeMapper profileTypeMapper) {
        this.profileTypeRepository = profileTypeRepository;
        this.profileTypeMapper = profileTypeMapper;
    }

    /**
     * Save a profileType.
     *
     * @param profileTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileTypeDTO save(ProfileTypeDTO profileTypeDTO) {
        log.debug("Request to save ProfileType : {}", profileTypeDTO);
        ProfileType profileType = profileTypeMapper.toEntity(profileTypeDTO);
        profileType = profileTypeRepository.save(profileType);
        return profileTypeMapper.toDto(profileType);
    }

    /**
     * Get all the profileTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileTypes");
        return profileTypeRepository.findAll(pageable)
            .map(profileTypeMapper::toDto);
    }


    /**
     * Get one profileType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileTypeDTO> findOne(Long id) {
        log.debug("Request to get ProfileType : {}", id);
        return profileTypeRepository.findById(id)
            .map(profileTypeMapper::toDto);
    }

    /**
     * Delete the profileType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProfileType : {}", id);
        profileTypeRepository.deleteById(id);
    }
}
