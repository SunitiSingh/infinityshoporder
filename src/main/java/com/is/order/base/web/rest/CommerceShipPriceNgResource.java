package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceShipPriceNgService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceShipPriceNgDTO;
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
 * REST controller for managing CommerceShipPriceNg.
 */
@RestController
@RequestMapping("/api")
public class CommerceShipPriceNgResource {

    private final Logger log = LoggerFactory.getLogger(CommerceShipPriceNgResource.class);

    private static final String ENTITY_NAME = "commerceShipPriceNg";

    private final CommerceShipPriceNgService commerceShipPriceNgService;

    public CommerceShipPriceNgResource(CommerceShipPriceNgService commerceShipPriceNgService) {
        this.commerceShipPriceNgService = commerceShipPriceNgService;
    }

    /**
     * POST  /commerce-ship-price-ngs : Create a new commerceShipPriceNg.
     *
     * @param commerceShipPriceNgDTO the commerceShipPriceNgDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceShipPriceNgDTO, or with status 400 (Bad Request) if the commerceShipPriceNg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-ship-price-ngs")
    @Timed
    public ResponseEntity<CommerceShipPriceNgDTO> createCommerceShipPriceNg(@Valid @RequestBody CommerceShipPriceNgDTO commerceShipPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceShipPriceNg : {}", commerceShipPriceNgDTO);
        if (commerceShipPriceNgDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceShipPriceNg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceShipPriceNgDTO result = commerceShipPriceNgService.save(commerceShipPriceNgDTO);
        return ResponseEntity.created(new URI("/api/commerce-ship-price-ngs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-ship-price-ngs : Updates an existing commerceShipPriceNg.
     *
     * @param commerceShipPriceNgDTO the commerceShipPriceNgDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceShipPriceNgDTO,
     * or with status 400 (Bad Request) if the commerceShipPriceNgDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceShipPriceNgDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-ship-price-ngs")
    @Timed
    public ResponseEntity<CommerceShipPriceNgDTO> updateCommerceShipPriceNg(@Valid @RequestBody CommerceShipPriceNgDTO commerceShipPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceShipPriceNg : {}", commerceShipPriceNgDTO);
        if (commerceShipPriceNgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceShipPriceNgDTO result = commerceShipPriceNgService.save(commerceShipPriceNgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceShipPriceNgDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-ship-price-ngs : get all the commerceShipPriceNgs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceShipPriceNgs in body
     */
    @GetMapping("/commerce-ship-price-ngs")
    @Timed
    public List<CommerceShipPriceNgDTO> getAllCommerceShipPriceNgs() {
        log.debug("REST request to get all CommerceShipPriceNgs");
        return commerceShipPriceNgService.findAll();
    }

    /**
     * GET  /commerce-ship-price-ngs/:id : get the "id" commerceShipPriceNg.
     *
     * @param id the id of the commerceShipPriceNgDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceShipPriceNgDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-ship-price-ngs/{id}")
    @Timed
    public ResponseEntity<CommerceShipPriceNgDTO> getCommerceShipPriceNg(@PathVariable Long id) {
        log.debug("REST request to get CommerceShipPriceNg : {}", id);
        Optional<CommerceShipPriceNgDTO> commerceShipPriceNgDTO = commerceShipPriceNgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceShipPriceNgDTO);
    }

    /**
     * DELETE  /commerce-ship-price-ngs/:id : delete the "id" commerceShipPriceNg.
     *
     * @param id the id of the commerceShipPriceNgDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-ship-price-ngs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceShipPriceNg(@PathVariable Long id) {
        log.debug("REST request to delete CommerceShipPriceNg : {}", id);
        commerceShipPriceNgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
