package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISEPayService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISEPayDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ISEPay.
 */
@RestController
@RequestMapping("/api")
public class ISEPayResource {

    private final Logger log = LoggerFactory.getLogger(ISEPayResource.class);

    private static final String ENTITY_NAME = "iSEPay";

    private final ISEPayService iSEPayService;

    public ISEPayResource(ISEPayService iSEPayService) {
        this.iSEPayService = iSEPayService;
    }

    /**
     * POST  /ise-pays : Create a new iSEPay.
     *
     * @param iSEPayDTO the iSEPayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSEPayDTO, or with status 400 (Bad Request) if the iSEPay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ise-pays")
    @Timed
    public ResponseEntity<ISEPayDTO> createISEPay(@RequestBody ISEPayDTO iSEPayDTO) throws URISyntaxException {
        log.debug("REST request to save ISEPay : {}", iSEPayDTO);
        if (iSEPayDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSEPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISEPayDTO result = iSEPayService.save(iSEPayDTO);
        return ResponseEntity.created(new URI("/api/ise-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ise-pays : Updates an existing iSEPay.
     *
     * @param iSEPayDTO the iSEPayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSEPayDTO,
     * or with status 400 (Bad Request) if the iSEPayDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSEPayDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ise-pays")
    @Timed
    public ResponseEntity<ISEPayDTO> updateISEPay(@RequestBody ISEPayDTO iSEPayDTO) throws URISyntaxException {
        log.debug("REST request to update ISEPay : {}", iSEPayDTO);
        if (iSEPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISEPayDTO result = iSEPayService.save(iSEPayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSEPayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ise-pays : get all the iSEPays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSEPays in body
     */
    @GetMapping("/ise-pays")
    @Timed
    public List<ISEPayDTO> getAllISEPays() {
        log.debug("REST request to get all ISEPays");
        return iSEPayService.findAll();
    }

    /**
     * GET  /ise-pays/:id : get the "id" iSEPay.
     *
     * @param id the id of the iSEPayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSEPayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ise-pays/{id}")
    @Timed
    public ResponseEntity<ISEPayDTO> getISEPay(@PathVariable Long id) {
        log.debug("REST request to get ISEPay : {}", id);
        Optional<ISEPayDTO> iSEPayDTO = iSEPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSEPayDTO);
    }

    /**
     * DELETE  /ise-pays/:id : delete the "id" iSEPay.
     *
     * @param id the id of the iSEPayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ise-pays/{id}")
    @Timed
    public ResponseEntity<Void> deleteISEPay(@PathVariable Long id) {
        log.debug("REST request to delete ISEPay : {}", id);
        iSEPayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
