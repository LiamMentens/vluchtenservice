package com.example.vluchten.vluchtenservice.model;

import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

public class Vlucht {
    @Id
    private String id;
    private Integer luchthavenId;
    private String naam;

    public Vlucht(Integer luchthavenId, String naam) {
        this.luchthavenId = luchthavenId;
        this.naam = naam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLuchthavenId() {
        return luchthavenId;
    }

    public void setLuchthavenId(Integer luchthavenId) {
        this.luchthavenId = luchthavenId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Vlucht() {
    }
}
