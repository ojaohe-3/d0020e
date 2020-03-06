/**
 * 
 * 	This will use Ajax to recieve content from the server when a user is searching
 * 
 * 	Covers search filtering for Courses, KCs and programs	
 * 
 */

$(document).ready(function() {
	// ---- Search Course ----
	$('#searchCourse').on('keyup', function() {
		if($("#searchCourse").val().length > 2) {
			if($("#courseFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseName',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
	
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"course\" onclick=\"courseClicked('"+response[i].courseCode +"', '"+ response[i].year + "', '" + response[i].lp+ "')\"> <b>" + response[i].name + "</b> - " + response[i].courseCode ;
							s += "<div class=\"SearchResultExpander\"> Year : " + response[i].year +" </br> LP : " + response[i].lp + " </br> Examiner : " + response[i].examiner + " </br> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#course_search_results').html('');
						$('#course_search_results').html(s);
						
						
					}

				});
			} else if($("#courseFilterByCode").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByCourseCode',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
						
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"course\" onclick=\"courseClicked('"+response[i].courseCode +"', '"+ response[i].year + "', '" + response[i].lp+ "')\"> <b>" + response[i].name + "</b> - " + response[i].courseCode ;
							s += "<div class=\"SearchResultExpander\"> Year : " + response[i].year +" </br> LP : " + response[i].lp + " </br> Examiner : " + response[i].examiner + " </br> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#course_search_results').html('');
						$('#course_search_results').html(s);
						
					}

				});
			} else if($("#courseFilterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetCourses/FilterByTopic',
					data : {
						filter : $("#searchCourse").val()
					},
					success : function(response) {
						
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"course\" onclick=\"courseClicked('" + response[i].courseCode + "', '"+ response[i].year + "', '" + response[i].lp+ "')\"> <b>" + response[i].name + "</b> - " + response[i].courseCode ;
							s += "<div class=\"SearchResultExpander\"> Year : " + response[i].year +" </br> LP : " + response[i].lp + " </br> Examiner : " + response[i].examiner + " </br> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#course_search_results').html('');
						$('#course_search_results').html(s);
					}

				});
			} 
				
		} else {
			$('#course_search_results').html('');
		} 
		
	});
	//-- end Search Course -----
	
	// --- Search Program -----
	$('#searchProgram').on('keyup', function() {
		if($("#searchProgram").val().length > 2) {
			if($("#programFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByName',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"program\" onclick=\"programClicked('"+response[i].code+"','" +response[i].year+"','"+response[i].lp+"')\"> <b>" + response[i].name + "</b> - " + response[i].code ;
							s += "<div class=\"SearchResultExpander\"> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#program_search_results').html('');
						$('#program_search_results').html(s);
					}

				});
			} else if($("#programFilterByCode").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByCode',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"program\" onclick=\"programClicked('"+response[i].code+"','" +response[i].year+"','"+response[i].lp+"')\"> <b>" + response[i].name + "</b> - " + response[i].code ;
							s += "<div class=\"SearchResultExpander\"> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#program_search_results').html('');
						$('#program_search_results').html(s);
					}

				});
			} else if($("#programFilterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetPrograms/FilterByTopic',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"program\" onclick=\"programClicked('"+response[i].code+"','" +response[i].year+"','"+response[i].lp+"')\"> <b>" + response[i].name + "</b> - " + response[i].code ;
							s += "<div class=\"SearchResultExpander\"> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#program_search_results').html('');
						$('#program_search_results').html(s);
					}

				});
			} else if($("#specializationByName").is(':checked')) {
				$.ajax({

					url : 'GetSpecializations/FilterByName',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"program\" onclick=\"specializationClicked('"+response[i].name+"','"+response[i].code+"','" +response[i].year+"','"+response[i].lp+"')\"> <b>" + response[i].name + "</b> - " + response[i].code ;
							s += "<div class=\"SearchResultExpander\"> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#program_search_results').html('');
						$('#program_search_results').html(s);
					}

				});
			} else if($("#specializationByCode").is(':checked')) {
				$.ajax({

					url : 'GetSpecializations/FilterByCode',
					data : {
						filter : $("#searchProgram").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"program\" onclick=\"specializationClicked('"+response[i].name+"','"+response[i].code+"','" +response[i].year+"','"+response[i].lp+"')\"> <b>" + response[i].name + "</b> - " + response[i].code ;
							s += "<div class=\"SearchResultExpander\"> Credits : " + response[i].credit + "</div></div>";
						}
						s += "</div>";
						$('#program_search_results').html('');
						$('#program_search_results').html(s);
					}

				});
			}
				
		} else {
			$('#program_search_results').html('');
		} 
		
	});
	// -- end search Program

	// -- Search KC ---
	
	$('#searchKC').on('keyup', function() {
		if($("#searchKC").val().length > 2) {
			if($("#kcFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetKCs/FilterByNameGetOne',
					data : {
						filter : $("#searchKC").val()
					},
					success : function(response) {
						//$('#kc_search_results').text(response);
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"kc\" onclick=\"kcClicked('"+response[i].name+ "')\"> <b>" + response[i].name + "</b>";
							s += "<div class=\"SearchResultExpander\"> Description : " + response[i].generalDescription +"</div></div>";
						}
						s += "</div>";
						$('#kc_search_results').html('');
						$('#kc_search_results').html(s);
					}

				});
			} else if($("#kcFilterByTopic").is(':checked')) {
				$.ajax({

					url : 'GetKCs/FilterByTopic',
					data : {
						filter : $("#searchKC").val()
					},
					success : function(response) {
						//$('#kc_search_results').text(response);
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"kc\" onclick=\"kcClicked('"+response[i].name+ "')\"> <b>" + response[i].name + "</b>";
							s += "<div class=\"SearchResultExpander\"> Description : " + response[i].generalDescription +"</div></div>";
						}
						s += "</div>";
						$('#kc_search_results').html('');
						$('#kc_search_results').html(s);
					}

				});
			} 
		} else {
			$('#kc_search_results').html('');
		} 
		
	});
	// -- end Search Kc --
	// -- Search Topic ---

	$('#searchTopic').on('keyup', function() {
		if($("#searchTopic").val().length > 2) {
			if($("#topicFilterByName").is(':checked')) {
				$.ajax({

					url : 'GetTopics/FilterByName',
					data : {
						filter : $("#searchTopic").val()
					},
					success : function(response) {
						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"topic\" onclick=\"topicClicked()\"> <b>" + response[i].title + "</b></div>";
						}
						s += "</div>";
						$('#topic_search_results').html('');
						$('#topic_search_results').html(s);
					}

				});
			}
		} else {
			$('#topic_search_results').html('');
		}

	});
	// -- end Search Topic --
});