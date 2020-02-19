/**
 * 
 */


function courseClicked(code, year, lp) {
	
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

function programClicked() {
	$.ajax({

		url : 'GetProgram/getCourses',
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

function kcClicked() {
	

}