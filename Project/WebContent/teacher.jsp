<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="Data.Course" %>

<link rel="stylesheet" href="filterContainer.css">

<html>
<head>
<meta charset="UTF-8">
<title>Teacher Page</title>
</head>

<body>
	<%@include file="_includes.jsp" %>
	<%@include  file="_filterContainer.jsp" %>

    <%@include file="_graphCanvas.jsp"%>
    
    <footer>
    <div class="container-fluid" id="my_courses">
    <h3>My Courses</h3> <hr>
    
	    <div class="course_list">
	    	
	    	<%
	    		
	    		Course[] courses = (Course[])request.getAttribute("courses");
	    		
	    		if(courses != null) {
	    			for(Course course: courses) {
		    			
		    			String s = "<div class=\"SearchResult\" id=\"course_edit\" onclick=\"courseClickedEdit('" + course.getCourseCode() + "', '"+ course.getStartPeriod().getYear() + "', '" + course.getStartPeriod().getPeriod() + "')\">  <b>" + course.getName() + "</b> - " + course.getCourseCode();
						s += "<div class=\"SearchResultExpander\"> Year : " + course.getStartPeriod().getYear() +" </br> LP : " + course.getStartPeriod().getPeriod() + " </br> Examiner : " + course.getExaminer() + " </br> Credits : " + course.getCredit() + "</div></div>";
		    			
						out.print(s);
		    		}
	    		}
	    		
	    	%>
	    
	    </div>

    </div>
    </footer>

</body>
</html>