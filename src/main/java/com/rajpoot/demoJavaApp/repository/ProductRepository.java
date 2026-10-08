package com.rajpoot.demoJavaApp.repository;

import java.util.List;
import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.Product;

public interface ProductRepository {
	Product save(Product product);

	List<Product> findAll();

	Optional<Product> findById(String id);

	void deleteById(String id);
}
