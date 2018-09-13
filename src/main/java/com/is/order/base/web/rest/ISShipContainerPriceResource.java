package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISShipContainerPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISShipContainerPriceDTO;
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
 * REST controller for managing ISShipContainerPrice.
 */
@RestController
@RequestMapping("/api")
public class ISShipContainerPriceResource {

    private final Logger log = LoggerFactory.getLogger(ISShipContainerPriceResource.class);

    private static final String ENTITY_NAME = "iSShipContainerPrice";

    private final ISShipContainerPriceService iSShipContainerPriceService;

    public ISShipContainerPriceResource(ISShipContainerPriceService iSShipContainerPriceService) {
        this.iSShipContainerPriceService = iSShipContainerPriceService;
    }

    /**
     * POST  /is-ship-container-prices : Create a new iSShipContainerPrice.
     *
     * @param iSShipContainerPriceDTO the iSShipContainerPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSShipContainerPriceDTO, or with status 400 (Bad Request) if the iSShipContainerPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-ship-container-prices")
    @Timed
    public ResponseEntity<ISShipContainerPriceDTO> createISShipContainerPrice(@Valid @RequestBody ISShipContainerPriceDTO iSShipContainerPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ISShipContainerPrice : {}", iSShipContainerPriceDTO);
        if (iSShipContainerPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSShipContainerPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISShipContainerPriceDTO result = iSShipContainerPriceService.save(iSShipContainerPriceDTO);
        return ResponseEntity.created(new URI("/api/is-ship-container-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-ship-container-prices : Updates an existing iSShipContainerPrice.
     *
     * @param iSShipContainerPriceDTO the iSShipContainerPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSShipContainerPriceDTO,
     * or with status 400 (Bad Request) if the iSShipContainerPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSShipContainerPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-ship-container-prices")
    @Timed
    public ResponseEntity<ISShipContainerPriceDTO> updateISShipContainerPrice(@Valid @RequestBody ISShipContainerPriceDTO iSShipContainerPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ISShipContainerPrice : {}", iSShipContainerPriceDTO);
        if (iSShipContainerPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISShipContainerPriceDTO result = iSShipContainerPriceService.save(iSShipContainerPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSShipContainerPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-ship-container-prices : get all the iSShipContainerPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSShipContainerPrices in body
     */
    @GetMapping("/is-ship-container-prices")
    @Timed
    public List<ISShipContainerPriceDTO> getAllISShipContainerPrices() {
        log.debug("REST request to get all ISShipContainerPrices");
        return iSShipContainerPriceService.findAll();
    }

    /**
     * GET  /is-ship-container-prices/:id : get the "id" iSShipContainerPrice.
     *
     * @param id the id of the iSShipContainerPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSShipContainerPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-ship-container-prices/{id}")
    @Timed
    public ResponseEntity<ISShipContainerPriceDTO> getISShipContainerPrice(@PathVariable Long id) {
        log.debug("REST request to get ISShipContainerPrice : {}", id);
        Optional<ISShipContainerPriceDTO> iSShipContainerPriceDTO = iSShipContainerPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSShipContainerPriceDTO);
    }

    /**
     * DELETE  /is-ship-container-prices/:id : delete the "id" iSShipContainerPrice.
     *
     * @param id the id of the iSShipContainerPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-ship-container-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteISShipContainerPrice(@PathVariable Long id) {
        log.debug("REST request to delete ISShipContainerPrice : {}", id);
        iSShipContainerPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
