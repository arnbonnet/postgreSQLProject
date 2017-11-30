package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Book;
import entities.Client;
import entities.Gender;
import utils.DatabaseUtils;

public class Main {

	private static String URL = "jdbc:postgresql://localhost:5432/library";

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");

			
			
			/*
			 * DATABASE TABLES CREATION
			 */
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS buy");
			stmt.executeUpdate("DROP TABLE IF EXISTS client");
			stmt.executeUpdate("DROP TABLE IF EXISTS book");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS book(" + "id BIGSERIAL PRIMARY KEY,"
					+ " title varchar(255) UNIQUE NOT NULL," + " author varchar(255)" + ")");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS client(" + " id BIGSERIAL PRIMARY KEY,"
					+ " firstName varchar(255) NOT NULL," + " lastName varchar(255) NOT NULL," + " gender varchar(10),"
					+ " id_favorite_book INTEGER CONSTRAINT fk_id_favorite_book REFERENCES book(id)" + ")");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS buy("
					+ " id_client BIGINT CONSTRAINT fk_id_client REFERENCES client(id),"
					+ " id_book BIGINT CONSTRAINT fk_id_book REFERENCES book(id)" + ")");

			

			System.out.println("IT\'S WORKING !");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		
		/*
		 * DATABASE FILLING
		 */
		List<Book> books = new ArrayList<>();
		books.add(new Book("Ready Player One", "Ernest Cline"));
		books.add(new Book("L'Assassin Royal Tome 1 - L'Apprenti assassin", "Robin Hodd"));
		books.add(new Book("L'Assassin Royal Tome 2 - L'Assassin du roi", "Robin Hodd"));
		books.add(new Book("L'Assassin Royal Tome 3 - La Nef du crépuscule", "Robin Hodd"));

		for (Book book : books) {
			DatabaseUtils.addBookInDatabase(book);
		}
		
		List<Client> clients = new ArrayList<>();
		clients.add(new Client("John", "Smith", Gender.M, books.get(0).getId()));
		clients.add(new Client("Sarah", "Smith", Gender.F, books.get(1).getId()));
		clients.add(new Client("Jane", "Tayler", Gender.M, books.get(3).getId()));
		
		for(Client client : clients) {
			DatabaseUtils.addClientInDatabase(client);
		}
		
		DatabaseUtils.clientBuysAbook(clients.get(1), books.get(0));
		
		List<Book> clientBooks = DatabaseUtils.getClientBooks(clients.get(1));
		for(Book book : clientBooks) {
			System.out.println(book);
		}

		List<Client> bookClients = DatabaseUtils.getBookClients(books.get(1));
		for(Client client : bookClients) {
			System.out.println(client);
			
		}
	}

}
