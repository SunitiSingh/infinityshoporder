package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISOrder;
import com.is.order.base.repository.ISOrderRepository;
import com.is.order.base.service.ISOrderService;
import com.is.order.base.service.dto.ISOrderDTO;
import com.is.order.base.service.mapper.ISOrderMapper;
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
 * Test class for the ISOrderResource REST controller.
 *
 * @see ISOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISOrderResourceIntTest {

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
    private ISOrderRepository iSOrderRepository;


    @Autowired
    private ISOrderMapper iSOrderMapper;
    

    @Autowired
    private ISOrderService iSOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISOrderMockMvc;

    private ISOrder iSOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISOrderResource iSOrderResource = new ISOrderResource(iSOrderService);
        this.restISOrderMockMvc = MockMvcBuilders.standaloneSetup(iSOrderResource)
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
    public static ISOrder createEntity(EntityManager em) {
        ISOrder iSOrder = new ISOrder()
            .status(DEFAULT_STATUS)
            .custid(DEFAULT_CUSTID)
            .creationDate(DEFAULT_CREATION_DATE)
            .placedDate(DEFAULT_PLACED_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSOrder;
    }

    @Before
    public void initTest() {
        iSOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createISOrder() throws Exception {
        int databaseSizeBeforeCreate = iSOrderRepository.findAll().size();

        // Create the ISOrder
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);
        restISOrderMockMvc.perform(post("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ISOrder in the database
        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ISOrder testISOrder = iSOrderList.get(iSOrderList.size() - 1);
        assertThat(testISOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testISOrder.getCustid()).isEqualTo(DEFAULT_CUSTID);
        assertThat(testISOrder.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testISOrder.getPlacedDate()).isEqualTo(DEFAULT_PLACED_DATE);
        assertThat(testISOrder.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSOrderRepository.findAll().size();

        // Create the ISOrder with an existing ID
        iSOrder.setId(1L);
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISOrderMockMvc.perform(post("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrder in the database
        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSOrderRepository.findAll().size();
        // set the field null
        iSOrder.setStatus(null);

        // Create the ISOrder, which fails.
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);

        restISOrderMockMvc.perform(post("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustidIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSOrderRepository.findAll().size();
        // set the field null
        iSOrder.setCustid(null);

        // Create the ISOrder, which fails.
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);

        restISOrderMockMvc.perform(post("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSOrderRepository.findAll().size();
        // set the field null
        iSOrder.setCreationDate(null);

        // Create the ISOrder, which fails.
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);

        restISOrderMockMvc.perform(post("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISOrders() throws Exception {
        // Initialize the database
        iSOrderRepository.saveAndFlush(iSOrder);

        // Get all the iSOrderList
        restISOrderMockMvc.perform(get("/api/is-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].custid").value(hasItem(DEFAULT_CUSTID.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].placedDate").value(hasItem(sameInstant(DEFAULT_PLACED_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getISOrder() throws Exception {
        // Initialize the database
        iSOrderRepository.saveAndFlush(iSOrder);

        // Get the iSOrder
        restISOrderMockMvc.perform(get("/api/is-orders/{id}", iSOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.custid").value(DEFAULT_CUSTID.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.placedDate").value(sameInstant(DEFAULT_PLACED_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISOrder() throws Exception {
        // Get the iSOrder
        restISOrderMockMvc.perform(get("/api/is-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISOrder() throws Exception {
        // Initialize the database
        iSOrderRepository.saveAndFlush(iSOrder);

        int databaseSizeBeforeUpdate = iSOrderRepository.findAll().size();

        // Update the iSOrder
        ISOrder updatedISOrder = iSOrderRepository.findById(iSOrder.getId()).get();
        // Disconnect from session so that the updates on updatedISOrder are not directly saved in db
        em.detach(updatedISOrder);
        updatedISOrder
            .status(UPDATED_STATUS)
            .custid(UPDATED_CUSTID)
            .creationDate(UPDATED_CREATION_DATE)
            .placedDate(UPDATED_PLACED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(updatedISOrder);

        restISOrderMockMvc.perform(put("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ISOrder in the database
        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeUpdate);
        ISOrder testISOrder = iSOrderList.get(iSOrderList.size() - 1);
        assertThat(testISOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testISOrder.getCustid()).isEqualTo(UPDATED_CUSTID);
        assertThat(testISOrder.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testISOrder.getPlacedDate()).isEqualTo(UPDATED_PLACED_DATE);
        assertThat(testISOrder.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISOrder() throws Exception {
        int databaseSizeBeforeUpdate = iSOrderRepository.findAll().size();

        // Create the ISOrder
        ISOrderDTO iSOrderDTO = iSOrderMapper.toDto(iSOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISOrderMockMvc.perform(put("/api/is-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISOrder in the database
        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISOrder() throws Exception {
        // Initialize the database
        iSOrderRepository.saveAndFlush(iSOrder);

        int databaseSizeBeforeDelete = iSOrderRepository.findAll().size();

        // Get the iSOrder
        restISOrderMockMvc.perform(delete("/api/is-orders/{id}", iSOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISOrder> iSOrderList = iSOrderRepository.findAll();
        assertThat(iSOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrder.class);
        ISOrder iSOrder1 = new ISOrder();
        iSOrder1.setId(1L);
        ISOrder iSOrder2 = new ISOrder();
        iSOrder2.setId(iSOrder1.getId());
        assertThat(iSOrder1).isEqualTo(iSOrder2);
        iSOrder2.setId(2L);
        assertThat(iSOrder1).isNotEqualTo(iSOrder2);
        iSOrder1.setId(null);
        assertThat(iSOrder1).isNotEqualTo(iSOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISOrderDTO.class);
        ISOrderDTO iSOrderDTO1 = new ISOrderDTO();
        iSOrderDTO1.setId(1L);
        ISOrderDTO iSOrderDTO2 = new ISOrderDTO();
        assertThat(iSOrderDTO1).isNotEqualTo(iSOrderDTO2);
        iSOrderDTO2.setId(iSOrderDTO1.getId());
        assertThat(iSOrderDTO1).isEqualTo(iSOrderDTO2);
        iSOrderDTO2.setId(2L);
        assertThat(iSOrderDTO1).isNotEqualTo(iSOrderDTO2);
        iSOrderDTO1.setId(null);
        assertThat(iSOrderDTO1).isNotEqualTo(iSOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSOrderMapper.fromId(null)).isNull();
    }
}
