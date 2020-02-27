/**
 * 
 */

function get_users() {
	$.ajax({
		url : 'getAllUsers'
	
		,
		success : function(response) {
			
			alert("Users :\n" + response)
		}

	});
}