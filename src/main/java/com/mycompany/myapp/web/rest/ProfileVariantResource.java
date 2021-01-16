package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ProfileVariantService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ProfileVariantDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProfileVariant}.
 */
@RestController
@RequestMapping("/api")
public class ProfileVariantResource {

    private final Logger log = LoggerFactory.getLogger(ProfileVariantResource.class);

    private static final String ENTITY_NAME = "profileVariant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileVariantService profileVariantService;

    public ProfileVariantResource(ProfileVariantService profileVariantService) {
        this.profileVariantService = profileVariantService;
    }

    /**
     * {@code POST  /profile-variants} : Create a new profileVariant.
     *
     * @param profileVariantDTO the profileVariantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileVariantDTO, or with status {@code 400 (Bad Request)} if the profileVariant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile-variants")
    public ResponseEntity<ProfileVariantDTO> createProfileVariant(@RequestBody ProfileVariantDTO profileVariantDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileVariant : {}", profileVariantDTO);
        if (profileVariantDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileVariant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileVariantDTO result = profileVariantService.save(profileVariantDTO);
        return ResponseEntity.created(new URI("/api/profile-variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-variants} : Updates an existing profileVariant.
     *
     * @param profileVariantDTO the profileVariantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileVariantDTO,
     * or with status {@code 400 (Bad Request)} if the profileVariantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileVariantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile-variants")
    public ResponseEntity<ProfileVariantDTO> updateProfileVariant(@RequestBody ProfileVariantDTO profileVariantDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileVariant : {}", profileVariantDTO);
        if (profileVariantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileVariantDTO result = profileVariantService.save(profileVariantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileVariantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profile-variants} : get all the profileVariants.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileVariants in body.
     */
    @GetMapping("/profile-variants")
    public ResponseEntity<List<ProfileVariantDTO>> getAllProfileVariants(Pageable pageable) {
        log.debug("REST request to get a page of ProfileVariants");
        Page<ProfileVariantDTO> page = profileVariantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profile-variants/:id} : get the "id" profileVariant.
     *
     * @param id the id of the profileVariantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileVariantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile-variants/{id}")
    public ResponseEntity<ProfileVariantDTO> getProfileVariant(@PathVariable Long id) {
        log.debug("REST request to get ProfileVariant : {}", id);
        Optional<ProfileVariantDTO> profileVariantDTO = profileVariantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileVariantDTO);
    }

    /**
     * {@code DELETE  /profile-variants/:id} : delete the "id" profileVariant.
     *
     * @param id the id of the profileVariantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile-variants/{id}")
    public ResponseEntity<Void> deleteProfileVariant(@PathVariable Long id) {
        log.debug("REST request to delete ProfileVariant : {}", id);
        profileVariantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
