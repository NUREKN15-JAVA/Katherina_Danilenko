package kn156danilenko;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	
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

	public void testGetAge(){
		user.setDateOfBirthd(dateOfBirthd);
		assertEquals(2017 - 1998, user.getAge());
	}
	
}
