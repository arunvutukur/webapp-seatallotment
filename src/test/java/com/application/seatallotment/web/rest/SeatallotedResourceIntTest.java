package com.application.seatallotment.web.rest;

import com.application.seatallotment.SeatallocationApp;

import com.application.seatallotment.domain.Seatalloted;
import com.application.seatallotment.repository.SeatallotedRepository;
import com.application.seatallotment.web.rest.errors.ExceptionTranslator;

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

import java.util.List;


import static com.application.seatallotment.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeatallotedResource REST controller.
 *
 * @see SeatallotedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeatallocationApp.class)
public class SeatallotedResourceIntTest {

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_VACANCY = "AAAAAAAAAA";
    private static final String UPDATED_VACANCY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_FOR_APPROVAL = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_FOR_APPROVAL = "BBBBBBBBBB";

    private static final String DEFAULT_PENDING_FOR_APPROVAL = "AAAAAAAAAA";
    private static final String UPDATED_PENDING_FOR_APPROVAL = "BBBBBBBBBB";

    @Autowired
    private SeatallotedRepository seatallotedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSeatallotedMockMvc;

    private Seatalloted seatalloted;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeatallotedResource seatallotedResource = new SeatallotedResource(seatallotedRepository);
        this.restSeatallotedMockMvc = MockMvcBuilders.standaloneSetup(seatallotedResource)
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
    public static Seatalloted createEntity() {
        Seatalloted seatalloted = new Seatalloted()
            .seatNumber(DEFAULT_SEAT_NUMBER)
            .location(DEFAULT_LOCATION)
            .floor(DEFAULT_FLOOR)
            .vacancy(DEFAULT_VACANCY)
            .requestForApproval(DEFAULT_REQUEST_FOR_APPROVAL)
            .pendingForApproval(DEFAULT_PENDING_FOR_APPROVAL);
        return seatalloted;
    }

    @Before
    public void initTest() {
        seatallotedRepository.deleteAll();
        seatalloted = createEntity();
    }

