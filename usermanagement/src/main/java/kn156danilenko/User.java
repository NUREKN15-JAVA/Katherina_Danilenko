package kn156danilenko;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	
	public User ()
	{
		super();
	}
	
	public User(User user) {
		id = user.getId();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		dateOfBirth = user.getDateOfBirthd();
	}
	public User(Long id, String firstName, String lastName, Date dateOfBirth)
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}
	public User(String firstName, String lastName, Date dateOfBirth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}
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
		return dateOfBirth;
	}
	public void setDateOfBirthd(Date dateOfBirth) {
		this.dateOfBirth= dateOfBirth;
	}
	public String getFullName() {
		
		if (getLastName() == null || getFirstName() == null){
			throw new IllegalArgumentException("firstName or lastName is NULL");
		}
		return new StringBuilder(getLastName()).append(", ").append(getFirstName()).toString();
	}
	public long getAge() {
		Calendar calendar = Calendar.getInstance();
		long currentYear = calendar.get(Calendar.YEAR);
		long currentDay = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(getDateOfBirthd());
		long yearOfBirth = calendar.get(Calendar.YEAR);
		long dayOfBirth = calendar.get(Calendar.DAY_OF_YEAR);
		long age = currentYear - yearOfBirth;
		if (currentDay < dayOfBirth) {
			return age - 1;
		} else {
			return age;
		}
	}
	
	@Override
	  public int hashCode() {
	    if (this.getId() == null) {
	      return 0;
	    }
	    return this.getId().hashCode();
	  }
	
	  @Override
	  public boolean equals(Object obj) {
		  if (obj == null) {
			  return false;
		  }
		  if (this == obj) {
			  return true;
		  }
		  if (this.getId() == null && ((User) obj).getId() == null) {
			  return true;
		  }
		  return this.getId().equals(((User) obj).getId());
}

}
