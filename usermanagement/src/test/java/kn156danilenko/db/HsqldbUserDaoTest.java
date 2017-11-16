package kn156danilenko.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import kn156danilenko.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	/**
	 *  UPDATE_USER, DELETE_USER, FIND_USER Long constants which contain user's id 
	 *  for testing methods from the class "HsqldbUserDao
	 */ 
	
	private static final Long UPDATE_USER = 1000L;
	private static final Long DELETE_USER = 1001L;
	private static final Long FIND_USER = 1000L;
	
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setDateOfBirthd(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
		}
	}

	public void testUpdate() {
		try {
			User user = dao.find(UPDATE_USER);
			user.setFirstName("John");
			dao.update(user);
			User updatedUser = dao.find(UPDATE_USER);
			assertNotNull("User was not updated", updatedUser);
			assertEquals("Difference between old and updated data of user", user.getFirstName(), updatedUser.getFirstName());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	public void testFindById() {
		try {
			User findedUser = dao.find(FIND_USER);
			assertNotNull("There is no such user", findedUser);
			assertEquals("Difference between user's id", FIND_USER, findedUser.getId());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	public void testDeleteUser() {
		User user = null;
		try {
			user = dao.find(DELETE_USER);
			dao.delete(user);
			User deletedUser = dao.find(DELETE_USER);
			assertNotNull("User was not deleted", deletedUser);
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	public void testFindAll()
	{
		try {
			Collection<User> collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size", 2, collection.size());
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", 
				"jdbc:hsqldb:file:db/usermanagement", "sa","");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}
}
