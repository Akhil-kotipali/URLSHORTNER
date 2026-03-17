package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Url;
import com.example.demo.repository.UrlRepository;

import java.util.Optional;

@Service
public class UrlService 
{
    @Autowired
    private UrlRepository urlRepository;

    public Optional<Url> findUrlById(int id) 
    {
        return urlRepository.findById(id);
    }

   
    @CachePut(value = "urlShort", key = "#result.shortUrl")
    public Url addUrl(Url url2) 
    {
        Url savedUrl = urlRepository.save(url2);
        String shortCode = Base62.encode(savedUrl.getId());
        savedUrl.setShortUrl(shortCode);
        return urlRepository.save(savedUrl); 
    }


    @Cacheable(value = "urlShort", key = "#shortUrl")
    public Url findShortUrl(String shortUrl)
    {
        return urlRepository.findByShortUrl(shortUrl).orElse(null);
    }

    @Cacheable(value = "urlLong", key = "#longUrl")
    public Url findLongUrl(String longUrl)
    {
        return urlRepository.findByLongUrl(longUrl).orElse(null);
    }

}