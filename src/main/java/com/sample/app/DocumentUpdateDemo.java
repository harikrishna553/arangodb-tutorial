package com.sample.app;

import java.util.Arrays;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.DocumentUpdateEntity;
import com.arangodb.mapping.ArangoJack;

public class DocumentUpdateDemo {
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

		String collectionName = "test1";

		arangoDatabase.createCollection(collectionName);

		ArangoCollection collection = arangoDatabase.collection(collectionName);

		BaseDocument empDocument = new BaseDocument();
		// myObject.setKey("myKey");
		empDocument.addAttribute("firstName", "Krishna");
		empDocument.addAttribute("lastName", "Gurram");
		empDocument.addAttribute("hobbies", Arrays.asList("trekking", "playing cricket"));

		DocumentCreateEntity<BaseDocument> docEntity = collection.insertDocument(empDocument);
		String key = docEntity.getKey();
		BaseDocument persistedDoc = collection.getDocument(key, BaseDocument.class);
		System.out.println("persisted document : " + persistedDoc);

		// This doc updates firstName and add new attribute age.
		BaseDocument updatedDocument = new BaseDocument();
		updatedDocument.addAttribute("firstName", "Ram");
		updatedDocument.addAttribute("age", 33);

		DocumentUpdateEntity<BaseDocument> updatedEntity = collection.updateDocument(key, updatedDocument);
		key = updatedEntity.getKey();
		persistedDoc = collection.getDocument(key, BaseDocument.class);
		System.out.println("\nupdated entity : " + persistedDoc);

		// Dropping the collection and database
		collection.drop();
		arangoDatabase.drop();
		System.exit(0);
	}
}