/*
 *  Creating a user
 */

function user_create() {
	var input = prompt("username;password");
	
	var data = input.split(";");
	
	
	
	if(data.length == 2) {
	
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
				console.log("type of response = " + typeof response);
				readIn(response);
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
	var input = prompt("coursename;coursecode;LP(1-4);year;credits;examiner;description;topic");
	
	var data = input.split(";");
	
	if(data.length == 8 ){
		
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
				credits : parseFloat(data[4]),
				examiner : data[5],
				description : data[6],
				topic : data[7]
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
				newLP : data[5],
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
/*
 * Add Topic to a course 
 */
function course_add_topic() {
	var input = prompt("courseCode;lp(1-4);year;topic");
	
	var data = input.split(";");
	
	if(data.length == 4 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "COURSE",
				method : "ADD_TOPIC",
				courseCode : data[0],
				lp : data[1],
				year : data[2],
				topic : data[3]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "Added topic to course " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
function course_delete_topic() {
	var input = prompt("courseCode;lp(1-4);year;topic");
	
	var data = input.split(";");
	
	if(data.length == 4 ){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "COURSE",
				method : "DELETE_TOPIC",
				courseCode : data[0],
				lp : data[1],
				year : data[2],
				topic : data[3]
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "Removed topic from course " + data[0]  +"</br>";
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
 * Create a new kc
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
/*
 * Create a new program
 */
function program_create() {
	var input = prompt("name;code;startYear;startLP;credits;reading_periods;description");
	
	var data = input.split(";");
	
	if(data.length == 7){
		
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
				credits : parseFloat(data[4]),
				readingPeriods : data[5],
				description : data[6]
				
				
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
/*
 * create a new specialization
 */
function program_create_specialization() {
	var input = prompt("name;programCode;programYear;programLP;specializationYear;specializationLP;description;credits;readingPeriods");
	
	var data = input.split(";");
	
	if(data.length == 9){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "CREATE_SPECIAL",
				name : data[0],
				programCode : data[1],
				startProgramYear : data[2],
				startProgramLP : data[3],
				specYear : data[4],
				specLP : data[5],			
				description : data[6],
				credits : parseFloat(data[7]),
				readingPeriods : data[8]
				
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
/*
 * Delete a program
 */
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
				document.getElementById("log").innerHTML += "DELETE PROGRAM WITH CODE " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

/*
 * create a copy of a program with a new year and a new starting LP
 */
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

/*
 * Create a copy of a specialization with a new year and a new lp
 */
function program_copy_from_year_special() {
	var input = prompt("name;code;fromYear;fromLP;toYear;toLP");
	
	var data = input.split(";");
	
	if(data.length == 6){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "COPY_FROM_YEAR_SPECIAL",
				name : data[0],
				code : data[1],
				fromYear : data[2],
				fromLP : data[3],
				toYear : data[4],
				toLP : data[5]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "CREATE COPY OF SPECIALIZATION " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
/*
 * add a program to a course
 */
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
				programStartLP : data[2],
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
/*
 * Change parameters of a program
 */
function program_modify() {
	var input = prompt("oldCode;oldStartYear;oldStartLP;newName;newCode;newStartYear;newStartLP;newDescription;newCredits");
	
	var data = input.split(";");
	
	if(data.length == 10){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "MODIFY",
				oldCode : data[0],
				programStartYear : data[1],
				programStartLP : data[2],
				newName : data[3],
				newCode : data[4],
				newStartYear : data[5],
				newStartLP : data[6],
				newDescription : data[7],
				newCredits : parseFloat(data[8]),
				readingPeriods : data[9]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "MODIFY PROGRAM" + data[3] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
/*
 * change parameters of a specialization
 */
function program_modify_special() {
	var input = prompt("oldName;oldStartYear;oldStartLP;newName;newCode;newStartYear;newStartLP;newDescription;newCredits");
	
	var data = input.split(";");
	
	if(data.length == 10){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "MODIFY_SPECIAL",
				oldName : data[0],
				programStartYear : data[1],
				programStartLP : data[2],
				newName : data[3],
				newCode : data[4],
				newStartYear : data[5],
				newStartLP : data[6],
				newDescription : data[7],
				newCredits : parseFloat(data[8]),
				readingPeriods : data[9]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "MODIFY SPEICALIZATION" + data[3] + "</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
/*
 * Delete a specialization
 */
function program_delete_special() {
	var input = prompt("name;year;lp");
	
	var data = input.split(";");
	
	if(data.length == 3){
		
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "DELETE_SPECIAL",
				name : data[0],
				year : data[1],
				lp : data[2]
				
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "DELETE SPECIALIZATION WITH NAME " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}
	
function specialization_add_course() {
	var input = prompt("specializationName;programCode;specializationtartYear;specializationStartLP;courseCode;courseYear;courseLP");
		
	var data = input.split(";");
		
	if(data.length == 7){
			
		$.ajax({
			url : 'admin',
			type : "POST",
			data : {
				head : "PROGRAM",
				method : "ADD_COURSE_SPECIALIZATION",
				specializationName : data[0],
				programCode : data[1],
				specializationtartYear : data[2],
				specializationtartLP : data[3],
				courseCode : data[4],
				courseYear : data[5],
				courseLP : data[6]
					
			},
			success : function(response) {
				document.getElementById("log").innerHTML += "ADD COURSE " + data[4] + " TO SPECIALIZATION " + data[0]  +"</br>";
				document.getElementById("output").innerHTML += response + "</br>";
			}

		});
			
	} else {
		document.getElementById("log").innerHTML += "Invalid input " + input + "</br>";
	}
}

// YeAH 800 rows in one File :pPPpPPpPppppPPPp

// This is the oneline version
// /* * Creating a user */ function user_create() { var input = prompt("username;password"); var data = input.split(";"); if(data.length == 2) { $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "CREATE", username : data[0], password : data[1] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE USER " + data[0]; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input : " + input + "</br>"; } } /* * Deleteing a user */ function user_delete() { var input = prompt("username"); $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "DELETE", username : input }, success : function(response) { document.getElementById("log").innerHTML += "DELETE USER " + input; document.getElementById("output").innerHTML += response + "</br>"; } }); } /* * Change password for user */ function user_modify_password() { var input = prompt("username;newPassword"); var data = input.split(";"); if(data.length == 2 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "MODIFY_PASSWORD", username : data[0], newpassword : data[1] }, success : function(response) { document.getElementById("log").innerHTML += "CHANGE PASSWORD FOR USER : " + data[0] + "</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Change username for user */ function user_modify_username() { var input = prompt("username;newusername"); var data = input.split(";"); if(data.length == 2 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "MODIFY_USERNAME", username : data[0], newusername : data[1] }, success : function(response) { document.getElementById("log").innerHTML += "CHANGE USERNAME FOR USER : " + data[0] + "to :" + data[1] + "</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Give a user access to modify a course */ function user_set_course_relation() { var input = prompt("username;courseCode;LP(1-4);year"); var data = input.split(";"); if(data.length == 4 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "SET_RELATION_TO_COURSE", username : data[0], courseCode : data[1], lp : data[2], year : data[3] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE REALATION BETWEEN USER "+ data[0] + " and the course "+ data[1] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Remove access to modify a course from user */ function user_delete_course_relation() { var input = prompt("username;courseCode;LP(1-4);year"); var data = input.split(";"); if(data.length == 4 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "USER", method : "DELETE_RELATION_TO_COURSE", username : data[0], courseCode : data[1], lp : data[2], year : data[3] }, success : function(response) { document.getElementById("log").innerHTML += "DELETE REALATION BETWEEN USER "+ data[0] + " and the course "+ data[1] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } // END USER FUNCTIONS // COURSE FUNCTIONS /* * Create a new course */ function course_create() { var input = prompt("coursename;coursecode;LP(1-4);year;credits;examiner;description"); var data = input.split(";"); if(data.length == 7 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "COURSE", method : "CREATE", courseName : data[0], courseCode : data[1], lp : data[2], year : data[3], credits : parseFloat(data[4]), examiner : data[5], description : data[6] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE COURSE " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Modify Course * */ function course_modify() { var input = prompt("oldCourseCode;oldLP;oldYear;newCourseName;newCourseCode;newLP;newYear;newExaminer"); var data = input.split(";"); if(data.length == 8 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "COURSE", method : "MODIFY", oldCourseCode : data[0], oldLP : data[1], oldYear : data[2], newCourseName : data[3], newCourseCode : data[4], newLP : data[5], newYear : data[6], newExaminer : data[7] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY COURSE " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Modify a course */ function course_modify() { var input = prompt("oldCourseCode;oldLP,oldYear;newCourseName;newCourseCode;newLP;newYear;newExaminer"); var data = input.split(";"); if(data.length == 8 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "COURSE", method : "MODIFY", oldCourseCode : data[0], oldLP : data[1], oldYear : data[2], newCourseName : data[3], newCourseCode : data[4], newLP : data[5], newYear : data[6], newExaminer : data[7] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY COURSE with code " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Delete Course */ function course_delete() { var input = prompt("courseCode;lp(1-4);year"); var data = input.split(";"); if(data.length == 3 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "COURSE", method : "DELETE", courseCode : data[0], lp : data[1], year : data[2] }, success : function(response) { document.getElementById("log").innerHTML += "DELETE COURSE with code " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } // END COURSE FUNCTION // KC FUNCTIONS /* * Create a new kc */ function kc_create() { var input = prompt("name;generalDescription;Topic;taxonomyDescription1;taxonomyDescription2;taxonomyDescription3"); var data = input.split(";"); if(data.length == 6){ $.ajax({ url : 'admin', type : "POST", data : { head : "KC", method : "CREATE", name : data[0], generalDescription : data[1], topic : data[2], taxonomyDesc1 : data[3], taxonomyDesc2 : data[4], taxonomyDesc3 : data[5] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE KC " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Modify general description for KC */ function kc_modify_general() { var input = prompt("name;newDescription"); var data = input.split(";"); if(data.length == 2 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "KC", method : "MODIFY_GENERAL_DESC", name : data[0], generalDescription : data[1] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY GENERAL DESCRIPTION FOR KC " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Modify taxonomy description */ function kc_modify_taxonomy() { var input = prompt("name;taxonomyLevel;newTaxonomyDescription"); var data = input.split(";"); if(data.length == 3 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "KC", method : "MODIFY_TAXONOMY_DESC", name : data[0], taxonomyLevel : data[1], newtaxonomyDesc : data[2] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY TAXONOMY DESCRIPTION FOR KC " + data[0] + "WITH taxonomyLevel : " + data[1] + "</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Delete KC */ function kc_delete() { var input = prompt("name;taxonomyLevel"); var data = input.split(";"); if(data.length == 2 ){ $.ajax({ url : 'admin', type : "POST", data : { head : "KC", method : "DELETE", name : data[0], taxonomyLevel : data[1] }, success : function(response) { document.getElementById("log").innerHTML += "DELETE KCs with name " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } // END KC // BEGIN PROGRAM /* * Create a new program */ function program_create() { var input = prompt("name;code;startYear;startLP;credits;reading_periods;description"); var data = input.split(";"); if(data.length == 7){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "CREATE", name : data[0], code : data[1], startYear : data[2], startLP : data[3], credits : parseFloat(data[4]), readingPeriods : data[5], description : data[6] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE PROGRAM " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * create a new specialization */ function program_create_specialization() { var input = prompt("name;programCode;programYear;programLP;specializationYear;specializationLP;description;credits;readingPeriods"); var data = input.split(";"); if(data.length == 9){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "CREATE_SPECIAL", name : data[0], programCode : data[1], startProgramYear : data[2], startProgramLP : data[3], specYear : data[4], specLP : data[5], description : data[6], credits : parseFloat(data[7]), readingPeriods : data[8] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE SPECIALIZATION " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Delete a program */ function program_delete() { var input = prompt("code;year;lp"); var data = input.split(";"); if(data.length == 3){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "DELETE", code : data[0], year : data[1], lp : data[2] }, success : function(response) { document.getElementById("log").innerHTML += "DELETE PROGRAM WITH CODE " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * create a copy of a program with a new year and a new starting LP */ function program_copy_from_year() { var input = prompt("code;fromYear;fromLP;toYear;toLP"); var data = input.split(";"); if(data.length == 5){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "COPY_FROM_YEAR", code : data[0], fromYear : data[1], fromLP : data[2], toYear : data[3], toLP : data[4] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE COPY OF PROGRAM " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Create a copy of a specialization with a new year and a new lp */ function program_copy_from_year_special() { var input = prompt("name;code;fromYear;fromLP;toYear;toLP"); var data = input.split(";"); if(data.length == 6){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "COPY_FROM_YEAR_SPECIAL", name : data[0], code : data[1], fromYear : data[2], fromLP : data[3], toYear : data[4], toLP : data[5] }, success : function(response) { document.getElementById("log").innerHTML += "CREATE COPY OF SPECIALIZATION " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * add a program to a course */ function program_add_course() { var input = prompt("programCode;programStartYear;programStartLP;courseCode;courseYear;courseLP"); var data = input.split(";"); if(data.length == 6){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "ADD_COURSE", programCode : data[0], programStartYear : data[1], programStartLP : data[2], courseCode : data[3], courseYear : data[4], courseLP : data[5] }, success : function(response) { document.getElementById("log").innerHTML += "ADD COURSE " + data[3] + " TO PROGRAM " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Change parameters of a program */ function program_modify() { var input = prompt("oldCode;oldStartYear;oldStartLP;newName;newCode;newStartYear;newStartLP;newDescription;newCredits"); var data = input.split(";"); if(data.length == 10){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "MODIFY", oldCode : data[0], programStartYear : data[1], programStartLP : data[2], newName : data[3], newCode : data[4], newStartYear : data[5], newStartLP : data[6], newDescription : data[7], newCredits : parseFloat(data[8]), readingPeriods : data[9] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY PROGRAM" + data[3] + "</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * change parameters of a specialization */ function program_modify_special() { var input = prompt("oldName;oldStartYear;oldStartLP;newName;newCode;newStartYear;newStartLP;newDescription;newCredits"); var data = input.split(";"); if(data.length == 10){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "MODIFY_SPECIAL", oldName : data[0], programStartYear : data[1], programStartLP : data[2], newName : data[3], newCode : data[4], newStartYear : data[5], newStartLP : data[6], newDescription : data[7], newCredits : parseFloat(data[8]), readingPeriods : data[9] }, success : function(response) { document.getElementById("log").innerHTML += "MODIFY SPEICALIZATION" + data[3] + "</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } /* * Delete a specialization */ function program_delete_special() { var input = prompt("name;year;lp"); var data = input.split(";"); if(data.length == 3){ $.ajax({ url : 'admin', type : "POST", data : { head : "PROGRAM", method : "DELETE_SPECIAL", name : data[0], year : data[1], lp : data[2] }, success : function(response) { document.getElementById("log").innerHTML += "DELETE SPECIALIZATION WITH NAME " + data[0] +"</br>"; document.getElementById("output").innerHTML += response + "</br>"; } }); } else { document.getElementById("log").innerHTML += "Invalid input " + input + "</br>"; } } // YeAH 800 rows in one File :pPPpPPpPppppPPPp 

