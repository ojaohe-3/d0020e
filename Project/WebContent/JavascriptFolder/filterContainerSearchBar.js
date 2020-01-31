/**
 * 
 * 	First ajax file for the project..
 * 	
 * 
 */

$(document).ready(function() {
	$('#searchCoursesByName').on('keyup', function() {
		
		if($("#searchCoursesByName").val().length > 3) {
			if($("#filterByName").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseName',
					data : {
						filter : $("#searchCoursesByName").val()
					},
					success : function(response) {
						$('#search_results').text(response);
					}

				});
			} else if($("#filterByCode").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseCode',
					data : {
						filter : $("#searchCoursesByName").val()
					},
					success : function(response) {
						$('#search_results').text(response);
					}

				});
			} else if($("#filterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByTopic',
					data : {
						filter : $("#searchCoursesByName").val()
					},
					success : function(response) {
						$('#search_results').text(response);
					}

				});
			} else {
				$('#search_results').text($("#filterByTopic").val());
			}
				
		} 
		
	});
});