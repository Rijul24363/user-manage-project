<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Management Application</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" 
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" 
	crossorigin="anonymous">
</head>
<body>

	<header>
			<nav class = "navbar navbar-expand-md navbar-dark"
				style = "background-color:blue">
				<div>
					<a href = "http://www.xadmin.net" class = "navbar-brand"> User Management Application
					</a>
				</div>
				
				<ul class = "navbar-nav">
					<li><a href= "<%= request.getContextPath()%>/list"
					class = "nav-link">Users List</a></li>
				</ul>
			</nav>
		</header>
		<br>
		
		<div class = "container col-md-5">
		<div class = "card">
		<div class = "card-body"> 
		<c:if test="${user != null}">
			<form action = "update" method="get">
			
			</c:if>
			<c:if test="${user == null}">
				<form action = "insert" method="get">
				</c:if>
				
			<caption>
				<h2>
					<c:if test="${user != null  }">Edit User</c:if>
					<c:if test="${user == null }">Add New User</c:if>
				</h2>
			</caption>
			
			<c:if test="${user != null }">
				<input type="hidden" name="id" value="<c:out value='${user.id}' /> "/>
			</c:if>
			
			<fieldSet class = "form-group">
				<label>User id</label> <input type = "text"
				value="<c:out value='${user.id }' />" class = "form-control"
				name = "id">
			</fieldSet>
			
			<fieldSet class = "form-group">
				<label>User Name</label> <input type = "text"
				value="<c:out value='${user.name }' />" class = "form-control"
				name = "name" required="required">
			</fieldSet>
			
			<fieldSet class = "form-group">
				<label>User Email</label> <input type = "text"
				value="<c:out value='${user.email }' />" class = "form-control"
				name = "email">
			</fieldSet>
			
			<fieldSet class = "form-group">
				<label>User Phone</label> <input type = "text"
				value="<c:out value='${user.phone }' />" class = "form-control"
				name = "phone">
			</fieldSet>
			
			
			
			<button type="submit" class="btn btn-success">Submit</button>
			</form>
			</div>
		</div>
	</div>

</body>
</html>