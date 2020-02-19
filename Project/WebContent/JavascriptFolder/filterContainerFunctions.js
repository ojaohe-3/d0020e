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

function programClicked() {
	
}

function kcClicked() {
	

}

function topicClicked() {
	

}