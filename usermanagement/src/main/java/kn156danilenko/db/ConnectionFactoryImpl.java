package kn156danilenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

	private String driver;
	private String url;
	private String user;
	private String password;

	public ConnectionFactoryImpl(String driver, String url, String user, String password) {
		
		this.driver=driver;
		this.url=url;
		this.user=user;
		this.password=password;
		
	}

	public ConnectionFactoryImpl(Properties properties) {
		this.driver=properties.getProperty("connection.driver");
		this.url=properties.getProperty("connection.url");
		this.user=properties.getProperty("connection.user");
		this.password=properties.getProperty("connection.password");
	}

	@Override
	public Connection createConnection() throws DatabaseExeption {
		try{
			Class.forName(driver);
			
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
		try {
			return DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
		
			throw new DatabaseExeption(e);
		}
	}
	
}
