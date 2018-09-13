package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISOrderPriceNegotiationService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISOrderPriceNegotiationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ISOrderPriceNegotiation.
 */
@RestController
@RequestMapping("/api")
public class ISOrderPriceNegotiationResource {

    private final Logger log = LoggerFactory.getLogger(ISOrderPriceNegotiationResource.class);

    private static final String ENTITY_NAME = "iSOrderPriceNegotiation";

    private final ISOrderPriceNegotiationService iSOrderPriceNegotiationService;

    public ISOrderPriceNegotiationResource(ISOrderPriceNegotiationService iSOrderPriceNegotiationService) {
        this.iSOrderPriceNegotiationService = iSOrderPriceNegotiationService;
    }

    /**
     * POST  /is-order-price-negotiations : Create a new iSOrderPriceNegotiation.
     *
     * @param iSOrderPriceNegotiationDTO the iSOrderPriceNegotiationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSOrderPriceNegotiationDTO, or with status 400 (Bad Request) if the iSOrderPriceNegotiation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-order-price-negotiations")
    @Timed
    public ResponseEntity<ISOrderPriceNegotiationDTO> createISOrderPriceNegotiation(@Valid @RequestBody ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to save ISOrderPriceNegotiation : {}", iSOrderPriceNegotiationDTO);
        if (iSOrderPriceNegotiationDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSOrderPriceNegotiation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISOrderPriceNegotiationDTO result = iSOrderPriceNegotiationService.save(iSOrderPriceNegotiationDTO);
        return ResponseEntity.created(new URI("/api/is-order-price-negotiations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-order-price-negotiations : Updates an existing iSOrderPriceNegotiation.
     *
     * @param iSOrderPriceNegotiationDTO the iSOrderPriceNegotiationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSOrderPriceNegotiationDTO,
     * or with status 400 (Bad Request) if the iSOrderPriceNegotiationDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSOrderPriceNegotiationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-order-price-negotiations")
    @Timed
    public ResponseEntity<ISOrderPriceNegotiationDTO> updateISOrderPriceNegotiation(@Valid @RequestBody ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to update ISOrderPriceNegotiation : {}", iSOrderPriceNegotiationDTO);
        if (iSOrderPriceNegotiationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISOrderPriceNegotiationDTO result = iSOrderPriceNegotiationService.save(iSOrderPriceNegotiationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSOrderPriceNegotiationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-order-price-negotiations : get all the iSOrderPriceNegotiations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSOrderPriceNegotiations in body
     */
    @GetMapping("/is-order-price-negotiations")
    @Timed
    public List<ISOrderPriceNegotiationDTO> getAllISOrderPriceNegotiations() {
        log.debug("REST request to get all ISOrderPriceNegotiations");
        return iSOrderPriceNegotiationService.findAll();
    }

    /**
     * GET  /is-order-price-negotiations/:id : get the "id" iSOrderPriceNegotiation.
     *
     * @param id the id of the iSOrderPriceNegotiationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSOrderPriceNegotiationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-order-price-negotiations/{id}")
    @Timed
    public ResponseEntity<ISOrderPriceNegotiationDTO> getISOrderPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to get ISOrderPriceNegotiation : {}", id);
        Optional<ISOrderPriceNegotiationDTO> iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSOrderPriceNegotiationDTO);
    }

    /**
     * DELETE  /is-order-price-negotiations/:id : delete the "id" iSOrderPriceNegotiation.
     *
     * @param id the id of the iSOrderPriceNegotiationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-order-price-negotiations/{id}")
    @Timed
    public ResponseEntity<Void> deleteISOrderPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to delete ISOrderPriceNegotiation : {}", id);
        iSOrderPriceNegotiationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
