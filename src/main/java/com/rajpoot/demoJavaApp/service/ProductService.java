package com.rajpoot.demoJavaApp.service;

import java.util.List;
import java.util.UUID;

import com.rajpoot.demoJavaApp.entity.Product;
import com.rajpoot.demoJavaApp.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product create(Product product) {
		product.setId(UUID.randomUUID().toString());
		return productRepository.save(product);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(String id) {
		return productRepository.findById(id).orElseThrow(() -> notFound(id));
	}

	public Product update(String id, Product product) {
		if (productRepository.findById(id).isEmpty()) {
			throw notFound(id);
		}
		product.setId(id);
		return productRepository.save(product);
	}

	public void delete(String id) {
		if (productRepository.findById(id).isEmpty()) {
			throw notFound(id);
		}
		productRepository.deleteById(id);
	}

	private ResponseStatusException notFound(String id) {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + id + " was not found");
	}
}
