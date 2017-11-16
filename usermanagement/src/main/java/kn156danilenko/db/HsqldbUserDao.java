package kn156danilenko.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import kn156danilenko.User;

 class HsqldbUserDao implements UserDao {

	 	/**
		 * DELETE_QUERY, UPDATE_QUERY, SELECT_USER_BY_ID, SELECT_ALL_QUERY, INSERT_QUERY String constants which contain sql request for updating, inserting,
		 * deleting and selecting data from db table
		 */
	 
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
	private static final String SELECT_USER_BY_ID = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth) VALUE(?, ?, ?)";
	private ConnectionFactory connectionFactory;

	public HsqldbUserDao() {
	}

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public User create(User user) throws DatabaseExeption {
		try{
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if(n!=1){
				throw new DatabaseExeption("Number of inserted rows" + n);
			}
			CallableStatement callableStatement=connection
					.prepareCall("call IDENTITY()");
			ResultSet keys=callableStatement.executeQuery();
			User insertedUser=new User(user);
			if(keys.next()){
				insertedUser.setId(keys.getLong(1));	
			}
			keys.close();
			callableStatement.close();
			connection.close();
			return insertedUser;
		}catch(DatabaseExeption e){
			throw e;
			
		}catch(SQLException e){
			throw new DatabaseExeption(e);
		}
	}

	@Override
	public User find(Long id) throws DatabaseExeption {		
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			User user = new User();
			while (result.next()) {				
				user.setId(result.getLong(1));
				user.setFirstName(result.getString(2));
				user.setLastName(result.getString(3));
				user.setDateOfBirthd(result.getDate(4));
			}
			result.close();
			statement.close();
			connection.close();
			return user;
		} catch(DatabaseExeption e) {
			throw e;
		} catch(SQLException e) {
			throw new DatabaseExeption(e);
		} 
	}

	@Override
	public void update(User user) throws DatabaseExeption {		
		try {
			Connection connection=connectionFactory.createConnection();
			PreparedStatement statement=connection
					.prepareStatement(UPDATE_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			statement.setLong(4, user.getId());
			int n=statement.executeUpdate();
			if(n!=1){
				throw new DatabaseExeption("Number of inserted rows"+n);
			}
			connection.close();
			statement.close();
		} catch(DatabaseExeption e) {
			throw e;
		} catch(SQLException e) {
			throw new DatabaseExeption(e);
		} 
	}

	@Override
	public void delete(User user) throws DatabaseExeption {
		try {
			Connection connection=connectionFactory.createConnection();
			PreparedStatement statement=connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, user.getId());
			int n=statement.executeUpdate();
			if(n!=1){
				throw new DatabaseExeption("Number of inserted rows"+n);
			}
			connection.close();
			statement.close();
		} catch(DatabaseExeption e) {
			throw e;
		} catch(SQLException e) {
			throw new DatabaseExeption(e);
		} 
	}
	
	@Override
	public Collection<User> findAll() throws DatabaseExeption {
		Collection<User> result = new LinkedList<User>();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			User user;
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
				result.add(user);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch(DatabaseExeption e) {
			throw e;
		} catch(SQLException e) {
			throw new DatabaseExeption(e);
		}
		return result;
	}
}
