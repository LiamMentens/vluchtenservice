package com.example.vluchten.vluchtenservice.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
@Document(collection = "vluchten")
public class Vlucht {
    @Id
    private String id;
    private String maatschappijnaam;
    private String naam;

    public Vlucht() {
    }

    public Vlucht(String maatschappijnaam, String naam) {
        this.maatschappijnaam = maatschappijnaam;
        this.naam = naam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getMaatschappijnaam() {
        return maatschappijnaam;
    }

    public void setMaatschappijnaam(String maatschappijnaam) {
        this.maatschappijnaam = maatschappijnaam;
    }
}
