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

//    @GetMapping("/vluchten/{Id}")
//    public List<Vlucht> getVluchtById(@PathVariable String Id){
//        return vluchtRepository.findVluchtById(Id);
//    }

    @GetMapping("/vluchten/luchthaven/{luchthavenId}")
    public List<Vlucht> getVluchtByLuchthavenId(@PathVariable Integer luchthavenId){
        return vluchtRepository.findVluchtByLuchthavenId(luchthavenId);
    }

    @GetMapping("/vluchten/naam/{naam}")
    public List<Vlucht> getVluchtByNaam(@PathVariable String naam){
        return vluchtRepository.findVluchtenByNaam(naam);
    }


    @GetMapping("/vluchten/luchthaven/{luchthavenId}/naam/{naam}")
    public Vlucht getVluchtByLuchthavenIdAndNaam(@PathVariable Integer luchthavenId, @PathVariable String naam){
        return vluchtRepository.findVluchtByLuchthavenIdAndNaam(luchthavenId, naam);
    }

    @PostMapping("/vluchten")
    public Vlucht addVlucht(@RequestBody Vlucht vlucht){
        vluchtRepository.save(vlucht);
        return vlucht;
    }

    //List bij zetten of meer parameters
//    @PutMapping("/vluchten")
//    public Vlucht updateVlucht(@RequestBody Vlucht updatedVlucht){
//        Vlucht retrievedVlucht = vluchtRepository.findVluchtByLuchthavenIdAndNaam(updatedVlucht.getLuchthavenId(), updatedVlucht.getNaam());
//
//        retrievedVlucht.setNaam(updatedVlucht.getNaam());
//
//        vluchtRepository.save(retrievedVlucht);
//
//        return retrievedVlucht;
//    }

    @DeleteMapping("/vluchten/luchthaven/{luchthavenId}/naam/{naam}")
    public ResponseEntity deleteVlucht(@PathVariable Integer luchthavenId, @PathVariable String naam){
        Vlucht vlucht = vluchtRepository.findVluchtByLuchthavenIdAndNaam(luchthavenId,naam);
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
            vluchtRepository.save(new Vlucht(1,"brussel"));
        }
    }
}
