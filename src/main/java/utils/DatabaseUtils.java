package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Book;
import entities.Client;

public class DatabaseUtils {
	
	private static String URL = "jdbc:postgresql://localhost:5432/library";
	
	public static void addBookInDatabase(Book book) throws SQLException {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		Long id = null;
		
		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");
			prepStmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
			prepStmt.setString(1, book.getTitle());
			prepStmt.setString(2, book.getAuthor());
			
			prepStmt.executeUpdate();
			ResultSet resultSet = prepStmt.getGeneratedKeys();
			while(resultSet.next()) {
				id = resultSet.getLong("id");
			}
			book.setId(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				prepStmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}

	public static void addClientInDatabase(Client client) throws SQLException {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		Long id = null;
		
		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");
			prepStmt = conn.prepareStatement("INSERT INTO client(firstName, lastName, gender, id_favorite_book) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			prepStmt.setString(1, client.getFirstName());
			prepStmt.setString(2, client.getLastName());
			prepStmt.setString(3, client.getGender().name());
			prepStmt.setLong(4, client.getFavoriteBook());
			prepStmt.executeUpdate();
			ResultSet resultSet = prepStmt.getGeneratedKeys();
			while(resultSet.next()) {
				id = resultSet.getLong("id");
			}
			client.setId(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				prepStmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}
	
	public static void clientBuysAbook(Client client, Book book) throws SQLException {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");
			prepStmt = conn.prepareStatement("INSERT INTO buy(id_client, id_book) VALUES(?,?)");
			prepStmt.setLong(1, client.getId());
			prepStmt.setLong(2, book.getId());
			prepStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				prepStmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}
	
	public static List<Book> getClientBooks(Client client) throws SQLException {
		List<Book> books = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");
			prepStmt = conn.prepareStatement("SELECT book.title, book.author " + 
					"FROM book " + 
					"JOIN buy ON book.id = buy.id_book " +
					"WHERE buy.id_client = ?");
			prepStmt.setLong(1, client.getId());
			
			ResultSet resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				books.add(new Book(resultSet.getString(1), resultSet.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				prepStmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
		return books;
	}
	
	public static List<Client> getBookClients(Book book) throws SQLException {
		List<Client> clients = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		try {
			conn = DriverManager.getConnection(URL, "arnaud", "postegres");
			prepStmt = conn.prepareStatement("SELECT client.firstName, client.lastName " + 
					"FROM client " + 
					"JOIN buy ON client.id = buy.id_client " +  
					"WHERE buy.id_book = ?");
			prepStmt.setLong(1, book.getId());
			
			ResultSet resultSet = prepStmt.executeQuery();
			
			while(resultSet.next()) {
				clients.add(new Client(resultSet.getString(1), resultSet.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				prepStmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
		return clients;
	}
	
}
