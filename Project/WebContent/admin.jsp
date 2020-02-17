<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="_includes.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/admin.js"></script>




<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin page</title>
	</head>
	<body>
	<div class="container-fluid">
		<div class="col-md-4">
			<ul>
				<h4>User</h4>
				<li><button onclick="user_create())">Create</button></li>
				<li><button onclick="user_modify()">Modify</button></li>
				<li><button onclick="user_delete()">Delete</button></li>
				<li><button onclick="user_command()">Set Relation To Course</button></li>
				<li><button onclick="user_command()">Remove Relation To Course</button></li>
			</ul>
		</div>
		
		<div class="col-md-4">
			<ul>
			<h4>Course</h4>
				<li><button onclick="user_command()">Create</button></li>
				<li><button onclick="user_command()">Modify</button></li>
				<li><button onclick="user_command()">Delete</button></li>
			</ul>
		</div>
		<div class="col-md-4">
			<ul>
				<h4>KC</h4>
				<li><button onclick="user_command()">Create</button></li>
				<li><button onclick="user_command()">Modify</button></li>
				<li><button onclick="user_command()">Delete</button></li>
			</ul>
		</div>
		<div class="col-md-4">
			<ul>
				<h4>Program</h4>
				<li><button onclick="user_command()">Create</button></li>
				<li><button onclick="user_command()">Modify</button></li>
				<li><button onclick="user_command()">Delete</button></li>
				<li><button onclick="user_command()">Copy From Year</button></li>
			</ul>
		</div>
	
	
	</div>
	
	
	
	<div id="log"></div>
	<div id="output"></div>
	
	
	</body>
</html>

