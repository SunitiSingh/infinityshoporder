package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISBillingAddressService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISBillingAddressDTO;
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
 * REST controller for managing ISBillingAddress.
 */
@RestController
@RequestMapping("/api")
public class ISBillingAddressResource {

    private final Logger log = LoggerFactory.getLogger(ISBillingAddressResource.class);

    private static final String ENTITY_NAME = "iSBillingAddress";

    private final ISBillingAddressService iSBillingAddressService;

    public ISBillingAddressResource(ISBillingAddressService iSBillingAddressService) {
        this.iSBillingAddressService = iSBillingAddressService;
    }

    /**
     * POST  /is-billing-addresses : Create a new iSBillingAddress.
     *
     * @param iSBillingAddressDTO the iSBillingAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSBillingAddressDTO, or with status 400 (Bad Request) if the iSBillingAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-billing-addresses")
    @Timed
    public ResponseEntity<ISBillingAddressDTO> createISBillingAddress(@RequestBody ISBillingAddressDTO iSBillingAddressDTO) throws URISyntaxException {
        log.debug("REST request to save ISBillingAddress : {}", iSBillingAddressDTO);
        if (iSBillingAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSBillingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISBillingAddressDTO result = iSBillingAddressService.save(iSBillingAddressDTO);
        return ResponseEntity.created(new URI("/api/is-billing-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-billing-addresses : Updates an existing iSBillingAddress.
     *
     * @param iSBillingAddressDTO the iSBillingAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSBillingAddressDTO,
     * or with status 400 (Bad Request) if the iSBillingAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSBillingAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-billing-addresses")
    @Timed
    public ResponseEntity<ISBillingAddressDTO> updateISBillingAddress(@RequestBody ISBillingAddressDTO iSBillingAddressDTO) throws URISyntaxException {
        log.debug("REST request to update ISBillingAddress : {}", iSBillingAddressDTO);
        if (iSBillingAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISBillingAddressDTO result = iSBillingAddressService.save(iSBillingAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSBillingAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-billing-addresses : get all the iSBillingAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSBillingAddresses in body
     */
    @GetMapping("/is-billing-addresses")
    @Timed
    public List<ISBillingAddressDTO> getAllISBillingAddresses() {
        log.debug("REST request to get all ISBillingAddresses");
        return iSBillingAddressService.findAll();
    }

    /**
     * GET  /is-billing-addresses/:id : get the "id" iSBillingAddress.
     *
     * @param id the id of the iSBillingAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSBillingAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-billing-addresses/{id}")
    @Timed
    public ResponseEntity<ISBillingAddressDTO> getISBillingAddress(@PathVariable Long id) {
        log.debug("REST request to get ISBillingAddress : {}", id);
        Optional<ISBillingAddressDTO> iSBillingAddressDTO = iSBillingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSBillingAddressDTO);
    }

    /**
     * DELETE  /is-billing-addresses/:id : delete the "id" iSBillingAddress.
     *
     * @param id the id of the iSBillingAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-billing-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteISBillingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ISBillingAddress : {}", id);
        iSBillingAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
