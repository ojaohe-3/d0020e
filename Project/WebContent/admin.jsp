<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="_includes.jsp" %>

<%-- <%@ page import="Data.Credits" %> --%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>


<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/adminGet.js"></script>

<style>

	button {
		min-width: 300px;
		color: black;
	}

	ul {
		list-style:none;
	}

</style>



<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin page</title>
	</head>
	<body>
	<% try {
	if ((boolean)request.getSession().getAttribute("logged_in") == true)  { %>
	
	<div class="container" style="background-color: light-gray">
		<h1>Welcome to the admin page</h1>
		<h3>Dont forget to use ';' as separator</h3>
		
		
		<div class="container-fluid" style="background-color: gray">
			<div class="col-sm-3">
				<p> To assign the reading period use a number between 1 and 4 </p>
			</div>
		</div>

		<div class="container-fluid">
			<div class="col-md-4">
				<ul>
					<h4>User</h4>
					<li><button onclick="user_create()">Create</button></li>
					<li><button onclick="user_modify_password()">Modify Password</button></li>
					<li><button onclick="user_modify_username()">Modify Username</button></li>
					<li><button onclick="user_delete()">Delete</button></li>
					<li><button onclick="user_set_course_relation()">Set Relation To Course</button></li>
					<li><button onclick="user_delete_course_relation()">Remove Relation To Course</button></li>
					<li><button onclick="get_users()">Get all available users</button></li>
					
				</ul>
			</div>
			
			<div class="col-md-4">
				<ul>
				<h4>Course</h4>
					<li><button onclick="course_create()">Create</button></li>
					<li><button onclick="course_modify()">Modify</button></li>
					<li><button onclick="course_delete()">Delete</button></li>
					<li><button onclick="course_add_topic()">Add Topic to Course</button></li>
					<li><button onclick="course_delete_topic()">Delete Topic from Course</button></li>
	
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h4>KC</h4>
					<li><button onclick="kc_create()()">Create</button></li>
					<li><button onclick="kc_modify_general()">Modify General description</button></li>
					<li><button onclick="kc_modify_taxonomy()">Modify Taxonomy description</button></li>
					<li><button onclick="kc_delete()">Delete</button></li>
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h4>Program</h4>
					<li><button onclick="program_create()">Create Program</button></li>
					<li><button onclick="program_create_specialization()">Create Specialization</button></li>
					<li><button onclick="program_modify()">Modify</button></li>
					<li><button onclick="program_delete()">Delete</button></li>
					<li><button onclick="program_modify()">Modify Specialization</button></li>
					<li><button onclick="program_delete()">Delete Specialization</button></li>
					<li><button onclick="program_copy_from_year()">Copy From Year</button></li>
					<li><button onclick="program_copy_from_year_special()">Copy Specialization From Year</button></li>
					<li><button onclick="program_add_course()">Add Course to program</button></li>
					<li><button onclick="get_courses_in_program()">Get Courses in Program</button></li>
					<li><button onclick="graphModifyCourse()">Modify Course using graphics</button>
				</ul>
			</div>
		
		
		</div>
	
	<%@include file="_adminGraph.jsp" %>

	<div class="row">
		<div class="col-sm-4" id="log"> <h4>log</h4> </div>
		<div class="col-sm-4" id="output"> <h4>Responses</h4> </div>
	</div>

	</div>
<% } else { out.write(" <h1>Stop Hacking! The code is avaliable at github if you want to find bugs :) </h1>"); }
	

} catch(NullPointerException e) { out.write(" <h1>Stop Hacking! The code is avaliable at github if you want to find bugs :) </h1>");} %>
	</body>
</html>

