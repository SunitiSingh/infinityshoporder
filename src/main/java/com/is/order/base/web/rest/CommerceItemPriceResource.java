package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceItemPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceItemPriceDTO;
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
 * REST controller for managing CommerceItemPrice.
 */
@RestController
@RequestMapping("/api")
public class CommerceItemPriceResource {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPriceResource.class);

    private static final String ENTITY_NAME = "commerceItemPrice";

    private final CommerceItemPriceService commerceItemPriceService;

    public CommerceItemPriceResource(CommerceItemPriceService commerceItemPriceService) {
        this.commerceItemPriceService = commerceItemPriceService;
    }

    /**
     * POST  /commerce-item-prices : Create a new commerceItemPrice.
     *
     * @param commerceItemPriceDTO the commerceItemPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceItemPriceDTO, or with status 400 (Bad Request) if the commerceItemPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-item-prices")
    @Timed
    public ResponseEntity<CommerceItemPriceDTO> createCommerceItemPrice(@Valid @RequestBody CommerceItemPriceDTO commerceItemPriceDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceItemPrice : {}", commerceItemPriceDTO);
        if (commerceItemPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceItemPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceItemPriceDTO result = commerceItemPriceService.save(commerceItemPriceDTO);
        return ResponseEntity.created(new URI("/api/commerce-item-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-item-prices : Updates an existing commerceItemPrice.
     *
     * @param commerceItemPriceDTO the commerceItemPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceItemPriceDTO,
     * or with status 400 (Bad Request) if the commerceItemPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceItemPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-item-prices")
    @Timed
    public ResponseEntity<CommerceItemPriceDTO> updateCommerceItemPrice(@Valid @RequestBody CommerceItemPriceDTO commerceItemPriceDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceItemPrice : {}", commerceItemPriceDTO);
        if (commerceItemPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceItemPriceDTO result = commerceItemPriceService.save(commerceItemPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceItemPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-item-prices : get all the commerceItemPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceItemPrices in body
     */
    @GetMapping("/commerce-item-prices")
    @Timed
    public List<CommerceItemPriceDTO> getAllCommerceItemPrices() {
        log.debug("REST request to get all CommerceItemPrices");
        return commerceItemPriceService.findAll();
    }

    /**
     * GET  /commerce-item-prices/:id : get the "id" commerceItemPrice.
     *
     * @param id the id of the commerceItemPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceItemPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-item-prices/{id}")
    @Timed
    public ResponseEntity<CommerceItemPriceDTO> getCommerceItemPrice(@PathVariable Long id) {
        log.debug("REST request to get CommerceItemPrice : {}", id);
        Optional<CommerceItemPriceDTO> commerceItemPriceDTO = commerceItemPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceItemPriceDTO);
    }

    /**
     * DELETE  /commerce-item-prices/:id : delete the "id" commerceItemPrice.
     *
     * @param id the id of the commerceItemPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-item-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceItemPrice(@PathVariable Long id) {
        log.debug("REST request to delete CommerceItemPrice : {}", id);
        commerceItemPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
