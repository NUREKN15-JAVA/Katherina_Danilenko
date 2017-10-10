package kn156danilenko;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if(firstName == null) {
			throw new NullPointerException("FirstName can't be null");
		}
		else {
			this.firstName = firstName;
		}
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if(lastName == null) {
			throw new NullPointerException("LastName can't be null");
		}
		else {
			this.lastName = lastName;
		}	
	}
	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}
	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}
	public Object getFullName() {
		
		return getLastName() + ", " + getFirstName();
	}
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		calendar.setTime(getDateOfBirthd());
		int year = calendar.get(Calendar.YEAR);
		return currentYear - year;
	}
	
	

}
