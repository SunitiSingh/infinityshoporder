package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceShipContainerPrice;
import com.is.order.base.repository.CommerceShipContainerPriceRepository;
import com.is.order.base.service.CommerceShipContainerPriceService;
import com.is.order.base.service.dto.CommerceShipContainerPriceDTO;
import com.is.order.base.service.mapper.CommerceShipContainerPriceMapper;
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
 * Test class for the CommerceShipContainerPriceResource REST controller.
 *
 * @see CommerceShipContainerPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceShipContainerPriceResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private CommerceShipContainerPriceRepository commerceShipContainerPriceRepository;


    @Autowired
    private CommerceShipContainerPriceMapper commerceShipContainerPriceMapper;
    

    @Autowired
    private CommerceShipContainerPriceService commerceShipContainerPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceShipContainerPriceMockMvc;

    private CommerceShipContainerPrice commerceShipContainerPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceShipContainerPriceResource commerceShipContainerPriceResource = new CommerceShipContainerPriceResource(commerceShipContainerPriceService);
        this.restCommerceShipContainerPriceMockMvc = MockMvcBuilders.standaloneSetup(commerceShipContainerPriceResource)
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
    public static CommerceShipContainerPrice createEntity(EntityManager em) {
        CommerceShipContainerPrice commerceShipContainerPrice = new CommerceShipContainerPrice()
            .price(DEFAULT_PRICE);
        return commerceShipContainerPrice;
    }

    @Before
    public void initTest() {
        commerceShipContainerPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceShipContainerPrice() throws Exception {
        int databaseSizeBeforeCreate = commerceShipContainerPriceRepository.findAll().size();

        // Create the CommerceShipContainerPrice
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = commerceShipContainerPriceMapper.toDto(commerceShipContainerPrice);
        restCommerceShipContainerPriceMockMvc.perform(post("/api/commerce-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipContainerPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceShipContainerPrice in the database
        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceShipContainerPrice testCommerceShipContainerPrice = commerceShipContainerPriceList.get(commerceShipContainerPriceList.size() - 1);
        assertThat(testCommerceShipContainerPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCommerceShipContainerPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceShipContainerPriceRepository.findAll().size();

        // Create the CommerceShipContainerPrice with an existing ID
        commerceShipContainerPrice.setId(1L);
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = commerceShipContainerPriceMapper.toDto(commerceShipContainerPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceShipContainerPriceMockMvc.perform(post("/api/commerce-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShipContainerPrice in the database
        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceShipContainerPriceRepository.findAll().size();
        // set the field null
        commerceShipContainerPrice.setPrice(null);

        // Create the CommerceShipContainerPrice, which fails.
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = commerceShipContainerPriceMapper.toDto(commerceShipContainerPrice);

        restCommerceShipContainerPriceMockMvc.perform(post("/api/commerce-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceShipContainerPrices() throws Exception {
        // Initialize the database
        commerceShipContainerPriceRepository.saveAndFlush(commerceShipContainerPrice);

        // Get all the commerceShipContainerPriceList
        restCommerceShipContainerPriceMockMvc.perform(get("/api/commerce-ship-container-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceShipContainerPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getCommerceShipContainerPrice() throws Exception {
        // Initialize the database
        commerceShipContainerPriceRepository.saveAndFlush(commerceShipContainerPrice);

        // Get the commerceShipContainerPrice
        restCommerceShipContainerPriceMockMvc.perform(get("/api/commerce-ship-container-prices/{id}", commerceShipContainerPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceShipContainerPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceShipContainerPrice() throws Exception {
        // Get the commerceShipContainerPrice
        restCommerceShipContainerPriceMockMvc.perform(get("/api/commerce-ship-container-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceShipContainerPrice() throws Exception {
        // Initialize the database
        commerceShipContainerPriceRepository.saveAndFlush(commerceShipContainerPrice);

        int databaseSizeBeforeUpdate = commerceShipContainerPriceRepository.findAll().size();

        // Update the commerceShipContainerPrice
        CommerceShipContainerPrice updatedCommerceShipContainerPrice = commerceShipContainerPriceRepository.findById(commerceShipContainerPrice.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceShipContainerPrice are not directly saved in db
        em.detach(updatedCommerceShipContainerPrice);
        updatedCommerceShipContainerPrice
            .price(UPDATED_PRICE);
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = commerceShipContainerPriceMapper.toDto(updatedCommerceShipContainerPrice);

        restCommerceShipContainerPriceMockMvc.perform(put("/api/commerce-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipContainerPriceDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceShipContainerPrice in the database
        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeUpdate);
        CommerceShipContainerPrice testCommerceShipContainerPrice = commerceShipContainerPriceList.get(commerceShipContainerPriceList.size() - 1);
        assertThat(testCommerceShipContainerPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceShipContainerPrice() throws Exception {
        int databaseSizeBeforeUpdate = commerceShipContainerPriceRepository.findAll().size();

        // Create the CommerceShipContainerPrice
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO = commerceShipContainerPriceMapper.toDto(commerceShipContainerPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceShipContainerPriceMockMvc.perform(put("/api/commerce-ship-container-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipContainerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShipContainerPrice in the database
        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceShipContainerPrice() throws Exception {
        // Initialize the database
        commerceShipContainerPriceRepository.saveAndFlush(commerceShipContainerPrice);

        int databaseSizeBeforeDelete = commerceShipContainerPriceRepository.findAll().size();

        // Get the commerceShipContainerPrice
        restCommerceShipContainerPriceMockMvc.perform(delete("/api/commerce-ship-container-prices/{id}", commerceShipContainerPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceShipContainerPrice> commerceShipContainerPriceList = commerceShipContainerPriceRepository.findAll();
        assertThat(commerceShipContainerPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShipContainerPrice.class);
        CommerceShipContainerPrice commerceShipContainerPrice1 = new CommerceShipContainerPrice();
        commerceShipContainerPrice1.setId(1L);
        CommerceShipContainerPrice commerceShipContainerPrice2 = new CommerceShipContainerPrice();
        commerceShipContainerPrice2.setId(commerceShipContainerPrice1.getId());
        assertThat(commerceShipContainerPrice1).isEqualTo(commerceShipContainerPrice2);
        commerceShipContainerPrice2.setId(2L);
        assertThat(commerceShipContainerPrice1).isNotEqualTo(commerceShipContainerPrice2);
        commerceShipContainerPrice1.setId(null);
        assertThat(commerceShipContainerPrice1).isNotEqualTo(commerceShipContainerPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShipContainerPriceDTO.class);
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO1 = new CommerceShipContainerPriceDTO();
        commerceShipContainerPriceDTO1.setId(1L);
        CommerceShipContainerPriceDTO commerceShipContainerPriceDTO2 = new CommerceShipContainerPriceDTO();
        assertThat(commerceShipContainerPriceDTO1).isNotEqualTo(commerceShipContainerPriceDTO2);
        commerceShipContainerPriceDTO2.setId(commerceShipContainerPriceDTO1.getId());
        assertThat(commerceShipContainerPriceDTO1).isEqualTo(commerceShipContainerPriceDTO2);
        commerceShipContainerPriceDTO2.setId(2L);
        assertThat(commerceShipContainerPriceDTO1).isNotEqualTo(commerceShipContainerPriceDTO2);
        commerceShipContainerPriceDTO1.setId(null);
        assertThat(commerceShipContainerPriceDTO1).isNotEqualTo(commerceShipContainerPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceShipContainerPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceShipContainerPriceMapper.fromId(null)).isNull();
    }
}
