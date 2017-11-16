package kn156danilenko.db;

import java.util.Collection;

import kn156danilenko.User;

public interface UserDao {
	
	/**
	 * Add user into table and get new user's id from db
	 * @param user all field of user must be not-null except of id field
	 * @return copy of user from db with id auto-created
	 * @throws DatabaseException in case of any error with db
	 */
	User create(User user) throws DatabaseExeption;
	
	/**
	 * Update user data by user's id from db
	 * @param user all field of user must be not-null except of id field
	 * @throws DatabaseException in case of any error with db
	 */
	void update(User user) throws DatabaseExeption;
	
	/**
	 * Delete user data by user's id from db
	 * @param user all field of user must be not-null except of id field
	 * @throws DatabaseException in case of any error with db
	 */
	void delete(User user) throws DatabaseExeption;
	
	/**
	 * Find user in table by user's id from db
	 * @param id is used to find users from db and must be not-null
	 * @return copy of user from db 
	 * @throws DatabaseException in case of any error with db
	 */
	User find(Long id) throws DatabaseExeption;
	
	/**
	 * Find all users from db
	 * @return list of all users from db
	 * @throws DatabaseException in case of any error with db
	 */
	Collection<User> findAll() throws DatabaseExeption;
	
	/**
	 * Sets the connection factory
	 * @param connectionFactory the connection factory to use
	 */
	void setConnectionFactory(ConnectionFactory connectionFactory);
	
}
