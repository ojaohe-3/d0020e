<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="_includes.jsp" %>



<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="site.css">
<style>

.container {
 background: #292929;
 border-radius: 25px;
 width: 30%;
 padding: 10px 50px 20px 50px;
}

.container input {
  background: #404040;
  border: 1px solid #474747;
  color: #bababa;;
  padding-left: 10px;
  border-radius: 5px;
}

.container button {
background-color: orange; 
border: none; 
margin-top: 15px;
}

.container button:hover {
background-color: #FE9C05; 
}
</style>
</head>
<body>


<div class="container">
	<form class="form-signin form-horizontal" action="" method="POST">
		
		<% if(request.getAttribute("error") != null) { %>
			<div class="alert alert-danger">${error.err}</div>
		<% } %>
		
		<h1>Login</h1>
		<label for="username">Username</label>
		<input type="text" name="username" id="username" class="form-control" placeholder="Username" style="margin-bottom: 10px;">
		
		<label for="password">Password</label>
		<input type="password" name="password" id="password" class="form-control" placeholder="Password">
		
		<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
		
	</form>

</div>




</body>
</html>