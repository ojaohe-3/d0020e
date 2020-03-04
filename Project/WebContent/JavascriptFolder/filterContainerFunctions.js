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
			name : name,
		},
		success : function(response) {
			
			var name = JSON.parse(response.ONE);
			
			var generalDescription = JSON.parse(response.ONE);
			
			var firstTax = response.ONE;
			var firstTaxJ = JSON.parse(firstTax);

						
			var secondTax = response.TWO;
			var secondTaxJ = JSON.parse(secondTax);
			
			var thirdTax = response.THREE;
			var thirdTaxJ = JSON.parse(thirdTax);
			
			document.getElementById("KCInformation.KCName").innerHTML=name.name;
			document.getElementById("KCInformation.generalDescription").innerHTML=generalDescription.generalDescription;
			document.getElementById("KCInformation.taxonomyDescription1").innerHTML=firstTaxJ.taxonomyDescription;
			document.getElementById("KCInformation.taxonomyDescription2").innerHTML=secondTaxJ.taxonomyDescription;
			document.getElementById("KCInformation.taxonomyDescription3").innerHTML=thirdTaxJ.taxonomyDescription;

			
			
			console.log(response);
			console.log(response.ONE);

			document.getElementById("KCInformation").style.display = "flex";
			
		}
	});
}

function hideKCdiv() {
	$('#KCInformation').css('display','none');
	$('#close_btn').css('display','none');
}