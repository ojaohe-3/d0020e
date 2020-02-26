<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="Data.Course" %>

<link rel="stylesheet" href="filterContainer.css">
<link rel="stylesheet" href="site.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/teacher.js"></script>

<html>
<head>
<meta charset="UTF-8">
<title>Teacher Page</title>
</head>

<body>
	<%@include file="_includes.jsp" %>

	<%@include  file="_filterContainer.jsp" %>

	<%@include file="_editCourse.jsp" %>
    
    <div class="container-fluid">
	    <div class="row" id="my_courses" style="background-color: gray">
	    <h3>My Courses</h3> <hr>
	    
		    <div class="course_list">
		    	
		    	<%
		    		
		    		Course[] courses = (Course[])request.getAttribute("courses");
		    		
		    		if(courses != null) {
		    			for(Course course: courses) {
			    			
			    			String s = "<div style=\"max-width:250px\" class=\"SearchResult\" id=\"course_edit\" onclick=\"courseClickedEdit('" + course.getCourseCode() + "', '"+ course.getStartPeriod().getYear() + "', '" + course.getStartPeriod().getPeriod() + "')\">  <b>" + course.getName() + "</b> - " + course.getCourseCode();
							s += "<div style=\"background-color: orange; width:inherit\" class=\"SearchResultExpander\"> Year : " + course.getStartPeriod().getYear() +" </br> LP : " + course.getStartPeriod().getPeriod() + " </br> Examiner : " + course.getExaminer() + " </br> Credits : " + course.getCredit() + "</div></div>";
			    			
							out.print(s);
			    		}
		    		}
		    		
		    	%>
		    
		    </div>
	
	    </div>
    </div>
    
   <%@include file="_graphCanvas.jsp"%>
    
	
    

</body>
</html>