
/**
 * 
 * 	Function that is called when a teacher want to edit a course
 * 
 * 		Should call a function in the canvas with the response
 * 
 * @param code automatically set in .jsp file
 * @param year automatically set in .jsp file
 * @param lp automatically set in .jsp file
 * @returns
 */
function courseClickedEdit(code, year, lp) {

	$.ajax({

		url : 'GetCourse/byCodeYearLP',
		data : {
			courseCode : code,
			year : year,
			lp : lp
		},
		success : function(response) {
			//DIVS requiredKCs description developedKCs title_content

			var name = response.name;
			var code = response.courseCode;
			var examiner = response.examiner;
			var credits = response.credits;
			
			document.getElementById("title_content").innerHTML = name + " " + code + " " + examiner + " " + credits;

			var description = response.description;
			document.getElementById("description").value = description;
			
			var developedKCs = response.Developed;
			document.getElementById("developedKCs").innerHTML = "";
			/*for(i in developedKCs) {
				document.getElementById("developedKCs").innerHTML += coolKCbox(developedKCs[i]);
			}*/
			developedKCs.forEach( function(item) {
				document.getElementById("developedKCs").innerHTML += coolKCbox(item);
			});
			
			var requiredKCs = response.Required;
			document.getElementById("requiredKCs").innerHTML = "";
			for(i in requiredKCs) {
				document.getElementById("requiredKCs").innerHTML += coolKCbox(requiredKCs[i]);
			}
			
			
		}
	});
}

function coolKCbox(KC) {
	KC = JSON.parse(KC);
	var s = "<div class=\"\" onclick=\"dropKC('"+ KC.name+"', '"+KC.taxonomyLevel +"')\" id=\""+KC.name+"_"+KC.taxonomyLevel+"\">";
	s += KC.name;
	s += ": ";
	s += KC.taxonomyLevel;
	s += "</hr></div>";
	return s;
}
