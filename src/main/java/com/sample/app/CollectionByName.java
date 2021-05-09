package com.sample.app;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.CollectionStatus;
import com.arangodb.entity.CollectionType;
import com.arangodb.mapping.ArangoJack;

public class CollectionByName {
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

		String collectionName = "users";

		arangoDatabase.createCollection(collectionName);

		ArangoCollection arangoCollection = arangoDatabase.collection(collectionName);
		CollectionEntity collectionEntity = arangoCollection.getInfo();

		String collectionId = collectionEntity.getId();
		Boolean isSystemCollection = collectionEntity.getIsSystem();
		collectionName = collectionEntity.getName();
		CollectionStatus collectionStatus = collectionEntity.getStatus();
		CollectionType collectionType = collectionEntity.getType();

		System.out.println("Collection details ");
		System.out.println("collectionId : " + collectionId);
		System.out.println("isSystemCollection : " + isSystemCollection);
		System.out.println("collectionName : " + collectionName);
		System.out.println("collectionStatus : " + collectionStatus);
		System.out.println("collectionType : " + collectionType);

		// Dropping the collection and database
		arangoCollection.drop();
		arangoDatabase.drop();
	}
}