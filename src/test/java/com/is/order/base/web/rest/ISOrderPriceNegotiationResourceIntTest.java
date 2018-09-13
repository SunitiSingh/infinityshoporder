package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISOrderPriceNegotiation;
import com.is.order.base.repository.ISOrderPriceNegotiationRepository;
import com.is.order.base.service.ISOrderPriceNegotiationService;
import com.is.order.base.service.dto.ISOrderPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISOrderPriceNegotiationMapper;
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

/**
 * Test class for the ISOrderPriceNegotiationResource REST controller.
 *
 * @see ISOrderPriceNegotiationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISOrderPriceNegotiationResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_NEGOTIATIONID = "AAAAAAAAAA";
    private static final String UPDATED_NEGOTIATIONID = "BBBBBBBBBB";

    @Autowired
    private ISOrderPriceNegotiationRepository iSOrderPriceNegotiationRepository;


    @Autowired
    private ISOrderPriceNegotiationMapper iSOrderPriceNegotiationMapper;
    

    @Autowired
    private ISOrderPriceNegotiationService iSOrderPriceNegotiationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISOrderPriceNegotiationMockMvc;

    private ISOrderPriceNegotiation iSOrderPriceNegotiation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISOrderPriceNegotiationResource iSOrderPriceNegotiationResource = new ISOrderPriceNegotiationResource(iSOrderPriceNegotiationService);
        this.restISOrderPriceNegotiationMockMvc = MockMvcBuilders.standaloneSetup(iSOrderPriceNegotiationResource)
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
    public static ISOrderPriceNegotiation createEntity(EntityManager em) {
        ISOrderPriceNegotiation iSOrderPriceNegotiation = new ISOrderPriceNegotiation()
            .price(DEFAULT_PRICE)
            .negotiationid(DEFAULT_NEGOTIATIONID);
        return iSOrderPriceNegotiation;
    }

    @Before
    public void initTest() {
        iSOrderPriceNegotiation = createEntity(em);
    }

    @Test
    @Transactional
    public void createISOrderPriceNegotiation() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPriceNegotiationRepository.findAll().size();

        // Create the ISOrderPriceNegotiation
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationMapper.toDto(iSOrderPriceNegotiation);
        restISOrderPriceNegotiationMockMvc.perform(post("/api/is-order-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceNegotiationDTO)))
            .andExpect(status().isCreated());

        // Validate the ISOrderPriceNegotiation in the database
        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeCreate + 1);
        ISOrderPriceNegotiation testISOrderPriceNegotiation = iSOrderPriceNegotiationList.get(iSOrderPriceNegotiationList.size() - 1);
        assertThat(testISOrderPriceNegotiation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testISOrderPriceNegotiation.getNegotiationid()).isEqualTo(DEFAULT_NEGOTIATIONID);
    }

    @Test
    @Transactional
    public void createISOrderPriceNegotiationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPriceNegotiationRepository.findAll().size();

        // Create the ISOrderPriceNegotiation with an existing ID
        iSOrderPriceNegotiation.setId(1L);
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationMapper.toDto(iSOrderPriceNegotiation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISOrderPriceNegotiationMockMvc.perform(post("/api/is-order-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPriceNegotiation in the database
        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSOrderPriceNegotiationRepository.findAll().size();
        // set the field null
        iSOrderPriceNegotiation.setPrice(null);

        // Create the ISOrderPriceNegotiation, which fails.
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationMapper.toDto(iSOrderPriceNegotiation);

        restISOrderPriceNegotiationMockMvc.perform(post("/api/is-order-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISOrderPriceNegotiations() throws Exception {
        // Initialize the database
        iSOrderPriceNegotiationRepository.saveAndFlush(iSOrderPriceNegotiation);

        // Get all the iSOrderPriceNegotiationList
        restISOrderPriceNegotiationMockMvc.perform(get("/api/is-order-price-negotiations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSOrderPriceNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].negotiationid").value(hasItem(DEFAULT_NEGOTIATIONID.toString())));
    }
    

    @Test
    @Transactional
    public void getISOrderPriceNegotiation() throws Exception {
        // Initialize the database
        iSOrderPriceNegotiationRepository.saveAndFlush(iSOrderPriceNegotiation);

        // Get the iSOrderPriceNegotiation
        restISOrderPriceNegotiationMockMvc.perform(get("/api/is-order-price-negotiations/{id}", iSOrderPriceNegotiation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSOrderPriceNegotiation.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.negotiationid").value(DEFAULT_NEGOTIATIONID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingISOrderPriceNegotiation() throws Exception {
        // Get the iSOrderPriceNegotiation
        restISOrderPriceNegotiationMockMvc.perform(get("/api/is-order-price-negotiations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISOrderPriceNegotiation() throws Exception {
        // Initialize the database
        iSOrderPriceNegotiationRepository.saveAndFlush(iSOrderPriceNegotiation);

        int databaseSizeBeforeUpdate = iSOrderPriceNegotiationRepository.findAll().size();

        // Update the iSOrderPriceNegotiation
        ISOrderPriceNegotiation updatedISOrderPriceNegotiation = iSOrderPriceNegotiationRepository.findById(iSOrderPriceNegotiation.getId()).get();
        // Disconnect from session so that the updates on updatedISOrderPriceNegotiation are not directly saved in db
        em.detach(updatedISOrderPriceNegotiation);
        updatedISOrderPriceNegotiation
            .price(UPDATED_PRICE)
            .negotiationid(UPDATED_NEGOTIATIONID);
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationMapper.toDto(updatedISOrderPriceNegotiation);

        restISOrderPriceNegotiationMockMvc.perform(put("/api/is-order-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceNegotiationDTO)))
            .andExpect(status().isOk());

        // Validate the ISOrderPriceNegotiation in the database
        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
        ISOrderPriceNegotiation testISOrderPriceNegotiation = iSOrderPriceNegotiationList.get(iSOrderPriceNegotiationList.size() - 1);
        assertThat(testISOrderPriceNegotiation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testISOrderPriceNegotiation.getNegotiationid()).isEqualTo(UPDATED_NEGOTIATIONID);
    }

    @Test
    @Transactional
    public void updateNonExistingISOrderPriceNegotiation() throws Exception {
        int databaseSizeBeforeUpdate = iSOrderPriceNegotiationRepository.findAll().size();

        // Create the ISOrderPriceNegotiation
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO = iSOrderPriceNegotiationMapper.toDto(iSOrderPriceNegotiation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISOrderPriceNegotiationMockMvc.perform(put("/api/is-order-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPriceNegotiation in the database
        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISOrderPriceNegotiation() throws Exception {
        // Initialize the database
        iSOrderPriceNegotiationRepository.saveAndFlush(iSOrderPriceNegotiation);

        int databaseSizeBeforeDelete = iSOrderPriceNegotiationRepository.findAll().size();

        // Get the iSOrderPriceNegotiation
        restISOrderPriceNegotiationMockMvc.perform(delete("/api/is-order-price-negotiations/{id}", iSOrderPriceNegotiation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISOrderPriceNegotiation> iSOrderPriceNegotiationList = iSOrderPriceNegotiationRepository.findAll();
        assertThat(iSOrderPriceNegotiationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPriceNegotiation.class);
        ISOrderPriceNegotiation iSOrderPriceNegotiation1 = new ISOrderPriceNegotiation();
        iSOrderPriceNegotiation1.setId(1L);
        ISOrderPriceNegotiation iSOrderPriceNegotiation2 = new ISOrderPriceNegotiation();
        iSOrderPriceNegotiation2.setId(iSOrderPriceNegotiation1.getId());
        assertThat(iSOrderPriceNegotiation1).isEqualTo(iSOrderPriceNegotiation2);
        iSOrderPriceNegotiation2.setId(2L);
        assertThat(iSOrderPriceNegotiation1).isNotEqualTo(iSOrderPriceNegotiation2);
        iSOrderPriceNegotiation1.setId(null);
        assertThat(iSOrderPriceNegotiation1).isNotEqualTo(iSOrderPriceNegotiation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPriceNegotiationDTO.class);
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO1 = new ISOrderPriceNegotiationDTO();
        iSOrderPriceNegotiationDTO1.setId(1L);
        ISOrderPriceNegotiationDTO iSOrderPriceNegotiationDTO2 = new ISOrderPriceNegotiationDTO();
        assertThat(iSOrderPriceNegotiationDTO1).isNotEqualTo(iSOrderPriceNegotiationDTO2);
        iSOrderPriceNegotiationDTO2.setId(iSOrderPriceNegotiationDTO1.getId());
        assertThat(iSOrderPriceNegotiationDTO1).isEqualTo(iSOrderPriceNegotiationDTO2);
        iSOrderPriceNegotiationDTO2.setId(2L);
        assertThat(iSOrderPriceNegotiationDTO1).isNotEqualTo(iSOrderPriceNegotiationDTO2);
        iSOrderPriceNegotiationDTO1.setId(null);
        assertThat(iSOrderPriceNegotiationDTO1).isNotEqualTo(iSOrderPriceNegotiationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSOrderPriceNegotiationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSOrderPriceNegotiationMapper.fromId(null)).isNull();
    }
}
