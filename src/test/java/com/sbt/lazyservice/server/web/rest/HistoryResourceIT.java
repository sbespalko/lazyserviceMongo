package com.sbt.lazyservice.server.web.rest;

import com.sbt.lazyservice.server.LazyserviceMongoApp;
import com.sbt.lazyservice.server.domain.History;
import com.sbt.lazyservice.server.repository.HistoryRepository;
import com.sbt.lazyservice.server.service.HistoryService;
import com.sbt.lazyservice.server.service.dto.HistoryDTO;
import com.sbt.lazyservice.server.service.mapper.HistoryMapper;
import com.sbt.lazyservice.server.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.sbt.lazyservice.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HistoryResource} REST controller.
 */
@SpringBootTest(classes = LazyserviceMongoApp.class)
public class HistoryResourceIT {

    private static final Instant DEFAULT_START_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_START_DT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_END_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_END_DT = Instant.ofEpochMilli(-1L);

    private static final Integer DEFAULT_RISK_POWER = 1;
    private static final Integer UPDATED_RISK_POWER = 2;
    private static final Integer SMALLER_RISK_POWER = 1 - 1;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restHistoryMockMvc;

    private History history;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoryResource historyResource = new HistoryResource(historyService);
        this.restHistoryMockMvc = MockMvcBuilders.standaloneSetup(historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createEntity() {
        History history = new History()
            .startDt(DEFAULT_START_DT)
            .endDt(DEFAULT_END_DT)
            .riskPower(DEFAULT_RISK_POWER);
        return history;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createUpdatedEntity() {
        History history = new History()
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT)
            .riskPower(UPDATED_RISK_POWER);
        return history;
    }

    @BeforeEach
    public void initTest() {
        historyRepository.deleteAll();
        history = createEntity();
    }

    @Test
    public void createHistory() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);
        restHistoryMockMvc.perform(post("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isCreated());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate + 1);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getStartDt()).isEqualTo(DEFAULT_START_DT);
        assertThat(testHistory.getEndDt()).isEqualTo(DEFAULT_END_DT);
        assertThat(testHistory.getRiskPower()).isEqualTo(DEFAULT_RISK_POWER);
    }

    @Test
    public void createHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // Create the History with an existing ID
        history.setId("existing_id");
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryMockMvc.perform(post("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllHistories() throws Exception {
        // Initialize the database
        historyRepository.save(history);

        // Get all the historyList
        restHistoryMockMvc.perform(get("/api/histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId())))
            .andExpect(jsonPath("$.[*].startDt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].endDt").value(hasItem(DEFAULT_END_DT.toString())))
            .andExpect(jsonPath("$.[*].riskPower").value(hasItem(DEFAULT_RISK_POWER)));
    }
    
    @Test
    public void getHistory() throws Exception {
        // Initialize the database
        historyRepository.save(history);

        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(history.getId()))
            .andExpect(jsonPath("$.startDt").value(DEFAULT_START_DT.toString()))
            .andExpect(jsonPath("$.endDt").value(DEFAULT_END_DT.toString()))
            .andExpect(jsonPath("$.riskPower").value(DEFAULT_RISK_POWER));
    }

    @Test
    public void getNonExistingHistory() throws Exception {
        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHistory() throws Exception {
        // Initialize the database
        historyRepository.save(history);

        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history
        History updatedHistory = historyRepository.findById(history.getId()).get();
        updatedHistory
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT)
            .riskPower(UPDATED_RISK_POWER);
        HistoryDTO historyDTO = historyMapper.toDto(updatedHistory);

        restHistoryMockMvc.perform(put("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isOk());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getStartDt()).isEqualTo(UPDATED_START_DT);
        assertThat(testHistory.getEndDt()).isEqualTo(UPDATED_END_DT);
        assertThat(testHistory.getRiskPower()).isEqualTo(UPDATED_RISK_POWER);
    }

    @Test
    public void updateNonExistingHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryMockMvc.perform(put("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteHistory() throws Exception {
        // Initialize the database
        historyRepository.save(history);

        int databaseSizeBeforeDelete = historyRepository.findAll().size();

        // Delete the history
        restHistoryMockMvc.perform(delete("/api/histories/{id}", history.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(History.class);
        History history1 = new History();
        history1.setId("id1");
        History history2 = new History();
        history2.setId(history1.getId());
        assertThat(history1).isEqualTo(history2);
        history2.setId("id2");
        assertThat(history1).isNotEqualTo(history2);
        history1.setId(null);
        assertThat(history1).isNotEqualTo(history2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoryDTO.class);
        HistoryDTO historyDTO1 = new HistoryDTO();
        historyDTO1.setId("id1");
        HistoryDTO historyDTO2 = new HistoryDTO();
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
        historyDTO2.setId(historyDTO1.getId());
        assertThat(historyDTO1).isEqualTo(historyDTO2);
        historyDTO2.setId("id2");
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
        historyDTO1.setId(null);
        assertThat(historyDTO1).isNotEqualTo(historyDTO2);
    }
}
