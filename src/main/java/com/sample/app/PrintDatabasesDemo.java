package com.sample.app;

import java.util.Collection;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.mapping.ArangoJack;

public class PrintDatabasesDemo {
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "tiger";
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8529;

	private static void printDatabases(ArangoDB arangoDB) {
		Collection<String> databases = arangoDB.getDatabases();

		System.out.println("\nAll the databases");
		for (String database : databases) {
			System.out.println(database);
		}
	}

	public static void main(String args[]) {
		// Get an ArangoDB instance
		ArangoDB arangoDB = new ArangoDB.Builder().user(USER_NAME).password(PASSWORD).host(HOST, PORT)
				.serializer(new ArangoJack()).build();

		String databaseName = "testdb";

		boolean isDbCreated = arangoDB.createDatabase(databaseName);

		if (isDbCreated) {
			System.out.println("Database " + databaseName + " created successfully");
		} else {
			System.out.println("Unable to create a database : " + databaseName);
			System.exit(1);
		}

		printDatabases(arangoDB);

		// Drop the databases
		ArangoDatabase arangoDatabase = arangoDB.db(databaseName);
		Boolean isDropped = arangoDatabase.drop();

		System.out.println("\nIs the database " + databaseName + " dropped : " + isDropped);
		printDatabases(arangoDB);

		System.exit(0);

	}
}
