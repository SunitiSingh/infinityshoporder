package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISOrderPaymentService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISOrderPaymentDTO;
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
 * REST controller for managing ISOrderPayment.
 */
@RestController
@RequestMapping("/api")
public class ISOrderPaymentResource {

    private final Logger log = LoggerFactory.getLogger(ISOrderPaymentResource.class);

    private static final String ENTITY_NAME = "iSOrderPayment";

    private final ISOrderPaymentService iSOrderPaymentService;

    public ISOrderPaymentResource(ISOrderPaymentService iSOrderPaymentService) {
        this.iSOrderPaymentService = iSOrderPaymentService;
    }

    /**
     * POST  /is-order-payments : Create a new iSOrderPayment.
     *
     * @param iSOrderPaymentDTO the iSOrderPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSOrderPaymentDTO, or with status 400 (Bad Request) if the iSOrderPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-order-payments")
    @Timed
    public ResponseEntity<ISOrderPaymentDTO> createISOrderPayment(@RequestBody ISOrderPaymentDTO iSOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save ISOrderPayment : {}", iSOrderPaymentDTO);
        if (iSOrderPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSOrderPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISOrderPaymentDTO result = iSOrderPaymentService.save(iSOrderPaymentDTO);
        return ResponseEntity.created(new URI("/api/is-order-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-order-payments : Updates an existing iSOrderPayment.
     *
     * @param iSOrderPaymentDTO the iSOrderPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSOrderPaymentDTO,
     * or with status 400 (Bad Request) if the iSOrderPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSOrderPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-order-payments")
    @Timed
    public ResponseEntity<ISOrderPaymentDTO> updateISOrderPayment(@RequestBody ISOrderPaymentDTO iSOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update ISOrderPayment : {}", iSOrderPaymentDTO);
        if (iSOrderPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISOrderPaymentDTO result = iSOrderPaymentService.save(iSOrderPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSOrderPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-order-payments : get all the iSOrderPayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSOrderPayments in body
     */
    @GetMapping("/is-order-payments")
    @Timed
    public List<ISOrderPaymentDTO> getAllISOrderPayments() {
        log.debug("REST request to get all ISOrderPayments");
        return iSOrderPaymentService.findAll();
    }

    /**
     * GET  /is-order-payments/:id : get the "id" iSOrderPayment.
     *
     * @param id the id of the iSOrderPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSOrderPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-order-payments/{id}")
    @Timed
    public ResponseEntity<ISOrderPaymentDTO> getISOrderPayment(@PathVariable Long id) {
        log.debug("REST request to get ISOrderPayment : {}", id);
        Optional<ISOrderPaymentDTO> iSOrderPaymentDTO = iSOrderPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSOrderPaymentDTO);
    }

    /**
     * DELETE  /is-order-payments/:id : delete the "id" iSOrderPayment.
     *
     * @param id the id of the iSOrderPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-order-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteISOrderPayment(@PathVariable Long id) {
        log.debug("REST request to delete ISOrderPayment : {}", id);
        iSOrderPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
