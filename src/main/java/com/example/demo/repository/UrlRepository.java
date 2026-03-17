package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Url;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByLongUrl(String LongUrl);
}