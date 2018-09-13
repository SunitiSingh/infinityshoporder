package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceOrderPriceService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceOrderPriceDTO;
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
 * REST controller for managing CommerceOrderPrice.
 */
@RestController
@RequestMapping("/api")
public class CommerceOrderPriceResource {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderPriceResource.class);

    private static final String ENTITY_NAME = "commerceOrderPrice";

    private final CommerceOrderPriceService commerceOrderPriceService;

    public CommerceOrderPriceResource(CommerceOrderPriceService commerceOrderPriceService) {
        this.commerceOrderPriceService = commerceOrderPriceService;
    }

    /**
     * POST  /commerce-order-prices : Create a new commerceOrderPrice.
     *
     * @param commerceOrderPriceDTO the commerceOrderPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceOrderPriceDTO, or with status 400 (Bad Request) if the commerceOrderPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-order-prices")
    @Timed
    public ResponseEntity<CommerceOrderPriceDTO> createCommerceOrderPrice(@Valid @RequestBody CommerceOrderPriceDTO commerceOrderPriceDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceOrderPrice : {}", commerceOrderPriceDTO);
        if (commerceOrderPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceOrderPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceOrderPriceDTO result = commerceOrderPriceService.save(commerceOrderPriceDTO);
        return ResponseEntity.created(new URI("/api/commerce-order-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-order-prices : Updates an existing commerceOrderPrice.
     *
     * @param commerceOrderPriceDTO the commerceOrderPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceOrderPriceDTO,
     * or with status 400 (Bad Request) if the commerceOrderPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceOrderPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-order-prices")
    @Timed
    public ResponseEntity<CommerceOrderPriceDTO> updateCommerceOrderPrice(@Valid @RequestBody CommerceOrderPriceDTO commerceOrderPriceDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceOrderPrice : {}", commerceOrderPriceDTO);
        if (commerceOrderPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceOrderPriceDTO result = commerceOrderPriceService.save(commerceOrderPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceOrderPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-order-prices : get all the commerceOrderPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceOrderPrices in body
     */
    @GetMapping("/commerce-order-prices")
    @Timed
    public List<CommerceOrderPriceDTO> getAllCommerceOrderPrices() {
        log.debug("REST request to get all CommerceOrderPrices");
        return commerceOrderPriceService.findAll();
    }

    /**
     * GET  /commerce-order-prices/:id : get the "id" commerceOrderPrice.
     *
     * @param id the id of the commerceOrderPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceOrderPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-order-prices/{id}")
    @Timed
    public ResponseEntity<CommerceOrderPriceDTO> getCommerceOrderPrice(@PathVariable Long id) {
        log.debug("REST request to get CommerceOrderPrice : {}", id);
        Optional<CommerceOrderPriceDTO> commerceOrderPriceDTO = commerceOrderPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceOrderPriceDTO);
    }

    /**
     * DELETE  /commerce-order-prices/:id : delete the "id" commerceOrderPrice.
     *
     * @param id the id of the commerceOrderPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-order-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceOrderPrice(@PathVariable Long id) {
        log.debug("REST request to delete CommerceOrderPrice : {}", id);
        commerceOrderPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
