package com.application.seatallotment.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.application.seatallotment.domain.Access;
import com.application.seatallotment.repository.AccessRepository;
import com.application.seatallotment.web.rest.errors.BadRequestAlertException;
import com.application.seatallotment.web.rest.util.HeaderUtil;
import com.application.seatallotment.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Access.
 */
@RestController
@RequestMapping("/api")
public class AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResource.class);

    private static final String ENTITY_NAME = "access";

    private final AccessRepository accessRepository;

    public AccessResource(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    /**
     * POST  /accesses : Create a new access.
     *
     * @param access the access to create
     * @return the ResponseEntity with status 201 (Created) and with body the new access, or with status 400 (Bad Request) if the access has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accesses")
    @Timed
    public ResponseEntity<Access> createAccess(@RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to save Access : {}", access);
        if (access.getId() != null) {
            throw new BadRequestAlertException("A new access cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Access result = accessRepository.save(access);
        return ResponseEntity.created(new URI("/api/accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accesses : Updates an existing access.
     *
     * @param access the access to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated access,
     * or with status 400 (Bad Request) if the access is not valid,
     * or with status 500 (Internal Server Error) if the access couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accesses")
    @Timed
    public ResponseEntity<Access> updateAccess(@RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to update Access : {}", access);
        if (access.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Access result = accessRepository.save(access);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, access.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accesses : get all the accesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accesses in body
     */
    @GetMapping("/accesses")
    @Timed
    public ResponseEntity<List<Access>> getAllAccesses(Pageable pageable) {
        log.debug("REST request to get a page of Accesses");
        Page<Access> page = accessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accesses/:id : get the "id" access.
     *
     * @param id the id of the access to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the access, or with status 404 (Not Found)
     */
    @GetMapping("/accesses/{id}")
    @Timed
    public ResponseEntity<Access> getAccess(@PathVariable String id) {
        log.debug("REST request to get Access : {}", id);
        Optional<Access> access = accessRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(access);
    }

    /**
     * DELETE  /accesses/:id : delete the "id" access.
     *
     * @param id the id of the access to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccess(@PathVariable String id) {
        log.debug("REST request to delete Access : {}", id);

        accessRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
