package com.example.vluchten.vluchtenservice.controller;

import com.example.vluchten.vluchtenservice.model.Vlucht;
import com.example.vluchten.vluchtenservice.repository.VluchtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class VluchtController {
    @Autowired
    private VluchtRepository vluchtRepository;

    @GetMapping("/vluchten/maatschappij/{maatschappijnaam}")
    public List<Vlucht> getVluchtByLuchthavenId(@PathVariable String maatschappijnaam){
        return vluchtRepository.findVluchtByMaatschappijnaam(maatschappijnaam);
    }

    @GetMapping("/vluchten/naam/{naam}")
    public List<Vlucht> getVluchtByNaam(@PathVariable String naam){
        return vluchtRepository.findVluchtenByNaam(naam);
    }


    @GetMapping("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}")
    public Vlucht getVluchtByMaatschappijAndNaam(@PathVariable String maatschappijnaam, @PathVariable String naam){
        return vluchtRepository.findVluchtByMaatschappijnaamAndNaam(maatschappijnaam, naam);
    }

    @PostMapping("/vluchten")
    public Vlucht addVlucht(@RequestBody Vlucht vlucht){
        vluchtRepository.save(vlucht);
        return vlucht;
    }



    @DeleteMapping("/vluchten/maatschappij/{maatschappijnaam}/naam/{naam}")
    public ResponseEntity deleteVlucht(@PathVariable String maatschappijnaam, @PathVariable String naam){
        Vlucht vlucht = vluchtRepository.findVluchtByMaatschappijnaamAndNaam(maatschappijnaam,naam);
        if(vlucht!=null){
            vluchtRepository.delete(vlucht);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostConstruct
    public void fillDB(){
        if(vluchtRepository.count()==0){
            vluchtRepository.save(new Vlucht("jetair","brussel"));
        }
    }
}
