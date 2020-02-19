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
	var input = prompt("username;courseCode;LP(1-4);year");
	
	var data = input.split(";");
	
	if(data.length == 4 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "SET_RELATION_TO_COURSE",
				username : data[0],
				courseCode : data[1],
				lp : data[2],
				year : data[3]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE REALATION BETWEEN USER "+ data[0] + " and the course "+ data[1] +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}

}

/*
 * Remove access to modify a course from user
 */
function user_delete_course_relation() {
	var input = prompt("username;courseCode;LP(1-4);year");
	
	var data = input.split(";");
	
	if(data.length == 4 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "USER",
				method : "DELETE_RELATION_TO_COURSE",
				username : data[0],
				courseCode : data[1],
				lp : data[2],
				year : data[3]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "DELETE REALATION BETWEEN USER "+ data[0] + " and the course "+ data[1] +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}

}


// END USER FUNCTIONS


// COURSE FUNCTIONS
/*
 * Create a new course
 */
function course_create() {
	var input = prompt("coursename;coursecode;LP(1-4);year;credits;examiner;description");
	
	var data = input.split(";");
	
	if(data.length == 7 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "COURSE",
				method : "CREATE",
				courseName : data[0],
				courseCode : data[1],
				lp : data[2],
				year : data[3],
				credits : data[4],
				examiner : data[5],
				description : data[6]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE COURSE " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
/*
 * Modify Course
 * 
 * Don't know how to do this in a gr8 way
 */
function course_modify() {
	var input = prompt("oldCourseCode;oldLP;oldYear;newCourseName;newCourseCode;newLP;newYear;newExaminer");
	
	var data = input.split(";");
	
	if(data.length == 8 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "COURSE",
				method : "MODIFY",
				oldCourseCode : data[0],
				oldLP : data[1],
				oldYear : data[2],
				newCourseName : data[3],
				newCourseCode : data[4],
				newLP : data[5];
				newYear : data[6],
				newExaminer : data[7]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "MODIFY COURSE " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

/*
 * Delete Course
 */
function course_delete() {
	var input = prompt("courseCode;lp(1-4);year");
	
	var data = input.split(";");
	
	if(data.length == 3 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "COURSE",
				method : "DELETE",
				courseCode : data[0],
				lp : data[1],
				year : data[2]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "DELETE COURSE with code " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

// END COURSE FUNCTION

// KC FUNCTIONS
/*
 * Create a new course
 */
function kc_create() {
	var input = prompt("name;generalDescription;Topic;taxonomyDescription1;taxonomyDescription2;taxonomyDescription3");
	
	var data = input.split(";");
	
	if(data.length == 6){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "KC",
				method : "CREATE",
				name : data[0],
				generalDescription : data[1],
				topic : data[2],
				taxonomyDesc1 : data[3],
				taxonomyDesc2 : data[4],
				taxonomyDesc3 : data[5]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE KC " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
/*
 * Modify general description for KC
 */
function kc_modify_general() {
	var input = prompt("name;newDescription");
	
	var data = input.split(";");
	
	if(data.length == 2 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "KC",
				method : "MODIFY_GENERAL_DESC",
				name : data[0],
				generalDescription : data[1]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "MODIFY GENERAL DESCRIPTION FOR KC " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

/*
 * Modify taxonomy description
 */
function kc_modify_taxonomy() {
	var input = prompt("name;taxonomyLevel;newTaxonomyDescription");
	
	var data = input.split(";");
	
	if(data.length == 3 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "KC",
				method : "MODIFY_TAXONOMY_DESC",
				name : data[0],
				taxonomyLevel : data[1],
				newtaxonomyDesc : data[2]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "MODIFY TAXONOMY DESCRIPTION FOR KC " + data[0]  + "WITH taxonomyLevel : " + data[1] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

/*
 * Delete KC
 */
function kc_delete() {
	var input = prompt("name;taxonomyLevel");
	
	var data = input.split(";");
	
	if(data.length == 2 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "KC",
				method : "DELETE",
				name : data[0],
				taxonomyLevel : data[1]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "DELETE KCs with name " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

// END KC



// BEGIN PROGRAM 

function program_create() {
	var input = prompt("name;code;startYear;startLP;credits;description");
	
	var data = input.split(";");
	
	if(data.length == 6){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "CREATE",
				name : data[0],
				code : data[1],
				startYear : data[2],
				startLP : data[3],
				credits : data[4],
				description : data[5]
				
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE PROGRAM " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

function program_create_specialization() {
	var input = prompt("name;programCode;startYear;startLP;credits;description");
	
	var data = input.split(";");
	
	if(data.length == 6){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "CREATE",
				name : data[0],
				programCode : data[1],
				startYear : data[2],
				startLP : data[3],
				credits : data[4],
				description : data[5]
				
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE SPECIALIZATION " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

function program_delete() {
	var input = prompt("code;year;lp");
	
	var data = input.split(";");
	
	if(data.length == 3){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "DELETE",
				code : data[0],
				year : data[1],
				lp : data[2]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE PROGRAM WITH CODE " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

function program_copy_from_year() {
	var input = prompt("code;fromYear;fromLP;toYear;toLP");
	
	var data = input.split(";");
	
	if(data.length == 5){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "COPY_FROM_YEAR",
				code : data[0],
				fromYear : data[1],
				fromLP : data[2],
				toYear : data[3],
				toLP : data[4]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE COPY OF PROGRAM " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

function program_add_course() {
	var input = prompt("programCode;programStartYear;programStartLP;courseCode;courseYear;courseLP");
	
	var data = input.split(";");
	
	if(data.length == 6){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "ADD_COURSE",
				programCode : data[0],
				programStartYear : data[1],
				programSartLP : data[2],
				courseCode : data[3],
				courseYear : data[4],
				courseLP : data[5]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "ADD COURSE " + data[3] + " TO PROGRAM " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}





