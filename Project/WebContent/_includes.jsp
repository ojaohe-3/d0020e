<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/site.css">

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="navbar-brand">
				<small><s>KronoX</s></small>  <big><b>Study Planner</b></big>
			</div>
		</div>
		
	
	<ul class="nav navbar-nav navbar-right">
		<li>
			
			<%
			 try {
				 if ((boolean)request.getSession().getAttribute("logged_in") == true)  {
					 out.print("<a href=\"/project/login\"> <span class=\"glyphicon glyphicon-user\"></span> Logout</a>");
				 } else { 
					 out.print("<a href=\"/project/login\"> <span class=\"glyphicon glyphicon-user\"></span> Login</a>");	
				 } 
			 } catch(NullPointerException e) {
				 out.print("<a href=\"/project/login\"> <span class=\"glyphicon glyphicon-user\"></span> Login</a>");
				 
			 }%>
			 
			
		</li>
	</ul>
	
	
	</div>
	


</nav>