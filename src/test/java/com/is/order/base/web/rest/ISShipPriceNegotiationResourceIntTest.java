package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISShipPriceNegotiation;
import com.is.order.base.repository.ISShipPriceNegotiationRepository;
import com.is.order.base.service.ISShipPriceNegotiationService;
import com.is.order.base.service.dto.ISShipPriceNegotiationDTO;
import com.is.order.base.service.mapper.ISShipPriceNegotiationMapper;
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
 * Test class for the ISShipPriceNegotiationResource REST controller.
 *
 * @see ISShipPriceNegotiationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISShipPriceNegotiationResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private ISShipPriceNegotiationRepository iSShipPriceNegotiationRepository;


    @Autowired
    private ISShipPriceNegotiationMapper iSShipPriceNegotiationMapper;
    

    @Autowired
    private ISShipPriceNegotiationService iSShipPriceNegotiationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISShipPriceNegotiationMockMvc;

    private ISShipPriceNegotiation iSShipPriceNegotiation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISShipPriceNegotiationResource iSShipPriceNegotiationResource = new ISShipPriceNegotiationResource(iSShipPriceNegotiationService);
        this.restISShipPriceNegotiationMockMvc = MockMvcBuilders.standaloneSetup(iSShipPriceNegotiationResource)
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
    public static ISShipPriceNegotiation createEntity(EntityManager em) {
        ISShipPriceNegotiation iSShipPriceNegotiation = new ISShipPriceNegotiation()
            .price(DEFAULT_PRICE);
        return iSShipPriceNegotiation;
    }

    @Before
    public void initTest() {
        iSShipPriceNegotiation = createEntity(em);
    }

    @Test
    @Transactional
    public void createISShipPriceNegotiation() throws Exception {
        int databaseSizeBeforeCreate = iSShipPriceNegotiationRepository.findAll().size();

        // Create the ISShipPriceNegotiation
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = iSShipPriceNegotiationMapper.toDto(iSShipPriceNegotiation);
        restISShipPriceNegotiationMockMvc.perform(post("/api/is-ship-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipPriceNegotiationDTO)))
            .andExpect(status().isCreated());

        // Validate the ISShipPriceNegotiation in the database
        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeCreate + 1);
        ISShipPriceNegotiation testISShipPriceNegotiation = iSShipPriceNegotiationList.get(iSShipPriceNegotiationList.size() - 1);
        assertThat(testISShipPriceNegotiation.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createISShipPriceNegotiationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSShipPriceNegotiationRepository.findAll().size();

        // Create the ISShipPriceNegotiation with an existing ID
        iSShipPriceNegotiation.setId(1L);
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = iSShipPriceNegotiationMapper.toDto(iSShipPriceNegotiation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISShipPriceNegotiationMockMvc.perform(post("/api/is-ship-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShipPriceNegotiation in the database
        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSShipPriceNegotiationRepository.findAll().size();
        // set the field null
        iSShipPriceNegotiation.setPrice(null);

        // Create the ISShipPriceNegotiation, which fails.
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = iSShipPriceNegotiationMapper.toDto(iSShipPriceNegotiation);

        restISShipPriceNegotiationMockMvc.perform(post("/api/is-ship-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISShipPriceNegotiations() throws Exception {
        // Initialize the database
        iSShipPriceNegotiationRepository.saveAndFlush(iSShipPriceNegotiation);

        // Get all the iSShipPriceNegotiationList
        restISShipPriceNegotiationMockMvc.perform(get("/api/is-ship-price-negotiations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSShipPriceNegotiation.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getISShipPriceNegotiation() throws Exception {
        // Initialize the database
        iSShipPriceNegotiationRepository.saveAndFlush(iSShipPriceNegotiation);

        // Get the iSShipPriceNegotiation
        restISShipPriceNegotiationMockMvc.perform(get("/api/is-ship-price-negotiations/{id}", iSShipPriceNegotiation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSShipPriceNegotiation.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingISShipPriceNegotiation() throws Exception {
        // Get the iSShipPriceNegotiation
        restISShipPriceNegotiationMockMvc.perform(get("/api/is-ship-price-negotiations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISShipPriceNegotiation() throws Exception {
        // Initialize the database
        iSShipPriceNegotiationRepository.saveAndFlush(iSShipPriceNegotiation);

        int databaseSizeBeforeUpdate = iSShipPriceNegotiationRepository.findAll().size();

        // Update the iSShipPriceNegotiation
        ISShipPriceNegotiation updatedISShipPriceNegotiation = iSShipPriceNegotiationRepository.findById(iSShipPriceNegotiation.getId()).get();
        // Disconnect from session so that the updates on updatedISShipPriceNegotiation are not directly saved in db
        em.detach(updatedISShipPriceNegotiation);
        updatedISShipPriceNegotiation
            .price(UPDATED_PRICE);
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = iSShipPriceNegotiationMapper.toDto(updatedISShipPriceNegotiation);

        restISShipPriceNegotiationMockMvc.perform(put("/api/is-ship-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipPriceNegotiationDTO)))
            .andExpect(status().isOk());

        // Validate the ISShipPriceNegotiation in the database
        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
        ISShipPriceNegotiation testISShipPriceNegotiation = iSShipPriceNegotiationList.get(iSShipPriceNegotiationList.size() - 1);
        assertThat(testISShipPriceNegotiation.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingISShipPriceNegotiation() throws Exception {
        int databaseSizeBeforeUpdate = iSShipPriceNegotiationRepository.findAll().size();

        // Create the ISShipPriceNegotiation
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO = iSShipPriceNegotiationMapper.toDto(iSShipPriceNegotiation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISShipPriceNegotiationMockMvc.perform(put("/api/is-ship-price-negotiations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipPriceNegotiationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShipPriceNegotiation in the database
        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISShipPriceNegotiation() throws Exception {
        // Initialize the database
        iSShipPriceNegotiationRepository.saveAndFlush(iSShipPriceNegotiation);

        int databaseSizeBeforeDelete = iSShipPriceNegotiationRepository.findAll().size();

        // Get the iSShipPriceNegotiation
        restISShipPriceNegotiationMockMvc.perform(delete("/api/is-ship-price-negotiations/{id}", iSShipPriceNegotiation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISShipPriceNegotiation> iSShipPriceNegotiationList = iSShipPriceNegotiationRepository.findAll();
        assertThat(iSShipPriceNegotiationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShipPriceNegotiation.class);
        ISShipPriceNegotiation iSShipPriceNegotiation1 = new ISShipPriceNegotiation();
        iSShipPriceNegotiation1.setId(1L);
        ISShipPriceNegotiation iSShipPriceNegotiation2 = new ISShipPriceNegotiation();
        iSShipPriceNegotiation2.setId(iSShipPriceNegotiation1.getId());
        assertThat(iSShipPriceNegotiation1).isEqualTo(iSShipPriceNegotiation2);
        iSShipPriceNegotiation2.setId(2L);
        assertThat(iSShipPriceNegotiation1).isNotEqualTo(iSShipPriceNegotiation2);
        iSShipPriceNegotiation1.setId(null);
        assertThat(iSShipPriceNegotiation1).isNotEqualTo(iSShipPriceNegotiation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShipPriceNegotiationDTO.class);
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO1 = new ISShipPriceNegotiationDTO();
        iSShipPriceNegotiationDTO1.setId(1L);
        ISShipPriceNegotiationDTO iSShipPriceNegotiationDTO2 = new ISShipPriceNegotiationDTO();
        assertThat(iSShipPriceNegotiationDTO1).isNotEqualTo(iSShipPriceNegotiationDTO2);
        iSShipPriceNegotiationDTO2.setId(iSShipPriceNegotiationDTO1.getId());
        assertThat(iSShipPriceNegotiationDTO1).isEqualTo(iSShipPriceNegotiationDTO2);
        iSShipPriceNegotiationDTO2.setId(2L);
        assertThat(iSShipPriceNegotiationDTO1).isNotEqualTo(iSShipPriceNegotiationDTO2);
        iSShipPriceNegotiationDTO1.setId(null);
        assertThat(iSShipPriceNegotiationDTO1).isNotEqualTo(iSShipPriceNegotiationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSShipPriceNegotiationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSShipPriceNegotiationMapper.fromId(null)).isNull();
    }
}
