package com.is.order.base.web.rest;

import com.is.order.base.InfinityshoporderApp;

import com.is.order.base.domain.CommerceShippingContainer;
import com.is.order.base.repository.CommerceShippingContainerRepository;
import com.is.order.base.service.CommerceShippingContainerService;
import com.is.order.base.service.dto.CommerceShippingContainerDTO;
import com.is.order.base.service.mapper.CommerceShippingContainerMapper;
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

import com.is.order.base.domain.enumeration.CommerceShipStatus;
/**
 * Test class for the CommerceShippingContainerResource REST controller.
 *
 * @see CommerceShippingContainerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityshoporderApp.class)
public class CommerceShippingContainerResourceIntTest {

    private static final CommerceShipStatus DEFAULT_SHIPSTATUS = CommerceShipStatus.INIT;
    private static final CommerceShipStatus UPDATED_SHIPSTATUS = CommerceShipStatus.DROPPED;

    private static final String DEFAULT_CARRIER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommerceShippingContainerRepository commerceShippingContainerRepository;


    @Autowired
    private CommerceShippingContainerMapper commerceShippingContainerMapper;
    

    @Autowired
    private CommerceShippingContainerService commerceShippingContainerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommerceShippingContainerMockMvc;

    private CommerceShippingContainer commerceShippingContainer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceShippingContainerResource commerceShippingContainerResource = new CommerceShippingContainerResource(commerceShippingContainerService);
        this.restCommerceShippingContainerMockMvc = MockMvcBuilders.standaloneSetup(commerceShippingContainerResource)
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
    public static CommerceShippingContainer createEntity(EntityManager em) {
        CommerceShippingContainer commerceShippingContainer = new CommerceShippingContainer()
            .shipstatus(DEFAULT_SHIPSTATUS)
            .carrier(DEFAULT_CARRIER)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return commerceShippingContainer;
    }

    @Before
    public void initTest() {
        commerceShippingContainer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceShippingContainer() throws Exception {
        int databaseSizeBeforeCreate = commerceShippingContainerRepository.findAll().size();

        // Create the CommerceShippingContainer
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(commerceShippingContainer);
        restCommerceShippingContainerMockMvc.perform(post("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceShippingContainer in the database
        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceShippingContainer testCommerceShippingContainer = commerceShippingContainerList.get(commerceShippingContainerList.size() - 1);
        assertThat(testCommerceShippingContainer.getShipstatus()).isEqualTo(DEFAULT_SHIPSTATUS);
        assertThat(testCommerceShippingContainer.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testCommerceShippingContainer.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommerceShippingContainer.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createCommerceShippingContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceShippingContainerRepository.findAll().size();

        // Create the CommerceShippingContainer with an existing ID
        commerceShippingContainer.setId(1L);
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(commerceShippingContainer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceShippingContainerMockMvc.perform(post("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShippingContainer in the database
        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShipstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceShippingContainerRepository.findAll().size();
        // set the field null
        commerceShippingContainer.setShipstatus(null);

        // Create the CommerceShippingContainer, which fails.
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(commerceShippingContainer);

        restCommerceShippingContainerMockMvc.perform(post("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceShippingContainerRepository.findAll().size();
        // set the field null
        commerceShippingContainer.setCreationDate(null);

        // Create the CommerceShippingContainer, which fails.
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(commerceShippingContainer);

        restCommerceShippingContainerMockMvc.perform(post("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceShippingContainers() throws Exception {
        // Initialize the database
        commerceShippingContainerRepository.saveAndFlush(commerceShippingContainer);

        // Get all the commerceShippingContainerList
        restCommerceShippingContainerMockMvc.perform(get("/api/commerce-shipping-containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceShippingContainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipstatus").value(hasItem(DEFAULT_SHIPSTATUS.toString())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    

    @Test
    @Transactional
    public void getCommerceShippingContainer() throws Exception {
        // Initialize the database
        commerceShippingContainerRepository.saveAndFlush(commerceShippingContainer);

        // Get the commerceShippingContainer
        restCommerceShippingContainerMockMvc.perform(get("/api/commerce-shipping-containers/{id}", commerceShippingContainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceShippingContainer.getId().intValue()))
            .andExpect(jsonPath("$.shipstatus").value(DEFAULT_SHIPSTATUS.toString()))
            .andExpect(jsonPath("$.carrier").value(DEFAULT_CARRIER.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingCommerceShippingContainer() throws Exception {
        // Get the commerceShippingContainer
        restCommerceShippingContainerMockMvc.perform(get("/api/commerce-shipping-containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceShippingContainer() throws Exception {
        // Initialize the database
        commerceShippingContainerRepository.saveAndFlush(commerceShippingContainer);

        int databaseSizeBeforeUpdate = commerceShippingContainerRepository.findAll().size();

        // Update the commerceShippingContainer
        CommerceShippingContainer updatedCommerceShippingContainer = commerceShippingContainerRepository.findById(commerceShippingContainer.getId()).get();
        // Disconnect from session so that the updates on updatedCommerceShippingContainer are not directly saved in db
        em.detach(updatedCommerceShippingContainer);
        updatedCommerceShippingContainer
            .shipstatus(UPDATED_SHIPSTATUS)
            .carrier(UPDATED_CARRIER)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(updatedCommerceShippingContainer);

        restCommerceShippingContainerMockMvc.perform(put("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceShippingContainer in the database
        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeUpdate);
        CommerceShippingContainer testCommerceShippingContainer = commerceShippingContainerList.get(commerceShippingContainerList.size() - 1);
        assertThat(testCommerceShippingContainer.getShipstatus()).isEqualTo(UPDATED_SHIPSTATUS);
        assertThat(testCommerceShippingContainer.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testCommerceShippingContainer.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommerceShippingContainer.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceShippingContainer() throws Exception {
        int databaseSizeBeforeUpdate = commerceShippingContainerRepository.findAll().size();

        // Create the CommerceShippingContainer
        CommerceShippingContainerDTO commerceShippingContainerDTO = commerceShippingContainerMapper.toDto(commerceShippingContainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCommerceShippingContainerMockMvc.perform(put("/api/commerce-shipping-containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceShippingContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommerceShippingContainer in the database
        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommerceShippingContainer() throws Exception {
        // Initialize the database
        commerceShippingContainerRepository.saveAndFlush(commerceShippingContainer);

        int databaseSizeBeforeDelete = commerceShippingContainerRepository.findAll().size();

        // Get the commerceShippingContainer
        restCommerceShippingContainerMockMvc.perform(delete("/api/commerce-shipping-containers/{id}", commerceShippingContainer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceShippingContainer> commerceShippingContainerList = commerceShippingContainerRepository.findAll();
        assertThat(commerceShippingContainerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShippingContainer.class);
        CommerceShippingContainer commerceShippingContainer1 = new CommerceShippingContainer();
        commerceShippingContainer1.setId(1L);
        CommerceShippingContainer commerceShippingContainer2 = new CommerceShippingContainer();
        commerceShippingContainer2.setId(commerceShippingContainer1.getId());
        assertThat(commerceShippingContainer1).isEqualTo(commerceShippingContainer2);
        commerceShippingContainer2.setId(2L);
        assertThat(commerceShippingContainer1).isNotEqualTo(commerceShippingContainer2);
        commerceShippingContainer1.setId(null);
        assertThat(commerceShippingContainer1).isNotEqualTo(commerceShippingContainer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommerceShippingContainerDTO.class);
        CommerceShippingContainerDTO commerceShippingContainerDTO1 = new CommerceShippingContainerDTO();
        commerceShippingContainerDTO1.setId(1L);
        CommerceShippingContainerDTO commerceShippingContainerDTO2 = new CommerceShippingContainerDTO();
        assertThat(commerceShippingContainerDTO1).isNotEqualTo(commerceShippingContainerDTO2);
        commerceShippingContainerDTO2.setId(commerceShippingContainerDTO1.getId());
        assertThat(commerceShippingContainerDTO1).isEqualTo(commerceShippingContainerDTO2);
        commerceShippingContainerDTO2.setId(2L);
        assertThat(commerceShippingContainerDTO1).isNotEqualTo(commerceShippingContainerDTO2);
        commerceShippingContainerDTO1.setId(null);
        assertThat(commerceShippingContainerDTO1).isNotEqualTo(commerceShippingContainerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commerceShippingContainerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commerceShippingContainerMapper.fromId(null)).isNull();
    }
}
