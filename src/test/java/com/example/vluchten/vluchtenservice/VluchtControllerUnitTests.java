package com.example.vluchten.vluchtenservice;

import com.example.vluchten.vluchtenservice.model.Vlucht;
import com.example.vluchten.vluchtenservice.repository.VluchtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VluchtControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VluchtRepository vluchtRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenVlucht_whenGetVluchtByMaatschappijnaamAndNaam_thenReturnJsonVlucht() throws Exception {
        Vlucht vlucht1 = new Vlucht("ryanair","brussel");

        given(vluchtRepository.findVluchtByMaatschappijnaamAndNaam("ryanair","brussel")).willReturn(vlucht1);

        mockMvc.perform(get("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}", "ryanair","brussel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maatschappijnaam",is("ryanair")))
                .andExpect(jsonPath("$.naam",is("brussel")));
    }

    @Test
    public void givenVlucht_whenGetVluchtByMaatschappijnaam() throws Exception {
        Vlucht vlucht1 = new Vlucht("jetair","zaventem");
        Vlucht vlucht2 = new Vlucht("jetair","schiphol");
        List<Vlucht> vluchtenList = new ArrayList<>();
        vluchtenList.add(vlucht1);
        vluchtenList.add(vlucht2);

        given(vluchtRepository.findVluchtByMaatschappijnaam("jetair")).willReturn(vluchtenList);

        mockMvc.perform(get("/vluchten/maatschappij/{maatschappijnaam}", "jetair"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maatschappijnaam",is("jetair")))
                .andExpect(jsonPath("$[1].maatschappijnaam",is("jetair")));
    }


    @Test
    public void givenVlucht_whenGetVluchtbyNaam_thenReturnJsonVlucht() throws  Exception {
        Vlucht vlucht = new Vlucht("FlyingGroup","antwerpen");
        Vlucht vlucht2 = new Vlucht("FlyingGroup","schiphol");
        List<Vlucht> vluchtenList = new ArrayList<>();
        vluchtenList.add(vlucht);
        vluchtenList.add(vlucht2);

        given(vluchtRepository.findVluchtenByNaam("antwerpen")).willReturn(vluchtenList);

        mockMvc.perform(get("/vluchten/naam/{naam}","antwerpen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].naam",is("antwerpen")))
                .andExpect(jsonPath("$[1].naam",is("schiphol")));
    }

    @Test
    public void whenPostVlucht_thenReturnJsonVlucht() throws Exception{
        Vlucht vlucht = new Vlucht("FlyingGroup", "schiphol");

        mockMvc.perform(post("/vluchten")
                .content(mapper.writeValueAsString(vlucht))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maatschappijnaam",is("FlyingGroup")))
                .andExpect(jsonPath("$.naam",is("schiphol")));

    }

//    @Test
//    public void givenVlucht_whenPutVlucht_thenReturnJsonVlucht() throws Exception{
//        Vlucht vlucht = new Vlucht(005, "new york");
//
//        given(vluchtRepository.findVluchtByLuchthavenIdAndNaam(005,"new york")).willReturn(vlucht);
//        Vlucht updatedVlucht = new Vlucht(005, "New York");
//
//        mockMvc.perform(put("/vluchten")
//                .content(mapper.writeValueAsString(updatedVlucht))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.luchthavenId",is(005)))
//                .andExpect(jsonPath("$.naam",is("Amsterdam")));
//
//    }


    @Test
    public void givenVlucht_whenDeleteVlucht_thenStatusOk() throws Exception{
        Vlucht vluchtToBeDeleted = new Vlucht("JetAir","york");

        given(vluchtRepository.findVluchtByMaatschappijnaamAndNaam("JetAir","york")).willReturn(vluchtToBeDeleted);

        mockMvc.perform(delete("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}","JetAir", "york")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoVlucht_whenDeleteVlucht_thenStatusNotFound() throws Exception{

        given(vluchtRepository.findVluchtByMaatschappijnaamAndNaam("JetAir","york")).willReturn(null);

        mockMvc.perform(delete("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}","JetAir", "york")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
