package com.rajpoot.demoJavaApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.Product;
import com.rajpoot.demoJavaApp.repository.ProductRepository;
import com.rajpoot.demoJavaApp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class ProductServiceTests {
	private ProductRepository productRepository;
	private ProductService productService;

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductService(productRepository);
	}

	@Test
	void createAssignsIdAndSavesProduct() {
		Product input = new Product(null, "Keyboard", "Mechanical keyboard");
		when(productRepository.save(input)).thenAnswer(invocation -> invocation.getArgument(0));

		Product created = productService.create(input);

		assertNotNull(created.getId());
		assertEquals("Keyboard", created.getName());
		assertEquals("Mechanical keyboard", created.getDescription());
		verify(productRepository).save(input);
	}

	@Test
	void updateKeepsIdAndUpdatesFields() {
		Product existing = new Product("product-1", "Keyboard", "Old description");
		Product changes = new Product(null, "Mouse", "Wireless mouse");
		when(productRepository.findById("product-1")).thenReturn(Optional.of(existing));
		when(productRepository.save(changes)).thenAnswer(invocation -> invocation.getArgument(0));

		Product updated = productService.update("product-1", changes);

		assertEquals("product-1", updated.getId());
		assertEquals("Mouse", updated.getName());
		assertEquals("Wireless mouse", updated.getDescription());
		verify(productRepository).save(changes);
	}

	@Test
	void updateRejectsUnknownProduct() {
		when(productRepository.findById("missing")).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class,
				() -> productService.update("missing", new Product(null, "Mouse", "Wireless mouse")));
	}
}
