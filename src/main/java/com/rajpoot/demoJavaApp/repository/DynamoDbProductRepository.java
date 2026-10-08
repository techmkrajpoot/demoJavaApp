package com.rajpoot.demoJavaApp.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class DynamoDbProductRepository implements ProductRepository {
	private final DynamoDbTable<Product> productTable;

	public DynamoDbProductRepository(
			DynamoDbEnhancedClient enhancedClient,
			@Value("${dynamodb.productTableName}") String tableName) {
		this.productTable = enhancedClient.table(tableName, TableSchema.fromBean(Product.class));
	}

	@Override
	public Product save(Product product) {
		productTable.putItem(product);
		return product;
	}

	@Override
	public List<Product> findAll() {
		return productTable.scan().items().stream()
				.sorted(Comparator.comparing(Product::getId))
				.toList();
	}

	@Override
	public Optional<Product> findById(String id) {
		Product product = productTable.getItem(Key.builder().partitionValue(id).build());
		return Optional.ofNullable(product);
	}

	@Override
	public void deleteById(String id) {
		productTable.deleteItem(Key.builder().partitionValue(id).build());
	}
}
