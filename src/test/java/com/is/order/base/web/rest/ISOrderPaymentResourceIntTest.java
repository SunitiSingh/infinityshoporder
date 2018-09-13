package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISOrderPayment;
import com.is.order.base.repository.ISOrderPaymentRepository;
import com.is.order.base.service.ISOrderPaymentService;
import com.is.order.base.service.dto.ISOrderPaymentDTO;
import com.is.order.base.service.mapper.ISOrderPaymentMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static com.is.order.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.is.order.base.domain.enumeration.PayStatus;
import com.is.order.base.domain.enumeration.ISPaymentType;
/**
 * Test class for the ISOrderPaymentResource REST controller.
 *
 * @see ISOrderPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISOrderPaymentResourceIntTest {

    private static final PayStatus DEFAULT_PAYSTATUS = PayStatus.INITIAL;
    private static final PayStatus UPDATED_PAYSTATUS = PayStatus.AUTHORIZED;

    private static final BigDecimal DEFAULT_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_AMOUNT = new BigDecimal(2);

    private static final ISPaymentType DEFAULT_PAYMENT_TYPE = ISPaymentType.CARD;
    private static final ISPaymentType UPDATED_PAYMENT_TYPE = ISPaymentType.EPAY;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PHONE = "BBBBBBBBBB";

    @Autowired
    private ISOrderPaymentRepository iSOrderPaymentRepository;


    @Autowired
    private ISOrderPaymentMapper iSOrderPaymentMapper;
    

    @Autowired
    private ISOrderPaymentService iSOrderPaymentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISOrderPaymentMockMvc;

    private ISOrderPayment iSOrderPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISOrderPaymentResource iSOrderPaymentResource = new ISOrderPaymentResource(iSOrderPaymentService);
        this.restISOrderPaymentMockMvc = MockMvcBuilders.standaloneSetup(iSOrderPaymentResource)
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
    public static ISOrderPayment createEntity(EntityManager em) {
        ISOrderPayment iSOrderPayment = new ISOrderPayment()
            .paystatus(DEFAULT_PAYSTATUS)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .billingPhone(DEFAULT_BILLING_PHONE);
        return iSOrderPayment;
    }

    @Before
    public void initTest() {
        iSOrderPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createISOrderPayment() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPaymentRepository.findAll().size();

        // Create the ISOrderPayment
        ISOrderPaymentDTO iSOrderPaymentDTO = iSOrderPaymentMapper.toDto(iSOrderPayment);
        restISOrderPaymentMockMvc.perform(post("/api/is-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the ISOrderPayment in the database
        List<ISOrderPayment> iSOrderPaymentList = iSOrderPaymentRepository.findAll();
        assertThat(iSOrderPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        ISOrderPayment testISOrderPayment = iSOrderPaymentList.get(iSOrderPaymentList.size() - 1);
        assertThat(testISOrderPayment.getPaystatus()).isEqualTo(DEFAULT_PAYSTATUS);
        assertThat(testISOrderPayment.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testISOrderPayment.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testISOrderPayment.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testISOrderPayment.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testISOrderPayment.getBillingPhone()).isEqualTo(DEFAULT_BILLING_PHONE);
    }

    @Test
    @Transactional
    public void createISOrderPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPaymentRepository.findAll().size();

        // Create the ISOrderPayment with an existing ID
        iSOrderPayment.setId(1L);
        ISOrderPaymentDTO iSOrderPaymentDTO = iSOrderPaymentMapper.toDto(iSOrderPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISOrderPaymentMockMvc.perform(post("/api/is-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPayment in the database
        List<ISOrderPayment> iSOrderPaymentList = iSOrderPaymentRepository.findAll();
        assertThat(iSOrderPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllISOrderPayments() throws Exception {
        // Initialize the database
        iSOrderPaymentRepository.saveAndFlush(iSOrderPayment);

        // Get all the iSOrderPaymentList
        restISOrderPaymentMockMvc.perform(get("/api/is-order-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSOrderPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paystatus").value(hasItem(DEFAULT_PAYSTATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].billingPhone").value(hasItem(DEFAULT_BILLING_PHONE.toString())));
    }
    

    @Test
    @Transactional
    public void getISOrderPayment() throws Exception {
        // Initialize the database
        iSOrderPaymentRepository.saveAndFlush(iSOrderPayment);

        // Get the iSOrderPayment
        restISOrderPaymentMockMvc.perform(get("/api/is-order-payments/{id}", iSOrderPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSOrderPayment.getId().intValue()))
            .andExpect(jsonPath("$.paystatus").value(DEFAULT_PAYSTATUS.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.billingPhone").value(DEFAULT_BILLING_PHONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingISOrderPayment() throws Exception {
        // Get the iSOrderPayment
        restISOrderPaymentMockMvc.perform(get("/api/is-order-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISOrderPayment() throws Exception {
        // Initialize the database
        iSOrderPaymentRepository.saveAndFlush(iSOrderPayment);

        int databaseSizeBeforeUpdate = iSOrderPaymentRepository.findAll().size();

        // Update the iSOrderPayment
        ISOrderPayment updatedISOrderPayment = iSOrderPaymentRepository.findById(iSOrderPayment.getId()).get();
        // Disconnect from session so that the updates on updatedISOrderPayment are not directly saved in db
        em.detach(updatedISOrderPayment);
        updatedISOrderPayment
            .paystatus(UPDATED_PAYSTATUS)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .billingPhone(UPDATED_BILLING_PHONE);
        ISOrderPaymentDTO iSOrderPaymentDTO = iSOrderPaymentMapper.toDto(updatedISOrderPayment);

        restISOrderPaymentMockMvc.perform(put("/api/is-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the ISOrderPayment in the database
        List<ISOrderPayment> iSOrderPaymentList = iSOrderPaymentRepository.findAll();
        assertThat(iSOrderPaymentList).hasSize(databaseSizeBeforeUpdate);
        ISOrderPayment testISOrderPayment = iSOrderPaymentList.get(iSOrderPaymentList.size() - 1);
        assertThat(testISOrderPayment.getPaystatus()).isEqualTo(UPDATED_PAYSTATUS);
        assertThat(testISOrderPayment.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testISOrderPayment.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testISOrderPayment.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testISOrderPayment.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testISOrderPayment.getBillingPhone()).isEqualTo(UPDATED_BILLING_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingISOrderPayment() throws Exception {
        int databaseSizeBeforeUpdate = iSOrderPaymentRepository.findAll().size();

        // Create the ISOrderPayment
        ISOrderPaymentDTO iSOrderPaymentDTO = iSOrderPaymentMapper.toDto(iSOrderPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISOrderPaymentMockMvc.perform(put("/api/is-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPayment in the database
        List<ISOrderPayment> iSOrderPaymentList = iSOrderPaymentRepository.findAll();
        assertThat(iSOrderPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISOrderPayment() throws Exception {
        // Initialize the database
        iSOrderPaymentRepository.saveAndFlush(iSOrderPayment);

        int databaseSizeBeforeDelete = iSOrderPaymentRepository.findAll().size();

        // Get the iSOrderPayment
        restISOrderPaymentMockMvc.perform(delete("/api/is-order-payments/{id}", iSOrderPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISOrderPayment> iSOrderPaymentList = iSOrderPaymentRepository.findAll();
        assertThat(iSOrderPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPayment.class);
        ISOrderPayment iSOrderPayment1 = new ISOrderPayment();
        iSOrderPayment1.setId(1L);
        ISOrderPayment iSOrderPayment2 = new ISOrderPayment();
        iSOrderPayment2.setId(iSOrderPayment1.getId());
        assertThat(iSOrderPayment1).isEqualTo(iSOrderPayment2);
        iSOrderPayment2.setId(2L);
        assertThat(iSOrderPayment1).isNotEqualTo(iSOrderPayment2);
        iSOrderPayment1.setId(null);
        assertThat(iSOrderPayment1).isNotEqualTo(iSOrderPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPaymentDTO.class);
        ISOrderPaymentDTO iSOrderPaymentDTO1 = new ISOrderPaymentDTO();
        iSOrderPaymentDTO1.setId(1L);
        ISOrderPaymentDTO iSOrderPaymentDTO2 = new ISOrderPaymentDTO();
        assertThat(iSOrderPaymentDTO1).isNotEqualTo(iSOrderPaymentDTO2);
        iSOrderPaymentDTO2.setId(iSOrderPaymentDTO1.getId());
        assertThat(iSOrderPaymentDTO1).isEqualTo(iSOrderPaymentDTO2);
        iSOrderPaymentDTO2.setId(2L);
        assertThat(iSOrderPaymentDTO1).isNotEqualTo(iSOrderPaymentDTO2);
        iSOrderPaymentDTO1.setId(null);
        assertThat(iSOrderPaymentDTO1).isNotEqualTo(iSOrderPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSOrderPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSOrderPaymentMapper.fromId(null)).isNull();
    }
}
