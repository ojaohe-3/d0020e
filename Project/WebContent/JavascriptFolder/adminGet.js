/**
 * 
 */

function get_users() {
	$.ajax({
		url : 'getAllUsers'
	
		,
		success : function(response) {
			
			alert("Users :\n" + response);
		}

	});
}

function get_courses_in_program() {
	var input = prompt("programCode;year;lp");
	
	var data = input.split(";");
	
	if(data.length == 3) {
	
		$.ajax({
			url : 'getCoursesInProgram',
			data : {
				code : data[0],
				year : data[1],
				lp : data[2]
			},
			success : function(response) {
				alert("Courses :\n" + response);
			}

		});
		
	} else {
		alert("invalid input " + input);
	}
	
}