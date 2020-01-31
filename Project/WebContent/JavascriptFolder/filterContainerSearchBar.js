/**
 * 
 * 	First ajax file for the project..
 * 	
 * 
 */

$(document).ready(function() {
	$('#searchCoursesByName').blur(function() {
		$.ajax({

			url : 'GetCourses/FilterByCourseName',
			data : {
				filter : $("#searchCoursesByName").val()
			},
			success : function(response) {
				$('#search_results').text(response);
			}

		});
	});
});