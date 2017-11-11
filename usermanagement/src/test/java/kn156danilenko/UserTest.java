package kn156danilenko;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	private static final int AGE = 19;
	
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.JANUARY, 30);
		dateOfBirthd = calendar.getTime();
	}
	
	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Doe");
		assertEquals("Doe, John", user.getFullName());
	}
	public void testLastNameIsNull() {
		user.setLastName("Hondak");
		try {
			user.getFullName();
			fail("Should have thrown an exception");

		} catch (IllegalArgumentException e) {
			System.out.println("lastName is NULL");
		}
	}

	public void testFirstNameIsNull() {
		user.setFirstName("Kseniia");
		try {
			user.getFullName();
			fail("Should have thrown an exception");

		} catch (IllegalArgumentException e) {
			System.out.println("firstName is NULL");
		}

	}

	public void testGetAge(){
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(AGE, user.getAge());
	}
	
}
