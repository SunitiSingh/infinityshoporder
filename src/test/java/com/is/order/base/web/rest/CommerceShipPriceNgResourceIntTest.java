package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceShipPriceNg;
import com.is.order.base.repository.CommerceShipPriceNgRepository;
import com.is.order.base.service.CommerceShipPriceNgService;
import com.is.order.base.service.dto.CommerceShipPriceNgDTO;
import com.is.order.base.service.mapper.CommerceShipPriceNgMapper;
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
 * Test class for the CommerceShipPriceNgResource REST controller.
 *
 * @see CommerceShipPriceNgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceShipPriceNgResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private CommerceShipPriceNgRepository commerceShipPriceNgRepository;


    @Autowired
    private CommerceShipPriceNgMapper commerceShipPriceNgMapper;
    

    @Autowired
    private CommerceShipPriceNgService commerceShipPriceNgService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceShipPriceNgMockMvc;

    private CommerceShipPriceNg commerceShipPriceNg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceShipPriceNgResource commerceShipPriceNgResource = new CommerceShipPriceNgResource(commerceShipPriceNgService);
        this.restCommerceShipPriceNgMockMvc = MockMvcBuilders.standaloneSetup(commerceShipPriceNgResource)
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
    public static CommerceShipPriceNg createEntity(EntityManager em) {
        CommerceShipPriceNg commerceShipPriceNg = new CommerceShipPriceNg()
            .price(DEFAULT_PRICE);
        return commerceShipPriceNg;
    }

    @Before
    public void initTest() {
        commerceShipPriceNg = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceShipPriceNg() throws Exception {
        int databaseSizeBeforeCreate = commerceShipPriceNgRepository.findAll().size();

        // Create the CommerceShipPriceNg
        CommerceShipPriceNgDTO commerceShipPriceNgDTO = commerceShipPriceNgMapper.toDto(commerceShipPriceNg);
        restCommerceShipPriceNgMockMvc.perform(post("/api/commerce-ship-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipPriceNgDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceShipPriceNg in the database
        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceShipPriceNg testCommerceShipPriceNg = commerceShipPriceNgList.get(commerceShipPriceNgList.size() - 1);
        assertThat(testCommerceShipPriceNg.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCommerceShipPriceNgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceShipPriceNgRepository.findAll().size();

        // Create the CommerceShipPriceNg with an existing ID
        commerceShipPriceNg.setId(1L);
        CommerceShipPriceNgDTO commerceShipPriceNgDTO = commerceShipPriceNgMapper.toDto(commerceShipPriceNg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceShipPriceNgMockMvc.perform(post("/api/commerce-ship-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShipPriceNg in the database
        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceShipPriceNgRepository.findAll().size();
        // set the field null
        commerceShipPriceNg.setPrice(null);

        // Create the CommerceShipPriceNg, which fails.
        CommerceShipPriceNgDTO commerceShipPriceNgDTO = commerceShipPriceNgMapper.toDto(commerceShipPriceNg);

        restCommerceShipPriceNgMockMvc.perform(post("/api/commerce-ship-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipPriceNgDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceShipPriceNgs() throws Exception {
        // Initialize the database
        commerceShipPriceNgRepository.saveAndFlush(commerceShipPriceNg);

        // Get all the commerceShipPriceNgList
        restCommerceShipPriceNgMockMvc.perform(get("/api/commerce-ship-price-ngs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceShipPriceNg.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getCommerceShipPriceNg() throws Exception {
        // Initialize the database
        commerceShipPriceNgRepository.saveAndFlush(commerceShipPriceNg);

        // Get the commerceShipPriceNg
        restCommerceShipPriceNgMockMvc.perform(get("/api/commerce-ship-price-ngs/{id}", commerceShipPriceNg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceShipPriceNg.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceShipPriceNg() throws Exception {
        // Get the commerceShipPriceNg
        restCommerceShipPriceNgMockMvc.perform(get("/api/commerce-ship-price-ngs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceShipPriceNg() throws Exception {
        // Initialize the database
        commerceShipPriceNgRepository.saveAndFlush(commerceShipPriceNg);

        int databaseSizeBeforeUpdate = commerceShipPriceNgRepository.findAll().size();

        // Update the commerceShipPriceNg
        CommerceShipPriceNg updatedCommerceShipPriceNg = commerceShipPriceNgRepository.findById(commerceShipPriceNg.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceShipPriceNg are not directly saved in db
        em.detach(updatedCommerceShipPriceNg);
        updatedCommerceShipPriceNg
            .price(UPDATED_PRICE);
        CommerceShipPriceNgDTO commerceShipPriceNgDTO = commerceShipPriceNgMapper.toDto(updatedCommerceShipPriceNg);

        restCommerceShipPriceNgMockMvc.perform(put("/api/commerce-ship-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipPriceNgDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceShipPriceNg in the database
        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeUpdate);
        CommerceShipPriceNg testCommerceShipPriceNg = commerceShipPriceNgList.get(commerceShipPriceNgList.size() - 1);
        assertThat(testCommerceShipPriceNg.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceShipPriceNg() throws Exception {
        int databaseSizeBeforeUpdate = commerceShipPriceNgRepository.findAll().size();

        // Create the CommerceShipPriceNg
        CommerceShipPriceNgDTO commerceShipPriceNgDTO = commerceShipPriceNgMapper.toDto(commerceShipPriceNg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceShipPriceNgMockMvc.perform(put("/api/commerce-ship-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShipPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShipPriceNg in the database
        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceShipPriceNg() throws Exception {
        // Initialize the database
        commerceShipPriceNgRepository.saveAndFlush(commerceShipPriceNg);

        int databaseSizeBeforeDelete = commerceShipPriceNgRepository.findAll().size();

        // Get the commerceShipPriceNg
        restCommerceShipPriceNgMockMvc.perform(delete("/api/commerce-ship-price-ngs/{id}", commerceShipPriceNg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceShipPriceNg> commerceShipPriceNgList = commerceShipPriceNgRepository.findAll();
        assertThat(commerceShipPriceNgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShipPriceNg.class);
        CommerceShipPriceNg commerceShipPriceNg1 = new CommerceShipPriceNg();
        commerceShipPriceNg1.setId(1L);
        CommerceShipPriceNg commerceShipPriceNg2 = new CommerceShipPriceNg();
        commerceShipPriceNg2.setId(commerceShipPriceNg1.getId());
        assertThat(commerceShipPriceNg1).isEqualTo(commerceShipPriceNg2);
        commerceShipPriceNg2.setId(2L);
        assertThat(commerceShipPriceNg1).isNotEqualTo(commerceShipPriceNg2);
        commerceShipPriceNg1.setId(null);
        assertThat(commerceShipPriceNg1).isNotEqualTo(commerceShipPriceNg2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShipPriceNgDTO.class);
        CommerceShipPriceNgDTO commerceShipPriceNgDTO1 = new CommerceShipPriceNgDTO();
        commerceShipPriceNgDTO1.setId(1L);
        CommerceShipPriceNgDTO commerceShipPriceNgDTO2 = new CommerceShipPriceNgDTO();
        assertThat(commerceShipPriceNgDTO1).isNotEqualTo(commerceShipPriceNgDTO2);
        commerceShipPriceNgDTO2.setId(commerceShipPriceNgDTO1.getId());
        assertThat(commerceShipPriceNgDTO1).isEqualTo(commerceShipPriceNgDTO2);
        commerceShipPriceNgDTO2.setId(2L);
        assertThat(commerceShipPriceNgDTO1).isNotEqualTo(commerceShipPriceNgDTO2);
        commerceShipPriceNgDTO1.setId(null);
        assertThat(commerceShipPriceNgDTO1).isNotEqualTo(commerceShipPriceNgDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceShipPriceNgMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceShipPriceNgMapper.fromId(null)).isNull();
    }
}
