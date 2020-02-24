/**
 * Chosen course from search result should be send to canvas
 * @param code
 * @param year
 * @param lp
 * @returns
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

function programClicked(code,year,lp) {
	$.ajax({

		url : '/GetProgram/getCourses',
		data : {
			code : code,
			startyear : year,
			startperiod : lp
		},
		success : function(response) {
			// Should call a function in the canvas with the response
			console.log(JSON.stringify(response));
			$('#debug').html("response : " + response.name + ", courses: " +response.Courses.length);
		}
	}).fail(function (response) {
		console.log(response);
	});
	
}

function kcClicked() {
	

}