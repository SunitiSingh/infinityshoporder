package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceShipContainerPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceShipContainerPriceDTO;
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
 * REST controller for managing CommerceShipContainerPrice.
 */
@RestController
@RequestMapping("/api")
public class CommerceShipContainerPriceResource {

    private final Logger log = LoggerFactory.getLogger(CommerceShipContainerPriceResource.class);

    private static final String ENTITY_NAME = "commerceShipContainerPrice";

    private final CommerceShipContainerPriceService commerceShipContainerPriceService;

    public CommerceShipContainerPriceResource(CommerceShipContainerPriceService commerceShipContainerPriceService) {
        this.commerceShipContainerPriceService = commerceShipContainerPriceService;
    }

    /**
     * POST  /commerce-ship-container-prices : Create a new commerceShipContainerPrice.
     *
     * @param commerceShipContainerPriceDTO the commerceShipContainerPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceShipContainerPriceDTO, or with status 400 (Bad Request) if the commerceShipContainerPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-ship-container-prices")
    @Timed
    public ResponseEntity<CommerceShipContainerPriceDTO> createCommerceShipContainerPrice(@Valid @RequestBody CommerceShipContainerPriceDTO commerceShipContainerPriceDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceShipContainerPrice : {}", commerceShipContainerPriceDTO);
        if (commerceShipContainerPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceShipContainerPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceShipContainerPriceDTO result = commerceShipContainerPriceService.save(commerceShipContainerPriceDTO);
        return ResponseEntity.created(new URI("/api/commerce-ship-container-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-ship-container-prices : Updates an existing commerceShipContainerPrice.
     *
     * @param commerceShipContainerPriceDTO the commerceShipContainerPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceShipContainerPriceDTO,
     * or with status 400 (Bad Request) if the commerceShipContainerPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceShipContainerPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-ship-container-prices")
    @Timed
    public ResponseEntity<CommerceShipContainerPriceDTO> updateCommerceShipContainerPrice(@Valid @RequestBody CommerceShipContainerPriceDTO commerceShipContainerPriceDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceShipContainerPrice : {}", commerceShipContainerPriceDTO);
        if (commerceShipContainerPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceShipContainerPriceDTO result = commerceShipContainerPriceService.save(commerceShipContainerPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceShipContainerPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-ship-container-prices : get all the commerceShipContainerPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceShipContainerPrices in body
     */
    @GetMapping("/commerce-ship-container-prices")
    @Timed
    public List<CommerceShipContainerPriceDTO> getAllCommerceShipContainerPrices() {
        log.debug("REST request to get all CommerceShipContainerPrices");
        return commerceShipContainerPriceService.findAll();
    }

    /**
     * GET  /commerce-ship-container-prices/:id : get the "id" commerceShipContainerPrice.
     *
     * @param id the id of the commerceShipContainerPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceShipContainerPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-ship-container-prices/{id}")
    @Timed
    public ResponseEntity<CommerceShipContainerPriceDTO> getCommerceShipContainerPrice(@PathVariable Long id) {
        log.debug("REST request to get CommerceShipContainerPrice : {}", id);
        Optional<CommerceShipContainerPriceDTO> commerceShipContainerPriceDTO = commerceShipContainerPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceShipContainerPriceDTO);
    }

    /**
     * DELETE  /commerce-ship-container-prices/:id : delete the "id" commerceShipContainerPrice.
     *
     * @param id the id of the commerceShipContainerPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-ship-container-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceShipContainerPrice(@PathVariable Long id) {
        log.debug("REST request to delete CommerceShipContainerPrice : {}", id);
        commerceShipContainerPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
