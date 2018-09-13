package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceShippingContainerService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceShippingContainerDTO;
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
 * REST controller for managing CommerceShippingContainer.
 */
@RestController
@RequestMapping("/api")
public class CommerceShippingContainerResource {

    private final Logger log = LoggerFactory.getLogger(CommerceShippingContainerResource.class);

    private static final String ENTITY_NAME = "commerceShippingContainer";

    private final CommerceShippingContainerService commerceShippingContainerService;

    public CommerceShippingContainerResource(CommerceShippingContainerService commerceShippingContainerService) {
        this.commerceShippingContainerService = commerceShippingContainerService;
    }

    /**
     * POST  /commerce-shipping-containers : Create a new commerceShippingContainer.
     *
     * @param commerceShippingContainerDTO the commerceShippingContainerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceShippingContainerDTO, or with status 400 (Bad Request) if the commerceShippingContainer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-shipping-containers")
    @Timed
    public ResponseEntity<CommerceShippingContainerDTO> createCommerceShippingContainer(@Valid @RequestBody CommerceShippingContainerDTO commerceShippingContainerDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceShippingContainer : {}", commerceShippingContainerDTO);
        if (commerceShippingContainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceShippingContainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceShippingContainerDTO result = commerceShippingContainerService.save(commerceShippingContainerDTO);
        return ResponseEntity.created(new URI("/api/commerce-shipping-containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-shipping-containers : Updates an existing commerceShippingContainer.
     *
     * @param commerceShippingContainerDTO the commerceShippingContainerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceShippingContainerDTO,
     * or with status 400 (Bad Request) if the commerceShippingContainerDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceShippingContainerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-shipping-containers")
    @Timed
    public ResponseEntity<CommerceShippingContainerDTO> updateCommerceShippingContainer(@Valid @RequestBody CommerceShippingContainerDTO commerceShippingContainerDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceShippingContainer : {}", commerceShippingContainerDTO);
        if (commerceShippingContainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceShippingContainerDTO result = commerceShippingContainerService.save(commerceShippingContainerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceShippingContainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-shipping-containers : get all the commerceShippingContainers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceShippingContainers in body
     */
    @GetMapping("/commerce-shipping-containers")
    @Timed
    public List<CommerceShippingContainerDTO> getAllCommerceShippingContainers() {
        log.debug("REST request to get all CommerceShippingContainers");
        return commerceShippingContainerService.findAll();
    }

    /**
     * GET  /commerce-shipping-containers/:id : get the "id" commerceShippingContainer.
     *
     * @param id the id of the commerceShippingContainerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceShippingContainerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-shipping-containers/{id}")
    @Timed
    public ResponseEntity<CommerceShippingContainerDTO> getCommerceShippingContainer(@PathVariable Long id) {
        log.debug("REST request to get CommerceShippingContainer : {}", id);
        Optional<CommerceShippingContainerDTO> commerceShippingContainerDTO = commerceShippingContainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceShippingContainerDTO);
    }

    /**
     * DELETE  /commerce-shipping-containers/:id : delete the "id" commerceShippingContainer.
     *
     * @param id the id of the commerceShippingContainerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-shipping-containers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceShippingContainer(@PathVariable Long id) {
        log.debug("REST request to delete CommerceShippingContainer : {}", id);
        commerceShippingContainerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
