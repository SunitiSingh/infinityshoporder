package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISEPay;
import com.is.order.base.repository.ISEPayRepository;
import com.is.order.base.service.ISEPayService;
import com.is.order.base.service.dto.ISEPayDTO;
import com.is.order.base.service.mapper.ISEPayMapper;
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
 * Test class for the ISEPayResource REST controller.
 *
 * @see ISEPayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISEPayResourceIntTest {

    private static final String DEFAULT_E_PAY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_E_PAY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_E_PAY_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_E_PAY_TOKEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ISEPayRepository iSEPayRepository;


    @Autowired
    private ISEPayMapper iSEPayMapper;
    

    @Autowired
    private ISEPayService iSEPayService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISEPayMockMvc;

    private ISEPay iSEPay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISEPayResource iSEPayResource = new ISEPayResource(iSEPayService);
        this.restISEPayMockMvc = MockMvcBuilders.standaloneSetup(iSEPayResource)
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
    public static ISEPay createEntity(EntityManager em) {
        ISEPay iSEPay = new ISEPay()
            .ePayType(DEFAULT_E_PAY_TYPE)
            .ePayToken(DEFAULT_E_PAY_TOKEN)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSEPay;
    }

    @Before
    public void initTest() {
        iSEPay = createEntity(em);
    }

    @Test
    @Transactional
    public void createISEPay() throws Exception {
        int databaseSizeBeforeCreate = iSEPayRepository.findAll().size();

        // Create the ISEPay
        ISEPayDTO iSEPayDTO = iSEPayMapper.toDto(iSEPay);
        restISEPayMockMvc.perform(post("/api/ise-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSEPayDTO)))
            .andExpect(status().isCreated());

        // Validate the ISEPay in the database
        List<ISEPay> iSEPayList = iSEPayRepository.findAll();
        assertThat(iSEPayList).hasSize(databaseSizeBeforeCreate + 1);
        ISEPay testISEPay = iSEPayList.get(iSEPayList.size() - 1);
        assertThat(testISEPay.getePayType()).isEqualTo(DEFAULT_E_PAY_TYPE);
        assertThat(testISEPay.getePayToken()).isEqualTo(DEFAULT_E_PAY_TOKEN);
        assertThat(testISEPay.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testISEPay.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISEPayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSEPayRepository.findAll().size();

        // Create the ISEPay with an existing ID
        iSEPay.setId(1L);
        ISEPayDTO iSEPayDTO = iSEPayMapper.toDto(iSEPay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISEPayMockMvc.perform(post("/api/ise-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSEPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISEPay in the database
        List<ISEPay> iSEPayList = iSEPayRepository.findAll();
        assertThat(iSEPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllISEPays() throws Exception {
        // Initialize the database
        iSEPayRepository.saveAndFlush(iSEPay);

        // Get all the iSEPayList
        restISEPayMockMvc.perform(get("/api/ise-pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSEPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].ePayType").value(hasItem(DEFAULT_E_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ePayToken").value(hasItem(DEFAULT_E_PAY_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getISEPay() throws Exception {
        // Initialize the database
        iSEPayRepository.saveAndFlush(iSEPay);

        // Get the iSEPay
        restISEPayMockMvc.perform(get("/api/ise-pays/{id}", iSEPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSEPay.getId().intValue()))
            .andExpect(jsonPath("$.ePayType").value(DEFAULT_E_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.ePayToken").value(DEFAULT_E_PAY_TOKEN.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISEPay() throws Exception {
        // Get the iSEPay
        restISEPayMockMvc.perform(get("/api/ise-pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISEPay() throws Exception {
        // Initialize the database
        iSEPayRepository.saveAndFlush(iSEPay);

        int databaseSizeBeforeUpdate = iSEPayRepository.findAll().size();

        // Update the iSEPay
        ISEPay updatedISEPay = iSEPayRepository.findById(iSEPay.getId()).get();
        // Disconnect from session so that the updates on updatedISEPay are not directly saved in db
        em.detach(updatedISEPay);
        updatedISEPay
            .ePayType(UPDATED_E_PAY_TYPE)
            .ePayToken(UPDATED_E_PAY_TOKEN)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISEPayDTO iSEPayDTO = iSEPayMapper.toDto(updatedISEPay);

        restISEPayMockMvc.perform(put("/api/ise-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSEPayDTO)))
            .andExpect(status().isOk());

        // Validate the ISEPay in the database
        List<ISEPay> iSEPayList = iSEPayRepository.findAll();
        assertThat(iSEPayList).hasSize(databaseSizeBeforeUpdate);
        ISEPay testISEPay = iSEPayList.get(iSEPayList.size() - 1);
        assertThat(testISEPay.getePayType()).isEqualTo(UPDATED_E_PAY_TYPE);
        assertThat(testISEPay.getePayToken()).isEqualTo(UPDATED_E_PAY_TOKEN);
        assertThat(testISEPay.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testISEPay.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISEPay() throws Exception {
        int databaseSizeBeforeUpdate = iSEPayRepository.findAll().size();

        // Create the ISEPay
        ISEPayDTO iSEPayDTO = iSEPayMapper.toDto(iSEPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISEPayMockMvc.perform(put("/api/ise-pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSEPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISEPay in the database
        List<ISEPay> iSEPayList = iSEPayRepository.findAll();
        assertThat(iSEPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISEPay() throws Exception {
        // Initialize the database
        iSEPayRepository.saveAndFlush(iSEPay);

        int databaseSizeBeforeDelete = iSEPayRepository.findAll().size();

        // Get the iSEPay
        restISEPayMockMvc.perform(delete("/api/ise-pays/{id}", iSEPay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISEPay> iSEPayList = iSEPayRepository.findAll();
        assertThat(iSEPayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISEPay.class);
        ISEPay iSEPay1 = new ISEPay();
        iSEPay1.setId(1L);
        ISEPay iSEPay2 = new ISEPay();
        iSEPay2.setId(iSEPay1.getId());
        assertThat(iSEPay1).isEqualTo(iSEPay2);
        iSEPay2.setId(2L);
        assertThat(iSEPay1).isNotEqualTo(iSEPay2);
        iSEPay1.setId(null);
        assertThat(iSEPay1).isNotEqualTo(iSEPay2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISEPayDTO.class);
        ISEPayDTO iSEPayDTO1 = new ISEPayDTO();
        iSEPayDTO1.setId(1L);
        ISEPayDTO iSEPayDTO2 = new ISEPayDTO();
        assertThat(iSEPayDTO1).isNotEqualTo(iSEPayDTO2);
        iSEPayDTO2.setId(iSEPayDTO1.getId());
        assertThat(iSEPayDTO1).isEqualTo(iSEPayDTO2);
        iSEPayDTO2.setId(2L);
        assertThat(iSEPayDTO1).isNotEqualTo(iSEPayDTO2);
        iSEPayDTO1.setId(null);
        assertThat(iSEPayDTO1).isNotEqualTo(iSEPayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSEPayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSEPayMapper.fromId(null)).isNull();
    }
}
