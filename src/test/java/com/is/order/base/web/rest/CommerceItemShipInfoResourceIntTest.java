package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceItemShipInfo;
import com.is.order.base.repository.CommerceItemShipInfoRepository;
import com.is.order.base.service.CommerceItemShipInfoService;
import com.is.order.base.service.dto.CommerceItemShipInfoDTO;
import com.is.order.base.service.mapper.CommerceItemShipInfoMapper;
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

import com.is.order.base.domain.enumeration.ItemShipStatus;
/**
 * Test class for the CommerceItemShipInfoResource REST controller.
 *
 * @see CommerceItemShipInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceItemShipInfoResourceIntTest {

    private static final ItemShipStatus DEFAULT_STATUS = ItemShipStatus.INIT;
    private static final ItemShipStatus UPDATED_STATUS = ItemShipStatus.SHIPPED;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private CommerceItemShipInfoRepository commerceItemShipInfoRepository;


    @Autowired
    private CommerceItemShipInfoMapper commerceItemShipInfoMapper;
    

    @Autowired
    private CommerceItemShipInfoService commerceItemShipInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceItemShipInfoMockMvc;

    private CommerceItemShipInfo commerceItemShipInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceItemShipInfoResource commerceItemShipInfoResource = new CommerceItemShipInfoResource(commerceItemShipInfoService);
        this.restCommerceItemShipInfoMockMvc = MockMvcBuilders.standaloneSetup(commerceItemShipInfoResource)
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
    public static CommerceItemShipInfo createEntity(EntityManager em) {
        CommerceItemShipInfo commerceItemShipInfo = new CommerceItemShipInfo()
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY);
        return commerceItemShipInfo;
    }

    @Before
    public void initTest() {
        commerceItemShipInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItemShipInfo() throws Exception {
        int databaseSizeBeforeCreate = commerceItemShipInfoRepository.findAll().size();

        // Create the CommerceItemShipInfo
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(commerceItemShipInfo);
        restCommerceItemShipInfoMockMvc.perform(post("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItemShipInfo in the database
        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItemShipInfo testCommerceItemShipInfo = commerceItemShipInfoList.get(commerceItemShipInfoList.size() - 1);
        assertThat(testCommerceItemShipInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCommerceItemShipInfo.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createCommerceItemShipInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemShipInfoRepository.findAll().size();

        // Create the CommerceItemShipInfo with an existing ID
        commerceItemShipInfo.setId(1L);
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(commerceItemShipInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemShipInfoMockMvc.perform(post("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemShipInfo in the database
        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemShipInfoRepository.findAll().size();
        // set the field null
        commerceItemShipInfo.setStatus(null);

        // Create the CommerceItemShipInfo, which fails.
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(commerceItemShipInfo);

        restCommerceItemShipInfoMockMvc.perform(post("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemShipInfoRepository.findAll().size();
        // set the field null
        commerceItemShipInfo.setQuantity(null);

        // Create the CommerceItemShipInfo, which fails.
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(commerceItemShipInfo);

        restCommerceItemShipInfoMockMvc.perform(post("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItemShipInfos() throws Exception {
        // Initialize the database
        commerceItemShipInfoRepository.saveAndFlush(commerceItemShipInfo);

        // Get all the commerceItemShipInfoList
        restCommerceItemShipInfoMockMvc.perform(get("/api/commerce-item-ship-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItemShipInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    

    @Test
    @Transactional
    public void getCommerceItemShipInfo() throws Exception {
        // Initialize the database
        commerceItemShipInfoRepository.saveAndFlush(commerceItemShipInfo);

        // Get the commerceItemShipInfo
        restCommerceItemShipInfoMockMvc.perform(get("/api/commerce-item-ship-infos/{id}", commerceItemShipInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItemShipInfo.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceItemShipInfo() throws Exception {
        // Get the commerceItemShipInfo
        restCommerceItemShipInfoMockMvc.perform(get("/api/commerce-item-ship-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItemShipInfo() throws Exception {
        // Initialize the database
        commerceItemShipInfoRepository.saveAndFlush(commerceItemShipInfo);

        int databaseSizeBeforeUpdate = commerceItemShipInfoRepository.findAll().size();

        // Update the commerceItemShipInfo
        CommerceItemShipInfo updatedCommerceItemShipInfo = commerceItemShipInfoRepository.findById(commerceItemShipInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceItemShipInfo are not directly saved in db
        em.detach(updatedCommerceItemShipInfo);
        updatedCommerceItemShipInfo
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY);
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(updatedCommerceItemShipInfo);

        restCommerceItemShipInfoMockMvc.perform(put("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItemShipInfo in the database
        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeUpdate);
        CommerceItemShipInfo testCommerceItemShipInfo = commerceItemShipInfoList.get(commerceItemShipInfoList.size() - 1);
        assertThat(testCommerceItemShipInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCommerceItemShipInfo.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItemShipInfo() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemShipInfoRepository.findAll().size();

        // Create the CommerceItemShipInfo
        CommerceItemShipInfoDTO commerceItemShipInfoDTO = commerceItemShipInfoMapper.toDto(commerceItemShipInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceItemShipInfoMockMvc.perform(put("/api/commerce-item-ship-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemShipInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItemShipInfo in the database
        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceItemShipInfo() throws Exception {
        // Initialize the database
        commerceItemShipInfoRepository.saveAndFlush(commerceItemShipInfo);

        int databaseSizeBeforeDelete = commerceItemShipInfoRepository.findAll().size();

        // Get the commerceItemShipInfo
        restCommerceItemShipInfoMockMvc.perform(delete("/api/commerce-item-ship-infos/{id}", commerceItemShipInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItemShipInfo> commerceItemShipInfoList = commerceItemShipInfoRepository.findAll();
        assertThat(commerceItemShipInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemShipInfo.class);
        CommerceItemShipInfo commerceItemShipInfo1 = new CommerceItemShipInfo();
        commerceItemShipInfo1.setId(1L);
        CommerceItemShipInfo commerceItemShipInfo2 = new CommerceItemShipInfo();
        commerceItemShipInfo2.setId(commerceItemShipInfo1.getId());
        assertThat(commerceItemShipInfo1).isEqualTo(commerceItemShipInfo2);
        commerceItemShipInfo2.setId(2L);
        assertThat(commerceItemShipInfo1).isNotEqualTo(commerceItemShipInfo2);
        commerceItemShipInfo1.setId(null);
        assertThat(commerceItemShipInfo1).isNotEqualTo(commerceItemShipInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemShipInfoDTO.class);
        CommerceItemShipInfoDTO commerceItemShipInfoDTO1 = new CommerceItemShipInfoDTO();
        commerceItemShipInfoDTO1.setId(1L);
        CommerceItemShipInfoDTO commerceItemShipInfoDTO2 = new CommerceItemShipInfoDTO();
        assertThat(commerceItemShipInfoDTO1).isNotEqualTo(commerceItemShipInfoDTO2);
        commerceItemShipInfoDTO2.setId(commerceItemShipInfoDTO1.getId());
        assertThat(commerceItemShipInfoDTO1).isEqualTo(commerceItemShipInfoDTO2);
        commerceItemShipInfoDTO2.setId(2L);
        assertThat(commerceItemShipInfoDTO1).isNotEqualTo(commerceItemShipInfoDTO2);
        commerceItemShipInfoDTO1.setId(null);
        assertThat(commerceItemShipInfoDTO1).isNotEqualTo(commerceItemShipInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceItemShipInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceItemShipInfoMapper.fromId(null)).isNull();
    }
}
