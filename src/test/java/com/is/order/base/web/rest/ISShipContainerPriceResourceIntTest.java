package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISShipContainerPrice;
import com.is.order.base.repository.ISShipContainerPriceRepository;
import com.is.order.base.service.ISShipContainerPriceService;
import com.is.order.base.service.dto.ISShipContainerPriceDTO;
import com.is.order.base.service.mapper.ISShipContainerPriceMapper;
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
 * Test class for the ISShipContainerPriceResource REST controller.
 *
 * @see ISShipContainerPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISShipContainerPriceResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private ISShipContainerPriceRepository iSShipContainerPriceRepository;


    @Autowired
    private ISShipContainerPriceMapper iSShipContainerPriceMapper;
    

    @Autowired
    private ISShipContainerPriceService iSShipContainerPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISShipContainerPriceMockMvc;

    private ISShipContainerPrice iSShipContainerPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISShipContainerPriceResource iSShipContainerPriceResource = new ISShipContainerPriceResource(iSShipContainerPriceService);
        this.restISShipContainerPriceMockMvc = MockMvcBuilders.standaloneSetup(iSShipContainerPriceResource)
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
    public static ISShipContainerPrice createEntity(EntityManager em) {
        ISShipContainerPrice iSShipContainerPrice = new ISShipContainerPrice()
            .price(DEFAULT_PRICE);
        return iSShipContainerPrice;
    }

    @Before
    public void initTest() {
        iSShipContainerPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createISShipContainerPrice() throws Exception {
        int databaseSizeBeforeCreate = iSShipContainerPriceRepository.findAll().size();

        // Create the ISShipContainerPrice
        ISShipContainerPriceDTO iSShipContainerPriceDTO = iSShipContainerPriceMapper.toDto(iSShipContainerPrice);
        restISShipContainerPriceMockMvc.perform(post("/api/is-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipContainerPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ISShipContainerPrice in the database
        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ISShipContainerPrice testISShipContainerPrice = iSShipContainerPriceList.get(iSShipContainerPriceList.size() - 1);
        assertThat(testISShipContainerPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createISShipContainerPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSShipContainerPriceRepository.findAll().size();

        // Create the ISShipContainerPrice with an existing ID
        iSShipContainerPrice.setId(1L);
        ISShipContainerPriceDTO iSShipContainerPriceDTO = iSShipContainerPriceMapper.toDto(iSShipContainerPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISShipContainerPriceMockMvc.perform(post("/api/is-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShipContainerPrice in the database
        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSShipContainerPriceRepository.findAll().size();
        // set the field null
        iSShipContainerPrice.setPrice(null);

        // Create the ISShipContainerPrice, which fails.
        ISShipContainerPriceDTO iSShipContainerPriceDTO = iSShipContainerPriceMapper.toDto(iSShipContainerPrice);

        restISShipContainerPriceMockMvc.perform(post("/api/is-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISShipContainerPrices() throws Exception {
        // Initialize the database
        iSShipContainerPriceRepository.saveAndFlush(iSShipContainerPrice);

        // Get all the iSShipContainerPriceList
        restISShipContainerPriceMockMvc.perform(get("/api/is-ship-container-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSShipContainerPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getISShipContainerPrice() throws Exception {
        // Initialize the database
        iSShipContainerPriceRepository.saveAndFlush(iSShipContainerPrice);

        // Get the iSShipContainerPrice
        restISShipContainerPriceMockMvc.perform(get("/api/is-ship-container-prices/{id}", iSShipContainerPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSShipContainerPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingISShipContainerPrice() throws Exception {
        // Get the iSShipContainerPrice
        restISShipContainerPriceMockMvc.perform(get("/api/is-ship-container-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISShipContainerPrice() throws Exception {
        // Initialize the database
        iSShipContainerPriceRepository.saveAndFlush(iSShipContainerPrice);

        int databaseSizeBeforeUpdate = iSShipContainerPriceRepository.findAll().size();

        // Update the iSShipContainerPrice
        ISShipContainerPrice updatedISShipContainerPrice = iSShipContainerPriceRepository.findById(iSShipContainerPrice.getId()).get();
        // Disconnect from session so that the updates on updatedISShipContainerPrice are not directly saved in db
        em.detach(updatedISShipContainerPrice);
        updatedISShipContainerPrice
            .price(UPDATED_PRICE);
        ISShipContainerPriceDTO iSShipContainerPriceDTO = iSShipContainerPriceMapper.toDto(updatedISShipContainerPrice);

        restISShipContainerPriceMockMvc.perform(put("/api/is-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipContainerPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ISShipContainerPrice in the database
        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeUpdate);
        ISShipContainerPrice testISShipContainerPrice = iSShipContainerPriceList.get(iSShipContainerPriceList.size() - 1);
        assertThat(testISShipContainerPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingISShipContainerPrice() throws Exception {
        int databaseSizeBeforeUpdate = iSShipContainerPriceRepository.findAll().size();

        // Create the ISShipContainerPrice
        ISShipContainerPriceDTO iSShipContainerPriceDTO = iSShipContainerPriceMapper.toDto(iSShipContainerPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISShipContainerPriceMockMvc.perform(put("/api/is-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShipContainerPrice in the database
        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISShipContainerPrice() throws Exception {
        // Initialize the database
        iSShipContainerPriceRepository.saveAndFlush(iSShipContainerPrice);

        int databaseSizeBeforeDelete = iSShipContainerPriceRepository.findAll().size();

        // Get the iSShipContainerPrice
        restISShipContainerPriceMockMvc.perform(delete("/api/is-ship-container-prices/{id}", iSShipContainerPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISShipContainerPrice> iSShipContainerPriceList = iSShipContainerPriceRepository.findAll();
        assertThat(iSShipContainerPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShipContainerPrice.class);
        ISShipContainerPrice iSShipContainerPrice1 = new ISShipContainerPrice();
        iSShipContainerPrice1.setId(1L);
        ISShipContainerPrice iSShipContainerPrice2 = new ISShipContainerPrice();
        iSShipContainerPrice2.setId(iSShipContainerPrice1.getId());
        assertThat(iSShipContainerPrice1).isEqualTo(iSShipContainerPrice2);
        iSShipContainerPrice2.setId(2L);
        assertThat(iSShipContainerPrice1).isNotEqualTo(iSShipContainerPrice2);
        iSShipContainerPrice1.setId(null);
        assertThat(iSShipContainerPrice1).isNotEqualTo(iSShipContainerPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShipContainerPriceDTO.class);
        ISShipContainerPriceDTO iSShipContainerPriceDTO1 = new ISShipContainerPriceDTO();
        iSShipContainerPriceDTO1.setId(1L);
        ISShipContainerPriceDTO iSShipContainerPriceDTO2 = new ISShipContainerPriceDTO();
        assertThat(iSShipContainerPriceDTO1).isNotEqualTo(iSShipContainerPriceDTO2);
        iSShipContainerPriceDTO2.setId(iSShipContainerPriceDTO1.getId());
        assertThat(iSShipContainerPriceDTO1).isEqualTo(iSShipContainerPriceDTO2);
        iSShipContainerPriceDTO2.setId(2L);
        assertThat(iSShipContainerPriceDTO1).isNotEqualTo(iSShipContainerPriceDTO2);
        iSShipContainerPriceDTO1.setId(null);
        assertThat(iSShipContainerPriceDTO1).isNotEqualTo(iSShipContainerPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSShipContainerPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSShipContainerPriceMapper.fromId(null)).isNull();
    }
}
