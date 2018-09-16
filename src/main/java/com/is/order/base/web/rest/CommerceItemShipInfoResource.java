package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceItemShipInfoService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceItemShipInfoDTO;
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
 * REST controller for managing CommerceItemShipInfo.
 */
@RestController
@RequestMapping("/api")
public class CommerceItemShipInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommerceItemShipInfoResource.class);

    private static final String ENTITY_NAME = "commerceItemShipInfo";

    private final CommerceItemShipInfoService commerceItemShipInfoService;

    public CommerceItemShipInfoResource(CommerceItemShipInfoService commerceItemShipInfoService) {
        this.commerceItemShipInfoService = commerceItemShipInfoService;
    }

    /**
     * POST  /commerce-item-ship-infos : Create a new commerceItemShipInfo.
     *
     * @param commerceItemShipInfoDTO the commerceItemShipInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceItemShipInfoDTO, or with status 400 (Bad Request) if the commerceItemShipInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-item-ship-infos")
    @Timed
    public ResponseEntity<CommerceItemShipInfoDTO> createCommerceItemShipInfo(@Valid @RequestBody CommerceItemShipInfoDTO commerceItemShipInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceItemShipInfo : {}", commerceItemShipInfoDTO);
        if (commerceItemShipInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceItemShipInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceItemShipInfoDTO result = commerceItemShipInfoService.save(commerceItemShipInfoDTO);
        return ResponseEntity.created(new URI("/api/commerce-item-ship-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-item-ship-infos : Updates an existing commerceItemShipInfo.
     *
     * @param commerceItemShipInfoDTO the commerceItemShipInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceItemShipInfoDTO,
     * or with status 400 (Bad Request) if the commerceItemShipInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceItemShipInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-item-ship-infos")
    @Timed
    public ResponseEntity<CommerceItemShipInfoDTO> updateCommerceItemShipInfo(@Valid @RequestBody CommerceItemShipInfoDTO commerceItemShipInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceItemShipInfo : {}", commerceItemShipInfoDTO);
        if (commerceItemShipInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceItemShipInfoDTO result = commerceItemShipInfoService.save(commerceItemShipInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceItemShipInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-item-ship-infos : get all the commerceItemShipInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceItemShipInfos in body
     */
    @GetMapping("/commerce-item-ship-infos")
    @Timed
    public List<CommerceItemShipInfoDTO> getAllCommerceItemShipInfos() {
        log.debug("REST request to get all CommerceItemShipInfos");
        return commerceItemShipInfoService.findAll();
    }

    /**
     * GET  /commerce-item-ship-infos/:id : get the "id" commerceItemShipInfo.
     *
     * @param id the id of the commerceItemShipInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceItemShipInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-item-ship-infos/{id}")
    @Timed
    public ResponseEntity<CommerceItemShipInfoDTO> getCommerceItemShipInfo(@PathVariable Long id) {
        log.debug("REST request to get CommerceItemShipInfo : {}", id);
        Optional<CommerceItemShipInfoDTO> commerceItemShipInfoDTO = commerceItemShipInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceItemShipInfoDTO);
    }

    /**
     * DELETE  /commerce-item-ship-infos/:id : delete the "id" commerceItemShipInfo.
     *
     * @param id the id of the commerceItemShipInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-item-ship-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceItemShipInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommerceItemShipInfo : {}", id);
        commerceItemShipInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
