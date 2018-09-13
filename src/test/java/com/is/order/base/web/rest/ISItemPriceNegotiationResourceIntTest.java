package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISItemPriceNegotiation;
import com.is.order.base.repository.ISItemPriceNegotiationRepository;
import com.is.order.base.service.ISItemPriceNegotiationService;
import com.is.order.base.service.dto.ISItemPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISItemPriceNegotiationMapper;
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
 * Test class for the ISItemPriceNegotiationResource REST controller.
 *
 * @see ISItemPriceNegotiationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISItemPriceNegotiationResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_NEGOTIATIONID = "AAAAAAAAAA";
    private static final String UPDATED_NEGOTIATIONID = "BBBBBBBBBB";

    @Autowired
    private ISItemPriceNegotiationRepository iSItemPriceNegotiationRepository;


    @Autowired
    private ISItemPriceNegotiationMapper iSItemPriceNegotiationMapper;
    

    @Autowired
    private ISItemPriceNegotiationService iSItemPriceNegotiationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISItemPriceNegotiationMockMvc;

    private ISItemPriceNegotiation iSItemPriceNegotiation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISItemPriceNegotiationResource iSItemPriceNegotiationResource = new ISItemPriceNegotiationResource(iSItemPriceNegotiationService);
        this.restISItemPriceNegotiationMockMvc = MockMvcBuilders.standaloneSetup(iSItemPriceNegotiationResource)
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
    public static ISItemPriceNegotiation createEntity(EntityManager em) {
        ISItemPriceNegotiation iSItemPriceNegotiation = new ISItemPriceNegotiation()
            .price(DEFAULT_PRICE)
            .negotiationid(DEFAULT_NEGOTIATIONID);
        return iSItemPriceNegotiation;
    }

    @Before
    public void initTest() {
        iSItemPriceNegotiation = createEntity(em);
    }

    @Test
    @Transactional
    public void createISItemPriceNegotiation() throws Exception {
        int databaseSizeBeforeCreate = iSItemPriceNegotiationRepository.findAll().size();

        // Create the ISItemPriceNegotiation
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = iSItemPriceNegotiationMapper.toDto(iSItemPriceNegotiation);
        restISItemPriceNegotiationMockMvc.perform(post("/api/is-item-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceNegotiationDTO)))
            .andExpect(status().isCreated());

        // Validate the ISItemPriceNegotiation in the database
        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeCreate + 1);
        ISItemPriceNegotiation testISItemPriceNegotiation = iSItemPriceNegotiationList.get(iSItemPriceNegotiationList.size() - 1);
        assertThat(testISItemPriceNegotiation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testISItemPriceNegotiation.getNegotiationid()).isEqualTo(DEFAULT_NEGOTIATIONID);
    }

    @Test
    @Transactional
    public void createISItemPriceNegotiationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSItemPriceNegotiationRepository.findAll().size();

        // Create the ISItemPriceNegotiation with an existing ID
        iSItemPriceNegotiation.setId(1L);
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = iSItemPriceNegotiationMapper.toDto(iSItemPriceNegotiation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISItemPriceNegotiationMockMvc.perform(post("/api/is-item-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItemPriceNegotiation in the database
        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSItemPriceNegotiationRepository.findAll().size();
        // set the field null
        iSItemPriceNegotiation.setPrice(null);

        // Create the ISItemPriceNegotiation, which fails.
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = iSItemPriceNegotiationMapper.toDto(iSItemPriceNegotiation);

        restISItemPriceNegotiationMockMvc.perform(post("/api/is-item-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISItemPriceNegotiations() throws Exception {
        // Initialize the database
        iSItemPriceNegotiationRepository.saveAndFlush(iSItemPriceNegotiation);

        // Get all the iSItemPriceNegotiationList
        restISItemPriceNegotiationMockMvc.perform(get("/api/is-item-price-negotiations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSItemPriceNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].negotiationid").value(hasItem(DEFAULT_NEGOTIATIONID.toString())));
    }
    

    @Test
    @Transactional
    public void getISItemPriceNegotiation() throws Exception {
        // Initialize the database
        iSItemPriceNegotiationRepository.saveAndFlush(iSItemPriceNegotiation);

        // Get the iSItemPriceNegotiation
        restISItemPriceNegotiationMockMvc.perform(get("/api/is-item-price-negotiations/{id}", iSItemPriceNegotiation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSItemPriceNegotiation.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.negotiationid").value(DEFAULT_NEGOTIATIONID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingISItemPriceNegotiation() throws Exception {
        // Get the iSItemPriceNegotiation
        restISItemPriceNegotiationMockMvc.perform(get("/api/is-item-price-negotiations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISItemPriceNegotiation() throws Exception {
        // Initialize the database
        iSItemPriceNegotiationRepository.saveAndFlush(iSItemPriceNegotiation);

        int databaseSizeBeforeUpdate = iSItemPriceNegotiationRepository.findAll().size();

        // Update the iSItemPriceNegotiation
        ISItemPriceNegotiation updatedISItemPriceNegotiation = iSItemPriceNegotiationRepository.findById(iSItemPriceNegotiation.getId()).get();
        // Disconnect from session so that the updates on updatedISItemPriceNegotiation are not directly saved in db
        em.detach(updatedISItemPriceNegotiation);
        updatedISItemPriceNegotiation
            .price(UPDATED_PRICE)
            .negotiationid(UPDATED_NEGOTIATIONID);
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = iSItemPriceNegotiationMapper.toDto(updatedISItemPriceNegotiation);

        restISItemPriceNegotiationMockMvc.perform(put("/api/is-item-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceNegotiationDTO)))
            .andExpect(status().isOk());

        // Validate the ISItemPriceNegotiation in the database
        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
        ISItemPriceNegotiation testISItemPriceNegotiation = iSItemPriceNegotiationList.get(iSItemPriceNegotiationList.size() - 1);
        assertThat(testISItemPriceNegotiation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testISItemPriceNegotiation.getNegotiationid()).isEqualTo(UPDATED_NEGOTIATIONID);
    }

    @Test
    @Transactional
    public void updateNonExistingISItemPriceNegotiation() throws Exception {
        int databaseSizeBeforeUpdate = iSItemPriceNegotiationRepository.findAll().size();

        // Create the ISItemPriceNegotiation
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO = iSItemPriceNegotiationMapper.toDto(iSItemPriceNegotiation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISItemPriceNegotiationMockMvc.perform(put("/api/is-item-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItemPriceNegotiation in the database
        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISItemPriceNegotiation() throws Exception {
        // Initialize the database
        iSItemPriceNegotiationRepository.saveAndFlush(iSItemPriceNegotiation);

        int databaseSizeBeforeDelete = iSItemPriceNegotiationRepository.findAll().size();

        // Get the iSItemPriceNegotiation
        restISItemPriceNegotiationMockMvc.perform(delete("/api/is-item-price-negotiations/{id}", iSItemPriceNegotiation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISItemPriceNegotiation> iSItemPriceNegotiationList = iSItemPriceNegotiationRepository.findAll();
        assertThat(iSItemPriceNegotiationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItemPriceNegotiation.class);
        ISItemPriceNegotiation iSItemPriceNegotiation1 = new ISItemPriceNegotiation();
        iSItemPriceNegotiation1.setId(1L);
        ISItemPriceNegotiation iSItemPriceNegotiation2 = new ISItemPriceNegotiation();
        iSItemPriceNegotiation2.setId(iSItemPriceNegotiation1.getId());
        assertThat(iSItemPriceNegotiation1).isEqualTo(iSItemPriceNegotiation2);
        iSItemPriceNegotiation2.setId(2L);
        assertThat(iSItemPriceNegotiation1).isNotEqualTo(iSItemPriceNegotiation2);
        iSItemPriceNegotiation1.setId(null);
        assertThat(iSItemPriceNegotiation1).isNotEqualTo(iSItemPriceNegotiation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItemPriceNegotiationDTO.class);
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO1 = new ISItemPriceNegotiationDTO();
        iSItemPriceNegotiationDTO1.setId(1L);
        ISItemPriceNegotiationDTO iSItemPriceNegotiationDTO2 = new ISItemPriceNegotiationDTO();
        assertThat(iSItemPriceNegotiationDTO1).isNotEqualTo(iSItemPriceNegotiationDTO2);
        iSItemPriceNegotiationDTO2.setId(iSItemPriceNegotiationDTO1.getId());
        assertThat(iSItemPriceNegotiationDTO1).isEqualTo(iSItemPriceNegotiationDTO2);
        iSItemPriceNegotiationDTO2.setId(2L);
        assertThat(iSItemPriceNegotiationDTO1).isNotEqualTo(iSItemPriceNegotiationDTO2);
        iSItemPriceNegotiationDTO1.setId(null);
        assertThat(iSItemPriceNegotiationDTO1).isNotEqualTo(iSItemPriceNegotiationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSItemPriceNegotiationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSItemPriceNegotiationMapper.fromId(null)).isNull();
    }
}
