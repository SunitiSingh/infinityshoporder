package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceOrder;
import com.is.order.base.repository.CommerceOrderRepository;
import com.is.order.base.service.CommerceOrderService;
import com.is.order.base.service.dto.CommerceOrderDTO;
import com.is.order.base.service.mapper.CommerceOrderMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.is.order.base.web.rest.TestUtil.sameInstant;
import static com.is.order.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.is.order.base.domain.enumeration.OrderStatus;
/**
 * Test class for the CommerceOrderResource REST controller.
 *
 * @see CommerceOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceOrderResourceIntTest {

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.CART;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.PLACED;

    private static final String DEFAULT_CUSTID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLACED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLACED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommerceOrderRepository commerceOrderRepository;


    @Autowired
    private CommerceOrderMapper commerceOrderMapper;
    

    @Autowired
    private CommerceOrderService commerceOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceOrderMockMvc;

    private CommerceOrder commerceOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceOrderResource commerceOrderResource = new CommerceOrderResource(commerceOrderService);
        this.restCommerceOrderMockMvc = MockMvcBuilders.standaloneSetup(commerceOrderResource)
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
    public static CommerceOrder createEntity(EntityManager em) {
        CommerceOrder commerceOrder = new CommerceOrder()
            .status(DEFAULT_STATUS)
            .custid(DEFAULT_CUSTID)
            .creationDate(DEFAULT_CREATION_DATE)
            .placedDate(DEFAULT_PLACED_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commerceOrder;
    }

    @Before
    public void initTest() {
        commerceOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceOrder() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderRepository.findAll().size();

        // Create the CommerceOrder
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);
        restCommerceOrderMockMvc.perform(post("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceOrder in the database
        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceOrder testCommerceOrder = commerceOrderList.get(commerceOrderList.size() - 1);
        assertThat(testCommerceOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCommerceOrder.getCustid()).isEqualTo(DEFAULT_CUSTID);
        assertThat(testCommerceOrder.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommerceOrder.getPlacedDate()).isEqualTo(DEFAULT_PLACED_DATE);
        assertThat(testCommerceOrder.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommerceOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceOrderRepository.findAll().size();

        // Create the CommerceOrder with an existing ID
        commerceOrder.setId(1L);
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceOrderMockMvc.perform(post("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrder in the database
        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceOrderRepository.findAll().size();
        // set the field null
        commerceOrder.setStatus(null);

        // Create the CommerceOrder, which fails.
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);

        restCommerceOrderMockMvc.perform(post("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustidIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceOrderRepository.findAll().size();
        // set the field null
        commerceOrder.setCustid(null);

        // Create the CommerceOrder, which fails.
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);

        restCommerceOrderMockMvc.perform(post("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceOrderRepository.findAll().size();
        // set the field null
        commerceOrder.setCreationDate(null);

        // Create the CommerceOrder, which fails.
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);

        restCommerceOrderMockMvc.perform(post("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceOrders() throws Exception {
        // Initialize the database
        commerceOrderRepository.saveAndFlush(commerceOrder);

        // Get all the commerceOrderList
        restCommerceOrderMockMvc.perform(get("/api/commerce-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].custid").value(hasItem(DEFAULT_CUSTID.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].placedDate").value(hasItem(sameInstant(DEFAULT_PLACED_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getCommerceOrder() throws Exception {
        // Initialize the database
        commerceOrderRepository.saveAndFlush(commerceOrder);

        // Get the commerceOrder
        restCommerceOrderMockMvc.perform(get("/api/commerce-orders/{id}", commerceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.custid").value(DEFAULT_CUSTID.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.placedDate").value(sameInstant(DEFAULT_PLACED_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceOrder() throws Exception {
        // Get the commerceOrder
        restCommerceOrderMockMvc.perform(get("/api/commerce-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceOrder() throws Exception {
        // Initialize the database
        commerceOrderRepository.saveAndFlush(commerceOrder);

        int databaseSizeBeforeUpdate = commerceOrderRepository.findAll().size();

        // Update the commerceOrder
        CommerceOrder updatedCommerceOrder = commerceOrderRepository.findById(commerceOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceOrder are not directly saved in db
        em.detach(updatedCommerceOrder);
        updatedCommerceOrder
            .status(UPDATED_STATUS)
            .custid(UPDATED_CUSTID)
            .creationDate(UPDATED_CREATION_DATE)
            .placedDate(UPDATED_PLACED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(updatedCommerceOrder);

        restCommerceOrderMockMvc.perform(put("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceOrder in the database
        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeUpdate);
        CommerceOrder testCommerceOrder = commerceOrderList.get(commerceOrderList.size() - 1);
        assertThat(testCommerceOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCommerceOrder.getCustid()).isEqualTo(UPDATED_CUSTID);
        assertThat(testCommerceOrder.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommerceOrder.getPlacedDate()).isEqualTo(UPDATED_PLACED_DATE);
        assertThat(testCommerceOrder.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceOrder() throws Exception {
        int databaseSizeBeforeUpdate = commerceOrderRepository.findAll().size();

        // Create the CommerceOrder
        CommerceOrderDTO commerceOrderDTO = commerceOrderMapper.toDto(commerceOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceOrderMockMvc.perform(put("/api/commerce-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceOrder in the database
        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceOrder() throws Exception {
        // Initialize the database
        commerceOrderRepository.saveAndFlush(commerceOrder);

        int databaseSizeBeforeDelete = commerceOrderRepository.findAll().size();

        // Get the commerceOrder
        restCommerceOrderMockMvc.perform(delete("/api/commerce-orders/{id}", commerceOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceOrder> commerceOrderList = commerceOrderRepository.findAll();
        assertThat(commerceOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrder.class);
        CommerceOrder commerceOrder1 = new CommerceOrder();
        commerceOrder1.setId(1L);
        CommerceOrder commerceOrder2 = new CommerceOrder();
        commerceOrder2.setId(commerceOrder1.getId());
        assertThat(commerceOrder1).isEqualTo(commerceOrder2);
        commerceOrder2.setId(2L);
        assertThat(commerceOrder1).isNotEqualTo(commerceOrder2);
        commerceOrder1.setId(null);
        assertThat(commerceOrder1).isNotEqualTo(commerceOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceOrderDTO.class);
        CommerceOrderDTO commerceOrderDTO1 = new CommerceOrderDTO();
        commerceOrderDTO1.setId(1L);
        CommerceOrderDTO commerceOrderDTO2 = new CommerceOrderDTO();
        assertThat(commerceOrderDTO1).isNotEqualTo(commerceOrderDTO2);
        commerceOrderDTO2.setId(commerceOrderDTO1.getId());
        assertThat(commerceOrderDTO1).isEqualTo(commerceOrderDTO2);
        commerceOrderDTO2.setId(2L);
        assertThat(commerceOrderDTO1).isNotEqualTo(commerceOrderDTO2);
        commerceOrderDTO1.setId(null);
        assertThat(commerceOrderDTO1).isNotEqualTo(commerceOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceOrderMapper.fromId(null)).isNull();
    }
}
