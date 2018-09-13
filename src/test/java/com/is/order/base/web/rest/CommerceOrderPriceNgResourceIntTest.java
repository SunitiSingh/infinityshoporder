package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceOrderPriceNg;
import com.is.order.base.repository.CommerceOrderPriceNgRepository;
import com.is.order.base.service.CommerceOrderPriceNgService;
import com.is.order.base.service.dto.CommerceOrderPriceNgDTO;
import com.is.order.base.service.mapper.CommerceOrderPriceNgMapper;
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
 * Test class for the CommerceOrderPriceNgResource REST controller.
 *
 * @see CommerceOrderPriceNgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceOrderPriceNgResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_NGID = "AAAAAAAAAA";
    private static final String UPDATED_NGID = "BBBBBBBBBB";

    @Autowired
    private CommerceOrderPriceNgRepository commerceOrderPriceNgRepository;


    @Autowired
    private CommerceOrderPriceNgMapper commerceOrderPriceNgMapper;
    

    @Autowired
    private CommerceOrderPriceNgService commerceOrderPriceNgService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceOrderPriceNgMockMvc;

    private CommerceOrderPriceNg commerceOrderPriceNg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceOrderPriceNgResource commerceOrderPriceNgResource = new CommerceOrderPriceNgResource(commerceOrderPriceNgService);
        this.restCommerceOrderPriceNgMockMvc = MockMvcBuilders.standaloneSetup(commerceOrderPriceNgResource)
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
    public static CommerceOrderPriceNg createEntity(EntityManager em) {
        CommerceOrderPriceNg commerceOrderPriceNg = new CommerceOrderPriceNg()
            .price(DEFAULT_PRICE)
            .ngid(DEFAULT_NGID);
        return commerceOrderPriceNg;
    }

    @Before
    public void initTest() {
        commerceOrderPriceNg = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceOrderPriceNg() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPriceNgRepository.findAll().size();

        // Create the CommerceOrderPriceNg
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = commerceOrderPriceNgMapper.toDto(commerceOrderPriceNg);
        restCommerceOrderPriceNgMockMvc.perform(post("/api/commerce-order-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceNgDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceOrderPriceNg in the database
        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceOrderPriceNg testCommerceOrderPriceNg = commerceOrderPriceNgList.get(commerceOrderPriceNgList.size() - 1);
        assertThat(testCommerceOrderPriceNg.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCommerceOrderPriceNg.getNgid()).isEqualTo(DEFAULT_NGID);
    }

    @Test
    @Transactional
    public void createCommerceOrderPriceNgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderPriceNgRepository.findAll().size();

        // Create the CommerceOrderPriceNg with an existing ID
        commerceOrderPriceNg.setId(1L);
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = commerceOrderPriceNgMapper.toDto(commerceOrderPriceNg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceOrderPriceNgMockMvc.perform(post("/api/commerce-order-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPriceNg in the database
        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceOrderPriceNgRepository.findAll().size();
        // set the field null
        commerceOrderPriceNg.setPrice(null);

        // Create the CommerceOrderPriceNg, which fails.
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = commerceOrderPriceNgMapper.toDto(commerceOrderPriceNg);

        restCommerceOrderPriceNgMockMvc.perform(post("/api/commerce-order-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceNgDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceOrderPriceNgs() throws Exception {
        // Initialize the database
        commerceOrderPriceNgRepository.saveAndFlush(commerceOrderPriceNg);

        // Get all the commerceOrderPriceNgList
        restCommerceOrderPriceNgMockMvc.perform(get("/api/commerce-order-price-ngs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceOrderPriceNg.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ngid").value(hasItem(DEFAULT_NGID.toString())));
    }
    

    @Test
    @Transactional
    public void getCommerceOrderPriceNg() throws Exception {
        // Initialize the database
        commerceOrderPriceNgRepository.saveAndFlush(commerceOrderPriceNg);

        // Get the commerceOrderPriceNg
        restCommerceOrderPriceNgMockMvc.perform(get("/api/commerce-order-price-ngs/{id}", commerceOrderPriceNg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceOrderPriceNg.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.ngid").value(DEFAULT_NGID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceOrderPriceNg() throws Exception {
        // Get the commerceOrderPriceNg
        restCommerceOrderPriceNgMockMvc.perform(get("/api/commerce-order-price-ngs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceOrderPriceNg() throws Exception {
        // Initialize the database
        commerceOrderPriceNgRepository.saveAndFlush(commerceOrderPriceNg);

        int databaseSizeBeforeUpdate = commerceOrderPriceNgRepository.findAll().size();

        // Update the commerceOrderPriceNg
        CommerceOrderPriceNg updatedCommerceOrderPriceNg = commerceOrderPriceNgRepository.findById(commerceOrderPriceNg.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceOrderPriceNg are not directly saved in db
        em.detach(updatedCommerceOrderPriceNg);
        updatedCommerceOrderPriceNg
            .price(UPDATED_PRICE)
            .ngid(UPDATED_NGID);
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = commerceOrderPriceNgMapper.toDto(updatedCommerceOrderPriceNg);

        restCommerceOrderPriceNgMockMvc.perform(put("/api/commerce-order-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceNgDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceOrderPriceNg in the database
        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeUpdate);
        CommerceOrderPriceNg testCommerceOrderPriceNg = commerceOrderPriceNgList.get(commerceOrderPriceNgList.size() - 1);
        assertThat(testCommerceOrderPriceNg.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCommerceOrderPriceNg.getNgid()).isEqualTo(UPDATED_NGID);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceOrderPriceNg() throws Exception {
        int databaseSizeBeforeUpdate = commerceOrderPriceNgRepository.findAll().size();

        // Create the CommerceOrderPriceNg
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO = commerceOrderPriceNgMapper.toDto(commerceOrderPriceNg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceOrderPriceNgMockMvc.perform(put("/api/commerce-order-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrderPriceNg in the database
        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceOrderPriceNg() throws Exception {
        // Initialize the database
        commerceOrderPriceNgRepository.saveAndFlush(commerceOrderPriceNg);

        int databaseSizeBeforeDelete = commerceOrderPriceNgRepository.findAll().size();

        // Get the commerceOrderPriceNg
        restCommerceOrderPriceNgMockMvc.perform(delete("/api/commerce-order-price-ngs/{id}", commerceOrderPriceNg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceOrderPriceNg> commerceOrderPriceNgList = commerceOrderPriceNgRepository.findAll();
        assertThat(commerceOrderPriceNgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPriceNg.class);
        CommerceOrderPriceNg commerceOrderPriceNg1 = new CommerceOrderPriceNg();
        commerceOrderPriceNg1.setId(1L);
        CommerceOrderPriceNg commerceOrderPriceNg2 = new CommerceOrderPriceNg();
        commerceOrderPriceNg2.setId(commerceOrderPriceNg1.getId());
        assertThat(commerceOrderPriceNg1).isEqualTo(commerceOrderPriceNg2);
        commerceOrderPriceNg2.setId(2L);
        assertThat(commerceOrderPriceNg1).isNotEqualTo(commerceOrderPriceNg2);
        commerceOrderPriceNg1.setId(null);
        assertThat(commerceOrderPriceNg1).isNotEqualTo(commerceOrderPriceNg2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderPriceNgDTO.class);
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO1 = new CommerceOrderPriceNgDTO();
        commerceOrderPriceNgDTO1.setId(1L);
        CommerceOrderPriceNgDTO commerceOrderPriceNgDTO2 = new CommerceOrderPriceNgDTO();
        assertThat(commerceOrderPriceNgDTO1).isNotEqualTo(commerceOrderPriceNgDTO2);
        commerceOrderPriceNgDTO2.setId(commerceOrderPriceNgDTO1.getId());
        assertThat(commerceOrderPriceNgDTO1).isEqualTo(commerceOrderPriceNgDTO2);
        commerceOrderPriceNgDTO2.setId(2L);
        assertThat(commerceOrderPriceNgDTO1).isNotEqualTo(commerceOrderPriceNgDTO2);
        commerceOrderPriceNgDTO1.setId(null);
        assertThat(commerceOrderPriceNgDTO1).isNotEqualTo(commerceOrderPriceNgDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceOrderPriceNgMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceOrderPriceNgMapper.fromId(null)).isNull();
    }
}
