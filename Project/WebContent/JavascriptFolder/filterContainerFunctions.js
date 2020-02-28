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

function programClicked(code,year,lp) {
	$.ajax({

		url : '/GetProgram/getCourses',
		data : {
			code : code,
			startyear : year,
			startperiod : lp
		},
		success : function(response) {
			// Should call a function in the canvas with the response
			generateCanvas(response);
			$('#debug').html("response : " + response.name + ", courses: " +response.Courses.length);
		}
	}).fail(function (response) {
		console.log(response);
	});
	
}

function kcClicked(name) {
	
	$.ajax({

		url : 'GetKC/byName',
		data : {
			name : name
		},
		success : function(response) {
			
			response = JSON.parse(JSON.stringify(response));
			
			//$('#displayKC').style.display = "block";
			
			console.log(response);
			console.log(response.ONE);
			
			var one = JSON.parse(response.ONE);
			var two = JSON.parse(response.TWO);
			var three = JSON.parse(response.THREE);
			
			
			console.log(one.name);
			
			$('#close_btn').css('display','block');
			$('#displayKC').css('display','block');
			
			
			$('#kc_name').html("<h1>" + one.name + "</h1>");
			$('#kc_general_desc').html("<h3>" + one.generalDescription + "</h3>");
			
			$('#kc_tax_desc_1').html("<p> Level 1 <br> " + one.taxonomyDescription + "</p>");
			$('#kc_tax_desc_2').html("<p> Level 2 <br> " + two.taxonomyDescription + "</p>");
			$('#kc_tax_desc_3').html("<p> Level 3 <br> " + three.taxonomyDescription + "</p>");
		}
	});
}

function hideKCdiv() {
	$('#displayKC').css('display','none');
	$('#close_btn').css('display','none');
}