package com.symbizsolutions.demo.repository;

import com.symbizsolutions.demo.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
