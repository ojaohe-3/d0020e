/*
 *  Creating a user
 */

function user_create() {
	var input = prompt("username;password");
	
	var data = input.split(";");
	
	if(data.lenght == 2) {
	
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "CREATE",
				username : data[0],
				password : data[1]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE USER " + data[0];
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input : " + input  + "</br>";
	}
	
}

/*
 * Deleteing a user
 */

function user_delete() {
	var input = prompt("username");

	$.ajax({
		url : 'admin',
		type : "POST",
		data : {
			head : "USER",
			method : "DELETE",
			username : input
		},
		success : function(response) {
			document.getElementById("log").innerHTML += "DELETE USER " + input;
			document.getElementById("output").innerHTML += response + "</br>";
		}

	});
		
	
}

/*
 *  Change password for user
 */

function user_modify_password() {
	var input = prompt("username;newPassword");
	
	var data = input.split(";");
	
	if(data.length == 2 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "MODIFY_PASSWORD",
				username : data[0],
				newpassword : data[1]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CHANGE PASSWORD FOR USER : " + data[0] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}

}

/*
 *  Change username for user
 */
function user_modify_username() {
	var input = prompt("username;newusername");
	
	var data = input.split(";");
	
	if(data.length == 2 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "MODIFY_USERNAME",
				username : data[0],
				newusername : data[1]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CHANGE USERNAME FOR USER : " + data[0] +  "to :" + data[1] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}

}


/*
 * Give a user access to modify a course
 */

function user_set_course_relation() {
	var input = prompt("username;newusername");
	
	var data = input.split(";");
	
	if(data.length == 2 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "MODIFY_USERNAME",
				username : data[0],
				newusername : data[1]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CHANGE USERNAME FOR USER : " + data[0] +  "to :" + data[1] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}

}

