package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProfileTypeRating;
import com.mycompany.myapp.repository.ProfileTypeRatingRepository;
import com.mycompany.myapp.service.dto.ProfileTypeRatingDTO;
import com.mycompany.myapp.service.mapper.ProfileTypeRatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfileTypeRating}.
 */
@Service
@Transactional
public class ProfileTypeRatingService {

    private final Logger log = LoggerFactory.getLogger(ProfileTypeRatingService.class);

    private final ProfileTypeRatingRepository profileTypeRatingRepository;

    private final ProfileTypeRatingMapper profileTypeRatingMapper;

    public ProfileTypeRatingService(ProfileTypeRatingRepository profileTypeRatingRepository, ProfileTypeRatingMapper profileTypeRatingMapper) {
        this.profileTypeRatingRepository = profileTypeRatingRepository;
        this.profileTypeRatingMapper = profileTypeRatingMapper;
    }

    /**
     * Save a profileTypeRating.
     *
     * @param profileTypeRatingDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileTypeRatingDTO save(ProfileTypeRatingDTO profileTypeRatingDTO) {
        log.debug("Request to save ProfileTypeRating : {}", profileTypeRatingDTO);
        ProfileTypeRating profileTypeRating = profileTypeRatingMapper.toEntity(profileTypeRatingDTO);
        profileTypeRating = profileTypeRatingRepository.save(profileTypeRating);
        return profileTypeRatingMapper.toDto(profileTypeRating);
    }

    /**
     * Get all the profileTypeRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileTypeRatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileTypeRatings");
        return profileTypeRatingRepository.findAll(pageable)
            .map(profileTypeRatingMapper::toDto);
    }


    /**
     * Get one profileTypeRating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileTypeRatingDTO> findOne(Long id) {
        log.debug("Request to get ProfileTypeRating : {}", id);
        return profileTypeRatingRepository.findById(id)
            .map(profileTypeRatingMapper::toDto);
    }

    /**
     * Delete the profileTypeRating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProfileTypeRating : {}", id);
        profileTypeRatingRepository.deleteById(id);
    }
}
