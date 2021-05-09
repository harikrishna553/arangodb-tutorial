package com.sample.app;

import java.util.Arrays;
import java.util.Collection;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.model.DocumentCreateOptions;

public class DocumentBulkCreateDemo {
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

		BaseDocument empDocument1 = new BaseDocument();
		empDocument1.addAttribute("firstName", "Krishna");
		empDocument1.addAttribute("lastName", "Gurram");
		empDocument1.addAttribute("hobbies", Arrays.asList("trekking", "playing cricket"));

		BaseDocument empDocument2 = new BaseDocument();
		empDocument2.addAttribute("firstName", "Joel");
		empDocument2.addAttribute("lastName", "Chelli");
		empDocument2.addAttribute("hobbies", Arrays.asList("designing games"));

		DocumentCreateOptions docOptions = new DocumentCreateOptions();
		docOptions.returnNew(true);
		
		MultiDocumentEntity<DocumentCreateEntity<BaseDocument>> persistedDocs = collection
				.insertDocuments(Arrays.asList(empDocument1, empDocument2), docOptions);

		Collection<DocumentCreateEntity<BaseDocument>> docs = persistedDocs.getDocuments();

		for (DocumentCreateEntity<BaseDocument> doc : docs) {
			System.out.println("\n");
			System.out.println("id : " + doc.getId());
			System.out.println("key : " + doc.getKey());
			System.out.println("rev : " + doc.getRev());
			System.out.println("doc : " + doc.getNew());
		}

		// Dropping the collection and database
		collection.drop();
		arangoDatabase.drop();
		System.exit(0);
	}
}