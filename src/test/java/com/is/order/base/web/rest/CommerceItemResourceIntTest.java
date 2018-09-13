package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceItem;
import com.is.order.base.repository.CommerceItemRepository;
import com.is.order.base.service.CommerceItemService;
import com.is.order.base.service.dto.CommerceItemDTO;
import com.is.order.base.service.mapper.CommerceItemMapper;
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
 * Test class for the CommerceItemResource REST controller.
 *
 * @see CommerceItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceItemResourceIntTest {

    private static final String DEFAULT_SKUID = "AAAAAAAAAA";
    private static final String UPDATED_SKUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommerceItemRepository commerceItemRepository;
    @Mock
    private CommerceItemRepository commerceItemRepositoryMock;

    @Autowired
    private CommerceItemMapper commerceItemMapper;
    
    @Mock
    private CommerceItemService commerceItemServiceMock;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceItemMockMvc;

    private CommerceItem commerceItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceItemResource commerceItemResource = new CommerceItemResource(commerceItemService);
        this.restCommerceItemMockMvc = MockMvcBuilders.standaloneSetup(commerceItemResource)
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
    public static CommerceItem createEntity(EntityManager em) {
        CommerceItem commerceItem = new CommerceItem()
            .skuid(DEFAULT_SKUID)
            .quantity(DEFAULT_QUANTITY)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commerceItem;
    }

    @Before
    public void initTest() {
        commerceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItem() throws Exception {
        int databaseSizeBeforeCreate = commerceItemRepository.findAll().size();

        // Create the CommerceItem
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);
        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItem testCommerceItem = commerceItemList.get(commerceItemList.size() - 1);
        assertThat(testCommerceItem.getSkuid()).isEqualTo(DEFAULT_SKUID);
        assertThat(testCommerceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCommerceItem.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommerceItem.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommerceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemRepository.findAll().size();

        // Create the CommerceItem with an existing ID
        commerceItem.setId(1L);
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemRepository.findAll().size();
        // set the field null
        commerceItem.setSkuid(null);

        // Create the CommerceItem, which fails.
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);

        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemRepository.findAll().size();
        // set the field null
        commerceItem.setQuantity(null);

        // Create the CommerceItem, which fails.
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);

        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemRepository.findAll().size();
        // set the field null
        commerceItem.setCreationDate(null);

        // Create the CommerceItem, which fails.
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);

        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItems() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        // Get all the commerceItemList
        restCommerceItemMockMvc.perform(get("/api/commerce-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].skuid").value(hasItem(DEFAULT_SKUID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    
    public void getAllCommerceItemsWithEagerRelationshipsIsEnabled() throws Exception {
        CommerceItemResource commerceItemResource = new CommerceItemResource(commerceItemServiceMock);
        when(commerceItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCommerceItemMockMvc = MockMvcBuilders.standaloneSetup(commerceItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCommerceItemMockMvc.perform(get("/api/commerce-items?eagerload=true"))
        .andExpect(status().isOk());

        verify(commerceItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllCommerceItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        CommerceItemResource commerceItemResource = new CommerceItemResource(commerceItemServiceMock);
            when(commerceItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCommerceItemMockMvc = MockMvcBuilders.standaloneSetup(commerceItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCommerceItemMockMvc.perform(get("/api/commerce-items?eagerload=true"))
        .andExpect(status().isOk());

            verify(commerceItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        // Get the commerceItem
        restCommerceItemMockMvc.perform(get("/api/commerce-items/{id}", commerceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItem.getId().intValue()))
            .andExpect(jsonPath("$.skuid").value(DEFAULT_SKUID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceItem() throws Exception {
        // Get the commerceItem
        restCommerceItemMockMvc.perform(get("/api/commerce-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        int databaseSizeBeforeUpdate = commerceItemRepository.findAll().size();

        // Update the commerceItem
        CommerceItem updatedCommerceItem = commerceItemRepository.findById(commerceItem.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceItem are not directly saved in db
        em.detach(updatedCommerceItem);
        updatedCommerceItem
            .skuid(UPDATED_SKUID)
            .quantity(UPDATED_QUANTITY)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(updatedCommerceItem);

        restCommerceItemMockMvc.perform(put("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeUpdate);
        CommerceItem testCommerceItem = commerceItemList.get(commerceItemList.size() - 1);
        assertThat(testCommerceItem.getSkuid()).isEqualTo(UPDATED_SKUID);
        assertThat(testCommerceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommerceItem.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommerceItem.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItem() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemRepository.findAll().size();

        // Create the CommerceItem
        CommerceItemDTO commerceItemDTO = commerceItemMapper.toDto(commerceItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceItemMockMvc.perform(put("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        int databaseSizeBeforeDelete = commerceItemRepository.findAll().size();

        // Get the commerceItem
        restCommerceItemMockMvc.perform(delete("/api/commerce-items/{id}", commerceItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItem.class);
        CommerceItem commerceItem1 = new CommerceItem();
        commerceItem1.setId(1L);
        CommerceItem commerceItem2 = new CommerceItem();
        commerceItem2.setId(commerceItem1.getId());
        assertThat(commerceItem1).isEqualTo(commerceItem2);
        commerceItem2.setId(2L);
        assertThat(commerceItem1).isNotEqualTo(commerceItem2);
        commerceItem1.setId(null);
        assertThat(commerceItem1).isNotEqualTo(commerceItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceItemDTO.class);
        CommerceItemDTO commerceItemDTO1 = new CommerceItemDTO();
        commerceItemDTO1.setId(1L);
        CommerceItemDTO commerceItemDTO2 = new CommerceItemDTO();
        assertThat(commerceItemDTO1).isNotEqualTo(commerceItemDTO2);
        commerceItemDTO2.setId(commerceItemDTO1.getId());
        assertThat(commerceItemDTO1).isEqualTo(commerceItemDTO2);
        commerceItemDTO2.setId(2L);
        assertThat(commerceItemDTO1).isNotEqualTo(commerceItemDTO2);
        commerceItemDTO1.setId(null);
        assertThat(commerceItemDTO1).isNotEqualTo(commerceItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceItemMapper.fromId(null)).isNull();
    }
}
