package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommerceItemPayInfoService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommerceItemPayInfoDTO;
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
 * REST controller for managing CommerceItemPayInfo.
 */
@RestController
@RequestMapping("/api")
public class CommerceItemPayInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommerceItemPayInfoResource.class);

    private static final String ENTITY_NAME = "commerceItemPayInfo";

    private final CommerceItemPayInfoService commerceItemPayInfoService;

    public CommerceItemPayInfoResource(CommerceItemPayInfoService commerceItemPayInfoService) {
        this.commerceItemPayInfoService = commerceItemPayInfoService;
    }

    /**
     * POST  /commerce-item-pay-infos : Create a new commerceItemPayInfo.
     *
     * @param commerceItemPayInfoDTO the commerceItemPayInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceItemPayInfoDTO, or with status 400 (Bad Request) if the commerceItemPayInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-item-pay-infos")
    @Timed
    public ResponseEntity<CommerceItemPayInfoDTO> createCommerceItemPayInfo(@Valid @RequestBody CommerceItemPayInfoDTO commerceItemPayInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceItemPayInfo : {}", commerceItemPayInfoDTO);
        if (commerceItemPayInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commerceItemPayInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommerceItemPayInfoDTO result = commerceItemPayInfoService.save(commerceItemPayInfoDTO);
        return ResponseEntity.created(new URI("/api/commerce-item-pay-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-item-pay-infos : Updates an existing commerceItemPayInfo.
     *
     * @param commerceItemPayInfoDTO the commerceItemPayInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceItemPayInfoDTO,
     * or with status 400 (Bad Request) if the commerceItemPayInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceItemPayInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-item-pay-infos")
    @Timed
    public ResponseEntity<CommerceItemPayInfoDTO> updateCommerceItemPayInfo(@Valid @RequestBody CommerceItemPayInfoDTO commerceItemPayInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceItemPayInfo : {}", commerceItemPayInfoDTO);
        if (commerceItemPayInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommerceItemPayInfoDTO result = commerceItemPayInfoService.save(commerceItemPayInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerceItemPayInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-item-pay-infos : get all the commerceItemPayInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceItemPayInfos in body
     */
    @GetMapping("/commerce-item-pay-infos")
    @Timed
    public List<CommerceItemPayInfoDTO> getAllCommerceItemPayInfos() {
        log.debug("REST request to get all CommerceItemPayInfos");
        return commerceItemPayInfoService.findAll();
    }

    /**
     * GET  /commerce-item-pay-infos/:id : get the "id" commerceItemPayInfo.
     *
     * @param id the id of the commerceItemPayInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceItemPayInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-item-pay-infos/{id}")
    @Timed
    public ResponseEntity<CommerceItemPayInfoDTO> getCommerceItemPayInfo(@PathVariable Long id) {
        log.debug("REST request to get CommerceItemPayInfo : {}", id);
        Optional<CommerceItemPayInfoDTO> commerceItemPayInfoDTO = commerceItemPayInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commerceItemPayInfoDTO);
    }

    /**
     * DELETE  /commerce-item-pay-infos/:id : delete the "id" commerceItemPayInfo.
     *
     * @param id the id of the commerceItemPayInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-item-pay-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceItemPayInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommerceItemPayInfo : {}", id);
        commerceItemPayInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
