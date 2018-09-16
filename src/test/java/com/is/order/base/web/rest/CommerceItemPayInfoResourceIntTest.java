package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceItemPayInfo;
import com.is.order.base.repository.CommerceItemPayInfoRepository;
import com.is.order.base.service.CommerceItemPayInfoService;
import com.is.order.base.service.dto.CommerceItemPayInfoDTO;
import com.is.order.base.service.mapper.CommerceItemPayInfoMapper;
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
import java.util.List;


import static com.is.order.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.is.order.base.domain.enumeration.ItemPayStatus;
/**
 * Test class for the CommerceItemPayInfoResource REST controller.
 *
 * @see CommerceItemPayInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceItemPayInfoResourceIntTest {

    private static final ItemPayStatus DEFAULT_STATUS = ItemPayStatus.INIT;
    private static final ItemPayStatus UPDATED_STATUS = ItemPayStatus.PAID;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private CommerceItemPayInfoRepository commerceItemPayInfoRepository;


    @Autowired
    private CommerceItemPayInfoMapper commerceItemPayInfoMapper;
    

    @Autowired
    private CommerceItemPayInfoService commerceItemPayInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceItemPayInfoMockMvc;

    private CommerceItemPayInfo commerceItemPayInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceItemPayInfoResource commerceItemPayInfoResource = new CommerceItemPayInfoResource(commerceItemPayInfoService);
        this.restCommerceItemPayInfoMockMvc = MockMvcBuilders.standaloneSetup(commerceItemPayInfoResource)
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
    public static CommerceItemPayInfo createEntity(EntityManager em) {
        CommerceItemPayInfo commerceItemPayInfo = new CommerceItemPayInfo()
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY);
        return commerceItemPayInfo;
    }

    @Before
    public void initTest() {
        commerceItemPayInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItemPayInfo() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPayInfoRepository.findAll().size();

        // Create the CommerceItemPayInfo
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(commerceItemPayInfo);
        restCommerceItemPayInfoMockMvc.perform(post("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItemPayInfo in the database
        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItemPayInfo testCommerceItemPayInfo = commerceItemPayInfoList.get(commerceItemPayInfoList.size() - 1);
        assertThat(testCommerceItemPayInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCommerceItemPayInfo.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createCommerceItemPayInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemPayInfoRepository.findAll().size();

        // Create the CommerceItemPayInfo with an existing ID
        commerceItemPayInfo.setId(1L);
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(commerceItemPayInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemPayInfoMockMvc.perform(post("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPayInfo in the database
        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemPayInfoRepository.findAll().size();
        // set the field null
        commerceItemPayInfo.setStatus(null);

        // Create the CommerceItemPayInfo, which fails.
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(commerceItemPayInfo);

        restCommerceItemPayInfoMockMvc.perform(post("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemPayInfoRepository.findAll().size();
        // set the field null
        commerceItemPayInfo.setQuantity(null);

        // Create the CommerceItemPayInfo, which fails.
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(commerceItemPayInfo);

        restCommerceItemPayInfoMockMvc.perform(post("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItemPayInfos() throws Exception {
        // Initialize the database
        commerceItemPayInfoRepository.saveAndFlush(commerceItemPayInfo);

        // Get all the commerceItemPayInfoList
        restCommerceItemPayInfoMockMvc.perform(get("/api/commerce-item-pay-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItemPayInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    

    @Test
    @Transactional
    public void getCommerceItemPayInfo() throws Exception {
        // Initialize the database
        commerceItemPayInfoRepository.saveAndFlush(commerceItemPayInfo);

        // Get the commerceItemPayInfo
        restCommerceItemPayInfoMockMvc.perform(get("/api/commerce-item-pay-infos/{id}", commerceItemPayInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItemPayInfo.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceItemPayInfo() throws Exception {
        // Get the commerceItemPayInfo
        restCommerceItemPayInfoMockMvc.perform(get("/api/commerce-item-pay-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItemPayInfo() throws Exception {
        // Initialize the database
        commerceItemPayInfoRepository.saveAndFlush(commerceItemPayInfo);

        int databaseSizeBeforeUpdate = commerceItemPayInfoRepository.findAll().size();

        // Update the commerceItemPayInfo
        CommerceItemPayInfo updatedCommerceItemPayInfo = commerceItemPayInfoRepository.findById(commerceItemPayInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceItemPayInfo are not directly saved in db
        em.detach(updatedCommerceItemPayInfo);
        updatedCommerceItemPayInfo
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY);
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(updatedCommerceItemPayInfo);

        restCommerceItemPayInfoMockMvc.perform(put("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItemPayInfo in the database
        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeUpdate);
        CommerceItemPayInfo testCommerceItemPayInfo = commerceItemPayInfoList.get(commerceItemPayInfoList.size() - 1);
        assertThat(testCommerceItemPayInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCommerceItemPayInfo.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItemPayInfo() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemPayInfoRepository.findAll().size();

        // Create the CommerceItemPayInfo
        CommerceItemPayInfoDTO commerceItemPayInfoDTO = commerceItemPayInfoMapper.toDto(commerceItemPayInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceItemPayInfoMockMvc.perform(put("/api/commerce-item-pay-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemPayInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemPayInfo in the database
        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceItemPayInfo() throws Exception {
        // Initialize the database
        commerceItemPayInfoRepository.saveAndFlush(commerceItemPayInfo);

        int databaseSizeBeforeDelete = commerceItemPayInfoRepository.findAll().size();

        // Get the commerceItemPayInfo
        restCommerceItemPayInfoMockMvc.perform(delete("/api/commerce-item-pay-infos/{id}", commerceItemPayInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItemPayInfo> commerceItemPayInfoList = commerceItemPayInfoRepository.findAll();
        assertThat(commerceItemPayInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPayInfo.class);
        CommerceItemPayInfo commerceItemPayInfo1 = new CommerceItemPayInfo();
        commerceItemPayInfo1.setId(1L);
        CommerceItemPayInfo commerceItemPayInfo2 = new CommerceItemPayInfo();
        commerceItemPayInfo2.setId(commerceItemPayInfo1.getId());
        assertThat(commerceItemPayInfo1).isEqualTo(commerceItemPayInfo2);
        commerceItemPayInfo2.setId(2L);
        assertThat(commerceItemPayInfo1).isNotEqualTo(commerceItemPayInfo2);
        commerceItemPayInfo1.setId(null);
        assertThat(commerceItemPayInfo1).isNotEqualTo(commerceItemPayInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemPayInfoDTO.class);
        CommerceItemPayInfoDTO commerceItemPayInfoDTO1 = new CommerceItemPayInfoDTO();
        commerceItemPayInfoDTO1.setId(1L);
        CommerceItemPayInfoDTO commerceItemPayInfoDTO2 = new CommerceItemPayInfoDTO();
        assertThat(commerceItemPayInfoDTO1).isNotEqualTo(commerceItemPayInfoDTO2);
        commerceItemPayInfoDTO2.setId(commerceItemPayInfoDTO1.getId());
        assertThat(commerceItemPayInfoDTO1).isEqualTo(commerceItemPayInfoDTO2);
        commerceItemPayInfoDTO2.setId(2L);
        assertThat(commerceItemPayInfoDTO1).isNotEqualTo(commerceItemPayInfoDTO2);
        commerceItemPayInfoDTO1.setId(null);
        assertThat(commerceItemPayInfoDTO1).isNotEqualTo(commerceItemPayInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceItemPayInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceItemPayInfoMapper.fromId(null)).isNull();
    }
}
