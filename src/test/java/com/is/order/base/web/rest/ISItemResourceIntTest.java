package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISItem;
import com.is.order.base.repository.ISItemRepository;
import com.is.order.base.service.ISItemService;
import com.is.order.base.service.dto.ISItemDTO;
import com.is.order.base.service.mapper.ISItemMapper;
import com.is.order.base.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.is.order.base.web.rest.TestUtil.sameInstant;
import static com.is.order.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ISItemResource REST controller.
 *
 * @see ISItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISItemResourceIntTest {

    private static final String DEFAULT_SKUID = "AAAAAAAAAA";
    private static final String UPDATED_SKUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ISItemRepository iSItemRepository;
    @Mock
    private ISItemRepository iSItemRepositoryMock;

    @Autowired
    private ISItemMapper iSItemMapper;
    
    @Mock
    private ISItemService iSItemServiceMock;

    @Autowired
    private ISItemService iSItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISItemMockMvc;

    private ISItem iSItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISItemResource iSItemResource = new ISItemResource(iSItemService);
        this.restISItemMockMvc = MockMvcBuilders.standaloneSetup(iSItemResource)
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
    public static ISItem createEntity(EntityManager em) {
        ISItem iSItem = new ISItem()
            .skuid(DEFAULT_SKUID)
            .quantity(DEFAULT_QUANTITY)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSItem;
    }

    @Before
    public void initTest() {
        iSItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createISItem() throws Exception {
        int databaseSizeBeforeCreate = iSItemRepository.findAll().size();

        // Create the ISItem
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);
        restISItemMockMvc.perform(post("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ISItem in the database
        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeCreate + 1);
        ISItem testISItem = iSItemList.get(iSItemList.size() - 1);
        assertThat(testISItem.getSkuid()).isEqualTo(DEFAULT_SKUID);
        assertThat(testISItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testISItem.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testISItem.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSItemRepository.findAll().size();

        // Create the ISItem with an existing ID
        iSItem.setId(1L);
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISItemMockMvc.perform(post("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItem in the database
        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSItemRepository.findAll().size();
        // set the field null
        iSItem.setSkuid(null);

        // Create the ISItem, which fails.
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);

        restISItemMockMvc.perform(post("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isBadRequest());

        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSItemRepository.findAll().size();
        // set the field null
        iSItem.setQuantity(null);

        // Create the ISItem, which fails.
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);

        restISItemMockMvc.perform(post("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isBadRequest());

        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSItemRepository.findAll().size();
        // set the field null
        iSItem.setCreationDate(null);

        // Create the ISItem, which fails.
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);

        restISItemMockMvc.perform(post("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isBadRequest());

        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISItems() throws Exception {
        // Initialize the database
        iSItemRepository.saveAndFlush(iSItem);

        // Get all the iSItemList
        restISItemMockMvc.perform(get("/api/is-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].skuid").value(hasItem(DEFAULT_SKUID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    
    public void getAllISItemsWithEagerRelationshipsIsEnabled() throws Exception {
        ISItemResource iSItemResource = new ISItemResource(iSItemServiceMock);
        when(iSItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restISItemMockMvc = MockMvcBuilders.standaloneSetup(iSItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restISItemMockMvc.perform(get("/api/is-items?eagerload=true"))
        .andExpect(status().isOk());

        verify(iSItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllISItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ISItemResource iSItemResource = new ISItemResource(iSItemServiceMock);
            when(iSItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restISItemMockMvc = MockMvcBuilders.standaloneSetup(iSItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restISItemMockMvc.perform(get("/api/is-items?eagerload=true"))
        .andExpect(status().isOk());

            verify(iSItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getISItem() throws Exception {
        // Initialize the database
        iSItemRepository.saveAndFlush(iSItem);

        // Get the iSItem
        restISItemMockMvc.perform(get("/api/is-items/{id}", iSItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSItem.getId().intValue()))
            .andExpect(jsonPath("$.skuid").value(DEFAULT_SKUID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISItem() throws Exception {
        // Get the iSItem
        restISItemMockMvc.perform(get("/api/is-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISItem() throws Exception {
        // Initialize the database
        iSItemRepository.saveAndFlush(iSItem);

        int databaseSizeBeforeUpdate = iSItemRepository.findAll().size();

        // Update the iSItem
        ISItem updatedISItem = iSItemRepository.findById(iSItem.getId()).get();
        // Disconnect from session so that the updates on updatedISItem are not directly saved in db
        em.detach(updatedISItem);
        updatedISItem
            .skuid(UPDATED_SKUID)
            .quantity(UPDATED_QUANTITY)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISItemDTO iSItemDTO = iSItemMapper.toDto(updatedISItem);

        restISItemMockMvc.perform(put("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isOk());

        // Validate the ISItem in the database
        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeUpdate);
        ISItem testISItem = iSItemList.get(iSItemList.size() - 1);
        assertThat(testISItem.getSkuid()).isEqualTo(UPDATED_SKUID);
        assertThat(testISItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testISItem.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testISItem.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISItem() throws Exception {
        int databaseSizeBeforeUpdate = iSItemRepository.findAll().size();

        // Create the ISItem
        ISItemDTO iSItemDTO = iSItemMapper.toDto(iSItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISItemMockMvc.perform(put("/api/is-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISItem in the database
        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISItem() throws Exception {
        // Initialize the database
        iSItemRepository.saveAndFlush(iSItem);

        int databaseSizeBeforeDelete = iSItemRepository.findAll().size();

        // Get the iSItem
        restISItemMockMvc.perform(delete("/api/is-items/{id}", iSItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISItem> iSItemList = iSItemRepository.findAll();
        assertThat(iSItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItem.class);
        ISItem iSItem1 = new ISItem();
        iSItem1.setId(1L);
        ISItem iSItem2 = new ISItem();
        iSItem2.setId(iSItem1.getId());
        assertThat(iSItem1).isEqualTo(iSItem2);
        iSItem2.setId(2L);
        assertThat(iSItem1).isNotEqualTo(iSItem2);
        iSItem1.setId(null);
        assertThat(iSItem1).isNotEqualTo(iSItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISItemDTO.class);
        ISItemDTO iSItemDTO1 = new ISItemDTO();
        iSItemDTO1.setId(1L);
        ISItemDTO iSItemDTO2 = new ISItemDTO();
        assertThat(iSItemDTO1).isNotEqualTo(iSItemDTO2);
        iSItemDTO2.setId(iSItemDTO1.getId());
        assertThat(iSItemDTO1).isEqualTo(iSItemDTO2);
        iSItemDTO2.setId(2L);
        assertThat(iSItemDTO1).isNotEqualTo(iSItemDTO2);
        iSItemDTO1.setId(null);
        assertThat(iSItemDTO1).isNotEqualTo(iSItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSItemMapper.fromId(null)).isNull();
    }
}
