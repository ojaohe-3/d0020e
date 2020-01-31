
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/filterContainerNavbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/filterContainerSearchBar.js"></script>


<div class="container-fluid sidenav" style="background-color:orange;">
  <h2>Filter Container</h2>
  <ul class="nav nav-pills nav-justified">
    <li class="active"><a data-toggle="pill" href="#course">Course</a></li>
    <li><a data-toggle="pill" href="#program">Program</a></li>
    <li><a data-toggle="pill" href="#KC">Knowledge Component</a></li>
    <li><a data-toggle="pill" href="#topic">Topic ??</a></li>
  </ul>
  
  <div class="tab-content">
    <div id="course" class="tab-pane fade in active">
      <h3>Course</h3>
	    <div class="radio">
		      <label><input type="radio" name="optradio" id="courseFilterByName" checked>Filter by Name</label>
	    </div>
	    <div class="radio">
	      	  <label><input type="radio" name="optradio" id="courseFilterByCode">Filter by Course code</label>
	    </div>
	    <div class="radio disabled">
	          <label><input type="radio" name="optradio" id="courseFilterByTopic">Filter by Topic</label>
	    </div>
	      
	      <form>
			<input type="text" id="searchCourse" placeholder="Search.. ">
		  </form>

	  <div id="course_search_results">
	
	</div>
      
      
    </div>
    
    
    <div id="program" class="tab-pane fade">
      <h3>Program</h3>
      		<div class="radio">
		      <label><input type="radio" name="optradio" id="programFilterByName" checked>Filter by Name</label>
	    </div>
	    <div class="radio">
	      	  <label><input type="radio" name="optradio" id="programFilterByCode">Filter by Program Code</label>
	    </div>
	    <div class="radio">
	      	  <label><input type="radio" name="optradio" id="programiFlterByTopic">Filter by Topic</label>
	    </div>
	    
	    <form>
			<input type="text" id="searchProgram" placeholder="Search.. ">
		  </form>

	  <div id="program_search_results">
	    
    </div>
    
    
    <div id="KC" class="tab-pane fade">
      <h3>Knowledge Component</h3>
      
      <div class="radio">
		      <label><input type="radio" name="optradio" id="kcFilterByName" checked>Filter by Name</label>
	    </div>
	    <div class="radio disabled">
	          <label><input type="radio" name="optradio" id="kcFilterByTopic">Filter by Topic</label>
	    </div>
	      
	      <form>
			<input type="text" id="searchKC" placeholder="Search.. ">
		  </form>

	  <div id="kc_search_results">
      
    </div>
    
    
    <div id="topic" class="tab-pane fade">
      <h3>Topic ??</h3>
      
    </div>
    
    
  </div>
</div>