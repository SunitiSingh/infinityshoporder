package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISItemPriceNegotiationService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISItemPriceNegotiationDTO;
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
 * REST controller for managing ISItemPriceNegotiation.
 */
@RestController
@RequestMapping("/api")
public class ISItemPriceNegotiationResource {

    private final Logger log = LoggerFactory.getLogger(ISItemPriceNegotiationResource.class);

    private static final String ENTITY_NAME = "iSItemPriceNegotiation";

    private final ISItemPriceNegotiationService iSItemPriceNegotiationService;

    public ISItemPriceNegotiationResource(ISItemPriceNegotiationService iSItemPriceNegotiationService) {
        this.iSItemPriceNegotiationService = iSItemPriceNegotiationService;
    }

    /**
     * POST  /is-item-price-negotiations : Create a new iSItemPriceNegotiation.
     *
     * @param iSItemPriceNegotiationDTO the iSItemPriceNegotiationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSItemPriceNegotiationDTO, or with status 400 (Bad Request) if the iSItemPriceNegotiation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-item-price-negotiations")
    @Timed
    public ResponseEntity<ISItemPriceNegotiationDTO> createISItemPriceNegotiation(@Valid @RequestBody ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to save ISItemPriceNegotiation : {}", iSItemPriceNegotiationDTO);
        if (iSItemPriceNegotiationDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSItemPriceNegotiation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISItemPriceNegotiationDTO result = iSItemPriceNegotiationService.save(iSItemPriceNegotiationDTO);
        return ResponseEntity.created(new URI("/api/is-item-price-negotiations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-item-price-negotiations : Updates an existing iSItemPriceNegotiation.
     *
     * @param iSItemPriceNegotiationDTO the iSItemPriceNegotiationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSItemPriceNegotiationDTO,
     * or with status 400 (Bad Request) if the iSItemPriceNegotiationDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSItemPriceNegotiationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-item-price-negotiations")
    @Timed
    public ResponseEntity<ISItemPriceNegotiationDTO> updateISItemPriceNegotiation(@Valid @RequestBody ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to update ISItemPriceNegotiation : {}", iSItemPriceNegotiationDTO);
        if (iSItemPriceNegotiationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISItemPriceNegotiationDTO result = iSItemPriceNegotiationService.save(iSItemPriceNegotiationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSItemPriceNegotiationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-item-price-negotiations : get all the iSItemPriceNegotiations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSItemPriceNegotiations in body
     */
    @GetMapping("/is-item-price-negotiations")
    @Timed
    public List<ISItemPriceNegotiationDTO> getAllISItemPriceNegotiations() {
        log.debug("REST request to get all ISItemPriceNegotiations");
        return iSItemPriceNegotiationService.findAll();
    }

    /**
     * GET  /is-item-price-negotiations/:id : get the "id" iSItemPriceNegotiation.
     *
     * @param id the id of the iSItemPriceNegotiationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSItemPriceNegotiationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-item-price-negotiations/{id}")
    @Timed
    public ResponseEntity<ISItemPriceNegotiationDTO> getISItemPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to get ISItemPriceNegotiation : {}", id);
        Optional<ISItemPriceNegotiationDTO> iSItemPriceNegotiationDTO = iSItemPriceNegotiationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSItemPriceNegotiationDTO);
    }

    /**
     * DELETE  /is-item-price-negotiations/:id : delete the "id" iSItemPriceNegotiation.
     *
     * @param id the id of the iSItemPriceNegotiationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-item-price-negotiations/{id}")
    @Timed
    public ResponseEntity<Void> deleteISItemPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to delete ISItemPriceNegotiation : {}", id);
        iSItemPriceNegotiationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
