<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/site.css">
<link href="https://fonts.googleapis.com/css?family=Luckiest+Guy&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Alegreya+Sans+SC:900i&display=swap" rel="stylesheet">


<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="navbar-brand">
				<a href="/"> <small><s>KronoX</s></small>  <big><b style="font-family: 'Alegreya Sans SC';";>Study Planner</b></big> </a>
			</div>
		</div>

		<%
			try {
				if ((boolean)request.getSession().getAttribute("logged_in") == true)  {

					if ((boolean)request.getSession().getAttribute("is_admin") == true) {
						out.print("<ul class=\"nav navbar-nav\"> <li> <a href=\"/admin\">Admin Page</a> </li>  </ul>");
					} else {
						out.print("<ul class=\"nav navbar-nav\"> <li> <a href=\"/teacher\">Teacher Page</a> </li>  </ul>");
					}
				}


			} catch(NullPointerException e) {}



		%>


		<ul class="nav navbar-nav navbar-right">
			<li>

				<%
					try {
						if ((boolean)request.getSession().getAttribute("logged_in") == true)  {
							out.print("<a href=\"/login\"> <span class=\"glyphicon glyphicon-user\"></span> Logout</a>");
						} else {
							out.print("<a href=\"/login\"> <span class=\"glyphicon glyphicon-user\"></span> Login</a>");
						}
					} catch(NullPointerException e) {
						out.print("<a href=\"/login\"> <span class=\"glyphicon glyphicon-user\"></span> Login</a>");

					}%>


			</li>
		</ul>


	</div>
</nav>

<!-- 
<footer style:"position: fixed;margin-bottom: 0px;bottom: 0px;">
	<p>All rights belongs to Tommy and Wilma</p>
</footer>
 -->