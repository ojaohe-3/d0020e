<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/site.css">

<nav class="navbar navbar-inverse">
<<<<<<< HEAD

	<ul class="nav navbar-nav navbar-right">
		<ul>	
			<li><a href="/project/login"> <span class="glyphicon glyphicon-log-in"></span>Login</a></li>
		</ul>
	</ul>
=======
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="navbar-brand">
				<a href="/project"> <small><s>KronoX</s></small>  <big><b>Study Planner</b></big> </a>
			</div>
		</div>
		
		<%
			try {
				if ((boolean)request.getSession().getAttribute("logged_in") == true)  {
					
					if ((boolean)request.getSession().getAttribute("is_admin") == true) {
						out.print("<ul class=\"nav navbar-nav\"> <li> <a href=\"/project/admin\">Admin Page</a> </li>  </ul>");
					} else {
						out.print("<ul class=\"nav navbar-nav\"> <li> <a href=\"/project/teacher\">Teacher Page</a> </li>  </ul>");
					}
				}
				
				
			} catch(NullPointerException e) {}
		
		
		
		%>
		
	
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
	
>>>>>>> merge with admin admin page


</nav>