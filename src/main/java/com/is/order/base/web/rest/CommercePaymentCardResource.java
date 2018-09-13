package com.is.order.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.is.order.base.service.CommercePaymentCardService;
import com.is.order.base.web.rest.errors.BadRequestAlertException;
import com.is.order.base.web.rest.util.HeaderUtil;
import com.is.order.base.service.dto.CommercePaymentCardDTO;
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
 * REST controller for managing CommercePaymentCard.
 */
@RestController
@RequestMapping("/api")
public class CommercePaymentCardResource {

    private final Logger log = LoggerFactory.getLogger(CommercePaymentCardResource.class);

    private static final String ENTITY_NAME = "commercePaymentCard";

    private final CommercePaymentCardService commercePaymentCardService;

    public CommercePaymentCardResource(CommercePaymentCardService commercePaymentCardService) {
        this.commercePaymentCardService = commercePaymentCardService;
    }

    /**
     * POST  /commerce-payment-cards : Create a new commercePaymentCard.
     *
     * @param commercePaymentCardDTO the commercePaymentCardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercePaymentCardDTO, or with status 400 (Bad Request) if the commercePaymentCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-payment-cards")
    @Timed
    public ResponseEntity<CommercePaymentCardDTO> createCommercePaymentCard(@RequestBody CommercePaymentCardDTO commercePaymentCardDTO) throws URISyntaxException {
        log.debug("REST request to save CommercePaymentCard : {}", commercePaymentCardDTO);
        if (commercePaymentCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercePaymentCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercePaymentCardDTO result = commercePaymentCardService.save(commercePaymentCardDTO);
        return ResponseEntity.created(new URI("/api/commerce-payment-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-payment-cards : Updates an existing commercePaymentCard.
     *
     * @param commercePaymentCardDTO the commercePaymentCardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercePaymentCardDTO,
     * or with status 400 (Bad Request) if the commercePaymentCardDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercePaymentCardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-payment-cards")
    @Timed
    public ResponseEntity<CommercePaymentCardDTO> updateCommercePaymentCard(@RequestBody CommercePaymentCardDTO commercePaymentCardDTO) throws URISyntaxException {
        log.debug("REST request to update CommercePaymentCard : {}", commercePaymentCardDTO);
        if (commercePaymentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercePaymentCardDTO result = commercePaymentCardService.save(commercePaymentCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercePaymentCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-payment-cards : get all the commercePaymentCards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commercePaymentCards in body
     */
    @GetMapping("/commerce-payment-cards")
    @Timed
    public List<CommercePaymentCardDTO> getAllCommercePaymentCards() {
        log.debug("REST request to get all CommercePaymentCards");
        return commercePaymentCardService.findAll();
    }

    /**
     * GET  /commerce-payment-cards/:id : get the "id" commercePaymentCard.
     *
     * @param id the id of the commercePaymentCardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercePaymentCardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-payment-cards/{id}")
    @Timed
    public ResponseEntity<CommercePaymentCardDTO> getCommercePaymentCard(@PathVariable Long id) {
        log.debug("REST request to get CommercePaymentCard : {}", id);
        Optional<CommercePaymentCardDTO> commercePaymentCardDTO = commercePaymentCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercePaymentCardDTO);
    }

    /**
     * DELETE  /commerce-payment-cards/:id : delete the "id" commercePaymentCard.
     *
     * @param id the id of the commercePaymentCardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-payment-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommercePaymentCard(@PathVariable Long id) {
        log.debug("REST request to delete CommercePaymentCard : {}", id);
        commercePaymentCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
