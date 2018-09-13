package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISShippingContainerService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISShippingContainerDTO;
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
 * REST controller for managing ISShippingContainer.
 */
@RestController
@RequestMapping("/api")
public class ISShippingContainerResource {

    private final Logger log = LoggerFactory.getLogger(ISShippingContainerResource.class);

    private static final String ENTITY_NAME = "iSShippingContainer";

    private final ISShippingContainerService iSShippingContainerService;

    public ISShippingContainerResource(ISShippingContainerService iSShippingContainerService) {
        this.iSShippingContainerService = iSShippingContainerService;
    }

    /**
     * POST  /is-shipping-containers : Create a new iSShippingContainer.
     *
     * @param iSShippingContainerDTO the iSShippingContainerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSShippingContainerDTO, or with status 400 (Bad Request) if the iSShippingContainer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-shipping-containers")
    @Timed
    public ResponseEntity<ISShippingContainerDTO> createISShippingContainer(@Valid @RequestBody ISShippingContainerDTO iSShippingContainerDTO) throws URISyntaxException {
        log.debug("REST request to save ISShippingContainer : {}", iSShippingContainerDTO);
        if (iSShippingContainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSShippingContainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISShippingContainerDTO result = iSShippingContainerService.save(iSShippingContainerDTO);
        return ResponseEntity.created(new URI("/api/is-shipping-containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-shipping-containers : Updates an existing iSShippingContainer.
     *
     * @param iSShippingContainerDTO the iSShippingContainerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSShippingContainerDTO,
     * or with status 400 (Bad Request) if the iSShippingContainerDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSShippingContainerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-shipping-containers")
    @Timed
    public ResponseEntity<ISShippingContainerDTO> updateISShippingContainer(@Valid @RequestBody ISShippingContainerDTO iSShippingContainerDTO) throws URISyntaxException {
        log.debug("REST request to update ISShippingContainer : {}", iSShippingContainerDTO);
        if (iSShippingContainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISShippingContainerDTO result = iSShippingContainerService.save(iSShippingContainerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSShippingContainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-shipping-containers : get all the iSShippingContainers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSShippingContainers in body
     */
    @GetMapping("/is-shipping-containers")
    @Timed
    public List<ISShippingContainerDTO> getAllISShippingContainers() {
        log.debug("REST request to get all ISShippingContainers");
        return iSShippingContainerService.findAll();
    }

    /**
     * GET  /is-shipping-containers/:id : get the "id" iSShippingContainer.
     *
     * @param id the id of the iSShippingContainerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSShippingContainerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-shipping-containers/{id}")
    @Timed
    public ResponseEntity<ISShippingContainerDTO> getISShippingContainer(@PathVariable Long id) {
        log.debug("REST request to get ISShippingContainer : {}", id);
        Optional<ISShippingContainerDTO> iSShippingContainerDTO = iSShippingContainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSShippingContainerDTO);
    }

    /**
     * DELETE  /is-shipping-containers/:id : delete the "id" iSShippingContainer.
     *
     * @param id the id of the iSShippingContainerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-shipping-containers/{id}")
    @Timed
    public ResponseEntity<Void> deleteISShippingContainer(@PathVariable Long id) {
        log.debug("REST request to delete ISShippingContainer : {}", id);
        iSShippingContainerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
