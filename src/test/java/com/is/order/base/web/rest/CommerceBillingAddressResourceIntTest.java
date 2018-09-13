package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceBillingAddress;
import com.is.order.base.repository.CommerceBillingAddressRepository;
import com.is.order.base.service.CommerceBillingAddressService;
import com.is.order.base.service.dto.CommerceBillingAddressDTO;
import com.is.order.base.service.mapper.CommerceBillingAddressMapper;
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
 * Test class for the CommerceBillingAddressResource REST controller.
 *
 * @see CommerceBillingAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceBillingAddressResourceIntTest {

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
    private CommerceBillingAddressRepository commerceBillingAddressRepository;


    @Autowired
    private CommerceBillingAddressMapper commerceBillingAddressMapper;
    

    @Autowired
    private CommerceBillingAddressService commerceBillingAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceBillingAddressMockMvc;

    private CommerceBillingAddress commerceBillingAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceBillingAddressResource commerceBillingAddressResource = new CommerceBillingAddressResource(commerceBillingAddressService);
        this.restCommerceBillingAddressMockMvc = MockMvcBuilders.standaloneSetup(commerceBillingAddressResource)
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
    public static CommerceBillingAddress createEntity(EntityManager em) {
        CommerceBillingAddress commerceBillingAddress = new CommerceBillingAddress()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .postalcode(DEFAULT_POSTALCODE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commerceBillingAddress;
    }

    @Before
    public void initTest() {
        commerceBillingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceBillingAddress() throws Exception {
        int databaseSizeBeforeCreate = commerceBillingAddressRepository.findAll().size();

        // Create the CommerceBillingAddress
        CommerceBillingAddressDTO commerceBillingAddressDTO = commerceBillingAddressMapper.toDto(commerceBillingAddress);
        restCommerceBillingAddressMockMvc.perform(post("/api/commerce-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceBillingAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceBillingAddress in the database
        List<CommerceBillingAddress> commerceBillingAddressList = commerceBillingAddressRepository.findAll();
        assertThat(commerceBillingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceBillingAddress testCommerceBillingAddress = commerceBillingAddressList.get(commerceBillingAddressList.size() - 1);
        assertThat(testCommerceBillingAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCommerceBillingAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCommerceBillingAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCommerceBillingAddress.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
        assertThat(testCommerceBillingAddress.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testCommerceBillingAddress.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommerceBillingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceBillingAddressRepository.findAll().size();

        // Create the CommerceBillingAddress with an existing ID
        commerceBillingAddress.setId(1L);
        CommerceBillingAddressDTO commerceBillingAddressDTO = commerceBillingAddressMapper.toDto(commerceBillingAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceBillingAddressMockMvc.perform(post("/api/commerce-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceBillingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceBillingAddress in the database
        List<CommerceBillingAddress> commerceBillingAddressList = commerceBillingAddressRepository.findAll();
        assertThat(commerceBillingAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommerceBillingAddresses() throws Exception {
        // Initialize the database
        commerceBillingAddressRepository.saveAndFlush(commerceBillingAddress);

        // Get all the commerceBillingAddressList
        restCommerceBillingAddressMockMvc.perform(get("/api/commerce-billing-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceBillingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getCommerceBillingAddress() throws Exception {
        // Initialize the database
        commerceBillingAddressRepository.saveAndFlush(commerceBillingAddress);

        // Get the commerceBillingAddress
        restCommerceBillingAddressMockMvc.perform(get("/api/commerce-billing-addresses/{id}", commerceBillingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceBillingAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalcode").value(DEFAULT_POSTALCODE.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceBillingAddress() throws Exception {
        // Get the commerceBillingAddress
        restCommerceBillingAddressMockMvc.perform(get("/api/commerce-billing-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceBillingAddress() throws Exception {
        // Initialize the database
        commerceBillingAddressRepository.saveAndFlush(commerceBillingAddress);

        int databaseSizeBeforeUpdate = commerceBillingAddressRepository.findAll().size();

        // Update the commerceBillingAddress
        CommerceBillingAddress updatedCommerceBillingAddress = commerceBillingAddressRepository.findById(commerceBillingAddress.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceBillingAddress are not directly saved in db
        em.detach(updatedCommerceBillingAddress);
        updatedCommerceBillingAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .postalcode(UPDATED_POSTALCODE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommerceBillingAddressDTO commerceBillingAddressDTO = commerceBillingAddressMapper.toDto(updatedCommerceBillingAddress);

        restCommerceBillingAddressMockMvc.perform(put("/api/commerce-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceBillingAddressDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceBillingAddress in the database
        List<CommerceBillingAddress> commerceBillingAddressList = commerceBillingAddressRepository.findAll();
        assertThat(commerceBillingAddressList).hasSize(databaseSizeBeforeUpdate);
        CommerceBillingAddress testCommerceBillingAddress = commerceBillingAddressList.get(commerceBillingAddressList.size() - 1);
        assertThat(testCommerceBillingAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCommerceBillingAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCommerceBillingAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCommerceBillingAddress.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
        assertThat(testCommerceBillingAddress.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testCommerceBillingAddress.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceBillingAddress() throws Exception {
        int databaseSizeBeforeUpdate = commerceBillingAddressRepository.findAll().size();

        // Create the CommerceBillingAddress
        CommerceBillingAddressDTO commerceBillingAddressDTO = commerceBillingAddressMapper.toDto(commerceBillingAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceBillingAddressMockMvc.perform(put("/api/commerce-billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceBillingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceBillingAddress in the database
        List<CommerceBillingAddress> commerceBillingAddressList = commerceBillingAddressRepository.findAll();
        assertThat(commerceBillingAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceBillingAddress() throws Exception {
        // Initialize the database
        commerceBillingAddressRepository.saveAndFlush(commerceBillingAddress);

        int databaseSizeBeforeDelete = commerceBillingAddressRepository.findAll().size();

        // Get the commerceBillingAddress
        restCommerceBillingAddressMockMvc.perform(delete("/api/commerce-billing-addresses/{id}", commerceBillingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceBillingAddress> commerceBillingAddressList = commerceBillingAddressRepository.findAll();
        assertThat(commerceBillingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceBillingAddress.class);
        CommerceBillingAddress commerceBillingAddress1 = new CommerceBillingAddress();
        commerceBillingAddress1.setId(1L);
        CommerceBillingAddress commerceBillingAddress2 = new CommerceBillingAddress();
        commerceBillingAddress2.setId(commerceBillingAddress1.getId());
        assertThat(commerceBillingAddress1).isEqualTo(commerceBillingAddress2);
        commerceBillingAddress2.setId(2L);
        assertThat(commerceBillingAddress1).isNotEqualTo(commerceBillingAddress2);
        commerceBillingAddress1.setId(null);
        assertThat(commerceBillingAddress1).isNotEqualTo(commerceBillingAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceBillingAddressDTO.class);
        CommerceBillingAddressDTO commerceBillingAddressDTO1 = new CommerceBillingAddressDTO();
        commerceBillingAddressDTO1.setId(1L);
        CommerceBillingAddressDTO commerceBillingAddressDTO2 = new CommerceBillingAddressDTO();
        assertThat(commerceBillingAddressDTO1).isNotEqualTo(commerceBillingAddressDTO2);
        commerceBillingAddressDTO2.setId(commerceBillingAddressDTO1.getId());
        assertThat(commerceBillingAddressDTO1).isEqualTo(commerceBillingAddressDTO2);
        commerceBillingAddressDTO2.setId(2L);
        assertThat(commerceBillingAddressDTO1).isNotEqualTo(commerceBillingAddressDTO2);
        commerceBillingAddressDTO1.setId(null);
        assertThat(commerceBillingAddressDTO1).isNotEqualTo(commerceBillingAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceBillingAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceBillingAddressMapper.fromId(null)).isNull();
    }
}
