package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISBillingAddress;
import com.is.order.base.repository.ISBillingAddressRepository;
import com.is.order.base.service.ISBillingAddressService;
import com.is.order.base.service.dto.ISBillingAddressDTO;
import com.is.order.base.service.mapper.ISBillingAddressMapper;
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

/**
 * Test class for the ISBillingAddressResource REST controller.
 *
 * @see ISBillingAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISBillingAddressResourceIntTest {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTALCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTALCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ISBillingAddressRepository iSBillingAddressRepository;


    @Autowired
    private ISBillingAddressMapper iSBillingAddressMapper;
    

    @Autowired
    private ISBillingAddressService iSBillingAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISBillingAddressMockMvc;

    private ISBillingAddress iSBillingAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISBillingAddressResource iSBillingAddressResource = new ISBillingAddressResource(iSBillingAddressService);
        this.restISBillingAddressMockMvc = MockMvcBuilders.standaloneSetup(iSBillingAddressResource)
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
    public static ISBillingAddress createEntity(EntityManager em) {
        ISBillingAddress iSBillingAddress = new ISBillingAddress()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .postalcode(DEFAULT_POSTALCODE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSBillingAddress;
    }

    @Before
    public void initTest() {
        iSBillingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createISBillingAddress() throws Exception {
        int databaseSizeBeforeCreate = iSBillingAddressRepository.findAll().size();

        // Create the ISBillingAddress
        ISBillingAddressDTO iSBillingAddressDTO = iSBillingAddressMapper.toDto(iSBillingAddress);
        restISBillingAddressMockMvc.perform(post("/api/is-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSBillingAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the ISBillingAddress in the database
        List<ISBillingAddress> iSBillingAddressList = iSBillingAddressRepository.findAll();
        assertThat(iSBillingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ISBillingAddress testISBillingAddress = iSBillingAddressList.get(iSBillingAddressList.size() - 1);
        assertThat(testISBillingAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testISBillingAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testISBillingAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testISBillingAddress.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
        assertThat(testISBillingAddress.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testISBillingAddress.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISBillingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSBillingAddressRepository.findAll().size();

        // Create the ISBillingAddress with an existing ID
        iSBillingAddress.setId(1L);
        ISBillingAddressDTO iSBillingAddressDTO = iSBillingAddressMapper.toDto(iSBillingAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISBillingAddressMockMvc.perform(post("/api/is-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSBillingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISBillingAddress in the database
        List<ISBillingAddress> iSBillingAddressList = iSBillingAddressRepository.findAll();
        assertThat(iSBillingAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllISBillingAddresses() throws Exception {
        // Initialize the database
        iSBillingAddressRepository.saveAndFlush(iSBillingAddress);

        // Get all the iSBillingAddressList
        restISBillingAddressMockMvc.perform(get("/api/is-billing-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSBillingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getISBillingAddress() throws Exception {
        // Initialize the database
        iSBillingAddressRepository.saveAndFlush(iSBillingAddress);

        // Get the iSBillingAddress
        restISBillingAddressMockMvc.perform(get("/api/is-billing-addresses/{id}", iSBillingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSBillingAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalcode").value(DEFAULT_POSTALCODE.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISBillingAddress() throws Exception {
        // Get the iSBillingAddress
        restISBillingAddressMockMvc.perform(get("/api/is-billing-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISBillingAddress() throws Exception {
        // Initialize the database
        iSBillingAddressRepository.saveAndFlush(iSBillingAddress);

        int databaseSizeBeforeUpdate = iSBillingAddressRepository.findAll().size();

        // Update the iSBillingAddress
        ISBillingAddress updatedISBillingAddress = iSBillingAddressRepository.findById(iSBillingAddress.getId()).get();
        // Disconnect from session so that the updates on updatedISBillingAddress are not directly saved in db
        em.detach(updatedISBillingAddress);
        updatedISBillingAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .postalcode(UPDATED_POSTALCODE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISBillingAddressDTO iSBillingAddressDTO = iSBillingAddressMapper.toDto(updatedISBillingAddress);

        restISBillingAddressMockMvc.perform(put("/api/is-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSBillingAddressDTO)))
            .andExpect(status().isOk());

        // Validate the ISBillingAddress in the database
        List<ISBillingAddress> iSBillingAddressList = iSBillingAddressRepository.findAll();
        assertThat(iSBillingAddressList).hasSize(databaseSizeBeforeUpdate);
        ISBillingAddress testISBillingAddress = iSBillingAddressList.get(iSBillingAddressList.size() - 1);
        assertThat(testISBillingAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testISBillingAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testISBillingAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testISBillingAddress.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
        assertThat(testISBillingAddress.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testISBillingAddress.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISBillingAddress() throws Exception {
        int databaseSizeBeforeUpdate = iSBillingAddressRepository.findAll().size();

        // Create the ISBillingAddress
        ISBillingAddressDTO iSBillingAddressDTO = iSBillingAddressMapper.toDto(iSBillingAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISBillingAddressMockMvc.perform(put("/api/is-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSBillingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISBillingAddress in the database
        List<ISBillingAddress> iSBillingAddressList = iSBillingAddressRepository.findAll();
        assertThat(iSBillingAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISBillingAddress() throws Exception {
        // Initialize the database
        iSBillingAddressRepository.saveAndFlush(iSBillingAddress);

        int databaseSizeBeforeDelete = iSBillingAddressRepository.findAll().size();

        // Get the iSBillingAddress
        restISBillingAddressMockMvc.perform(delete("/api/is-billing-addresses/{id}", iSBillingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISBillingAddress> iSBillingAddressList = iSBillingAddressRepository.findAll();
        assertThat(iSBillingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISBillingAddress.class);
        ISBillingAddress iSBillingAddress1 = new ISBillingAddress();
        iSBillingAddress1.setId(1L);
        ISBillingAddress iSBillingAddress2 = new ISBillingAddress();
        iSBillingAddress2.setId(iSBillingAddress1.getId());
        assertThat(iSBillingAddress1).isEqualTo(iSBillingAddress2);
        iSBillingAddress2.setId(2L);
        assertThat(iSBillingAddress1).isNotEqualTo(iSBillingAddress2);
        iSBillingAddress1.setId(null);
        assertThat(iSBillingAddress1).isNotEqualTo(iSBillingAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISBillingAddressDTO.class);
        ISBillingAddressDTO iSBillingAddressDTO1 = new ISBillingAddressDTO();
        iSBillingAddressDTO1.setId(1L);
        ISBillingAddressDTO iSBillingAddressDTO2 = new ISBillingAddressDTO();
        assertThat(iSBillingAddressDTO1).isNotEqualTo(iSBillingAddressDTO2);
        iSBillingAddressDTO2.setId(iSBillingAddressDTO1.getId());
        assertThat(iSBillingAddressDTO1).isEqualTo(iSBillingAddressDTO2);
        iSBillingAddressDTO2.setId(2L);
        assertThat(iSBillingAddressDTO1).isNotEqualTo(iSBillingAddressDTO2);
        iSBillingAddressDTO1.setId(null);
        assertThat(iSBillingAddressDTO1).isNotEqualTo(iSBillingAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSBillingAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSBillingAddressMapper.fromId(null)).isNull();
    }
}
