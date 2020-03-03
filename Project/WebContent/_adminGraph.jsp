<script src="https://unpkg.com/konva@4.1.6/konva.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin_graph_javascript/kAnvas.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin_graph_javascript/types.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin_graph_javascript/functions.js"></script>


<style>

#GKcontainer {
    background-color: #e0e0e0;
    width: 80%;
    height: 80%;
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translateX(-50%) translateY(-50%);
    display: none;  /* This must be set to flex when visible. */
    flex-flow: column;
    overflow: auto;
    border-radius: 50pt;
}

#closeBtn {
    position: fixed;
    top: 0;
    right: 50pt;
    width:50pt;
}
#saveBtn {
    position: fixed;
    top: 0;
    right: 150pt;
    width: 50pt;
}

button {
	min-width:0;
}

.container-fluid button {
	min-width: 300;
}

</style>

<div class="container" id="GKcontainer">
	 
	<div id="g_container" class="container" style="overflow-x: scroll;overflow-y: scroll; max-width: 2000px; max-height: 800px;"></div>
	 <div id="lost_kcs"></div> 
	<div id="command_log" style:"display:none;"></div>   
	<button id="saveBtn" class="btn btn-primary" onclick="save()">Save</button> 
	<button id="closeBtn" class="btn btn-danger" onclick="hideit()">Cancel</button>
</div>
    
<script>


function hideit() {
	document.getElementById("command_log").innerHTML = "";
	document.getElementById("GKcontainer").style.display = "none";
	window.data = new Object();
	window.initialized = false;
}

function save() {
	
	
	
	var str = document.getElementById("command_log").innerHTML;
	
	var arr = str.split(";;;");
	
	console.log(arr);
	
	for(var i = 0; i < arr.length -1; i++) {
		t = arr.split(";");
		if(t[0] == "ADD") {
			
		} else if(t[0] == "DELETE") {
			
		}
	}
	
	
	window.data = new Object();
	window.initialized = false;
	hideit();
}

function GKaddCourse() {
	
}

function GKdeleteCourse() {
	
}


function graphModifyCourse() {
	var input = prompt("programCode;year;lp");
	
	var data = input.split(";");
	
	if(data.length == 3) {
	
		$.ajax({
			url : '/GetProgram/getCourses',
			type : "GET",
			data : {
				code : data[0],
				startyear : data[1],
				startperiod : data[2]
			},
			success : function(response) {
				//document.getElementById("log").innerHTML += "Read in program " + data[0] + "</br>";
				//document.getElementById("output").innerHTML += response + "</br>";
				
				console.log("type of response = " + typeof response);
				readIn(response);
			}

		});
		
		
	} else {
		document.getElementById("log").innerHTML += "Invalid input : " + input  + "</br>";
	}
	
}

</script>