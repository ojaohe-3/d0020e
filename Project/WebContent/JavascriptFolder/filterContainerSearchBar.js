/**
 * 
 * 	First ajax file for the project..
 * 	
 * 
 */

$(document).ready(function() {
	// ---- Search Course ----
	$('#searchCourse').on('keyup', function() {
		if($("#searchCourse").val().length > 3) {
			if($("#courseFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseName',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
						$('#course_search_results').text(response);
					}

				});
			} else if($("#courseFilterByCode").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseCode',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
						$('#course_search_results').text(response);
					}

				});
			} else if($("#courseFilterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByTopic',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
						$('#course_search_results').text(response);
					}

				});
			} 
				
		} 
		
	});
	//-- end Search Course -----
	
	// --- Search Program -----
	$('#searchProgram').on('keyup', function() {
		if($("#searchProgram").val().length > 3) {
			if($("#programFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByName',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						$('#program_search_results').text(response);
					}

				});
			} else if($("#programFilterByCode").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByCode',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						$('#program_search_results').text(response);
					}

				});
			} else if($("#programFilterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByTopic',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						$('#program_search_results').text(response);
					}

				});
			} 
				
		} 
		
	});
	// -- end search Program
	
	// -- Search KC ---
	
	$('#searchKC').on('keyup', function() {
		if($("#searchKC").val().length > 3) {
			if($("#kcFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetKCs/FilterByName',
					data : {
						filter : $("#searchKC").val()
					},
					success : function(response) {
						$('#kc_search_results').text(response);
					}

				});
			} else if($("#kcFilterByCode").is(':checked')) {
				$.ajax({

					url : 'GetKcs/FilterByCode',
					data : {
						filter : $("#searchKC").val()
					},
					success : function(response) {
						$('#kc_search_results').text(response);
					}

				});
			}
		} 
		
	});
	
});