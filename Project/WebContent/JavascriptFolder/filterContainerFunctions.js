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
			
		}
	});
}

function programClicked() {
	
}

function kcClicked() {
	

}