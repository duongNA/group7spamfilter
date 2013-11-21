package ict542.group7.spamfilter.engine.utils;
import ict542.group7.spamfilter.engine.common.Feature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;


public class SqlUtils {
	private static final Logger logger = Logger.getLogger(SqlUtils.class);
	
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
			String dbUrl = "jdbc:derby:AppDerbyDB;create=true";
			Connection c = DriverManager.getConnection(dbUrl);
			createFeatureTable(c);
			return c;
		} catch (SQLException e) {
			logger.error("Error when creating database", e);
			return null;
		}
	}
	
	private static void createFeatureTable(Connection c) throws SQLException {
		System.out.println("Creating table Feature...");
		Statement statement = c.createStatement();
		
		String createUrl = "CREATE TABLE Feature(" +
							"token_string VARCHAR(300) PRIMARY KEY, " +
							"occur_in_spam INT NOT NULL," +
							"occur_in_ham INT NOT NULL," +
							"probability DOUBLE NOT NULL" +
							")";
		statement.execute(createUrl);
		System.out.println("Complete creating table Feature!!!");
		statement.close();
	}
	
	public static boolean insertFeature(Feature feature) {
		Connection c = getConnection();
		PreparedStatement ps = null;

		String sql = "INSERT INTO Feature VALUES (?, ? , ?, ?)";
		try {
			ps = c.prepareStatement(sql);
			ps.setString(1, feature.getTokenString());
			ps.setInt(2, feature.getOccurInSpam());
			ps.setInt(3, feature.getOccurInHam());
			ps.setDouble(4, feature.getPropability());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("error when inserting feature", e);
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
	
	public static boolean updateFeature(Feature feature) {
		Connection c = getConnection();
		PreparedStatement ps = null;
		String sql = 	"UPDATE Feature SET " +
						"occur_in_spam = ?, " +
						"occur_in_ham = ?, " +
						"probability = ? " +
						"WHERE token_string = ?";
					
		try {
			ps = c.prepareStatement(sql);
			ps.setInt(1, feature.getOccurInSpam());
			ps.setInt(2, feature.getOccurInHam());
			ps.setDouble(3, feature.getPropability());
			ps.setString(4, feature.getTokenString());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Error when updating feature", e);
			return false;
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {};
			}
			
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {};
			}
		}
	}
	
	public static boolean clearTableFeature() {
		Connection c = getConnection();
		Statement statement = null;
		 try {
			 String sql = "DELETE FROM Feature";
			 statement = c.createStatement();
			 statement.executeUpdate(sql);
			 return true;
		 } catch (SQLException e) {
			 logger.error(null, e);
			 return false;
		 }
	}

	public static Feature searchFeatureByTokenString(String tokenString) {
		Connection c = getConnection();
		String sql = "SELECT * FROM Feature WHERE token_string = ?";
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement(sql);
			ps.setString(1, tokenString);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Feature feature = new Feature(tokenString);
				int occurInSpam = rs.getInt("occur_in_spam");
				int occurInHam = rs.getInt("occur_in_ham");
				double probability = rs.getDouble("probability");
				
				feature.setOccurInSpam(occurInSpam);
				feature.setOccurInHam(occurInHam);
				feature.setProbability(probability);
				return feature;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.error("error when searching feature by token string", e);
			return null;
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
				String tokenString = resultSet.getString("token_string");
				int occurInSpam = resultSet.getInt("occur_in_spam");
				int occurInHam = resultSet.getInt("occur_in_ham");
				double probability = resultSet.getDouble("probability");
				System.out.println("tokenString: " + tokenString + ", occurInSpam: " + occurInSpam + ", occurInHam: " + occurInHam + ", probability: " + probability);
			}
		} catch (SQLException e) {
			logger.error(null, e);
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
