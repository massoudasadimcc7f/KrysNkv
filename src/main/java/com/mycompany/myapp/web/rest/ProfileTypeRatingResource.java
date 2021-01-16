package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ProfileTypeRatingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ProfileTypeRatingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ProfileTypeRating}.
 */
@RestController
@RequestMapping("/api")
public class ProfileTypeRatingResource {

    private final Logger log = LoggerFactory.getLogger(ProfileTypeRatingResource.class);

    private static final String ENTITY_NAME = "profileTypeRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileTypeRatingService profileTypeRatingService;

    public ProfileTypeRatingResource(ProfileTypeRatingService profileTypeRatingService) {
        this.profileTypeRatingService = profileTypeRatingService;
    }

    /**
     * {@code POST  /profile-type-ratings} : Create a new profileTypeRating.
     *
     * @param profileTypeRatingDTO the profileTypeRatingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileTypeRatingDTO, or with status {@code 400 (Bad Request)} if the profileTypeRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile-type-ratings")
    public ResponseEntity<ProfileTypeRatingDTO> createProfileTypeRating(@RequestBody ProfileTypeRatingDTO profileTypeRatingDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileTypeRating : {}", profileTypeRatingDTO);
        if (profileTypeRatingDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileTypeRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileTypeRatingDTO result = profileTypeRatingService.save(profileTypeRatingDTO);
        return ResponseEntity.created(new URI("/api/profile-type-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-type-ratings} : Updates an existing profileTypeRating.
     *
     * @param profileTypeRatingDTO the profileTypeRatingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileTypeRatingDTO,
     * or with status {@code 400 (Bad Request)} if the profileTypeRatingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileTypeRatingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile-type-ratings")
    public ResponseEntity<ProfileTypeRatingDTO> updateProfileTypeRating(@RequestBody ProfileTypeRatingDTO profileTypeRatingDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileTypeRating : {}", profileTypeRatingDTO);
        if (profileTypeRatingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileTypeRatingDTO result = profileTypeRatingService.save(profileTypeRatingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileTypeRatingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profile-type-ratings} : get all the profileTypeRatings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileTypeRatings in body.
     */
    @GetMapping("/profile-type-ratings")
    public ResponseEntity<List<ProfileTypeRatingDTO>> getAllProfileTypeRatings(Pageable pageable) {
        log.debug("REST request to get a page of ProfileTypeRatings");
        Page<ProfileTypeRatingDTO> page = profileTypeRatingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profile-type-ratings/:id} : get the "id" profileTypeRating.
     *
     * @param id the id of the profileTypeRatingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileTypeRatingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile-type-ratings/{id}")
    public ResponseEntity<ProfileTypeRatingDTO> getProfileTypeRating(@PathVariable Long id) {
        log.debug("REST request to get ProfileTypeRating : {}", id);
        Optional<ProfileTypeRatingDTO> profileTypeRatingDTO = profileTypeRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileTypeRatingDTO);
    }

    /**
     * {@code DELETE  /profile-type-ratings/:id} : delete the "id" profileTypeRating.
     *
     * @param id the id of the profileTypeRatingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile-type-ratings/{id}")
    public ResponseEntity<Void> deleteProfileTypeRating(@PathVariable Long id) {
        log.debug("REST request to delete ProfileTypeRating : {}", id);
        profileTypeRatingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