    @Test
    public void createSeatalloted() throws Exception {
        int databaseSizeBeforeCreate = seatallotedRepository.findAll().size();

        // Create the Seatalloted
        restSeatallotedMockMvc.perform(post("/api/seatalloteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatalloted)))
            .andExpect(status().isCreated());

        // Validate the Seatalloted in the database
        List<Seatalloted> seatallotedList = seatallotedRepository.findAll();
        assertThat(seatallotedList).hasSize(databaseSizeBeforeCreate + 1);
        Seatalloted testSeatalloted = seatallotedList.get(seatallotedList.size() - 1);
        assertThat(testSeatalloted.getSeatNumber()).isEqualTo(DEFAULT_SEAT_NUMBER);
        assertThat(testSeatalloted.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSeatalloted.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testSeatalloted.getVacancy()).isEqualTo(DEFAULT_VACANCY);
        assertThat(testSeatalloted.getRequestForApproval()).isEqualTo(DEFAULT_REQUEST_FOR_APPROVAL);
        assertThat(testSeatalloted.getPendingForApproval()).isEqualTo(DEFAULT_PENDING_FOR_APPROVAL);
    }

    @Test
    public void createSeatallotedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatallotedRepository.findAll().size();

        // Create the Seatalloted with an existing ID
        seatalloted.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatallotedMockMvc.perform(post("/api/seatalloteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatalloted)))
            .andExpect(status().isBadRequest());

        // Validate the Seatalloted in the database
        List<Seatalloted> seatallotedList = seatallotedRepository.findAll();
        assertThat(seatallotedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSeatalloteds() throws Exception {
        // Initialize the database
        seatallotedRepository.save(seatalloted);

        // Get all the seatallotedList
        restSeatallotedMockMvc.perform(get("/api/seatalloteds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seatalloted.getId())))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR.toString())))
            .andExpect(jsonPath("$.[*].vacancy").value(hasItem(DEFAULT_VACANCY.toString())))
            .andExpect(jsonPath("$.[*].requestForApproval").value(hasItem(DEFAULT_REQUEST_FOR_APPROVAL.toString())))
            .andExpect(jsonPath("$.[*].pendingForApproval").value(hasItem(DEFAULT_PENDING_FOR_APPROVAL.toString())));
    }
    
    @Test
    public void getSeatalloted() throws Exception {
        // Initialize the database
        seatallotedRepository.save(seatalloted);

        // Get the seatalloted
        restSeatallotedMockMvc.perform(get("/api/seatalloteds/{id}", seatalloted.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seatalloted.getId()))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR.toString()))
            .andExpect(jsonPath("$.vacancy").value(DEFAULT_VACANCY.toString()))
            .andExpect(jsonPath("$.requestForApproval").value(DEFAULT_REQUEST_FOR_APPROVAL.toString()))
            .andExpect(jsonPath("$.pendingForApproval").value(DEFAULT_PENDING_FOR_APPROVAL.toString()));
    }

    @Test
    public void getNonExistingSeatalloted() throws Exception {
        // Get the seatalloted
        restSeatallotedMockMvc.perform(get("/api/seatalloteds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSeatalloted() throws Exception {
        // Initialize the database
        seatallotedRepository.save(seatalloted);

        int databaseSizeBeforeUpdate = seatallotedRepository.findAll().size();

        // Update the seatalloted
        Seatalloted updatedSeatalloted = seatallotedRepository.findById(seatalloted.getId()).get();
        updatedSeatalloted
            .seatNumber(UPDATED_SEAT_NUMBER)
            .location(UPDATED_LOCATION)
            .floor(UPDATED_FLOOR)
            .vacancy(UPDATED_VACANCY)
            .requestForApproval(UPDATED_REQUEST_FOR_APPROVAL)
            .pendingForApproval(UPDATED_PENDING_FOR_APPROVAL);

        restSeatallotedMockMvc.perform(put("/api/seatalloteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeatalloted)))
            .andExpect(status().isOk());

        // Validate the Seatalloted in the database
        List<Seatalloted> seatallotedList = seatallotedRepository.findAll();
        assertThat(seatallotedList).hasSize(databaseSizeBeforeUpdate);
        Seatalloted testSeatalloted = seatallotedList.get(seatallotedList.size() - 1);
        assertThat(testSeatalloted.getSeatNumber()).isEqualTo(UPDATED_SEAT_NUMBER);
        assertThat(testSeatalloted.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSeatalloted.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testSeatalloted.getVacancy()).isEqualTo(UPDATED_VACANCY);
        assertThat(testSeatalloted.getRequestForApproval()).isEqualTo(UPDATED_REQUEST_FOR_APPROVAL);
        assertThat(testSeatalloted.getPendingForApproval()).isEqualTo(UPDATED_PENDING_FOR_APPROVAL);
    }

    @Test
    public void updateNonExistingSeatalloted() throws Exception {
        int databaseSizeBeforeUpdate = seatallotedRepository.findAll().size();

        // Create the Seatalloted

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatallotedMockMvc.perform(put("/api/seatalloteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatalloted)))
            .andExpect(status().isBadRequest());

        // Validate the Seatalloted in the database
        List<Seatalloted> seatallotedList = seatallotedRepository.findAll();
        assertThat(seatallotedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSeatalloted() throws Exception {
        // Initialize the database
        seatallotedRepository.save(seatalloted);

        int databaseSizeBeforeDelete = seatallotedRepository.findAll().size();

        // Get the seatalloted
        restSeatallotedMockMvc.perform(delete("/api/seatalloteds/{id}", seatalloted.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seatalloted> seatallotedList = seatallotedRepository.findAll();
        assertThat(seatallotedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seatalloted.class);
        Seatalloted seatalloted1 = new Seatalloted();
        seatalloted1.setId("id1");
        Seatalloted seatalloted2 = new Seatalloted();
        seatalloted2.setId(seatalloted1.getId());
        assertThat(seatalloted1).isEqualTo(seatalloted2);
        seatalloted2.setId("id2");
        assertThat(seatalloted1).isNotEqualTo(seatalloted2);
        seatalloted1.setId(null);
        assertThat(seatalloted1).isNotEqualTo(seatalloted2);
    }
}
