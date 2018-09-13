package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceItemPriceNgService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceItemPriceNgDTO;
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
 * REST controller for managing CommerceItemPriceNg.
 */
@RestController
@RequestMapping("/api")
public class CommerceItemPriceNgResource {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPriceNgResource.class);

    private static final String ENTITY_NAME = "commerceItemPriceNg";

    private final CommerceItemPriceNgService commerceItemPriceNgService;

    public CommerceItemPriceNgResource(CommerceItemPriceNgService commerceItemPriceNgService) {
        this.commerceItemPriceNgService = commerceItemPriceNgService;
    }

    /**
     * POST  /commerce-item-price-ngs : Create a new commerceItemPriceNg.
     *
     * @param commerceItemPriceNgDTO the commerceItemPriceNgDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceItemPriceNgDTO, or with status 400 (Bad Request) if the commerceItemPriceNg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-item-price-ngs")
    @Timed
    public ResponseEntity<CommerceItemPriceNgDTO> createCommerceItemPriceNg(@Valid @RequestBody CommerceItemPriceNgDTO commerceItemPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceItemPriceNg : {}", commerceItemPriceNgDTO);
        if (commerceItemPriceNgDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceItemPriceNg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceItemPriceNgDTO result = commerceItemPriceNgService.save(commerceItemPriceNgDTO);
        return ResponseEntity.created(new URI("/api/commerce-item-price-ngs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-item-price-ngs : Updates an existing commerceItemPriceNg.
     *
     * @param commerceItemPriceNgDTO the commerceItemPriceNgDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceItemPriceNgDTO,
     * or with status 400 (Bad Request) if the commerceItemPriceNgDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceItemPriceNgDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-item-price-ngs")
    @Timed
    public ResponseEntity<CommerceItemPriceNgDTO> updateCommerceItemPriceNg(@Valid @RequestBody CommerceItemPriceNgDTO commerceItemPriceNgDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceItemPriceNg : {}", commerceItemPriceNgDTO);
        if (commerceItemPriceNgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceItemPriceNgDTO result = commerceItemPriceNgService.save(commerceItemPriceNgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceItemPriceNgDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-item-price-ngs : get all the commerceItemPriceNgs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceItemPriceNgs in body
     */
    @GetMapping("/commerce-item-price-ngs")
    @Timed
    public List<CommerceItemPriceNgDTO> getAllCommerceItemPriceNgs() {
        log.debug("REST request to get all CommerceItemPriceNgs");
        return commerceItemPriceNgService.findAll();
    }

    /**
     * GET  /commerce-item-price-ngs/:id : get the "id" commerceItemPriceNg.
     *
     * @param id the id of the commerceItemPriceNgDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceItemPriceNgDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-item-price-ngs/{id}")
    @Timed
    public ResponseEntity<CommerceItemPriceNgDTO> getCommerceItemPriceNg(@PathVariable Long id) {
        log.debug("REST request to get CommerceItemPriceNg : {}", id);
        Optional<CommerceItemPriceNgDTO> commerceItemPriceNgDTO = commerceItemPriceNgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceItemPriceNgDTO);
    }

    /**
     * DELETE  /commerce-item-price-ngs/:id : delete the "id" commerceItemPriceNg.
     *
     * @param id the id of the commerceItemPriceNgDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-item-price-ngs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceItemPriceNg(@PathVariable Long id) {
        log.debug("REST request to delete CommerceItemPriceNg : {}", id);
        commerceItemPriceNgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
