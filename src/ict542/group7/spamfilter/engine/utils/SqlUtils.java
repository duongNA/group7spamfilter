package ict542.group7.spamfilter.engine.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlUtils {
	public static Connection getConnection() {
		try {
			String dbUrl = "jdbc:derby:AppDerbyDB;create=false";
			
			Connection connection = DriverManager.getConnection(dbUrl);
			return connection;
		} catch (SQLException e) {
			// it means database doesn't exist
			// create database
			return createDB(); 
		}
	}
	
	private static Connection createDB() {
		try {
			String dbUrl = "jdbc:derby:TestDerbyDB;create=true";
			Connection c = DriverManager.getConnection(dbUrl);
			createFeatureTable(c);
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void createFeatureTable(Connection c) throws SQLException {
		System.out.println("Creating table Feature...");
		Statement statement = c.createStatement();
		
		String createUrl = "CREATE TABLE Feature(" +
							"tokenString varchar(500) PRIMARY KEY, " +
							"occurInSpam INT NOT NULL," +
							"occurInHam INT NOT NULL," +
							"probability DOUBLE NOT NULL" +
							")";
		statement.execute(createUrl);
		System.out.println("Complete creating table Feature!!!");
		statement.close();
	}
	
	public static boolean insertFeature(String tokenString, int occurInSpam, int occurInHam, double probability) {
		Connection c = getConnection();
		PreparedStatement ps = null;

		String sql = "INSERT INTO Feature VALUES (?, ? , ?, ?)";
		try {
			ps = c.prepareStatement(sql);
			ps.setString(1, tokenString);
			ps.setInt(2, occurInSpam);
			ps.setInt(3, occurInHam);
			ps.setDouble(4, probability);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void listFeatures() {
		Connection c = getConnection();
		Statement statement = null;
		String sql = "SELECT * FROM Feature";
		
		try {
			statement = c.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String tokenString = resultSet.getString("tokenString");
				int occurInSpam = resultSet.getInt("occurInSpam");
				int occurInHam = resultSet.getInt("occurInHam");
				double probability = resultSet.getDouble("probability");
				System.out.println("tokenString: " + tokenString + ", occurInSpam: " + occurInSpam + ", occurInHam: " + occurInHam + ", probability: " + probability);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean shutdownDerby() {
		try {
			String shutdownUrl = "jdbc:derby:;shutdown=true";
			DriverManager.getConnection(shutdownUrl);
		} catch (SQLException e) {
			if (e.getMessage().equals("Derby system shutdown.")) {
				return true;
			}
		}
		return false;
	}
}
