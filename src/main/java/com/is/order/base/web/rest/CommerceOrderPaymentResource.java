package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceOrderPaymentService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceOrderPaymentDTO;
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
 * REST controller for managing CommerceOrderPayment.
 */
@RestController
@RequestMapping("/api")
public class CommerceOrderPaymentResource {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPaymentResource.class);

    private static final String ENTITY_NAME = "commerceOrderPayment";

    private final CommerceOrderPaymentService commerceOrderPaymentService;

    public CommerceOrderPaymentResource(CommerceOrderPaymentService commerceOrderPaymentService) {
        this.commerceOrderPaymentService = commerceOrderPaymentService;
    }

    /**
     * POST  /commerce-order-payments : Create a new commerceOrderPayment.
     *
     * @param commerceOrderPaymentDTO the commerceOrderPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceOrderPaymentDTO, or with status 400 (Bad Request) if the commerceOrderPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-order-payments")
    @Timed
    public ResponseEntity<CommerceOrderPaymentDTO> createCommerceOrderPayment(@RequestBody CommerceOrderPaymentDTO commerceOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceOrderPayment : {}", commerceOrderPaymentDTO);
        if (commerceOrderPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceOrderPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceOrderPaymentDTO result = commerceOrderPaymentService.save(commerceOrderPaymentDTO);
        return ResponseEntity.created(new URI("/api/commerce-order-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-order-payments : Updates an existing commerceOrderPayment.
     *
     * @param commerceOrderPaymentDTO the commerceOrderPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceOrderPaymentDTO,
     * or with status 400 (Bad Request) if the commerceOrderPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceOrderPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-order-payments")
    @Timed
    public ResponseEntity<CommerceOrderPaymentDTO> updateCommerceOrderPayment(@RequestBody CommerceOrderPaymentDTO commerceOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceOrderPayment : {}", commerceOrderPaymentDTO);
        if (commerceOrderPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceOrderPaymentDTO result = commerceOrderPaymentService.save(commerceOrderPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceOrderPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-order-payments : get all the commerceOrderPayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceOrderPayments in body
     */
    @GetMapping("/commerce-order-payments")
    @Timed
    public List<CommerceOrderPaymentDTO> getAllCommerceOrderPayments() {
        log.debug("REST request to get all CommerceOrderPayments");
        return commerceOrderPaymentService.findAll();
    }

    /**
     * GET  /commerce-order-payments/:id : get the "id" commerceOrderPayment.
     *
     * @param id the id of the commerceOrderPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceOrderPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-order-payments/{id}")
    @Timed
    public ResponseEntity<CommerceOrderPaymentDTO> getCommerceOrderPayment(@PathVariable Long id) {
        log.debug("REST request to get CommerceOrderPayment : {}", id);
        Optional<CommerceOrderPaymentDTO> commerceOrderPaymentDTO = commerceOrderPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceOrderPaymentDTO);
    }

    /**
     * DELETE  /commerce-order-payments/:id : delete the "id" commerceOrderPayment.
     *
     * @param id the id of the commerceOrderPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-order-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceOrderPayment(@PathVariable Long id) {
        log.debug("REST request to delete CommerceOrderPayment : {}", id);
        commerceOrderPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
