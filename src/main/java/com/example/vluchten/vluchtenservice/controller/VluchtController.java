package com.example.vluchten.vluchtenservice.controller;

import com.example.vluchten.vluchtenservice.model.Vlucht;
import com.example.vluchten.vluchtenservice.repository.VluchtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
@RestController
public class VluchtController {
    @Autowired
    private VluchtRepository vluchtRepository;

    @GetMapping("/vlucht/{Id}")
    public List<Vlucht> getVluchtById(@PathVariable String Id){
        return vluchtRepository.findVluchtById(Id);
    }

    @GetMapping("/vlucht/naam/{naam}")
    public List<Vlucht> getVluchtByNaam(@PathVariable String naam){
        return vluchtRepository.findVluchtByNaam(naam);
    }

    @PostMapping("/vlucht")
    public Vlucht addVlucht(@RequestBody Vlucht vlucht){
        vluchtRepository.save(vlucht);
        return vlucht;
    }

    @PostConstruct
    public void fillDB(){
        if(vluchtRepository.count()==0){
            vluchtRepository.save(new Vlucht(1,"brussel"));
        }
    }
}
