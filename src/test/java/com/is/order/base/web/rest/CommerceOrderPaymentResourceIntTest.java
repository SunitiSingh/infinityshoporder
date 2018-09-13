package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceOrderPayment;
import com.is.order.base.repository.CommerceOrderPaymentRepository;
import com.is.order.base.service.CommerceOrderPaymentService;
import com.is.order.base.service.dto.CommerceOrderPaymentDTO;
import com.is.order.base.service.mapper.CommerceOrderPaymentMapper;
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
import com.is.order.base.domain.enumeration.CommercePaymentType;
/**
 * Test class for the CommerceOrderPaymentResource REST controller.
 *
 * @see CommerceOrderPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceOrderPaymentResourceIntTest {

    private static final PayStatus DEFAULT_PAYSTATUS = PayStatus.INITIAL;
    private static final PayStatus UPDATED_PAYSTATUS = PayStatus.AUTHORIZED;

    private static final BigDecimal DEFAULT_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_AMOUNT = new BigDecimal(2);

    private static final CommercePaymentType DEFAULT_PAYMENT_TYPE = CommercePaymentType.CARD;
    private static final CommercePaymentType UPDATED_PAYMENT_TYPE = CommercePaymentType.EPAY;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PHONE = "BBBBBBBBBB";

    @Autowired
    private CommerceOrderPaymentRepository commerceOrderPaymentRepository;


    @Autowired
    private CommerceOrderPaymentMapper commerceOrderPaymentMapper;
    

    @Autowired
    private CommerceOrderPaymentService commerceOrderPaymentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceOrderPaymentMockMvc;

    private CommerceOrderPayment commerceOrderPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceOrderPaymentResource commerceOrderPaymentResource = new CommerceOrderPaymentResource(commerceOrderPaymentService);
        this.restCommerceOrderPaymentMockMvc = MockMvcBuilders.standaloneSetup(commerceOrderPaymentResource)
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
    public static CommerceOrderPayment createEntity(EntityManager em) {
        CommerceOrderPayment commerceOrderPayment = new CommerceOrderPayment()
            .paystatus(DEFAULT_PAYSTATUS)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .billingPhone(DEFAULT_BILLING_PHONE);
        return commerceOrderPayment;
    }

    @Before
    public void initTest() {
        commerceOrderPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceOrderPayment() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPaymentRepository.findAll().size();

        // Create the CommerceOrderPayment
        CommerceOrderPaymentDTO commerceOrderPaymentDTO = commerceOrderPaymentMapper.toDto(commerceOrderPayment);
        restCommerceOrderPaymentMockMvc.perform(post("/api/commerce-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceOrderPayment in the database
        List<CommerceOrderPayment> commerceOrderPaymentList = commerceOrderPaymentRepository.findAll();
        assertThat(commerceOrderPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceOrderPayment testCommerceOrderPayment = commerceOrderPaymentList.get(commerceOrderPaymentList.size() - 1);
        assertThat(testCommerceOrderPayment.getPaystatus()).isEqualTo(DEFAULT_PAYSTATUS);
        assertThat(testCommerceOrderPayment.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testCommerceOrderPayment.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testCommerceOrderPayment.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCommerceOrderPayment.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCommerceOrderPayment.getBillingPhone()).isEqualTo(DEFAULT_BILLING_PHONE);
    }

    @Test
    @Transactional
    public void createCommerceOrderPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPaymentRepository.findAll().size();

        // Create the CommerceOrderPayment with an existing ID
        commerceOrderPayment.setId(1L);
        CommerceOrderPaymentDTO commerceOrderPaymentDTO = commerceOrderPaymentMapper.toDto(commerceOrderPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceOrderPaymentMockMvc.perform(post("/api/commerce-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPayment in the database
        List<CommerceOrderPayment> commerceOrderPaymentList = commerceOrderPaymentRepository.findAll();
        assertThat(commerceOrderPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommerceOrderPayments() throws Exception {
        // Initialize the database
        commerceOrderPaymentRepository.saveAndFlush(commerceOrderPayment);

        // Get all the commerceOrderPaymentList
        restCommerceOrderPaymentMockMvc.perform(get("/api/commerce-order-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceOrderPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paystatus").value(hasItem(DEFAULT_PAYSTATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].billingPhone").value(hasItem(DEFAULT_BILLING_PHONE.toString())));
    }
    

    @Test
    @Transactional
    public void getCommerceOrderPayment() throws Exception {
        // Initialize the database
        commerceOrderPaymentRepository.saveAndFlush(commerceOrderPayment);

        // Get the commerceOrderPayment
        restCommerceOrderPaymentMockMvc.perform(get("/api/commerce-order-payments/{id}", commerceOrderPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceOrderPayment.getId().intValue()))
            .andExpect(jsonPath("$.paystatus").value(DEFAULT_PAYSTATUS.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.billingPhone").value(DEFAULT_BILLING_PHONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceOrderPayment() throws Exception {
        // Get the commerceOrderPayment
        restCommerceOrderPaymentMockMvc.perform(get("/api/commerce-order-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceOrderPayment() throws Exception {
        // Initialize the database
        commerceOrderPaymentRepository.saveAndFlush(commerceOrderPayment);

        int databaseSizeBeforeUpdate = commerceOrderPaymentRepository.findAll().size();

        // Update the commerceOrderPayment
        CommerceOrderPayment updatedCommerceOrderPayment = commerceOrderPaymentRepository.findById(commerceOrderPayment.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceOrderPayment are not directly saved in db
        em.detach(updatedCommerceOrderPayment);
        updatedCommerceOrderPayment
            .paystatus(UPDATED_PAYSTATUS)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .billingPhone(UPDATED_BILLING_PHONE);
        CommerceOrderPaymentDTO commerceOrderPaymentDTO = commerceOrderPaymentMapper.toDto(updatedCommerceOrderPayment);

        restCommerceOrderPaymentMockMvc.perform(put("/api/commerce-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceOrderPayment in the database
        List<CommerceOrderPayment> commerceOrderPaymentList = commerceOrderPaymentRepository.findAll();
        assertThat(commerceOrderPaymentList).hasSize(databaseSizeBeforeUpdate);
        CommerceOrderPayment testCommerceOrderPayment = commerceOrderPaymentList.get(commerceOrderPaymentList.size() - 1);
        assertThat(testCommerceOrderPayment.getPaystatus()).isEqualTo(UPDATED_PAYSTATUS);
        assertThat(testCommerceOrderPayment.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testCommerceOrderPayment.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testCommerceOrderPayment.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCommerceOrderPayment.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCommerceOrderPayment.getBillingPhone()).isEqualTo(UPDATED_BILLING_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceOrderPayment() throws Exception {
        int databaseSizeBeforeUpdate = commerceOrderPaymentRepository.findAll().size();

        // Create the CommerceOrderPayment
        CommerceOrderPaymentDTO commerceOrderPaymentDTO = commerceOrderPaymentMapper.toDto(commerceOrderPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceOrderPaymentMockMvc.perform(put("/api/commerce-order-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPayment in the database
        List<CommerceOrderPayment> commerceOrderPaymentList = commerceOrderPaymentRepository.findAll();
        assertThat(commerceOrderPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceOrderPayment() throws Exception {
        // Initialize the database
        commerceOrderPaymentRepository.saveAndFlush(commerceOrderPayment);

        int databaseSizeBeforeDelete = commerceOrderPaymentRepository.findAll().size();

        // Get the commerceOrderPayment
        restCommerceOrderPaymentMockMvc.perform(delete("/api/commerce-order-payments/{id}", commerceOrderPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceOrderPayment> commerceOrderPaymentList = commerceOrderPaymentRepository.findAll();
        assertThat(commerceOrderPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPayment.class);
        CommerceOrderPayment commerceOrderPayment1 = new CommerceOrderPayment();
        commerceOrderPayment1.setId(1L);
        CommerceOrderPayment commerceOrderPayment2 = new CommerceOrderPayment();
        commerceOrderPayment2.setId(commerceOrderPayment1.getId());
        assertThat(commerceOrderPayment1).isEqualTo(commerceOrderPayment2);
        commerceOrderPayment2.setId(2L);
        assertThat(commerceOrderPayment1).isNotEqualTo(commerceOrderPayment2);
        commerceOrderPayment1.setId(null);
        assertThat(commerceOrderPayment1).isNotEqualTo(commerceOrderPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPaymentDTO.class);
        CommerceOrderPaymentDTO commerceOrderPaymentDTO1 = new CommerceOrderPaymentDTO();
        commerceOrderPaymentDTO1.setId(1L);
        CommerceOrderPaymentDTO commerceOrderPaymentDTO2 = new CommerceOrderPaymentDTO();
        assertThat(commerceOrderPaymentDTO1).isNotEqualTo(commerceOrderPaymentDTO2);
        commerceOrderPaymentDTO2.setId(commerceOrderPaymentDTO1.getId());
        assertThat(commerceOrderPaymentDTO1).isEqualTo(commerceOrderPaymentDTO2);
        commerceOrderPaymentDTO2.setId(2L);
        assertThat(commerceOrderPaymentDTO1).isNotEqualTo(commerceOrderPaymentDTO2);
        commerceOrderPaymentDTO1.setId(null);
        assertThat(commerceOrderPaymentDTO1).isNotEqualTo(commerceOrderPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceOrderPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceOrderPaymentMapper.fromId(null)).isNull();
    }
}
