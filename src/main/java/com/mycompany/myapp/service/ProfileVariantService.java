package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProfileVariant;
import com.mycompany.myapp.repository.ProfileVariantRepository;
import com.mycompany.myapp.service.dto.ProfileVariantDTO;
import com.mycompany.myapp.service.mapper.ProfileVariantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfileVariant}.
 */
@Service
@Transactional
public class ProfileVariantService {

    private final Logger log = LoggerFactory.getLogger(ProfileVariantService.class);

    private final ProfileVariantRepository profileVariantRepository;

    private final ProfileVariantMapper profileVariantMapper;

    public ProfileVariantService(ProfileVariantRepository profileVariantRepository, ProfileVariantMapper profileVariantMapper) {
        this.profileVariantRepository = profileVariantRepository;
        this.profileVariantMapper = profileVariantMapper;
    }

    /**
     * Save a profileVariant.
     *
     * @param profileVariantDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileVariantDTO save(ProfileVariantDTO profileVariantDTO) {
        log.debug("Request to save ProfileVariant : {}", profileVariantDTO);
        ProfileVariant profileVariant = profileVariantMapper.toEntity(profileVariantDTO);
        profileVariant = profileVariantRepository.save(profileVariant);
        return profileVariantMapper.toDto(profileVariant);
    }

    /**
     * Get all the profileVariants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileVariantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileVariants");
        return profileVariantRepository.findAll(pageable)
            .map(profileVariantMapper::toDto);
    }


    /**
     * Get one profileVariant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileVariantDTO> findOne(Long id) {
        log.debug("Request to get ProfileVariant : {}", id);
        return profileVariantRepository.findById(id)
            .map(profileVariantMapper::toDto);
    }

    /**
     * Delete the profileVariant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProfileVariant : {}", id);
        profileVariantRepository.deleteById(id);
    }
}
