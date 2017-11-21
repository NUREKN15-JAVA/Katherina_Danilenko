package kn156danilenko.web;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import kn156danilenko.User;

public class DetailsServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user =null;
		try {
			user = getUser(req);
		} catch (ValidationException e) {
			req.setAttribute("error", e.getMessage());
			showPage(req, resp);
			return;
		}
		req.setAttribute("user", user);
		showPage(req, resp);
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/details.jsp").forward(req, resp);
	}

	private User getUser(HttpServletRequest req) throws ValidationException {
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("date");
		if (idStr != null) {
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
}
