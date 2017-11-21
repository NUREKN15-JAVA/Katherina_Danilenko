package kn156danilenko.web;

import java.util.Date;

import kn156danilenko.User;

public class DeleteServletTest extends MockServletTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(DeleteServlet.class);
	}
	
	public void testDelete(){
		Date date = new Date();
		User user = new User(new Long(1000), "John", "Doe", date);
		getMockUserDao().expect("delete", user);
		addRequestParameter("id", "1000");
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	public void testDeleteEmptyId(){
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in request scope", errorMessage);
	}
}