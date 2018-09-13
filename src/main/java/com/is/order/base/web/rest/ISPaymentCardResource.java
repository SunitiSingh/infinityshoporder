package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.ISPaymentCardService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.ISPaymentCardDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ISPaymentCard.
 */
@RestController
@RequestMapping("/api")
public class ISPaymentCardResource {

    private final Logger log = LoggerFactory.getLogger(ISPaymentCardResource.class);

    private static final String ENTITY_NAME = "iSPaymentCard";

    private final ISPaymentCardService iSPaymentCardService;

    public ISPaymentCardResource(ISPaymentCardService iSPaymentCardService) {
        this.iSPaymentCardService = iSPaymentCardService;
    }

    /**
     * POST  /is-payment-cards : Create a new iSPaymentCard.
     *
     * @param iSPaymentCardDTO the iSPaymentCardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iSPaymentCardDTO, or with status 400 (Bad Request) if the iSPaymentCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-payment-cards")
    @Timed
    public ResponseEntity<ISPaymentCardDTO> createISPaymentCard(@RequestBody ISPaymentCardDTO iSPaymentCardDTO) throws URISyntaxException {
        log.debug("REST request to save ISPaymentCard : {}", iSPaymentCardDTO);
        if (iSPaymentCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new iSPaymentCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ISPaymentCardDTO result = iSPaymentCardService.save(iSPaymentCardDTO);
        return ResponseEntity.created(new URI("/api/is-payment-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-payment-cards : Updates an existing iSPaymentCard.
     *
     * @param iSPaymentCardDTO the iSPaymentCardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iSPaymentCardDTO,
     * or with status 400 (Bad Request) if the iSPaymentCardDTO is not valid,
     * or with status 500 (Internal Server Error) if the iSPaymentCardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-payment-cards")
    @Timed
    public ResponseEntity<ISPaymentCardDTO> updateISPaymentCard(@RequestBody ISPaymentCardDTO iSPaymentCardDTO) throws URISyntaxException {
        log.debug("REST request to update ISPaymentCard : {}", iSPaymentCardDTO);
        if (iSPaymentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ISPaymentCardDTO result = iSPaymentCardService.save(iSPaymentCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iSPaymentCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-payment-cards : get all the iSPaymentCards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iSPaymentCards in body
     */
    @GetMapping("/is-payment-cards")
    @Timed
    public List<ISPaymentCardDTO> getAllISPaymentCards() {
        log.debug("REST request to get all ISPaymentCards");
        return iSPaymentCardService.findAll();
    }

    /**
     * GET  /is-payment-cards/:id : get the "id" iSPaymentCard.
     *
     * @param id the id of the iSPaymentCardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iSPaymentCardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/is-payment-cards/{id}")
    @Timed
    public ResponseEntity<ISPaymentCardDTO> getISPaymentCard(@PathVariable Long id) {
        log.debug("REST request to get ISPaymentCard : {}", id);
        Optional<ISPaymentCardDTO> iSPaymentCardDTO = iSPaymentCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iSPaymentCardDTO);
    }

    /**
     * DELETE  /is-payment-cards/:id : delete the "id" iSPaymentCard.
     *
     * @param id the id of the iSPaymentCardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-payment-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteISPaymentCard(@PathVariable Long id) {
        log.debug("REST request to delete ISPaymentCard : {}", id);
        iSPaymentCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
