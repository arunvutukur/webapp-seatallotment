package com.application.seatallotment.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.application.seatallotment.domain.Seatalloted;
import com.application.seatallotment.repository.SeatallotedRepository;
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
 * REST controller for managing Seatalloted.
 */
@RestController
@RequestMapping("/api")
public class SeatallotedResource {

    private final Logger log = LoggerFactory.getLogger(SeatallotedResource.class);

    private static final String ENTITY_NAME = "seatalloted";

    private final SeatallotedRepository seatallotedRepository;

    public SeatallotedResource(SeatallotedRepository seatallotedRepository) {
        this.seatallotedRepository = seatallotedRepository;
    }

    /**
     * POST  /seatalloteds : Create a new seatalloted.
     *
     * @param seatalloted the seatalloted to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seatalloted, or with status 400 (Bad Request) if the seatalloted has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seatalloteds")
    @Timed
    public ResponseEntity<Seatalloted> createSeatalloted(@RequestBody Seatalloted seatalloted) throws URISyntaxException {
        log.debug("REST request to save Seatalloted : {}", seatalloted);
        if (seatalloted.getId() != null) {
            throw new BadRequestAlertException("A new seatalloted cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seatalloted result = seatallotedRepository.save(seatalloted);
        return ResponseEntity.created(new URI("/api/seatalloteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seatalloteds : Updates an existing seatalloted.
     *
     * @param seatalloted the seatalloted to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seatalloted,
     * or with status 400 (Bad Request) if the seatalloted is not valid,
     * or with status 500 (Internal Server Error) if the seatalloted couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seatalloteds")
    @Timed
    public ResponseEntity<Seatalloted> updateSeatalloted(@RequestBody Seatalloted seatalloted) throws URISyntaxException {
        log.debug("REST request to update Seatalloted : {}", seatalloted);
        if (seatalloted.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Seatalloted result = seatallotedRepository.save(seatalloted);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seatalloted.getId().toString()))
            .body(result);
    }

    @PutMapping("/seatalloteds/request")
    @Timed
    public ResponseEntity<Seatalloted> updateSeatalloted(@RequestBody Seatalloted seatalloted,boolean requestForApproval)
            throws URISyntaxException {
        log.debug("REST request to update Seatalloted : {}", seatalloted);
        if (seatalloted.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Seatalloted> seatallotedOptional=seatallotedRepository.findById(seatalloted.getId());
        Seatalloted seatallotedEntity=seatallotedOptional.get();
        seatallotedEntity.setRequestForApproval(requestForApproval);
        seatallotedEntity.setPendingForApproval(!requestForApproval);
        Seatalloted result = seatallotedRepository.save(seatallotedEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }
    /**
     * GET  /seatalloteds : get all the seatalloteds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seatalloteds in body
     */
    @GetMapping("/seatalloteds")
    @Timed
    public ResponseEntity<List<Seatalloted>> getAllSeatalloteds(Pageable pageable) {
        log.debug("REST request to get a page of Seatalloteds");
        Page<Seatalloted> page = seatallotedRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seatalloteds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seatalloteds/:id : get the "id" seatalloted.
     *
     * @param id the id of the seatalloted to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seatalloted, or with status 404 (Not Found)
     */
    @GetMapping("/seatalloteds/{id}")
    @Timed
    public ResponseEntity<Seatalloted> getSeatalloted(@PathVariable String id) {
        log.debug("REST request to get Seatalloted : {}", id);
        Optional<Seatalloted> seatalloted = seatallotedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seatalloted);
    }

    /**
     * DELETE  /seatalloteds/:id : delete the "id" seatalloted.
     *
     * @param id the id of the seatalloted to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seatalloteds/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeatalloted(@PathVariable String id) {
        log.debug("REST request to delete Seatalloted : {}", id);

        seatallotedRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
