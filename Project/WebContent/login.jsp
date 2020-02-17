<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="_includes.jsp" %>



<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>


<div class="container" >
	<form class="form-signin form-horizontal" action="" method="POST" stype="margin:auto; margin-top:6em">
		
		<% if(request.getAttribute("error") != null) { %>
			<div class="alert alert-danger">${error.err}</div>
		<% } %>
		
		<h1>Login</h1>
		<label for="username">Username</label>
		<input type="text" name="username" id="username" class="form-control" placeholder="Username.." >
		
		<label for="password">Password</label>
		<input type="password" name="password" id="password" class="form-control" placeholder="password">
		
		<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
		
	</form>

</div>




</body>
</html>