package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceItemPrice;
import com.is.order.base.repository.CommerceItemPriceRepository;
import com.is.order.base.service.CommerceItemPriceService;
import com.is.order.base.service.dto.CommerceItemPriceDTO;
import com.is.order.base.service.mapper.CommerceItemPriceMapper;
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
 * Test class for the CommerceItemPriceResource REST controller.
 *
 * @see CommerceItemPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceItemPriceResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private CommerceItemPriceRepository commerceItemPriceRepository;


    @Autowired
    private CommerceItemPriceMapper commerceItemPriceMapper;
    

    @Autowired
    private CommerceItemPriceService commerceItemPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceItemPriceMockMvc;

    private CommerceItemPrice commerceItemPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceItemPriceResource commerceItemPriceResource = new CommerceItemPriceResource(commerceItemPriceService);
        this.restCommerceItemPriceMockMvc = MockMvcBuilders.standaloneSetup(commerceItemPriceResource)
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
    public static CommerceItemPrice createEntity(EntityManager em) {
        CommerceItemPrice commerceItemPrice = new CommerceItemPrice()
            .price(DEFAULT_PRICE);
        return commerceItemPrice;
    }

    @Before
    public void initTest() {
        commerceItemPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItemPrice() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPriceRepository.findAll().size();

        // Create the CommerceItemPrice
        CommerceItemPriceDTO commerceItemPriceDTO = commerceItemPriceMapper.toDto(commerceItemPrice);
        restCommerceItemPriceMockMvc.perform(post("/api/commerce-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItemPrice in the database
        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItemPrice testCommerceItemPrice = commerceItemPriceList.get(commerceItemPriceList.size() - 1);
        assertThat(testCommerceItemPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCommerceItemPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPriceRepository.findAll().size();

        // Create the CommerceItemPrice with an existing ID
        commerceItemPrice.setId(1L);
        CommerceItemPriceDTO commerceItemPriceDTO = commerceItemPriceMapper.toDto(commerceItemPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemPriceMockMvc.perform(post("/api/commerce-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPrice in the database
        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemPriceRepository.findAll().size();
        // set the field null
        commerceItemPrice.setPrice(null);

        // Create the CommerceItemPrice, which fails.
        CommerceItemPriceDTO commerceItemPriceDTO = commerceItemPriceMapper.toDto(commerceItemPrice);

        restCommerceItemPriceMockMvc.perform(post("/api/commerce-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItemPrices() throws Exception {
        // Initialize the database
        commerceItemPriceRepository.saveAndFlush(commerceItemPrice);

        // Get all the commerceItemPriceList
        restCommerceItemPriceMockMvc.perform(get("/api/commerce-item-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItemPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getCommerceItemPrice() throws Exception {
        // Initialize the database
        commerceItemPriceRepository.saveAndFlush(commerceItemPrice);

        // Get the commerceItemPrice
        restCommerceItemPriceMockMvc.perform(get("/api/commerce-item-prices/{id}", commerceItemPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItemPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceItemPrice() throws Exception {
        // Get the commerceItemPrice
        restCommerceItemPriceMockMvc.perform(get("/api/commerce-item-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItemPrice() throws Exception {
        // Initialize the database
        commerceItemPriceRepository.saveAndFlush(commerceItemPrice);

        int databaseSizeBeforeUpdate = commerceItemPriceRepository.findAll().size();

        // Update the commerceItemPrice
        CommerceItemPrice updatedCommerceItemPrice = commerceItemPriceRepository.findById(commerceItemPrice.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceItemPrice are not directly saved in db
        em.detach(updatedCommerceItemPrice);
        updatedCommerceItemPrice
            .price(UPDATED_PRICE);
        CommerceItemPriceDTO commerceItemPriceDTO = commerceItemPriceMapper.toDto(updatedCommerceItemPrice);

        restCommerceItemPriceMockMvc.perform(put("/api/commerce-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItemPrice in the database
        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeUpdate);
        CommerceItemPrice testCommerceItemPrice = commerceItemPriceList.get(commerceItemPriceList.size() - 1);
        assertThat(testCommerceItemPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItemPrice() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemPriceRepository.findAll().size();

        // Create the CommerceItemPrice
        CommerceItemPriceDTO commerceItemPriceDTO = commerceItemPriceMapper.toDto(commerceItemPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceItemPriceMockMvc.perform(put("/api/commerce-item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPrice in the database
        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceItemPrice() throws Exception {
        // Initialize the database
        commerceItemPriceRepository.saveAndFlush(commerceItemPrice);

        int databaseSizeBeforeDelete = commerceItemPriceRepository.findAll().size();

        // Get the commerceItemPrice
        restCommerceItemPriceMockMvc.perform(delete("/api/commerce-item-prices/{id}", commerceItemPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItemPrice> commerceItemPriceList = commerceItemPriceRepository.findAll();
        assertThat(commerceItemPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPrice.class);
        CommerceItemPrice commerceItemPrice1 = new CommerceItemPrice();
        commerceItemPrice1.setId(1L);
        CommerceItemPrice commerceItemPrice2 = new CommerceItemPrice();
        commerceItemPrice2.setId(commerceItemPrice1.getId());
        assertThat(commerceItemPrice1).isEqualTo(commerceItemPrice2);
        commerceItemPrice2.setId(2L);
        assertThat(commerceItemPrice1).isNotEqualTo(commerceItemPrice2);
        commerceItemPrice1.setId(null);
        assertThat(commerceItemPrice1).isNotEqualTo(commerceItemPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPriceDTO.class);
        CommerceItemPriceDTO commerceItemPriceDTO1 = new CommerceItemPriceDTO();
        commerceItemPriceDTO1.setId(1L);
        CommerceItemPriceDTO commerceItemPriceDTO2 = new CommerceItemPriceDTO();
        assertThat(commerceItemPriceDTO1).isNotEqualTo(commerceItemPriceDTO2);
        commerceItemPriceDTO2.setId(commerceItemPriceDTO1.getId());
        assertThat(commerceItemPriceDTO1).isEqualTo(commerceItemPriceDTO2);
        commerceItemPriceDTO2.setId(2L);
        assertThat(commerceItemPriceDTO1).isNotEqualTo(commerceItemPriceDTO2);
        commerceItemPriceDTO1.setId(null);
        assertThat(commerceItemPriceDTO1).isNotEqualTo(commerceItemPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceItemPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceItemPriceMapper.fromId(null)).isNull();
    }
}
