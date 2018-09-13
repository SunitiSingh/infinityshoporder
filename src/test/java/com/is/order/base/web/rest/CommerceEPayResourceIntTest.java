package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceEPay;
import com.is.order.base.repository.CommerceEPayRepository;
import com.is.order.base.service.CommerceEPayService;
import com.is.order.base.service.dto.CommerceEPayDTO;
import com.is.order.base.service.mapper.CommerceEPayMapper;
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
 * Test class for the CommerceEPayResource REST controller.
 *
 * @see CommerceEPayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceEPayResourceIntTest {

    private static final String DEFAULT_E_PAY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_E_PAY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_E_PAY_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_E_PAY_TOKEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommerceEPayRepository commerceEPayRepository;


    @Autowired
    private CommerceEPayMapper commerceEPayMapper;
    

    @Autowired
    private CommerceEPayService commerceEPayService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceEPayMockMvc;

    private CommerceEPay commerceEPay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceEPayResource commerceEPayResource = new CommerceEPayResource(commerceEPayService);
        this.restCommerceEPayMockMvc = MockMvcBuilders.standaloneSetup(commerceEPayResource)
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
    public static CommerceEPay createEntity(EntityManager em) {
        CommerceEPay commerceEPay = new CommerceEPay()
            .ePayType(DEFAULT_E_PAY_TYPE)
            .ePayToken(DEFAULT_E_PAY_TOKEN)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commerceEPay;
    }

    @Before
    public void initTest() {
        commerceEPay = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceEPay() throws Exception {
        int databaseSizeBeforeCreate = commerceEPayRepository.findAll().size();

        // Create the CommerceEPay
        CommerceEPayDTO commerceEPayDTO = commerceEPayMapper.toDto(commerceEPay);
        restCommerceEPayMockMvc.perform(post("/api/commerce-e-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceEPayDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceEPay in the database
        List<CommerceEPay> commerceEPayList = commerceEPayRepository.findAll();
        assertThat(commerceEPayList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceEPay testCommerceEPay = commerceEPayList.get(commerceEPayList.size() - 1);
        assertThat(testCommerceEPay.getePayType()).isEqualTo(DEFAULT_E_PAY_TYPE);
        assertThat(testCommerceEPay.getePayToken()).isEqualTo(DEFAULT_E_PAY_TOKEN);
        assertThat(testCommerceEPay.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testCommerceEPay.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommerceEPayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceEPayRepository.findAll().size();

        // Create the CommerceEPay with an existing ID
        commerceEPay.setId(1L);
        CommerceEPayDTO commerceEPayDTO = commerceEPayMapper.toDto(commerceEPay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceEPayMockMvc.perform(post("/api/commerce-e-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceEPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceEPay in the database
        List<CommerceEPay> commerceEPayList = commerceEPayRepository.findAll();
        assertThat(commerceEPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommerceEPays() throws Exception {
        // Initialize the database
        commerceEPayRepository.saveAndFlush(commerceEPay);

        // Get all the commerceEPayList
        restCommerceEPayMockMvc.perform(get("/api/commerce-e-pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceEPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].ePayType").value(hasItem(DEFAULT_E_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ePayToken").value(hasItem(DEFAULT_E_PAY_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getCommerceEPay() throws Exception {
        // Initialize the database
        commerceEPayRepository.saveAndFlush(commerceEPay);

        // Get the commerceEPay
        restCommerceEPayMockMvc.perform(get("/api/commerce-e-pays/{id}", commerceEPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceEPay.getId().intValue()))
            .andExpect(jsonPath("$.ePayType").value(DEFAULT_E_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.ePayToken").value(DEFAULT_E_PAY_TOKEN.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceEPay() throws Exception {
        // Get the commerceEPay
        restCommerceEPayMockMvc.perform(get("/api/commerce-e-pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceEPay() throws Exception {
        // Initialize the database
        commerceEPayRepository.saveAndFlush(commerceEPay);

        int databaseSizeBeforeUpdate = commerceEPayRepository.findAll().size();

        // Update the commerceEPay
        CommerceEPay updatedCommerceEPay = commerceEPayRepository.findById(commerceEPay.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceEPay are not directly saved in db
        em.detach(updatedCommerceEPay);
        updatedCommerceEPay
            .ePayType(UPDATED_E_PAY_TYPE)
            .ePayToken(UPDATED_E_PAY_TOKEN)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommerceEPayDTO commerceEPayDTO = commerceEPayMapper.toDto(updatedCommerceEPay);

        restCommerceEPayMockMvc.perform(put("/api/commerce-e-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceEPayDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceEPay in the database
        List<CommerceEPay> commerceEPayList = commerceEPayRepository.findAll();
        assertThat(commerceEPayList).hasSize(databaseSizeBeforeUpdate);
        CommerceEPay testCommerceEPay = commerceEPayList.get(commerceEPayList.size() - 1);
        assertThat(testCommerceEPay.getePayType()).isEqualTo(UPDATED_E_PAY_TYPE);
        assertThat(testCommerceEPay.getePayToken()).isEqualTo(UPDATED_E_PAY_TOKEN);
        assertThat(testCommerceEPay.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testCommerceEPay.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceEPay() throws Exception {
        int databaseSizeBeforeUpdate = commerceEPayRepository.findAll().size();

        // Create the CommerceEPay
        CommerceEPayDTO commerceEPayDTO = commerceEPayMapper.toDto(commerceEPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceEPayMockMvc.perform(put("/api/commerce-e-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceEPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceEPay in the database
        List<CommerceEPay> commerceEPayList = commerceEPayRepository.findAll();
        assertThat(commerceEPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceEPay() throws Exception {
        // Initialize the database
        commerceEPayRepository.saveAndFlush(commerceEPay);

        int databaseSizeBeforeDelete = commerceEPayRepository.findAll().size();

        // Get the commerceEPay
        restCommerceEPayMockMvc.perform(delete("/api/commerce-e-pays/{id}", commerceEPay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceEPay> commerceEPayList = commerceEPayRepository.findAll();
        assertThat(commerceEPayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceEPay.class);
        CommerceEPay commerceEPay1 = new CommerceEPay();
        commerceEPay1.setId(1L);
        CommerceEPay commerceEPay2 = new CommerceEPay();
        commerceEPay2.setId(commerceEPay1.getId());
        assertThat(commerceEPay1).isEqualTo(commerceEPay2);
        commerceEPay2.setId(2L);
        assertThat(commerceEPay1).isNotEqualTo(commerceEPay2);
        commerceEPay1.setId(null);
        assertThat(commerceEPay1).isNotEqualTo(commerceEPay2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceEPayDTO.class);
        CommerceEPayDTO commerceEPayDTO1 = new CommerceEPayDTO();
        commerceEPayDTO1.setId(1L);
        CommerceEPayDTO commerceEPayDTO2 = new CommerceEPayDTO();
        assertThat(commerceEPayDTO1).isNotEqualTo(commerceEPayDTO2);
        commerceEPayDTO2.setId(commerceEPayDTO1.getId());
        assertThat(commerceEPayDTO1).isEqualTo(commerceEPayDTO2);
        commerceEPayDTO2.setId(2L);
        assertThat(commerceEPayDTO1).isNotEqualTo(commerceEPayDTO2);
        commerceEPayDTO1.setId(null);
        assertThat(commerceEPayDTO1).isNotEqualTo(commerceEPayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceEPayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceEPayMapper.fromId(null)).isNull();
    }
}
