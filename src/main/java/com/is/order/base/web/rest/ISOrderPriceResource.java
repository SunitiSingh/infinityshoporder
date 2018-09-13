package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISOrderPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISOrderPriceDTO;
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
 * REST controller for managing ISOrderPrice.
 */
@RestController
@RequestMapping("/api")
public class ISOrderPriceResource {

    private final Logger log = LoggerFactory.getLogger(ISOrderPriceResource.class);

    private static final String ENTITY_NAME = "iSOrderPrice";

    private final ISOrderPriceService iSOrderPriceService;

    public ISOrderPriceResource(ISOrderPriceService iSOrderPriceService) {
        this.iSOrderPriceService = iSOrderPriceService;
    }

    /**
     * POST  /is-order-prices : Create a new iSOrderPrice.
     *
     * @param iSOrderPriceDTO the iSOrderPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSOrderPriceDTO, or with status 400 (Bad Request) if the iSOrderPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-order-prices")
    @Timed
    public ResponseEntity<ISOrderPriceDTO> createISOrderPrice(@Valid @RequestBody ISOrderPriceDTO iSOrderPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ISOrderPrice : {}", iSOrderPriceDTO);
        if (iSOrderPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSOrderPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISOrderPriceDTO result = iSOrderPriceService.save(iSOrderPriceDTO);
        return ResponseEntity.created(new URI("/api/is-order-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-order-prices : Updates an existing iSOrderPrice.
     *
     * @param iSOrderPriceDTO the iSOrderPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSOrderPriceDTO,
     * or with status 400 (Bad Request) if the iSOrderPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSOrderPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-order-prices")
    @Timed
    public ResponseEntity<ISOrderPriceDTO> updateISOrderPrice(@Valid @RequestBody ISOrderPriceDTO iSOrderPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ISOrderPrice : {}", iSOrderPriceDTO);
        if (iSOrderPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISOrderPriceDTO result = iSOrderPriceService.save(iSOrderPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSOrderPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-order-prices : get all the iSOrderPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSOrderPrices in body
     */
    @GetMapping("/is-order-prices")
    @Timed
    public List<ISOrderPriceDTO> getAllISOrderPrices() {
        log.debug("REST request to get all ISOrderPrices");
        return iSOrderPriceService.findAll();
    }

    /**
     * GET  /is-order-prices/:id : get the "id" iSOrderPrice.
     *
     * @param id the id of the iSOrderPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSOrderPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-order-prices/{id}")
    @Timed
    public ResponseEntity<ISOrderPriceDTO> getISOrderPrice(@PathVariable Long id) {
        log.debug("REST request to get ISOrderPrice : {}", id);
        Optional<ISOrderPriceDTO> iSOrderPriceDTO = iSOrderPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSOrderPriceDTO);
    }

    /**
     * DELETE  /is-order-prices/:id : delete the "id" iSOrderPrice.
     *
     * @param id the id of the iSOrderPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-order-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteISOrderPrice(@PathVariable Long id) {
        log.debug("REST request to delete ISOrderPrice : {}", id);
        iSOrderPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
