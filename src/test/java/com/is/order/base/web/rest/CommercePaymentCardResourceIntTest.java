package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommercePaymentCard;
import com.is.order.base.repository.CommercePaymentCardRepository;
import com.is.order.base.service.CommercePaymentCardService;
import com.is.order.base.service.dto.CommercePaymentCardDTO;
import com.is.order.base.service.mapper.CommercePaymentCardMapper;
import com.is.order.base.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.is.order.base.web.rest.TestUtil.sameInstant;
import static com.is.order.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommercePaymentCardResource REST controller.
 *
 * @see CommercePaymentCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommercePaymentCardResourceIntTest {

    private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXP_MONTH = 1;
    private static final Integer UPDATED_EXP_MONTH = 2;

    private static final Integer DEFAULT_EXP_YEAR = 1;
    private static final Integer UPDATED_EXP_YEAR = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommercePaymentCardRepository commercePaymentCardRepository;


    @Autowired
    private CommercePaymentCardMapper commercePaymentCardMapper;
    

    @Autowired
    private CommercePaymentCardService commercePaymentCardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommercePaymentCardMockMvc;

    private CommercePaymentCard commercePaymentCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercePaymentCardResource commercePaymentCardResource = new CommercePaymentCardResource(commercePaymentCardService);
        this.restCommercePaymentCardMockMvc = MockMvcBuilders.standaloneSetup(commercePaymentCardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommercePaymentCard createEntity(EntityManager em) {
        CommercePaymentCard commercePaymentCard = new CommercePaymentCard()
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expMonth(DEFAULT_EXP_MONTH)
            .expYear(DEFAULT_EXP_YEAR)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commercePaymentCard;
    }

    @Before
    public void initTest() {
        commercePaymentCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercePaymentCard() throws Exception {
        int databaseSizeBeforeCreate = commercePaymentCardRepository.findAll().size();

        // Create the CommercePaymentCard
        CommercePaymentCardDTO commercePaymentCardDTO = commercePaymentCardMapper.toDto(commercePaymentCard);
        restCommercePaymentCardMockMvc.perform(post("/api/commerce-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercePaymentCardDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercePaymentCard in the database
        List<CommercePaymentCard> commercePaymentCardList = commercePaymentCardRepository.findAll();
        assertThat(commercePaymentCardList).hasSize(databaseSizeBeforeCreate + 1);
        CommercePaymentCard testCommercePaymentCard = commercePaymentCardList.get(commercePaymentCardList.size() - 1);
        assertThat(testCommercePaymentCard.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testCommercePaymentCard.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testCommercePaymentCard.getExpMonth()).isEqualTo(DEFAULT_EXP_MONTH);
        assertThat(testCommercePaymentCard.getExpYear()).isEqualTo(DEFAULT_EXP_YEAR);
        assertThat(testCommercePaymentCard.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testCommercePaymentCard.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommercePaymentCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercePaymentCardRepository.findAll().size();

        // Create the CommercePaymentCard with an existing ID
        commercePaymentCard.setId(1L);
        CommercePaymentCardDTO commercePaymentCardDTO = commercePaymentCardMapper.toDto(commercePaymentCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercePaymentCardMockMvc.perform(post("/api/commerce-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercePaymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercePaymentCard in the database
        List<CommercePaymentCard> commercePaymentCardList = commercePaymentCardRepository.findAll();
        assertThat(commercePaymentCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommercePaymentCards() throws Exception {
        // Initialize the database
        commercePaymentCardRepository.saveAndFlush(commercePaymentCard);

        // Get all the commercePaymentCardList
        restCommercePaymentCardMockMvc.perform(get("/api/commerce-payment-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercePaymentCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expMonth").value(hasItem(DEFAULT_EXP_MONTH)))
            .andExpect(jsonPath("$.[*].expYear").value(hasItem(DEFAULT_EXP_YEAR)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getCommercePaymentCard() throws Exception {
        // Initialize the database
        commercePaymentCardRepository.saveAndFlush(commercePaymentCard);

        // Get the commercePaymentCard
        restCommercePaymentCardMockMvc.perform(get("/api/commerce-payment-cards/{id}", commercePaymentCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercePaymentCard.getId().intValue()))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.expMonth").value(DEFAULT_EXP_MONTH))
            .andExpect(jsonPath("$.expYear").value(DEFAULT_EXP_YEAR))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommercePaymentCard() throws Exception {
        // Get the commercePaymentCard
        restCommercePaymentCardMockMvc.perform(get("/api/commerce-payment-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercePaymentCard() throws Exception {
        // Initialize the database
        commercePaymentCardRepository.saveAndFlush(commercePaymentCard);

        int databaseSizeBeforeUpdate = commercePaymentCardRepository.findAll().size();

        // Update the commercePaymentCard
        CommercePaymentCard updatedCommercePaymentCard = commercePaymentCardRepository.findById(commercePaymentCard.getId()).get();
        // Disconnect from session so that the updates on updatedCommercePaymentCard are not directly saved in db
        em.detach(updatedCommercePaymentCard);
        updatedCommercePaymentCard
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expMonth(UPDATED_EXP_MONTH)
            .expYear(UPDATED_EXP_YEAR)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommercePaymentCardDTO commercePaymentCardDTO = commercePaymentCardMapper.toDto(updatedCommercePaymentCard);

        restCommercePaymentCardMockMvc.perform(put("/api/commerce-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercePaymentCardDTO)))
            .andExpect(status().isOk());

        // Validate the CommercePaymentCard in the database
        List<CommercePaymentCard> commercePaymentCardList = commercePaymentCardRepository.findAll();
        assertThat(commercePaymentCardList).hasSize(databaseSizeBeforeUpdate);
        CommercePaymentCard testCommercePaymentCard = commercePaymentCardList.get(commercePaymentCardList.size() - 1);
        assertThat(testCommercePaymentCard.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testCommercePaymentCard.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testCommercePaymentCard.getExpMonth()).isEqualTo(UPDATED_EXP_MONTH);
        assertThat(testCommercePaymentCard.getExpYear()).isEqualTo(UPDATED_EXP_YEAR);
        assertThat(testCommercePaymentCard.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testCommercePaymentCard.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercePaymentCard() throws Exception {
        int databaseSizeBeforeUpdate = commercePaymentCardRepository.findAll().size();

        // Create the CommercePaymentCard
        CommercePaymentCardDTO commercePaymentCardDTO = commercePaymentCardMapper.toDto(commercePaymentCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommercePaymentCardMockMvc.perform(put("/api/commerce-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercePaymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercePaymentCard in the database
        List<CommercePaymentCard> commercePaymentCardList = commercePaymentCardRepository.findAll();
        assertThat(commercePaymentCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommercePaymentCard() throws Exception {
        // Initialize the database
        commercePaymentCardRepository.saveAndFlush(commercePaymentCard);

        int databaseSizeBeforeDelete = commercePaymentCardRepository.findAll().size();

        // Get the commercePaymentCard
        restCommercePaymentCardMockMvc.perform(delete("/api/commerce-payment-cards/{id}", commercePaymentCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercePaymentCard> commercePaymentCardList = commercePaymentCardRepository.findAll();
        assertThat(commercePaymentCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercePaymentCard.class);
        CommercePaymentCard commercePaymentCard1 = new CommercePaymentCard();
        commercePaymentCard1.setId(1L);
        CommercePaymentCard commercePaymentCard2 = new CommercePaymentCard();
        commercePaymentCard2.setId(commercePaymentCard1.getId());
        assertThat(commercePaymentCard1).isEqualTo(commercePaymentCard2);
        commercePaymentCard2.setId(2L);
        assertThat(commercePaymentCard1).isNotEqualTo(commercePaymentCard2);
        commercePaymentCard1.setId(null);
        assertThat(commercePaymentCard1).isNotEqualTo(commercePaymentCard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercePaymentCardDTO.class);
        CommercePaymentCardDTO commercePaymentCardDTO1 = new CommercePaymentCardDTO();
        commercePaymentCardDTO1.setId(1L);
        CommercePaymentCardDTO commercePaymentCardDTO2 = new CommercePaymentCardDTO();
        assertThat(commercePaymentCardDTO1).isNotEqualTo(commercePaymentCardDTO2);
        commercePaymentCardDTO2.setId(commercePaymentCardDTO1.getId());
        assertThat(commercePaymentCardDTO1).isEqualTo(commercePaymentCardDTO2);
        commercePaymentCardDTO2.setId(2L);
        assertThat(commercePaymentCardDTO1).isNotEqualTo(commercePaymentCardDTO2);
        commercePaymentCardDTO1.setId(null);
        assertThat(commercePaymentCardDTO1).isNotEqualTo(commercePaymentCardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercePaymentCardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercePaymentCardMapper.fromId(null)).isNull();
    }
}
