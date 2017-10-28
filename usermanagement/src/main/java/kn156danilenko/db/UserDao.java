package kn156danilenko.db;

import java.util.Collection;

import kn156danilenko.User;

public interface UserDao {
	User create(User user) throws DatabaseExeption;
	
	void update(User user) throws DatabaseExeption;
	
	void delete(User user) throws DatabaseExeption;
	
	User find(Long id) throws DatabaseExeption;
	
	Collection<User> findAll() throws DatabaseExeption;
	
	void setConnectionFactory(ConnectionFactory connectionFactory);
}
