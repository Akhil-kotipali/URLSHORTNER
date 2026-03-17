package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Url;
import com.example.demo.redis.RedisRateLimiter;
import com.example.demo.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/getShort/{id}")
    public Optional<Url> getUrlById(@PathVariable int id) {
        return urlService.findUrlById(id);
    }

    
    @PostMapping("/postLong")
    public Url postMethodName( 
    @RequestBody Url Url) {
        return urlService.addUrl(Url);
    }

     @Autowired
    RedisRateLimiter rateLimiter;

    @GetMapping("/r/{code}")
public ResponseEntity<Void> reDirect(@PathVariable String code, HttpServletRequest request) {
    
    String clientId = request.getRemoteAddr();

   
    
    if (!rateLimiter.isAllowed(clientId)) {
        return ResponseEntity.status(429).build(); 
    }

    
    Url url = urlService.findShortUrl(code);

    if (url == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI.create(url.getLongUrl()))
            .build();
}


}