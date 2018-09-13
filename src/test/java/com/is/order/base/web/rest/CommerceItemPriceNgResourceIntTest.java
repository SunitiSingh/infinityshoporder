package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceItemPriceNg;
import com.is.order.base.repository.CommerceItemPriceNgRepository;
import com.is.order.base.service.CommerceItemPriceNgService;
import com.is.order.base.service.dto.CommerceItemPriceNgDTO;
import com.is.order.base.service.mapper.CommerceItemPriceNgMapper;
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
 * Test class for the CommerceItemPriceNgResource REST controller.
 *
 * @see CommerceItemPriceNgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceItemPriceNgResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_NGID = "AAAAAAAAAA";
    private static final String UPDATED_NGID = "BBBBBBBBBB";

    @Autowired
    private CommerceItemPriceNgRepository commerceItemPriceNgRepository;


    @Autowired
    private CommerceItemPriceNgMapper commerceItemPriceNgMapper;
    

    @Autowired
    private CommerceItemPriceNgService commerceItemPriceNgService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceItemPriceNgMockMvc;

    private CommerceItemPriceNg commerceItemPriceNg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceItemPriceNgResource commerceItemPriceNgResource = new CommerceItemPriceNgResource(commerceItemPriceNgService);
        this.restCommerceItemPriceNgMockMvc = MockMvcBuilders.standaloneSetup(commerceItemPriceNgResource)
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
    public static CommerceItemPriceNg createEntity(EntityManager em) {
        CommerceItemPriceNg commerceItemPriceNg = new CommerceItemPriceNg()
            .price(DEFAULT_PRICE)
            .ngid(DEFAULT_NGID);
        return commerceItemPriceNg;
    }

    @Before
    public void initTest() {
        commerceItemPriceNg = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItemPriceNg() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPriceNgRepository.findAll().size();

        // Create the CommerceItemPriceNg
        CommerceItemPriceNgDTO commerceItemPriceNgDTO = commerceItemPriceNgMapper.toDto(commerceItemPriceNg);
        restCommerceItemPriceNgMockMvc.perform(post("/api/commerce-item-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceNgDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItemPriceNg in the database
        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItemPriceNg testCommerceItemPriceNg = commerceItemPriceNgList.get(commerceItemPriceNgList.size() - 1);
        assertThat(testCommerceItemPriceNg.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCommerceItemPriceNg.getNgid()).isEqualTo(DEFAULT_NGID);
    }

    @Test
    @Transactional
    public void createCommerceItemPriceNgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPriceNgRepository.findAll().size();

        // Create the CommerceItemPriceNg with an existing ID
        commerceItemPriceNg.setId(1L);
        CommerceItemPriceNgDTO commerceItemPriceNgDTO = commerceItemPriceNgMapper.toDto(commerceItemPriceNg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemPriceNgMockMvc.perform(post("/api/commerce-item-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPriceNg in the database
        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemPriceNgRepository.findAll().size();
        // set the field null
        commerceItemPriceNg.setPrice(null);

        // Create the CommerceItemPriceNg, which fails.
        CommerceItemPriceNgDTO commerceItemPriceNgDTO = commerceItemPriceNgMapper.toDto(commerceItemPriceNg);

        restCommerceItemPriceNgMockMvc.perform(post("/api/commerce-item-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceNgDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItemPriceNgs() throws Exception {
        // Initialize the database
        commerceItemPriceNgRepository.saveAndFlush(commerceItemPriceNg);

        // Get all the commerceItemPriceNgList
        restCommerceItemPriceNgMockMvc.perform(get("/api/commerce-item-price-ngs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItemPriceNg.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].ngid").value(hasItem(DEFAULT_NGID.toString())));
    }
    

    @Test
    @Transactional
    public void getCommerceItemPriceNg() throws Exception {
        // Initialize the database
        commerceItemPriceNgRepository.saveAndFlush(commerceItemPriceNg);

        // Get the commerceItemPriceNg
        restCommerceItemPriceNgMockMvc.perform(get("/api/commerce-item-price-ngs/{id}", commerceItemPriceNg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItemPriceNg.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.ngid").value(DEFAULT_NGID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceItemPriceNg() throws Exception {
        // Get the commerceItemPriceNg
        restCommerceItemPriceNgMockMvc.perform(get("/api/commerce-item-price-ngs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItemPriceNg() throws Exception {
        // Initialize the database
        commerceItemPriceNgRepository.saveAndFlush(commerceItemPriceNg);

        int databaseSizeBeforeUpdate = commerceItemPriceNgRepository.findAll().size();

        // Update the commerceItemPriceNg
        CommerceItemPriceNg updatedCommerceItemPriceNg = commerceItemPriceNgRepository.findById(commerceItemPriceNg.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceItemPriceNg are not directly saved in db
        em.detach(updatedCommerceItemPriceNg);
        updatedCommerceItemPriceNg
            .price(UPDATED_PRICE)
            .ngid(UPDATED_NGID);
        CommerceItemPriceNgDTO commerceItemPriceNgDTO = commerceItemPriceNgMapper.toDto(updatedCommerceItemPriceNg);

        restCommerceItemPriceNgMockMvc.perform(put("/api/commerce-item-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceNgDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItemPriceNg in the database
        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeUpdate);
        CommerceItemPriceNg testCommerceItemPriceNg = commerceItemPriceNgList.get(commerceItemPriceNgList.size() - 1);
        assertThat(testCommerceItemPriceNg.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCommerceItemPriceNg.getNgid()).isEqualTo(UPDATED_NGID);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItemPriceNg() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemPriceNgRepository.findAll().size();

        // Create the CommerceItemPriceNg
        CommerceItemPriceNgDTO commerceItemPriceNgDTO = commerceItemPriceNgMapper.toDto(commerceItemPriceNg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceItemPriceNgMockMvc.perform(put("/api/commerce-item-price-ngs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPriceNgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPriceNg in the database
        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceItemPriceNg() throws Exception {
        // Initialize the database
        commerceItemPriceNgRepository.saveAndFlush(commerceItemPriceNg);

        int databaseSizeBeforeDelete = commerceItemPriceNgRepository.findAll().size();

        // Get the commerceItemPriceNg
        restCommerceItemPriceNgMockMvc.perform(delete("/api/commerce-item-price-ngs/{id}", commerceItemPriceNg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItemPriceNg> commerceItemPriceNgList = commerceItemPriceNgRepository.findAll();
        assertThat(commerceItemPriceNgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPriceNg.class);
        CommerceItemPriceNg commerceItemPriceNg1 = new CommerceItemPriceNg();
        commerceItemPriceNg1.setId(1L);
        CommerceItemPriceNg commerceItemPriceNg2 = new CommerceItemPriceNg();
        commerceItemPriceNg2.setId(commerceItemPriceNg1.getId());
        assertThat(commerceItemPriceNg1).isEqualTo(commerceItemPriceNg2);
        commerceItemPriceNg2.setId(2L);
        assertThat(commerceItemPriceNg1).isNotEqualTo(commerceItemPriceNg2);
        commerceItemPriceNg1.setId(null);
        assertThat(commerceItemPriceNg1).isNotEqualTo(commerceItemPriceNg2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPriceNgDTO.class);
        CommerceItemPriceNgDTO commerceItemPriceNgDTO1 = new CommerceItemPriceNgDTO();
        commerceItemPriceNgDTO1.setId(1L);
        CommerceItemPriceNgDTO commerceItemPriceNgDTO2 = new CommerceItemPriceNgDTO();
        assertThat(commerceItemPriceNgDTO1).isNotEqualTo(commerceItemPriceNgDTO2);
        commerceItemPriceNgDTO2.setId(commerceItemPriceNgDTO1.getId());
        assertThat(commerceItemPriceNgDTO1).isEqualTo(commerceItemPriceNgDTO2);
        commerceItemPriceNgDTO2.setId(2L);
        assertThat(commerceItemPriceNgDTO1).isNotEqualTo(commerceItemPriceNgDTO2);
        commerceItemPriceNgDTO1.setId(null);
        assertThat(commerceItemPriceNgDTO1).isNotEqualTo(commerceItemPriceNgDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceItemPriceNgMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceItemPriceNgMapper.fromId(null)).isNull();
    }
}
