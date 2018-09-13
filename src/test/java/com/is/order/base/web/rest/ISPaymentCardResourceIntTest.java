package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISPaymentCard;
import com.is.order.base.repository.ISPaymentCardRepository;
import com.is.order.base.service.ISPaymentCardService;
import com.is.order.base.service.dto.ISPaymentCardDTO;
import com.is.order.base.service.mapper.ISPaymentCardMapper;
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
 * Test class for the ISPaymentCardResource REST controller.
 *
 * @see ISPaymentCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISPaymentCardResourceIntTest {

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
    private ISPaymentCardRepository iSPaymentCardRepository;


    @Autowired
    private ISPaymentCardMapper iSPaymentCardMapper;
    

    @Autowired
    private ISPaymentCardService iSPaymentCardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISPaymentCardMockMvc;

    private ISPaymentCard iSPaymentCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISPaymentCardResource iSPaymentCardResource = new ISPaymentCardResource(iSPaymentCardService);
        this.restISPaymentCardMockMvc = MockMvcBuilders.standaloneSetup(iSPaymentCardResource)
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
    public static ISPaymentCard createEntity(EntityManager em) {
        ISPaymentCard iSPaymentCard = new ISPaymentCard()
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expMonth(DEFAULT_EXP_MONTH)
            .expYear(DEFAULT_EXP_YEAR)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSPaymentCard;
    }

    @Before
    public void initTest() {
        iSPaymentCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createISPaymentCard() throws Exception {
        int databaseSizeBeforeCreate = iSPaymentCardRepository.findAll().size();

        // Create the ISPaymentCard
        ISPaymentCardDTO iSPaymentCardDTO = iSPaymentCardMapper.toDto(iSPaymentCard);
        restISPaymentCardMockMvc.perform(post("/api/is-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSPaymentCardDTO)))
            .andExpect(status().isCreated());

        // Validate the ISPaymentCard in the database
        List<ISPaymentCard> iSPaymentCardList = iSPaymentCardRepository.findAll();
        assertThat(iSPaymentCardList).hasSize(databaseSizeBeforeCreate + 1);
        ISPaymentCard testISPaymentCard = iSPaymentCardList.get(iSPaymentCardList.size() - 1);
        assertThat(testISPaymentCard.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testISPaymentCard.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testISPaymentCard.getExpMonth()).isEqualTo(DEFAULT_EXP_MONTH);
        assertThat(testISPaymentCard.getExpYear()).isEqualTo(DEFAULT_EXP_YEAR);
        assertThat(testISPaymentCard.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testISPaymentCard.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISPaymentCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSPaymentCardRepository.findAll().size();

        // Create the ISPaymentCard with an existing ID
        iSPaymentCard.setId(1L);
        ISPaymentCardDTO iSPaymentCardDTO = iSPaymentCardMapper.toDto(iSPaymentCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISPaymentCardMockMvc.perform(post("/api/is-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSPaymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISPaymentCard in the database
        List<ISPaymentCard> iSPaymentCardList = iSPaymentCardRepository.findAll();
        assertThat(iSPaymentCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllISPaymentCards() throws Exception {
        // Initialize the database
        iSPaymentCardRepository.saveAndFlush(iSPaymentCard);

        // Get all the iSPaymentCardList
        restISPaymentCardMockMvc.perform(get("/api/is-payment-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSPaymentCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expMonth").value(hasItem(DEFAULT_EXP_MONTH)))
            .andExpect(jsonPath("$.[*].expYear").value(hasItem(DEFAULT_EXP_YEAR)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getISPaymentCard() throws Exception {
        // Initialize the database
        iSPaymentCardRepository.saveAndFlush(iSPaymentCard);

        // Get the iSPaymentCard
        restISPaymentCardMockMvc.perform(get("/api/is-payment-cards/{id}", iSPaymentCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSPaymentCard.getId().intValue()))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.expMonth").value(DEFAULT_EXP_MONTH))
            .andExpect(jsonPath("$.expYear").value(DEFAULT_EXP_YEAR))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISPaymentCard() throws Exception {
        // Get the iSPaymentCard
        restISPaymentCardMockMvc.perform(get("/api/is-payment-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISPaymentCard() throws Exception {
        // Initialize the database
        iSPaymentCardRepository.saveAndFlush(iSPaymentCard);

        int databaseSizeBeforeUpdate = iSPaymentCardRepository.findAll().size();

        // Update the iSPaymentCard
        ISPaymentCard updatedISPaymentCard = iSPaymentCardRepository.findById(iSPaymentCard.getId()).get();
        // Disconnect from session so that the updates on updatedISPaymentCard are not directly saved in db
        em.detach(updatedISPaymentCard);
        updatedISPaymentCard
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expMonth(UPDATED_EXP_MONTH)
            .expYear(UPDATED_EXP_YEAR)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISPaymentCardDTO iSPaymentCardDTO = iSPaymentCardMapper.toDto(updatedISPaymentCard);

        restISPaymentCardMockMvc.perform(put("/api/is-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSPaymentCardDTO)))
            .andExpect(status().isOk());

        // Validate the ISPaymentCard in the database
        List<ISPaymentCard> iSPaymentCardList = iSPaymentCardRepository.findAll();
        assertThat(iSPaymentCardList).hasSize(databaseSizeBeforeUpdate);
        ISPaymentCard testISPaymentCard = iSPaymentCardList.get(iSPaymentCardList.size() - 1);
        assertThat(testISPaymentCard.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testISPaymentCard.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testISPaymentCard.getExpMonth()).isEqualTo(UPDATED_EXP_MONTH);
        assertThat(testISPaymentCard.getExpYear()).isEqualTo(UPDATED_EXP_YEAR);
        assertThat(testISPaymentCard.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testISPaymentCard.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISPaymentCard() throws Exception {
        int databaseSizeBeforeUpdate = iSPaymentCardRepository.findAll().size();

        // Create the ISPaymentCard
        ISPaymentCardDTO iSPaymentCardDTO = iSPaymentCardMapper.toDto(iSPaymentCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISPaymentCardMockMvc.perform(put("/api/is-payment-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSPaymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISPaymentCard in the database
        List<ISPaymentCard> iSPaymentCardList = iSPaymentCardRepository.findAll();
        assertThat(iSPaymentCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISPaymentCard() throws Exception {
        // Initialize the database
        iSPaymentCardRepository.saveAndFlush(iSPaymentCard);

        int databaseSizeBeforeDelete = iSPaymentCardRepository.findAll().size();

        // Get the iSPaymentCard
        restISPaymentCardMockMvc.perform(delete("/api/is-payment-cards/{id}", iSPaymentCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISPaymentCard> iSPaymentCardList = iSPaymentCardRepository.findAll();
        assertThat(iSPaymentCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISPaymentCard.class);
        ISPaymentCard iSPaymentCard1 = new ISPaymentCard();
        iSPaymentCard1.setId(1L);
        ISPaymentCard iSPaymentCard2 = new ISPaymentCard();
        iSPaymentCard2.setId(iSPaymentCard1.getId());
        assertThat(iSPaymentCard1).isEqualTo(iSPaymentCard2);
        iSPaymentCard2.setId(2L);
        assertThat(iSPaymentCard1).isNotEqualTo(iSPaymentCard2);
        iSPaymentCard1.setId(null);
        assertThat(iSPaymentCard1).isNotEqualTo(iSPaymentCard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISPaymentCardDTO.class);
        ISPaymentCardDTO iSPaymentCardDTO1 = new ISPaymentCardDTO();
        iSPaymentCardDTO1.setId(1L);
        ISPaymentCardDTO iSPaymentCardDTO2 = new ISPaymentCardDTO();
        assertThat(iSPaymentCardDTO1).isNotEqualTo(iSPaymentCardDTO2);
        iSPaymentCardDTO2.setId(iSPaymentCardDTO1.getId());
        assertThat(iSPaymentCardDTO1).isEqualTo(iSPaymentCardDTO2);
        iSPaymentCardDTO2.setId(2L);
        assertThat(iSPaymentCardDTO1).isNotEqualTo(iSPaymentCardDTO2);
        iSPaymentCardDTO1.setId(null);
        assertThat(iSPaymentCardDTO1).isNotEqualTo(iSPaymentCardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSPaymentCardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSPaymentCardMapper.fromId(null)).isNull();
    }
}
