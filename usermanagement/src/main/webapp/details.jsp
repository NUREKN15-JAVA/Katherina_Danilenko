<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>User management. Details</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/browse" method="post">
		<table>
			<thead>
				<tr>
					<th>First name</th>
					<th>Last name</th>
					<th>Date of Birth</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>${user.firstName}</th>
					<th>${user.lastName}</th>
					<th><fmt:formatDate value="${user.date}" type="date" dateStyle="medium" /></th>
				</tr>
			</tbody>
		</table>
		<input type="submit" name="browseButton" value="Ok">
	</form>
	<c:if test="${requestScope.error != null}">
		<script type="text/javascript">
			alert('${requestScope.error.message}');
		</script>
	</c:if>
</body>
</html>