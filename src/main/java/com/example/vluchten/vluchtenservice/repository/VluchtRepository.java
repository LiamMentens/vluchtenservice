package com.example.vluchten.vluchtenservice.repository;

import com.example.vluchten.vluchtenservice.model.Vlucht;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VluchtRepository extends MongoRepository<Vlucht, String> {
    List<Vlucht> findVluchtById(String id);
    List<Vlucht> findVluchtByNaam(String naam);
}
