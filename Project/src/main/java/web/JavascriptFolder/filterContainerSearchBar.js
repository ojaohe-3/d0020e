/**
 * 
 * 	First ajax file for the project..
 * 	
 * 
 */

$(document).ready(function() {
		$('#searchCoursesByName').blur(function(event) {
			
			var input = $("#searchCoursesByName").val();
			$.ajax({
				url : 'GetCoursesFilterByCourseName',
				data : {
					filter : input
				},
				success : function(response) {
					$('#search_results').text(response);
				}
			})
			
		});
	});
});