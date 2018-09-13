package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISShipPriceNegotiationService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISShipPriceNegotiationDTO;
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
 * REST controller for managing ISShipPriceNegotiation.
 */
@RestController
@RequestMapping("/api")
public class ISShipPriceNegotiationResource {

    private final Logger log = LoggerFactory.getLogger(ISShipPriceNegotiationResource.class);

    private static final String ENTITY_NAME = "iSShipPriceNegotiation";

    private final ISShipPriceNegotiationService iSShipPriceNegotiationService;

    public ISShipPriceNegotiationResource(ISShipPriceNegotiationService iSShipPriceNegotiationService) {
        this.iSShipPriceNegotiationService = iSShipPriceNegotiationService;
    }

    /**
     * POST  /is-ship-price-negotiations : Create a new iSShipPriceNegotiation.
     *
     * @param iSShipPriceNegotiationDTO the iSShipPriceNegotiationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSShipPriceNegotiationDTO, or with status 400 (Bad Request) if the iSShipPriceNegotiation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-ship-price-negotiations")
    @Timed
    public ResponseEntity<ISShipPriceNegotiationDTO> createISShipPriceNegotiation(@Valid @RequestBody ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to save ISShipPriceNegotiation : {}", iSShipPriceNegotiationDTO);
        if (iSShipPriceNegotiationDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSShipPriceNegotiation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISShipPriceNegotiationDTO result = iSShipPriceNegotiationService.save(iSShipPriceNegotiationDTO);
        return ResponseEntity.created(new URI("/api/is-ship-price-negotiations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-ship-price-negotiations : Updates an existing iSShipPriceNegotiation.
     *
     * @param iSShipPriceNegotiationDTO the iSShipPriceNegotiationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSShipPriceNegotiationDTO,
     * or with status 400 (Bad Request) if the iSShipPriceNegotiationDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSShipPriceNegotiationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-ship-price-negotiations")
    @Timed
    public ResponseEntity<ISShipPriceNegotiationDTO> updateISShipPriceNegotiation(@Valid @RequestBody ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to update ISShipPriceNegotiation : {}", iSShipPriceNegotiationDTO);
        if (iSShipPriceNegotiationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISShipPriceNegotiationDTO result = iSShipPriceNegotiationService.save(iSShipPriceNegotiationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSShipPriceNegotiationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-ship-price-negotiations : get all the iSShipPriceNegotiations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSShipPriceNegotiations in body
     */
    @GetMapping("/is-ship-price-negotiations")
    @Timed
    public List<ISShipPriceNegotiationDTO> getAllISShipPriceNegotiations() {
        log.debug("REST request to get all ISShipPriceNegotiations");
        return iSShipPriceNegotiationService.findAll();
    }

    /**
     * GET  /is-ship-price-negotiations/:id : get the "id" iSShipPriceNegotiation.
     *
     * @param id the id of the iSShipPriceNegotiationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSShipPriceNegotiationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-ship-price-negotiations/{id}")
    @Timed
    public ResponseEntity<ISShipPriceNegotiationDTO> getISShipPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to get ISShipPriceNegotiation : {}", id);
        Optional<ISShipPriceNegotiationDTO> iSShipPriceNegotiationDTO = iSShipPriceNegotiationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSShipPriceNegotiationDTO);
    }

    /**
     * DELETE  /is-ship-price-negotiations/:id : delete the "id" iSShipPriceNegotiation.
     *
     * @param id the id of the iSShipPriceNegotiationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-ship-price-negotiations/{id}")
    @Timed
    public ResponseEntity<Void> deleteISShipPriceNegotiation(@PathVariable Long id) {
        log.debug("REST request to delete ISShipPriceNegotiation : {}", id);
        iSShipPriceNegotiationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
