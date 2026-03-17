package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Url;

public interface CustomerRepository extends CrudRepository<Url,Integer> {
	
}
