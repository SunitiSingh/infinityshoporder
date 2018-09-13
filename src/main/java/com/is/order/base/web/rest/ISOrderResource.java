package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISOrderService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.web.rest.util.PaginationUtil;
import com.is.order.base.service.dto.ISOrderDTO;
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
 * REST controller for managing ISOrder.
 */
@RestController
@RequestMapping("/api")
public class ISOrderResource {

    private final Logger log = LoggerFactory.getLogger(ISOrderResource.class);

    private static final String ENTITY_NAME = "iSOrder";

    private final ISOrderService iSOrderService;

    public ISOrderResource(ISOrderService iSOrderService) {
        this.iSOrderService = iSOrderService;
    }

    /**
     * POST  /is-orders : Create a new iSOrder.
     *
     * @param iSOrderDTO the iSOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSOrderDTO, or with status 400 (Bad Request) if the iSOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-orders")
    @Timed
    public ResponseEntity<ISOrderDTO> createISOrder(@Valid @RequestBody ISOrderDTO iSOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ISOrder : {}", iSOrderDTO);
        if (iSOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISOrderDTO result = iSOrderService.save(iSOrderDTO);
        return ResponseEntity.created(new URI("/api/is-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-orders : Updates an existing iSOrder.
     *
     * @param iSOrderDTO the iSOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSOrderDTO,
     * or with status 400 (Bad Request) if the iSOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-orders")
    @Timed
    public ResponseEntity<ISOrderDTO> updateISOrder(@Valid @RequestBody ISOrderDTO iSOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ISOrder : {}", iSOrderDTO);
        if (iSOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISOrderDTO result = iSOrderService.save(iSOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-orders : get all the iSOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of iSOrders in body
     */
    @GetMapping("/is-orders")
    @Timed
    public ResponseEntity<List<ISOrderDTO>> getAllISOrders(Pageable pageable) {
        log.debug("REST request to get a page of ISOrders");
        Page<ISOrderDTO> page = iSOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/is-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /is-orders/:id : get the "id" iSOrder.
     *
     * @param id the id of the iSOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-orders/{id}")
    @Timed
    public ResponseEntity<ISOrderDTO> getISOrder(@PathVariable Long id) {
        log.debug("REST request to get ISOrder : {}", id);
        Optional<ISOrderDTO> iSOrderDTO = iSOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSOrderDTO);
    }

    /**
     * DELETE  /is-orders/:id : delete the "id" iSOrder.
     *
     * @param id the id of the iSOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteISOrder(@PathVariable Long id) {
        log.debug("REST request to delete ISOrder : {}", id);
        iSOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
