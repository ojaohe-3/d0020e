<script type="text/javascript" src="${pageContext.request.contextPath}/JavascriptFolder/editCourse.js"></script>
<link rel="stylesheet" href="CssFolder/editCourse.css">

<div class="container">

	<h1>Edit course</h1>
	<div id="title_content"></div>
	<div class="requiredKC">
		<h2>Required</h2>
		<div id="requiredKCs">
		</div>
			  <form class="search">
				<input type="text" class="searchBar" id="searchRequiredKC" placeholder="Search... ">
			  </form>
		  <div id="kcRequired_search_results" class="result pre-scrollable">
		  </div>
	</div>
	
	<div class="description">
		<h2>Description</h2>
		<form>
  			<textarea id="description" name="description" rows="10" cols="30">Description here...</textarea>
  			<br><br>
		</form>
		<button type="button" onclick="">Cancel</button>
		<button type="button" onclick="saveCourseChanges()">Save</button>
	</div>
	
	<div class="developedKC">
		<h2>Developed</h2>
		<div id="developedKCs">
		</div>
		<form class="search">
			<input type="text" class="searchBar" id="searchDevelopedKC" placeholder="Search... ">
		</form>
		<div id="kcDeveloped_search_results" class="result pre-scrollable">
		</div>
	</div>
	
</div>