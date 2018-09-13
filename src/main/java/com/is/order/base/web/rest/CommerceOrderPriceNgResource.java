package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceOrderPriceNgService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceOrderPriceNgDTO;
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
 * REST controller for managing CommerceOrderPriceNg.
 */
@RestController
@RequestMapping("/api")
public class CommerceOrderPriceNgResource {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPriceNgResource.class);

    private static final String ENTITY_NAME = "commerceOrderPriceNg";

    private final CommerceOrderPriceNgService commerceOrderPriceNgService;

    public CommerceOrderPriceNgResource(CommerceOrderPriceNgService commerceOrderPriceNgService) {
        this.commerceOrderPriceNgService = commerceOrderPriceNgService;
    }

    /**
     * POST  /commerce-order-price-ngs : Create a new commerceOrderPriceNg.
     *
     * @param commerceOrderPriceNgDTO the commerceOrderPriceNgDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceOrderPriceNgDTO, or with status 400 (Bad Request) if the commerceOrderPriceNg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-order-price-ngs")
    @Timed
    public ResponseEntity<CommerceOrderPriceNgDTO> createCommerceOrderPriceNg(@Valid @RequestBody CommerceOrderPriceNgDTO commerceOrderPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceOrderPriceNg : {}", commerceOrderPriceNgDTO);
        if (commerceOrderPriceNgDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceOrderPriceNg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceOrderPriceNgDTO result = commerceOrderPriceNgService.save(commerceOrderPriceNgDTO);
        return ResponseEntity.created(new URI("/api/commerce-order-price-ngs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-order-price-ngs : Updates an existing commerceOrderPriceNg.
     *
     * @param commerceOrderPriceNgDTO the commerceOrderPriceNgDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceOrderPriceNgDTO,
     * or with status 400 (Bad Request) if the commerceOrderPriceNgDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceOrderPriceNgDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-order-price-ngs")
    @Timed
    public ResponseEntity<CommerceOrderPriceNgDTO> updateCommerceOrderPriceNg(@Valid @RequestBody CommerceOrderPriceNgDTO commerceOrderPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceOrderPriceNg : {}", commerceOrderPriceNgDTO);
        if (commerceOrderPriceNgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceOrderPriceNgDTO result = commerceOrderPriceNgService.save(commerceOrderPriceNgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceOrderPriceNgDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-order-price-ngs : get all the commerceOrderPriceNgs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceOrderPriceNgs in body
     */
    @GetMapping("/commerce-order-price-ngs")
    @Timed
    public List<CommerceOrderPriceNgDTO> getAllCommerceOrderPriceNgs() {
        log.debug("REST request to get all CommerceOrderPriceNgs");
        return commerceOrderPriceNgService.findAll();
    }

    /**
     * GET  /commerce-order-price-ngs/:id : get the "id" commerceOrderPriceNg.
     *
     * @param id the id of the commerceOrderPriceNgDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceOrderPriceNgDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-order-price-ngs/{id}")
    @Timed
    public ResponseEntity<CommerceOrderPriceNgDTO> getCommerceOrderPriceNg(@PathVariable Long id) {
        log.debug("REST request to get CommerceOrderPriceNg : {}", id);
        Optional<CommerceOrderPriceNgDTO> commerceOrderPriceNgDTO = commerceOrderPriceNgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceOrderPriceNgDTO);
    }

    /**
     * DELETE  /commerce-order-price-ngs/:id : delete the "id" commerceOrderPriceNg.
     *
     * @param id the id of the commerceOrderPriceNgDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-order-price-ngs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceOrderPriceNg(@PathVariable Long id) {
        log.debug("REST request to delete CommerceOrderPriceNg : {}", id);
        commerceOrderPriceNgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
