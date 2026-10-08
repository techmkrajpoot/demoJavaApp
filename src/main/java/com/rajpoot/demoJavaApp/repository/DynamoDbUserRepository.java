package com.rajpoot.demoJavaApp.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class DynamoDbUserRepository implements UserRepository {
	private final DynamoDbTable<User> userTable;

	public DynamoDbUserRepository(
			DynamoDbEnhancedClient enhancedClient,
			@Value("${dynamodb.tableName}") String tableName) {
		this.userTable = enhancedClient.table(tableName, TableSchema.fromBean(User.class));
	}

	@Override
	public User save(User user) {
		userTable.putItem(user);
		return user;
	}

	@Override
	public List<User> findAll() {
		return userTable.scan().items().stream()
				.sorted(Comparator.comparing(User::getId))
				.toList();
	}

	@Override
	public Optional<User> findById(String id) {
		User user = userTable.getItem(Key.builder().partitionValue(id).build());
		return Optional.ofNullable(user);
	}

	@Override
	public void deleteById(String id) {
		userTable.deleteItem(Key.builder().partitionValue(id).build());
	}
}
