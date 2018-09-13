package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISOrderPrice;
import com.is.order.base.repository.ISOrderPriceRepository;
import com.is.order.base.service.ISOrderPriceService;
import com.is.order.base.service.dto.ISOrderPriceDTO;
import com.is.order.base.service.mapper.ISOrderPriceMapper;
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
 * Test class for the ISOrderPriceResource REST controller.
 *
 * @see ISOrderPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISOrderPriceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private ISOrderPriceRepository iSOrderPriceRepository;


    @Autowired
    private ISOrderPriceMapper iSOrderPriceMapper;
    

    @Autowired
    private ISOrderPriceService iSOrderPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISOrderPriceMockMvc;

    private ISOrderPrice iSOrderPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISOrderPriceResource iSOrderPriceResource = new ISOrderPriceResource(iSOrderPriceService);
        this.restISOrderPriceMockMvc = MockMvcBuilders.standaloneSetup(iSOrderPriceResource)
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
    public static ISOrderPrice createEntity(EntityManager em) {
        ISOrderPrice iSOrderPrice = new ISOrderPrice()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return iSOrderPrice;
    }

    @Before
    public void initTest() {
        iSOrderPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createISOrderPrice() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPriceRepository.findAll().size();

        // Create the ISOrderPrice
        ISOrderPriceDTO iSOrderPriceDTO = iSOrderPriceMapper.toDto(iSOrderPrice);
        restISOrderPriceMockMvc.perform(post("/api/is-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ISOrderPrice in the database
        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ISOrderPrice testISOrderPrice = iSOrderPriceList.get(iSOrderPriceList.size() - 1);
        assertThat(testISOrderPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testISOrderPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createISOrderPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSOrderPriceRepository.findAll().size();

        // Create the ISOrderPrice with an existing ID
        iSOrderPrice.setId(1L);
        ISOrderPriceDTO iSOrderPriceDTO = iSOrderPriceMapper.toDto(iSOrderPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISOrderPriceMockMvc.perform(post("/api/is-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPrice in the database
        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSOrderPriceRepository.findAll().size();
        // set the field null
        iSOrderPrice.setPrice(null);

        // Create the ISOrderPrice, which fails.
        ISOrderPriceDTO iSOrderPriceDTO = iSOrderPriceMapper.toDto(iSOrderPrice);

        restISOrderPriceMockMvc.perform(post("/api/is-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISOrderPrices() throws Exception {
        // Initialize the database
        iSOrderPriceRepository.saveAndFlush(iSOrderPrice);

        // Get all the iSOrderPriceList
        restISOrderPriceMockMvc.perform(get("/api/is-order-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSOrderPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getISOrderPrice() throws Exception {
        // Initialize the database
        iSOrderPriceRepository.saveAndFlush(iSOrderPrice);

        // Get the iSOrderPrice
        restISOrderPriceMockMvc.perform(get("/api/is-order-prices/{id}", iSOrderPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSOrderPrice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingISOrderPrice() throws Exception {
        // Get the iSOrderPrice
        restISOrderPriceMockMvc.perform(get("/api/is-order-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISOrderPrice() throws Exception {
        // Initialize the database
        iSOrderPriceRepository.saveAndFlush(iSOrderPrice);

        int databaseSizeBeforeUpdate = iSOrderPriceRepository.findAll().size();

        // Update the iSOrderPrice
        ISOrderPrice updatedISOrderPrice = iSOrderPriceRepository.findById(iSOrderPrice.getId()).get();
        // Disconnect from session so that the updates on updatedISOrderPrice are not directly saved in db
        em.detach(updatedISOrderPrice);
        updatedISOrderPrice
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        ISOrderPriceDTO iSOrderPriceDTO = iSOrderPriceMapper.toDto(updatedISOrderPrice);

        restISOrderPriceMockMvc.perform(put("/api/is-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ISOrderPrice in the database
        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeUpdate);
        ISOrderPrice testISOrderPrice = iSOrderPriceList.get(iSOrderPriceList.size() - 1);
        assertThat(testISOrderPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testISOrderPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingISOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = iSOrderPriceRepository.findAll().size();

        // Create the ISOrderPrice
        ISOrderPriceDTO iSOrderPriceDTO = iSOrderPriceMapper.toDto(iSOrderPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISOrderPriceMockMvc.perform(put("/api/is-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrderPrice in the database
        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISOrderPrice() throws Exception {
        // Initialize the database
        iSOrderPriceRepository.saveAndFlush(iSOrderPrice);

        int databaseSizeBeforeDelete = iSOrderPriceRepository.findAll().size();

        // Get the iSOrderPrice
        restISOrderPriceMockMvc.perform(delete("/api/is-order-prices/{id}", iSOrderPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISOrderPrice> iSOrderPriceList = iSOrderPriceRepository.findAll();
        assertThat(iSOrderPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPrice.class);
        ISOrderPrice iSOrderPrice1 = new ISOrderPrice();
        iSOrderPrice1.setId(1L);
        ISOrderPrice iSOrderPrice2 = new ISOrderPrice();
        iSOrderPrice2.setId(iSOrderPrice1.getId());
        assertThat(iSOrderPrice1).isEqualTo(iSOrderPrice2);
        iSOrderPrice2.setId(2L);
        assertThat(iSOrderPrice1).isNotEqualTo(iSOrderPrice2);
        iSOrderPrice1.setId(null);
        assertThat(iSOrderPrice1).isNotEqualTo(iSOrderPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderPriceDTO.class);
        ISOrderPriceDTO iSOrderPriceDTO1 = new ISOrderPriceDTO();
        iSOrderPriceDTO1.setId(1L);
        ISOrderPriceDTO iSOrderPriceDTO2 = new ISOrderPriceDTO();
        assertThat(iSOrderPriceDTO1).isNotEqualTo(iSOrderPriceDTO2);
        iSOrderPriceDTO2.setId(iSOrderPriceDTO1.getId());
        assertThat(iSOrderPriceDTO1).isEqualTo(iSOrderPriceDTO2);
        iSOrderPriceDTO2.setId(2L);
        assertThat(iSOrderPriceDTO1).isNotEqualTo(iSOrderPriceDTO2);
        iSOrderPriceDTO1.setId(null);
        assertThat(iSOrderPriceDTO1).isNotEqualTo(iSOrderPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSOrderPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSOrderPriceMapper.fromId(null)).isNull();
    }
}
