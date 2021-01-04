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
    public void givenVlucht_whenGetVluchtByLuchthavenIdAndNaam_thenReturnJsonVlucht() throws Exception {
        Vlucht vlucht1 = new Vlucht(001,"kutnest");

        given(vluchtRepository.findVluchtByLuchthavenIdAndNaam(001,"kutnest")).willReturn(vlucht1);

        mockMvc.perform(get("/vluchten/luchthaven/{luchthavenId}/naam/{naam}", 001,"kutnest"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.luchthavenId",is(001)))
                .andExpect(jsonPath("$.naam",is("kutnest")));
    }

    @Test
    public void givenVlucht_whenGetVluchtByLuchthavenId() throws Exception {
        Vlucht vlucht1 = new Vlucht(001,"zaventem");
        Vlucht vlucht2 = new Vlucht(001,"schiphol");
        List<Vlucht> vluchtenList = new ArrayList<>();
        vluchtenList.add(vlucht1);
        vluchtenList.add(vlucht2);

        given(vluchtRepository.findVluchtByLuchthavenId(001)).willReturn(vluchtenList);

        mockMvc.perform(get("/vluchten/luchthaven/{luchthavenId}", 001))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].luchthavenId",is(001)))
                .andExpect(jsonPath("$[1].luchthavenId",is(001)));
    }


    @Test
    public void givenVlucht_whenGetVluchtbyNaam_thenReturnJsonVlucht() throws  Exception {
        Vlucht vlucht = new Vlucht(001,"antwerpen");
        Vlucht vlucht2 = new Vlucht(002,"schiphol");
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
        Vlucht vlucht = new Vlucht(004, "schiphol");

        mockMvc.perform(post("/vluchten")
                .content(mapper.writeValueAsString(vlucht))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.luchthavenId",is(004)))
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
        Vlucht vluchtToBeDeleted = new Vlucht(999,"york");

        given(vluchtRepository.findVluchtByLuchthavenIdAndNaam(999,"york")).willReturn(vluchtToBeDeleted);

        mockMvc.perform(delete("/vluchten/luchthaven/{luchthavenId}/naam/{naam}",999, "york")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoVlucht_whenDeleteVlucht_thenStatusNotFound() throws Exception{

        given(vluchtRepository.findVluchtByLuchthavenIdAndNaam(564,"york")).willReturn(null);

        mockMvc.perform(delete("/vluchten/luchthaven/{luchthavenId}/naam/{naam}",564, "york")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
