package com.sample.app;

import java.util.Arrays;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.mapping.ArangoJack;

public class DocumentDelete {
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

		BaseDocument empAddress = new BaseDocument();
		empAddress.addAttribute("city", "Bangalore");
		empAddress.addAttribute("country", "India");

		BaseDocument empDocument = new BaseDocument();
		// myObject.setKey("myKey");
		empDocument.addAttribute("firstName", "Krishna");
		empDocument.addAttribute("lastName", "Gurram");
		empDocument.addAttribute("hobbies", Arrays.asList("trekking", "playing cricket"));
		empDocument.addAttribute("address", empAddress);

		DocumentCreateEntity<BaseDocument> docEntity = collection.insertDocument(empDocument);

		String key = docEntity.getKey();
		String id = docEntity.getId();

		System.out.println("key : " + key);
		System.out.println("id : " + id);

		BaseDocument persistedDoc = collection.getDocument(key, BaseDocument.class);
		System.out.println(persistedDoc);

		System.out.println("\nDeleting document");
		collection.deleteDocument(persistedDoc.getKey());

		Boolean isDocExists = collection.documentExists(persistedDoc.getKey());
		if (isDocExists) {
			System.out.println("Document is not deleted");
		} else {
			System.out.println("Document is deleted");
		}

		// Dropping the collection and database
		collection.drop();
		arangoDatabase.drop();
		System.exit(0);
	}
}