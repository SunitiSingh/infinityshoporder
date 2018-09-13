package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.ISShippingContainer;
import com.is.order.base.repository.ISShippingContainerRepository;
import com.is.order.base.service.ISShippingContainerService;
import com.is.order.base.service.dto.ISShippingContainerDTO;
import com.is.order.base.service.mapper.ISShippingContainerMapper;
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

import com.is.order.base.domain.enumeration.ISShipStatus;
/**
 * Test class for the ISShippingContainerResource REST controller.
 *
 * @see ISShippingContainerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class ISShippingContainerResourceIntTest {

    private static final ISShipStatus DEFAULT_SHIPSTATUS = ISShipStatus.INIT;
    private static final ISShipStatus UPDATED_SHIPSTATUS = ISShipStatus.DROPPED;

    private static final String DEFAULT_CARRIER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ISShippingContainerRepository iSShippingContainerRepository;


    @Autowired
    private ISShippingContainerMapper iSShippingContainerMapper;
    

    @Autowired
    private ISShippingContainerService iSShippingContainerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restISShippingContainerMockMvc;

    private ISShippingContainer iSShippingContainer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ISShippingContainerResource iSShippingContainerResource = new ISShippingContainerResource(iSShippingContainerService);
        this.restISShippingContainerMockMvc = MockMvcBuilders.standaloneSetup(iSShippingContainerResource)
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
    public static ISShippingContainer createEntity(EntityManager em) {
        ISShippingContainer iSShippingContainer = new ISShippingContainer()
            .shipstatus(DEFAULT_SHIPSTATUS)
            .carrier(DEFAULT_CARRIER)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return iSShippingContainer;
    }

    @Before
    public void initTest() {
        iSShippingContainer = createEntity(em);
    }

    @Test
    @Transactional
    public void createISShippingContainer() throws Exception {
        int databaseSizeBeforeCreate = iSShippingContainerRepository.findAll().size();

        // Create the ISShippingContainer
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(iSShippingContainer);
        restISShippingContainerMockMvc.perform(post("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isCreated());

        // Validate the ISShippingContainer in the database
        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeCreate + 1);
        ISShippingContainer testISShippingContainer = iSShippingContainerList.get(iSShippingContainerList.size() - 1);
        assertThat(testISShippingContainer.getShipstatus()).isEqualTo(DEFAULT_SHIPSTATUS);
        assertThat(testISShippingContainer.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testISShippingContainer.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testISShippingContainer.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createISShippingContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iSShippingContainerRepository.findAll().size();

        // Create the ISShippingContainer with an existing ID
        iSShippingContainer.setId(1L);
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(iSShippingContainer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restISShippingContainerMockMvc.perform(post("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShippingContainer in the database
        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShipstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSShippingContainerRepository.findAll().size();
        // set the field null
        iSShippingContainer.setShipstatus(null);

        // Create the ISShippingContainer, which fails.
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(iSShippingContainer);

        restISShippingContainerMockMvc.perform(post("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSShippingContainerRepository.findAll().size();
        // set the field null
        iSShippingContainer.setCreationDate(null);

        // Create the ISShippingContainer, which fails.
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(iSShippingContainer);

        restISShippingContainerMockMvc.perform(post("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllISShippingContainers() throws Exception {
        // Initialize the database
        iSShippingContainerRepository.saveAndFlush(iSShippingContainer);

        // Get all the iSShippingContainerList
        restISShippingContainerMockMvc.perform(get("/api/is-shipping-containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSShippingContainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipstatus").value(hasItem(DEFAULT_SHIPSTATUS.toString())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getISShippingContainer() throws Exception {
        // Initialize the database
        iSShippingContainerRepository.saveAndFlush(iSShippingContainer);

        // Get the iSShippingContainer
        restISShippingContainerMockMvc.perform(get("/api/is-shipping-containers/{id}", iSShippingContainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iSShippingContainer.getId().intValue()))
            .andExpect(jsonPath("$.shipstatus").value(DEFAULT_SHIPSTATUS.toString()))
            .andExpect(jsonPath("$.carrier").value(DEFAULT_CARRIER.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingISShippingContainer() throws Exception {
        // Get the iSShippingContainer
        restISShippingContainerMockMvc.perform(get("/api/is-shipping-containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateISShippingContainer() throws Exception {
        // Initialize the database
        iSShippingContainerRepository.saveAndFlush(iSShippingContainer);

        int databaseSizeBeforeUpdate = iSShippingContainerRepository.findAll().size();

        // Update the iSShippingContainer
        ISShippingContainer updatedISShippingContainer = iSShippingContainerRepository.findById(iSShippingContainer.getId()).get();
        // Disconnect from session so that the updates on updatedISShippingContainer are not directly saved in db
        em.detach(updatedISShippingContainer);
        updatedISShippingContainer
            .shipstatus(UPDATED_SHIPSTATUS)
            .carrier(UPDATED_CARRIER)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(updatedISShippingContainer);

        restISShippingContainerMockMvc.perform(put("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isOk());

        // Validate the ISShippingContainer in the database
        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeUpdate);
        ISShippingContainer testISShippingContainer = iSShippingContainerList.get(iSShippingContainerList.size() - 1);
        assertThat(testISShippingContainer.getShipstatus()).isEqualTo(UPDATED_SHIPSTATUS);
        assertThat(testISShippingContainer.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testISShippingContainer.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testISShippingContainer.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingISShippingContainer() throws Exception {
        int databaseSizeBeforeUpdate = iSShippingContainerRepository.findAll().size();

        // Create the ISShippingContainer
        ISShippingContainerDTO iSShippingContainerDTO = iSShippingContainerMapper.toDto(iSShippingContainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restISShippingContainerMockMvc.perform(put("/api/is-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iSShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ISShippingContainer in the database
        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteISShippingContainer() throws Exception {
        // Initialize the database
        iSShippingContainerRepository.saveAndFlush(iSShippingContainer);

        int databaseSizeBeforeDelete = iSShippingContainerRepository.findAll().size();

        // Get the iSShippingContainer
        restISShippingContainerMockMvc.perform(delete("/api/is-shipping-containers/{id}", iSShippingContainer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ISShippingContainer> iSShippingContainerList = iSShippingContainerRepository.findAll();
        assertThat(iSShippingContainerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShippingContainer.class);
        ISShippingContainer iSShippingContainer1 = new ISShippingContainer();
        iSShippingContainer1.setId(1L);
        ISShippingContainer iSShippingContainer2 = new ISShippingContainer();
        iSShippingContainer2.setId(iSShippingContainer1.getId());
        assertThat(iSShippingContainer1).isEqualTo(iSShippingContainer2);
        iSShippingContainer2.setId(2L);
        assertThat(iSShippingContainer1).isNotEqualTo(iSShippingContainer2);
        iSShippingContainer1.setId(null);
        assertThat(iSShippingContainer1).isNotEqualTo(iSShippingContainer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISShippingContainerDTO.class);
        ISShippingContainerDTO iSShippingContainerDTO1 = new ISShippingContainerDTO();
        iSShippingContainerDTO1.setId(1L);
        ISShippingContainerDTO iSShippingContainerDTO2 = new ISShippingContainerDTO();
        assertThat(iSShippingContainerDTO1).isNotEqualTo(iSShippingContainerDTO2);
        iSShippingContainerDTO2.setId(iSShippingContainerDTO1.getId());
        assertThat(iSShippingContainerDTO1).isEqualTo(iSShippingContainerDTO2);
        iSShippingContainerDTO2.setId(2L);
        assertThat(iSShippingContainerDTO1).isNotEqualTo(iSShippingContainerDTO2);
        iSShippingContainerDTO1.setId(null);
        assertThat(iSShippingContainerDTO1).isNotEqualTo(iSShippingContainerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(iSShippingContainerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(iSShippingContainerMapper.fromId(null)).isNull();
    }
}
