package com.sample.app;

import java.util.Collection;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.mapping.ArangoJack;

public class PrintCollecitonsDemo {
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "tiger";
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8529;

	public static void main(String args[]) {
		// Get an ArangoDB instance
		ArangoDB arangoDB = new ArangoDB.Builder().user(USER_NAME).password(PASSWORD).host(HOST, PORT)
				.serializer(new ArangoJack()).build();

		String databaseName = "testdb";
		arangoDB.createDatabase(databaseName);

		ArangoDatabase arangoDatabase = arangoDB.db(databaseName);

		String collectionName1 = "test1";
		String collectionName2 = "test2";

		arangoDatabase.createCollection(collectionName1);
		arangoDatabase.createCollection(collectionName2);

		Collection<CollectionEntity> collectionEntities = arangoDatabase.getCollections();

		for (CollectionEntity collectionEntity : collectionEntities) {
			System.out.println(collectionEntity.getName());
		}

		// Dropping the collection and database
		arangoDatabase.collection(collectionName1).drop();
		arangoDatabase.collection(collectionName2).drop();
		arangoDatabase.drop();
	}
}