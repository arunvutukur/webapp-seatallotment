package com.application.seatallotment.web.rest;

import com.application.seatallotment.SeatallocationApp;

import com.application.seatallotment.domain.Access;
import com.application.seatallotment.repository.AccessRepository;
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
 * Test class for the AccessResource REST controller.
 *
 * @see AccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeatallocationApp.class)
public class AccessResourceIntTest {

    private static final String DEFAULT_ASSOCIATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAccessMockMvc;

    private Access access;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccessResource accessResource = new AccessResource(accessRepository);
        this.restAccessMockMvc = MockMvcBuilders.standaloneSetup(accessResource)
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
    public static Access createEntity() {
        Access access = new Access()
            .associateId(DEFAULT_ASSOCIATE_ID)
            .role(DEFAULT_ROLE)
            .password(DEFAULT_PASSWORD);
        return access;
    }

    @Before
    public void initTest() {
        accessRepository.deleteAll();
        access = createEntity();
    }

    @Test
    public void createAccess() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();

        // Create the Access
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(access)))
            .andExpect(status().isCreated());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate + 1);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getAssociateId()).isEqualTo(DEFAULT_ASSOCIATE_ID);
        assertThat(testAccess.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testAccess.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    public void createAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessRepository.findAll().size();

        // Create the Access with an existing ID
        access.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessMockMvc.perform(post("/api/accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(access)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAccesses() throws Exception {
        // Initialize the database
        accessRepository.save(access);

        // Get all the accessList
        restAccessMockMvc.perform(get("/api/accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId())))
            .andExpect(jsonPath("$.[*].associateId").value(hasItem(DEFAULT_ASSOCIATE_ID.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    public void getAccess() throws Exception {
        // Initialize the database
        accessRepository.save(access);

        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", access.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(access.getId()))
            .andExpect(jsonPath("$.associateId").value(DEFAULT_ASSOCIATE_ID.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    public void getNonExistingAccess() throws Exception {
        // Get the access
        restAccessMockMvc.perform(get("/api/accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccess() throws Exception {
        // Initialize the database
        accessRepository.save(access);

        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Update the access
        Access updatedAccess = accessRepository.findById(access.getId()).get();
        updatedAccess
            .associateId(UPDATED_ASSOCIATE_ID)
            .role(UPDATED_ROLE)
            .password(UPDATED_PASSWORD);

        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccess)))
            .andExpect(status().isOk());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
        Access testAccess = accessList.get(accessList.size() - 1);
        assertThat(testAccess.getAssociateId()).isEqualTo(UPDATED_ASSOCIATE_ID);
        assertThat(testAccess.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testAccess.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    public void updateNonExistingAccess() throws Exception {
        int databaseSizeBeforeUpdate = accessRepository.findAll().size();

        // Create the Access

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessMockMvc.perform(put("/api/accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(access)))
            .andExpect(status().isBadRequest());

        // Validate the Access in the database
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAccess() throws Exception {
        // Initialize the database
        accessRepository.save(access);

        int databaseSizeBeforeDelete = accessRepository.findAll().size();

        // Get the access
        restAccessMockMvc.perform(delete("/api/accesses/{id}", access.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Access> accessList = accessRepository.findAll();
        assertThat(accessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Access.class);
        Access access1 = new Access();
        access1.setId("id1");
        Access access2 = new Access();
        access2.setId(access1.getId());
        assertThat(access1).isEqualTo(access2);
        access2.setId("id2");
        assertThat(access1).isNotEqualTo(access2);
        access1.setId(null);
        assertThat(access1).isNotEqualTo(access2);
    }
}
