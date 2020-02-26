/**
 * Search for kc to add as developed and required for a course
 * 
 */
$(document).ready(function() {
$('#searchRequiredKC').on('keyup', function() {
		if($("#searchRequiredKC").val().length > 3) {
				$.ajax({

					url : 'GetKCs/FilterByName',
					data : {
						filter : $("#searchRequiredKC").val()
					},
					success : function(response) {

						var s = "<div class=\"SearchResultContainer\">";
						for(i in response) {
							s += "<div class=\"SearchResult\" id=\"kc\" onclick=\"addRequiredKC('"+ response[i].name +"', '"+ response[i].taxonomyLevel +"')\"> <b>" + response[i].name + "</b> - " + response[i].taxonomyLevel;
							s += "<div class=\"SearchResultExpander\"> Description : " + response[i].generalDescription +"</div></div>";
						}
						s += "</div>";
						$('#kcRequired_search_results').html('');
						$('#kcRequired_search_results').html(s);
					}

				});			
		} else {
			$('#kcRequired_search_results').html('');
		} 
		
	});
$('#searchDevelopedKC').on('keyup', function() {
	if($("#searchDevelopedKC").val().length > 3) {
			$.ajax({

				url : 'GetKCs/FilterByName',
				data : {
					filter : $("#searchDevelopedKC").val()
				},
				success : function(response) {

					var s = "<div class=\"SearchResultContainer\">";
					for(i in response) {
						s += "<div class=\"SearchResult\" id=\"kc\" onclick=\"addDevelopedKC('"+ response[i].name +"', '"+ response[i].taxonomyLevel +"')\"> <b>" + response[i].name + "</b> - " + response[i].taxonomyLevel;
						s += "<div class=\"SearchResultExpander\"> Description : " + response[i].generalDescription +"</div></div>";
					}
					s += "</div>";
					$('#kcDeveloped_search_results').html('');
					$('#kcDeveloped_search_results').html(s);
				}

			});			
	} else {
		$('#kcDeveloped_search_results').html('');
	} 
	
});
});

function addRequiredKC(name, taxonomyLevel) {
	document.getElementById("requiredKCs").innerHTML += "<div id=\"" + name + "_" + taxonomyLevel + "\" onclick=\"dropKC('"+ name +"', '"+ taxonomyLevel +"')\"> " + name + " " + taxonomyLevel + "</div>";
}

function addDevelopedKC(name, taxonomyLevel) {
	document.getElementById("developedKCs").innerHTML += "<divid=\"" + name + "_" + taxonomyLevel + "\" onclick=\"dropKC('"+ name +"', '"+ taxonomyLevel +"')\"> " + name + " " + taxonomyLevel + "</div>";
}
/*
function dropRequiredKC(name, taxonomyLevel) {
	this.parentNode.removeChild(this); 
}

function dropDevelopedKC(name, taxonomyLevel) {
	
}*/

function dropKC(name, taxonomyLevel) {
	 document.getElementById(name+"_"+taxonomyLevel).remove();
}