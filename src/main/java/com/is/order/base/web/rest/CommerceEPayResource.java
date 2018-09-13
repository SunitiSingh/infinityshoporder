package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceEPayService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceEPayDTO;
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
 * REST controller for managing CommerceEPay.
 */
@RestController
@RequestMapping("/api")
public class CommerceEPayResource {

    private final Logger log = LoggerFactory.getLogger(CommerceEPayResource.class);

    private static final String ENTITY_NAME = "commerceEPay";

    private final CommerceEPayService commerceEPayService;

    public CommerceEPayResource(CommerceEPayService commerceEPayService) {
        this.commerceEPayService = commerceEPayService;
    }

    /**
     * POST  /commerce-e-pays : Create a new commerceEPay.
     *
     * @param commerceEPayDTO the commerceEPayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceEPayDTO, or with status 400 (Bad Request) if the commerceEPay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-e-pays")
    @Timed
    public ResponseEntity<CommerceEPayDTO> createCommerceEPay(@RequestBody CommerceEPayDTO commerceEPayDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceEPay : {}", commerceEPayDTO);
        if (commerceEPayDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceEPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceEPayDTO result = commerceEPayService.save(commerceEPayDTO);
        return ResponseEntity.created(new URI("/api/commerce-e-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-e-pays : Updates an existing commerceEPay.
     *
     * @param commerceEPayDTO the commerceEPayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceEPayDTO,
     * or with status 400 (Bad Request) if the commerceEPayDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceEPayDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-e-pays")
    @Timed
    public ResponseEntity<CommerceEPayDTO> updateCommerceEPay(@RequestBody CommerceEPayDTO commerceEPayDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceEPay : {}", commerceEPayDTO);
        if (commerceEPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceEPayDTO result = commerceEPayService.save(commerceEPayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceEPayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-e-pays : get all the commerceEPays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceEPays in body
     */
    @GetMapping("/commerce-e-pays")
    @Timed
    public List<CommerceEPayDTO> getAllCommerceEPays() {
        log.debug("REST request to get all CommerceEPays");
        return commerceEPayService.findAll();
    }

    /**
     * GET  /commerce-e-pays/:id : get the "id" commerceEPay.
     *
     * @param id the id of the commerceEPayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceEPayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-e-pays/{id}")
    @Timed
    public ResponseEntity<CommerceEPayDTO> getCommerceEPay(@PathVariable Long id) {
        log.debug("REST request to get CommerceEPay : {}", id);
        Optional<CommerceEPayDTO> commerceEPayDTO = commerceEPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceEPayDTO);
    }

    /**
     * DELETE  /commerce-e-pays/:id : delete the "id" commerceEPay.
     *
     * @param id the id of the commerceEPayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-e-pays/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceEPay(@PathVariable Long id) {
        log.debug("REST request to delete CommerceEPay : {}", id);
        commerceEPayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
