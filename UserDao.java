
package com.usermanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class UserDao {
	
	private String jdbcUrl = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Rizul@123";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	
	private static final String INSERT_USERS_SQL = " INSERT INTO users " + "(id,name, email, phone) VALUES " + "(?,?,?,?);";
	private static final String SELECT_USER_BY_ID = "select id, name, email, phone from users where id = ?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ? , email=? , phone = ? where id = ?;";
	
	public UserDao() {
		
	}
	
	protected Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection; 
		
	}
	
	// insert user 
	
	public void insertUser(User user) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_USERS_SQL);
		try (
				Connection connection = getConnection();
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	
	
	// select user by id 
	public User selectUser(int id) throws SQLException, ClassNotFoundException
	{
		User user = null;
		//Step1. Establishing a connection 
		try (Connection connection = getConnection();
				// Step2 . Create a statement using connection object. 
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step3. Execute the query or update query 
			ResultSet rs = preparedStatement.executeQuery();
			// Step4. Process the result set object..
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				user = new User(id, name, email, phone);
			}
		}catch (SQLException e)
		{
			printSQLException(e);
		}
		return user;
	}
	
	
	
	
	
	// select all users 
	
	public List<User> selectAllUsers() throws ClassNotFoundException {
		List<User> users = new ArrayList<> ();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				users.add(new User(id, name, email, phone));
			}
		}catch (SQLException e) {
			printSQLException(e);
		}
		
		return users;
		
	}
	
	
	// update user 
	
	public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
		
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(UPDATE_USERS_SQL);) {
			
			System.out.println("Updated user: " + statement);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPhone());
			statement.setInt(4, user.getId());
			
			rowUpdated = statement.executeUpdate() > 0;
			
		}
		return rowUpdated;
		
	}
	
	
	
	
	// delete user 
	
	public boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
		
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = (PreparedStatement) connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
		
		
	}


		
	

	private void printSQLException(SQLException ex) {
		for (Throwable e1 : ex) {
			if (e1 instanceof SQLException) {
				e1.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException ) e1).getSQLState());
				System.err.println("Error Code: " + ((SQLException ) e1).getErrorCode());
				System.err.println("Message: " + e1.getMessage());
				Throwable t  = ex.getCause();
				while(t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
					
				}
			}
		}
		
	}
	
	
	
	

}
