package com.example.vluchten.vluchtenservice;

import com.example.vluchten.vluchtenservice.model.Vlucht;
import com.example.vluchten.vluchtenservice.repository.VluchtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VluchtControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VluchtRepository vluchtRepository;

    private Vlucht vlucht1 = new Vlucht("JetAir","antwerpen");
    private Vlucht vlucht2 = new Vlucht("RyanAir","brussel");
    private Vlucht vlucht3 = new Vlucht("FlightGroup","zaventem");
    private Vlucht vluchtToBeDeleted = new Vlucht("Royal Air Antwerpen","Schiphol");

    @BeforeEach
    public void beforeAllTest(){
        vluchtRepository.deleteAll();
        vluchtRepository.save(vlucht1);
        vluchtRepository.save(vlucht2);
        vluchtRepository.save(vlucht3);
        vluchtRepository.save(vluchtToBeDeleted);
    }

    @AfterEach
    public void afterAllTests(){
        vluchtRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenVlucht_whenGetVluchtByLuchthavenIdAndNaam_thenReturnJsonReview() throws Exception {

        mockMvc.perform(get("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}", "JetAir", "antwerpen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maatschappijnaam", is("JetAir")))
                .andExpect(jsonPath("$.naam", is("antwerpen")));
    }

    @Test
    public void givenVlucht_whenGetVluchtByNaam_thenReturnJsonReviews() throws Exception {

        mockMvc.perform(get("/vluchten/naam/{naam}", "brussel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maatschappijnaam", is("RyanAir")))
                .andExpect(jsonPath("$[0].naam", is("brussel")));
    }


}
