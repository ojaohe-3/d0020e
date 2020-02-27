/**
 * 
 */

function get_users() {
	$.ajax({
		url : 'getAllUsers'
	
	}.success : function(response) {
			document.getElementById("log").innerHTML += "CREATE USER " + data[0];
			document.getElementById("output").innerHTML += response + "</br>";
		}

	});
}