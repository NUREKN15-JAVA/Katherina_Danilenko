package kn156danilenko.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import kn156danilenko.User;
import kn156danilenko.db.DaoFactory;
import kn156danilenko.db.DatabaseExeption;


public class EditServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("okButton") != null) {
		     doOk(req, resp);
		}else if (req.getParameter("cancelButton") != null) {
		     doCancel(req, resp);
		}else{
		     showPage(req, resp);
		}
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse").forward(req, resp);
		
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = null;
		try {
			user = getUser(req);
		} catch (ValidationException e1) {
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		try {
			processUser(user);
		} catch (DatabaseExeption e) {
			e.printStackTrace();
			new ServletException(e);
		}
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	private User getUser(HttpServletRequest req) throws ValidationException {
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("date");
		
		if(firstName == null) {
			throw new ValidationException("First name is empy");
		}
		if(lastName == null) {
			throw new ValidationException("Last name is empy");
		}
		if(dateStr == null) {
			throw new ValidationException("Date is empy");
		}
		
		if(idStr != null) {
			user.setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDateOfBirthd(DateFormat.getDateInstance().parse(dateStr));
		} catch (ParseException e) {
			throw new ValidationException("Date format is incorrect");
		}
		return user;
	}

	protected void processUser(User user) throws DatabaseExeption {
		DaoFactory.getInstance().getUserDao().update(user);
		
		
	}

	
	
}
