package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class Url implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String longUrl;
    private static final long serialVersionUID = 1L;
    private String shortUrl;
    
    public Url() {}
    
    public Url(String longUrl) {
        this.longUrl = longUrl;
    }
    public void setId(int id) { this.id = id; }
    
    public int getId() { 
        return id; 
        
    }
    public String getLongUrl() { 
        return longUrl;
        
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    
    public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
}
    
}