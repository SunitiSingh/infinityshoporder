package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceOrderPrice;
import com.is.order.base.repository.CommerceOrderPriceRepository;
import com.is.order.base.service.CommerceOrderPriceService;
import com.is.order.base.service.dto.CommerceOrderPriceDTO;
import com.is.order.base.service.mapper.CommerceOrderPriceMapper;
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
 * Test class for the CommerceOrderPriceResource REST controller.
 *
 * @see CommerceOrderPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceOrderPriceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private CommerceOrderPriceRepository commerceOrderPriceRepository;


    @Autowired
    private CommerceOrderPriceMapper commerceOrderPriceMapper;
    

    @Autowired
    private CommerceOrderPriceService commerceOrderPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceOrderPriceMockMvc;

    private CommerceOrderPrice commerceOrderPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceOrderPriceResource commerceOrderPriceResource = new CommerceOrderPriceResource(commerceOrderPriceService);
        this.restCommerceOrderPriceMockMvc = MockMvcBuilders.standaloneSetup(commerceOrderPriceResource)
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
    public static CommerceOrderPrice createEntity(EntityManager em) {
        CommerceOrderPrice commerceOrderPrice = new CommerceOrderPrice()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return commerceOrderPrice;
    }

    @Before
    public void initTest() {
        commerceOrderPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceOrderPrice() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPriceRepository.findAll().size();

        // Create the CommerceOrderPrice
        CommerceOrderPriceDTO commerceOrderPriceDTO = commerceOrderPriceMapper.toDto(commerceOrderPrice);
        restCommerceOrderPriceMockMvc.perform(post("/api/commerce-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceOrderPrice in the database
        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceOrderPrice testCommerceOrderPrice = commerceOrderPriceList.get(commerceOrderPriceList.size() - 1);
        assertThat(testCommerceOrderPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommerceOrderPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCommerceOrderPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPriceRepository.findAll().size();

        // Create the CommerceOrderPrice with an existing ID
        commerceOrderPrice.setId(1L);
        CommerceOrderPriceDTO commerceOrderPriceDTO = commerceOrderPriceMapper.toDto(commerceOrderPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceOrderPriceMockMvc.perform(post("/api/commerce-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPrice in the database
        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceOrderPriceRepository.findAll().size();
        // set the field null
        commerceOrderPrice.setPrice(null);

        // Create the CommerceOrderPrice, which fails.
        CommerceOrderPriceDTO commerceOrderPriceDTO = commerceOrderPriceMapper.toDto(commerceOrderPrice);

        restCommerceOrderPriceMockMvc.perform(post("/api/commerce-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceOrderPrices() throws Exception {
        // Initialize the database
        commerceOrderPriceRepository.saveAndFlush(commerceOrderPrice);

        // Get all the commerceOrderPriceList
        restCommerceOrderPriceMockMvc.perform(get("/api/commerce-order-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceOrderPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getCommerceOrderPrice() throws Exception {
        // Initialize the database
        commerceOrderPriceRepository.saveAndFlush(commerceOrderPrice);

        // Get the commerceOrderPrice
        restCommerceOrderPriceMockMvc.perform(get("/api/commerce-order-prices/{id}", commerceOrderPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceOrderPrice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceOrderPrice() throws Exception {
        // Get the commerceOrderPrice
        restCommerceOrderPriceMockMvc.perform(get("/api/commerce-order-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceOrderPrice() throws Exception {
        // Initialize the database
        commerceOrderPriceRepository.saveAndFlush(commerceOrderPrice);

        int databaseSizeBeforeUpdate = commerceOrderPriceRepository.findAll().size();

        // Update the commerceOrderPrice
        CommerceOrderPrice updatedCommerceOrderPrice = commerceOrderPriceRepository.findById(commerceOrderPrice.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceOrderPrice are not directly saved in db
        em.detach(updatedCommerceOrderPrice);
        updatedCommerceOrderPrice
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        CommerceOrderPriceDTO commerceOrderPriceDTO = commerceOrderPriceMapper.toDto(updatedCommerceOrderPrice);

        restCommerceOrderPriceMockMvc.perform(put("/api/commerce-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceOrderPrice in the database
        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeUpdate);
        CommerceOrderPrice testCommerceOrderPrice = commerceOrderPriceList.get(commerceOrderPriceList.size() - 1);
        assertThat(testCommerceOrderPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommerceOrderPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceOrderPrice() throws Exception {
        int databaseSizeBeforeUpdate = commerceOrderPriceRepository.findAll().size();

        // Create the CommerceOrderPrice
        CommerceOrderPriceDTO commerceOrderPriceDTO = commerceOrderPriceMapper.toDto(commerceOrderPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceOrderPriceMockMvc.perform(put("/api/commerce-order-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPrice in the database
        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceOrderPrice() throws Exception {
        // Initialize the database
        commerceOrderPriceRepository.saveAndFlush(commerceOrderPrice);

        int databaseSizeBeforeDelete = commerceOrderPriceRepository.findAll().size();

        // Get the commerceOrderPrice
        restCommerceOrderPriceMockMvc.perform(delete("/api/commerce-order-prices/{id}", commerceOrderPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceOrderPrice> commerceOrderPriceList = commerceOrderPriceRepository.findAll();
        assertThat(commerceOrderPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPrice.class);
        CommerceOrderPrice commerceOrderPrice1 = new CommerceOrderPrice();
        commerceOrderPrice1.setId(1L);
        CommerceOrderPrice commerceOrderPrice2 = new CommerceOrderPrice();
        commerceOrderPrice2.setId(commerceOrderPrice1.getId());
        assertThat(commerceOrderPrice1).isEqualTo(commerceOrderPrice2);
        commerceOrderPrice2.setId(2L);
        assertThat(commerceOrderPrice1).isNotEqualTo(commerceOrderPrice2);
        commerceOrderPrice1.setId(null);
        assertThat(commerceOrderPrice1).isNotEqualTo(commerceOrderPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPriceDTO.class);
        CommerceOrderPriceDTO commerceOrderPriceDTO1 = new CommerceOrderPriceDTO();
        commerceOrderPriceDTO1.setId(1L);
        CommerceOrderPriceDTO commerceOrderPriceDTO2 = new CommerceOrderPriceDTO();
        assertThat(commerceOrderPriceDTO1).isNotEqualTo(commerceOrderPriceDTO2);
        commerceOrderPriceDTO2.setId(commerceOrderPriceDTO1.getId());
        assertThat(commerceOrderPriceDTO1).isEqualTo(commerceOrderPriceDTO2);
        commerceOrderPriceDTO2.setId(2L);
        assertThat(commerceOrderPriceDTO1).isNotEqualTo(commerceOrderPriceDTO2);
        commerceOrderPriceDTO1.setId(null);
        assertThat(commerceOrderPriceDTO1).isNotEqualTo(commerceOrderPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceOrderPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceOrderPriceMapper.fromId(null)).isNull();
    }
}
