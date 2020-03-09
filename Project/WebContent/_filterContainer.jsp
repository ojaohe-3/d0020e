
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/filterContainerFunctions.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/filterContainerSearchBar.js"></script>
<link rel="stylesheet" href="CssFolder/filterContainer.css">


<script>
function openNav() {
  document.getElementById("main").style.marginLeft = "22em";
  document.getElementById("filterContainer").style.display = "block";
  document.getElementById("filterContainerClosed").style.display = "none";
}

function closeNav() {
  document.getElementById("filterContainer").style.display = "none";
  document.getElementById("filterContainerClosed").style.display = "block";
  document.getElementById("filterContainerClosed").style.width = "3.5em";
  document.getElementById("main").style.marginLeft= "3.5em";
}
</script>


<!-- Creates menu to choose to search for course, kc, program or topic by name, code or topic -->
<div id="filterContainerClosed">
	<a href="javascript:void(1)" class="closebtn" onclick="openNav()">&#9776;</a>
</div>
<div class="filterContainer" id="filterContainer">
	<div class="container-fluid sidenav">
	  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&#9776;</a>

	  <ul class="nav nav-pills nav-justified" id="filterMenu">
	  	<li class="active"><a data-toggle="pill" href="#program">Program</a></li>
		  <li><a data-toggle="pill" href="#course">Course</a></li>
		<li><a data-toggle="pill" href="#KC">KC</a></li>
		<li><a data-toggle="pill" href="#topic">Topic</a></li>

	  </ul>

	  <div class="tab-content">
	  
	  	<div id="program" class="tab-pane fade in active">
		  <h3>Program</h3>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="programFilterByName" checked><span class="checkmark"></span>Filter by name</label>
			</div>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="programFilterByCode"><span class="checkmark"></span>Filter by code</label>
			</div>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="programFilterByTopic"><span class="checkmark"></span>Filter by topic</label>
			</div>
		   <h3>Specialization</h3>
			<div class="radio">
				<label><input type="radio" name="optradio" id="specializationByName"><span class="checkmark"></span>Filter by name</label>
			</div>
			<div class="radio">
				<label><input type="radio" name="optradio" id="specializationByCode"><span class="checkmark"></span>Filter by code</label>
			</div>

			<form class="search">
				<input type="text" class="searchBar" id="searchProgram" placeholder="Search... ">
			  </form>

		  <div id="program_search_results" class="result pre-scrollable">

		  </div>

		</div>
		
		<div id="course" class="tab-pane fade in">
		  <h3>Course</h3>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="courseFilterByName"><span class="checkmark"></span>Filter by name</label>
			</div>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="courseFilterByCode"><span class="checkmark"></span>Filter by code</label>
			</div>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="courseFilterByTopic"><span class="checkmark"></span>Filter by topic</label>
			</div>

			 <form class="search">
				<input type="text" class="searchBar" id="searchCourse" placeholder="Search... ">
			  </form>

			 <div id="course_search_results" class="result pre-scrollable">

			 </div>


		</div>

		<div id="KC" class="tab-pane fade in">
		  <h3>Knowledge Component</h3>

		  <div class="radio">
				  <label><input type="radio" name="optradio" id="kcFilterByName"><span class="checkmark"></span>Filter by name</label>
			</div>
			<div class="radio">
				  <label><input type="radio" name="optradio" id="kcFilterByTopic"><span class="checkmark"></span>Filter by topic</label>
			</div>

			  <form class="search">
				<input type="text" class="searchBar" id="searchKC" placeholder="Search... ">
			  </form>

		  <div id="kc_search_results" class="result pre-scrollable">

		  </div>

		</div>

		<div id="topic" class="tab-pane fade in">
		  <h3>Topic</h3>
		  <div class="radio">
				  <label><input type="radio" name="optradio" id="topicFilterByName"><span class="checkmark"></span>Filter by name</label>
			</div>

			  <form class="search">
				<input type="text" class="searchBar" id="searchTopic" placeholder="Search... ">
			  </form>

			<div id="topic_search_results" class="result pre-scrollable">

			</div>
		</div>

	  </div>
	</div>
</div>
