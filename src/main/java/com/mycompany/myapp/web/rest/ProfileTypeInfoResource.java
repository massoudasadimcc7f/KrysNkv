package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ProfileTypeInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ProfileTypeInfoDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProfileTypeInfo}.
 */
@RestController
@RequestMapping("/api")
public class ProfileTypeInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProfileTypeInfoResource.class);

    private static final String ENTITY_NAME = "profileTypeInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileTypeInfoService profileTypeInfoService;

    public ProfileTypeInfoResource(ProfileTypeInfoService profileTypeInfoService) {
        this.profileTypeInfoService = profileTypeInfoService;
    }

    /**
     * {@code POST  /profile-type-infos} : Create a new profileTypeInfo.
     *
     * @param profileTypeInfoDTO the profileTypeInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileTypeInfoDTO, or with status {@code 400 (Bad Request)} if the profileTypeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile-type-infos")
    public ResponseEntity<ProfileTypeInfoDTO> createProfileTypeInfo(@RequestBody ProfileTypeInfoDTO profileTypeInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileTypeInfo : {}", profileTypeInfoDTO);
        if (profileTypeInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileTypeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileTypeInfoDTO result = profileTypeInfoService.save(profileTypeInfoDTO);
        return ResponseEntity.created(new URI("/api/profile-type-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-type-infos} : Updates an existing profileTypeInfo.
     *
     * @param profileTypeInfoDTO the profileTypeInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileTypeInfoDTO,
     * or with status {@code 400 (Bad Request)} if the profileTypeInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileTypeInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile-type-infos")
    public ResponseEntity<ProfileTypeInfoDTO> updateProfileTypeInfo(@RequestBody ProfileTypeInfoDTO profileTypeInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileTypeInfo : {}", profileTypeInfoDTO);
        if (profileTypeInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileTypeInfoDTO result = profileTypeInfoService.save(profileTypeInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileTypeInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profile-type-infos} : get all the profileTypeInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileTypeInfos in body.
     */
    @GetMapping("/profile-type-infos")
    public ResponseEntity<List<ProfileTypeInfoDTO>> getAllProfileTypeInfos(Pageable pageable) {
        log.debug("REST request to get a page of ProfileTypeInfos");
        Page<ProfileTypeInfoDTO> page = profileTypeInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profile-type-infos/:id} : get the "id" profileTypeInfo.
     *
     * @param id the id of the profileTypeInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileTypeInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile-type-infos/{id}")
    public ResponseEntity<ProfileTypeInfoDTO> getProfileTypeInfo(@PathVariable Long id) {
        log.debug("REST request to get ProfileTypeInfo : {}", id);
        Optional<ProfileTypeInfoDTO> profileTypeInfoDTO = profileTypeInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileTypeInfoDTO);
    }

    /**
     * {@code DELETE  /profile-type-infos/:id} : delete the "id" profileTypeInfo.
     *
     * @param id the id of the profileTypeInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile-type-infos/{id}")
    public ResponseEntity<Void> deleteProfileTypeInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProfileTypeInfo : {}", id);
        profileTypeInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
