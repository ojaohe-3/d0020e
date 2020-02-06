/**
 * 
 * 	First ajax file for the project..
 * 	
 * 
 */

$(document).ready(function() {
	$('#searchCoursesByName').blur(function() {
		console.log("code running");
		$.ajax({
			
			
			url : 'GetCoursesFilterByCourseName',
			data : {
				filter : $("#searchCoursesByName").val()
			},
			success : function(response) {
				$('#search_results').text(response);
			}

		});
	});
});