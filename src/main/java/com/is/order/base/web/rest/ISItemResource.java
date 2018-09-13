package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISItemService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.web.rest.util.PaginationUtil;
import com.is.order.base.service.dto.ISItemDTO;
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
 * REST controller for managing ISItem.
 */
@RestController
@RequestMapping("/api")
public class ISItemResource {

    private final Logger log = LoggerFactory.getLogger(ISItemResource.class);

    private static final String ENTITY_NAME = "iSItem";

    private final ISItemService iSItemService;

    public ISItemResource(ISItemService iSItemService) {
        this.iSItemService = iSItemService;
    }

    /**
     * POST  /is-items : Create a new iSItem.
     *
     * @param iSItemDTO the iSItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSItemDTO, or with status 400 (Bad Request) if the iSItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-items")
    @Timed
    public ResponseEntity<ISItemDTO> createISItem(@Valid @RequestBody ISItemDTO iSItemDTO) throws URISyntaxException {
        log.debug("REST request to save ISItem : {}", iSItemDTO);
        if (iSItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISItemDTO result = iSItemService.save(iSItemDTO);
        return ResponseEntity.created(new URI("/api/is-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-items : Updates an existing iSItem.
     *
     * @param iSItemDTO the iSItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSItemDTO,
     * or with status 400 (Bad Request) if the iSItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-items")
    @Timed
    public ResponseEntity<ISItemDTO> updateISItem(@Valid @RequestBody ISItemDTO iSItemDTO) throws URISyntaxException {
        log.debug("REST request to update ISItem : {}", iSItemDTO);
        if (iSItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISItemDTO result = iSItemService.save(iSItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-items : get all the iSItems.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of iSItems in body
     */
    @GetMapping("/is-items")
    @Timed
    public ResponseEntity<List<ISItemDTO>> getAllISItems(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ISItems");
        Page<ISItemDTO> page;
        if (eagerload) {
            page = iSItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = iSItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/is-items?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /is-items/:id : get the "id" iSItem.
     *
     * @param id the id of the iSItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-items/{id}")
    @Timed
    public ResponseEntity<ISItemDTO> getISItem(@PathVariable Long id) {
        log.debug("REST request to get ISItem : {}", id);
        Optional<ISItemDTO> iSItemDTO = iSItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSItemDTO);
    }

    /**
     * DELETE  /is-items/:id : delete the "id" iSItem.
     *
     * @param id the id of the iSItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteISItem(@PathVariable Long id) {
        log.debug("REST request to delete ISItem : {}", id);
        iSItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
