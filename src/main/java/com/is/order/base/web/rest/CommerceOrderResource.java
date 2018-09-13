package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceOrderService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.web.rest.util.PaginationUtil;
import com.is.order.base.service.dto.CommerceOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommerceOrder.
 */
@RestController
@RequestMapping("/api")
public class CommerceOrderResource {

    private final Logger log = LoggerFactory.getLogger(CommerceOrderResource.class);

    private static final String ENTITY_NAME = "commerceOrder";

    private final CommerceOrderService commerceOrderService;

    public CommerceOrderResource(CommerceOrderService commerceOrderService) {
        this.commerceOrderService = commerceOrderService;
    }

    /**
     * POST  /commerce-orders : Create a new commerceOrder.
     *
     * @param commerceOrderDTO the commerceOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceOrderDTO, or with status 400 (Bad Request) if the commerceOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-orders")
    @Timed
    public ResponseEntity<CommerceOrderDTO> createCommerceOrder(@Valid @RequestBody CommerceOrderDTO commerceOrderDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceOrder : {}", commerceOrderDTO);
        if (commerceOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceOrderDTO result = commerceOrderService.save(commerceOrderDTO);
        return ResponseEntity.created(new URI("/api/commerce-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-orders : Updates an existing commerceOrder.
     *
     * @param commerceOrderDTO the commerceOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceOrderDTO,
     * or with status 400 (Bad Request) if the commerceOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-orders")
    @Timed
    public ResponseEntity<CommerceOrderDTO> updateCommerceOrder(@Valid @RequestBody CommerceOrderDTO commerceOrderDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceOrder : {}", commerceOrderDTO);
        if (commerceOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceOrderDTO result = commerceOrderService.save(commerceOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-orders : get all the commerceOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commerceOrders in body
     */
    @GetMapping("/commerce-orders")
    @Timed
    public ResponseEntity<List<CommerceOrderDTO>> getAllCommerceOrders(Pageable pageable) {
        log.debug("REST request to get a page of CommerceOrders");
        Page<CommerceOrderDTO> page = commerceOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commerce-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commerce-orders/:id : get the "id" commerceOrder.
     *
     * @param id the id of the commerceOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-orders/{id}")
    @Timed
    public ResponseEntity<CommerceOrderDTO> getCommerceOrder(@PathVariable Long id) {
        log.debug("REST request to get CommerceOrder : {}", id);
        Optional<CommerceOrderDTO> commerceOrderDTO = commerceOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceOrderDTO);
    }

    /**
     * DELETE  /commerce-orders/:id : delete the "id" commerceOrder.
     *
     * @param id the id of the commerceOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceOrder(@PathVariable Long id) {
        log.debug("REST request to delete CommerceOrder : {}", id);
        commerceOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
