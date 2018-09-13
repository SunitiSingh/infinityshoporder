package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISItemPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISItemPriceDTO;
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
 * REST controller for managing ISItemPrice.
 */
@RestController
@RequestMapping("/api")
public class ISItemPriceResource {

    private final Logger log = LoggerFactory.getLogger(ISItemPriceResource.class);

    private static final String ENTITY_NAME = "iSItemPrice";

    private final ISItemPriceService iSItemPriceService;

    public ISItemPriceResource(ISItemPriceService iSItemPriceService) {
        this.iSItemPriceService = iSItemPriceService;
    }

    /**
     * POST  /is-item-prices : Create a new iSItemPrice.
     *
     * @param iSItemPriceDTO the iSItemPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSItemPriceDTO, or with status 400 (Bad Request) if the iSItemPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-item-prices")
    @Timed
    public ResponseEntity<ISItemPriceDTO> createISItemPrice(@Valid @RequestBody ISItemPriceDTO iSItemPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ISItemPrice : {}", iSItemPriceDTO);
        if (iSItemPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSItemPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISItemPriceDTO result = iSItemPriceService.save(iSItemPriceDTO);
        return ResponseEntity.created(new URI("/api/is-item-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-item-prices : Updates an existing iSItemPrice.
     *
     * @param iSItemPriceDTO the iSItemPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSItemPriceDTO,
     * or with status 400 (Bad Request) if the iSItemPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSItemPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-item-prices")
    @Timed
    public ResponseEntity<ISItemPriceDTO> updateISItemPrice(@Valid @RequestBody ISItemPriceDTO iSItemPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ISItemPrice : {}", iSItemPriceDTO);
        if (iSItemPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISItemPriceDTO result = iSItemPriceService.save(iSItemPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSItemPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-item-prices : get all the iSItemPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSItemPrices in body
     */
    @GetMapping("/is-item-prices")
    @Timed
    public List<ISItemPriceDTO> getAllISItemPrices() {
        log.debug("REST request to get all ISItemPrices");
        return iSItemPriceService.findAll();
    }

    /**
     * GET  /is-item-prices/:id : get the "id" iSItemPrice.
     *
     * @param id the id of the iSItemPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSItemPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-item-prices/{id}")
    @Timed
    public ResponseEntity<ISItemPriceDTO> getISItemPrice(@PathVariable Long id) {
        log.debug("REST request to get ISItemPrice : {}", id);
        Optional<ISItemPriceDTO> iSItemPriceDTO = iSItemPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSItemPriceDTO);
    }

    /**
     * DELETE  /is-item-prices/:id : delete the "id" iSItemPrice.
     *
     * @param id the id of the iSItemPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-item-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteISItemPrice(@PathVariable Long id) {
        log.debug("REST request to delete ISItemPrice : {}", id);
        iSItemPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
