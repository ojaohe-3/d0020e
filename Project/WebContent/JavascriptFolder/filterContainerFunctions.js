/**
 * 
 */


function courseClicked(code, year, lp) {
	
	$.ajax({

		url : 'GetCourse/byCodeAndDate',
		data : {
			courseCode : code,
			year : year,
			lp : lp
		},
		success : function(response) {
			// Should call a function in the canvas with the response
			$('#debug').html(response);
		}
	});
}

function programClicked() {
	
}

function kcClicked() {
	

}