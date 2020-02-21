
/**
 * 
 * 	Function that is called when a teacher want to edit a course
 * 
 * 		Should call a function in the canvas with the response
 * 
 * @param code automatically set in .jsp file
 * @param year automatically set in .jsp file
 * @param lp automatically set in .jsp file
 * @returns
 */
function courseClickedEdit(code, year, lp) {

	$.ajax({

		url : 'GetCourse/byCodeYearLP',
		data : {
			courseCode : code,
			year : year,
			lp : lp
		},
		success : function(response) {
			// Should call a function in the canvas with the response
			$('#debug').html("response : " + response);
		}
	});
	
}