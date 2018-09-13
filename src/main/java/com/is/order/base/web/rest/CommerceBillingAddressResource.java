package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceBillingAddressService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceBillingAddressDTO;
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
 * REST controller for managing CommerceBillingAddress.
 */
@RestController
@RequestMapping("/api")
public class CommerceBillingAddressResource {

    private final Logger log = LoggerFactory.getLogger(CommerceBillingAddressResource.class);

    private static final String ENTITY_NAME = "commerceBillingAddress";

    private final CommerceBillingAddressService commerceBillingAddressService;

    public CommerceBillingAddressResource(CommerceBillingAddressService commerceBillingAddressService) {
        this.commerceBillingAddressService = commerceBillingAddressService;
    }

    /**
     * POST  /commerce-billing-addresses : Create a new commerceBillingAddress.
     *
     * @param commerceBillingAddressDTO the commerceBillingAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceBillingAddressDTO, or with status 400 (Bad Request) if the commerceBillingAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-billing-addresses")
    @Timed
    public ResponseEntity<CommerceBillingAddressDTO> createCommerceBillingAddress(@RequestBody CommerceBillingAddressDTO commerceBillingAddressDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceBillingAddress : {}", commerceBillingAddressDTO);
        if (commerceBillingAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceBillingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceBillingAddressDTO result = commerceBillingAddressService.save(commerceBillingAddressDTO);
        return ResponseEntity.created(new URI("/api/commerce-billing-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-billing-addresses : Updates an existing commerceBillingAddress.
     *
     * @param commerceBillingAddressDTO the commerceBillingAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceBillingAddressDTO,
     * or with status 400 (Bad Request) if the commerceBillingAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceBillingAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-billing-addresses")
    @Timed
    public ResponseEntity<CommerceBillingAddressDTO> updateCommerceBillingAddress(@RequestBody CommerceBillingAddressDTO commerceBillingAddressDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceBillingAddress : {}", commerceBillingAddressDTO);
        if (commerceBillingAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceBillingAddressDTO result = commerceBillingAddressService.save(commerceBillingAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceBillingAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-billing-addresses : get all the commerceBillingAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceBillingAddresses in body
     */
    @GetMapping("/commerce-billing-addresses")
    @Timed
    public List<CommerceBillingAddressDTO> getAllCommerceBillingAddresses() {
        log.debug("REST request to get all CommerceBillingAddresses");
        return commerceBillingAddressService.findAll();
    }

    /**
     * GET  /commerce-billing-addresses/:id : get the "id" commerceBillingAddress.
     *
     * @param id the id of the commerceBillingAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceBillingAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-billing-addresses/{id}")
    @Timed
    public ResponseEntity<CommerceBillingAddressDTO> getCommerceBillingAddress(@PathVariable Long id) {
        log.debug("REST request to get CommerceBillingAddress : {}", id);
        Optional<CommerceBillingAddressDTO> commerceBillingAddressDTO = commerceBillingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceBillingAddressDTO);
    }

    /**
     * DELETE  /commerce-billing-addresses/:id : delete the "id" commerceBillingAddress.
     *
     * @param id the id of the commerceBillingAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-billing-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceBillingAddress(@PathVariable Long id) {
        log.debug("REST request to delete CommerceBillingAddress : {}", id);
        commerceBillingAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
