<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/editCourse.js"></script>
<link rel="stylesheet" href="CssFolder/editCourse.css">

<div class="container">
	<div class="courseInfo">
		<h3>Edit course</h3>
		<div id="title_content"></div>
	</div>
	<div class="editCourse">
		<div class="requiredKC">
			<h3>Required</h3>
			<div id="requiredKCs">
			</div>
				  <form class="search">
					<input type="text" class="searchBar" id="searchRequiredKC" placeholder="Search for knowledge compoenent...">
				  </form>
			  <div id="kcRequired_search_results" class="result pre-scrollable">
			  </div>
		</div>
		
		<div class="description">
			<h3>Description</h3>
			<form action="POST">
	  			<textarea id="description" name="description" rows="10" cols="30">Description here...</textarea>
	  			<br><br>
	  			<button type="button" onclick="cancel()">Cancel</button>
				<button type="button" onclick="saveCourseChanges()">Save</button>
			</form>
			
		</div>
		
		<div class="developedKC">
			<h3>Developed</h3>
			<div id="developedKCs">
			</div>
			<form class="search">
				<input type="text" class="searchBar" id="searchDevelopedKC" placeholder="Search for knowledge compoenent... ">
			</form>
			<div id="kcDeveloped_search_results" class="result pre-scrollable">
			</div>
		</div>
	</div>
</div>