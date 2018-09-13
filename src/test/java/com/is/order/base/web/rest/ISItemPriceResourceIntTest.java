package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISItemPrice;
import com.is.order.base.repository.ISItemPriceRepository;
import com.is.order.base.service.ISItemPriceService;
import com.is.order.base.service.dto.ISItemPriceDTO;
import com.is.order.base.service.mapper.ISItemPriceMapper;
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
 * Test class for the ISItemPriceResource REST controller.
 *
 * @see ISItemPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISItemPriceResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private ISItemPriceRepository iSItemPriceRepository;


    @Autowired
    private ISItemPriceMapper iSItemPriceMapper;
    

    @Autowired
    private ISItemPriceService iSItemPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISItemPriceMockMvc;

    private ISItemPrice iSItemPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISItemPriceResource iSItemPriceResource = new ISItemPriceResource(iSItemPriceService);
        this.restISItemPriceMockMvc = MockMvcBuilders.standaloneSetup(iSItemPriceResource)
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
    public static ISItemPrice createEntity(EntityManager em) {
        ISItemPrice iSItemPrice = new ISItemPrice()
            .price(DEFAULT_PRICE);
        return iSItemPrice;
    }

    @Before
    public void initTest() {
        iSItemPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createISItemPrice() throws Exception {
        int databaseSizeBeforeCreate = iSItemPriceRepository.findAll().size();

        // Create the ISItemPrice
        ISItemPriceDTO iSItemPriceDTO = iSItemPriceMapper.toDto(iSItemPrice);
        restISItemPriceMockMvc.perform(post("/api/is-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ISItemPrice in the database
        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ISItemPrice testISItemPrice = iSItemPriceList.get(iSItemPriceList.size() - 1);
        assertThat(testISItemPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createISItemPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSItemPriceRepository.findAll().size();

        // Create the ISItemPrice with an existing ID
        iSItemPrice.setId(1L);
        ISItemPriceDTO iSItemPriceDTO = iSItemPriceMapper.toDto(iSItemPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISItemPriceMockMvc.perform(post("/api/is-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItemPrice in the database
        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSItemPriceRepository.findAll().size();
        // set the field null
        iSItemPrice.setPrice(null);

        // Create the ISItemPrice, which fails.
        ISItemPriceDTO iSItemPriceDTO = iSItemPriceMapper.toDto(iSItemPrice);

        restISItemPriceMockMvc.perform(post("/api/is-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISItemPrices() throws Exception {
        // Initialize the database
        iSItemPriceRepository.saveAndFlush(iSItemPrice);

        // Get all the iSItemPriceList
        restISItemPriceMockMvc.perform(get("/api/is-item-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSItemPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getISItemPrice() throws Exception {
        // Initialize the database
        iSItemPriceRepository.saveAndFlush(iSItemPrice);

        // Get the iSItemPrice
        restISItemPriceMockMvc.perform(get("/api/is-item-prices/{id}", iSItemPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSItemPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingISItemPrice() throws Exception {
        // Get the iSItemPrice
        restISItemPriceMockMvc.perform(get("/api/is-item-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISItemPrice() throws Exception {
        // Initialize the database
        iSItemPriceRepository.saveAndFlush(iSItemPrice);

        int databaseSizeBeforeUpdate = iSItemPriceRepository.findAll().size();

        // Update the iSItemPrice
        ISItemPrice updatedISItemPrice = iSItemPriceRepository.findById(iSItemPrice.getId()).get();
        // Disconnect from session so that the updates on updatedISItemPrice are not directly saved in db
        em.detach(updatedISItemPrice);
        updatedISItemPrice
            .price(UPDATED_PRICE);
        ISItemPriceDTO iSItemPriceDTO = iSItemPriceMapper.toDto(updatedISItemPrice);

        restISItemPriceMockMvc.perform(put("/api/is-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ISItemPrice in the database
        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeUpdate);
        ISItemPrice testISItemPrice = iSItemPriceList.get(iSItemPriceList.size() - 1);
        assertThat(testISItemPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingISItemPrice() throws Exception {
        int databaseSizeBeforeUpdate = iSItemPriceRepository.findAll().size();

        // Create the ISItemPrice
        ISItemPriceDTO iSItemPriceDTO = iSItemPriceMapper.toDto(iSItemPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISItemPriceMockMvc.perform(put("/api/is-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItemPrice in the database
        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISItemPrice() throws Exception {
        // Initialize the database
        iSItemPriceRepository.saveAndFlush(iSItemPrice);

        int databaseSizeBeforeDelete = iSItemPriceRepository.findAll().size();

        // Get the iSItemPrice
        restISItemPriceMockMvc.perform(delete("/api/is-item-prices/{id}", iSItemPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISItemPrice> iSItemPriceList = iSItemPriceRepository.findAll();
        assertThat(iSItemPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItemPrice.class);
        ISItemPrice iSItemPrice1 = new ISItemPrice();
        iSItemPrice1.setId(1L);
        ISItemPrice iSItemPrice2 = new ISItemPrice();
        iSItemPrice2.setId(iSItemPrice1.getId());
        assertThat(iSItemPrice1).isEqualTo(iSItemPrice2);
        iSItemPrice2.setId(2L);
        assertThat(iSItemPrice1).isNotEqualTo(iSItemPrice2);
        iSItemPrice1.setId(null);
        assertThat(iSItemPrice1).isNotEqualTo(iSItemPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItemPriceDTO.class);
        ISItemPriceDTO iSItemPriceDTO1 = new ISItemPriceDTO();
        iSItemPriceDTO1.setId(1L);
        ISItemPriceDTO iSItemPriceDTO2 = new ISItemPriceDTO();
        assertThat(iSItemPriceDTO1).isNotEqualTo(iSItemPriceDTO2);
        iSItemPriceDTO2.setId(iSItemPriceDTO1.getId());
        assertThat(iSItemPriceDTO1).isEqualTo(iSItemPriceDTO2);
        iSItemPriceDTO2.setId(2L);
        assertThat(iSItemPriceDTO1).isNotEqualTo(iSItemPriceDTO2);
        iSItemPriceDTO1.setId(null);
        assertThat(iSItemPriceDTO1).isNotEqualTo(iSItemPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSItemPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSItemPriceMapper.fromId(null)).isNull();
    }
}
