package com.sample.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.mapping.ArangoJack;

public class APLQueryDemo {
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

		collection.insertDocuments(Arrays.asList(empDocument1, empDocument2));

		String query = "FOR c IN " + collectionName + " FILTER c.firstName == @fName RETURN c";
		Map<String, Object> bindVars = Collections.singletonMap("fName", "Krishna");

		ArangoCursor<BaseDocument> cusor = arangoDatabase.query(query, bindVars, null, BaseDocument.class);

		System.out.println("All the documents with firstName 'Krishna'\n");
		while (cusor.hasNext()) {
			BaseDocument baseDoc = cusor.next();
			System.out.println(baseDoc);
		}

		// Dropping the collection and database
		collection.drop();
		arangoDatabase.drop();
		System.exit(0);
	}
}