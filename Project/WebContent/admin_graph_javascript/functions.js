/**
 * 
 */
function readIn(dat) {
    
    //var dat = '{"code":"H\xf6gskoleingenj\xf6r Datateknik","year":2017,"lp":"ONE","credits":180,"name":2017,"description":"Ett program f\xf6r kodknackare","Courses":[{"lp":"ONE","Required":[],"year":2019,"courseCode":"D0029E","name":"Dator- och n\xe4tverks\xe4kerhet","examiner":"Evgeny Osipov","description":"Seedlabs","credit":7.5,"Developed":[]},{"lp":"TWO","Required":[],"year":2019,"courseCode":"D0020E","name":"Projekt i daatteknik","examiner":"Ulf Bodin","description":"F\xe5 ett roligt projekt som du ska g\xf6ra","credit":7.5,"Developed":[]},{"lp":"THREE","Required":[],"year":2020,"courseCode":"D0021E","name":"N\xe4tverk och mobilitet","examiner":"Christer \xc5hlund","description":"L\xe4r dig vad som h\xe4nder n\xe4r du byter n\xe4tverk","credit":7.5,"Developed":[]},{"lp":"FOUR","Required":[],"year":2020,"courseCode":"X0001E","name":"Examensarbete","examiner":"Josef Hallberg","description":"Snart \xe4r du klar, k\xe4mpa p\xe5!","credit":7.5,"Developed":[]},{"lp":"ONE","Required":[],"year":2018,"courseCode":"D0036D","name":"N\xe4tverksprogrammering","examiner":"Robert Br\xe4nnstr\xf6m","description":"N\xe4tverk mm","credit":7.5,"Developed":[]},{"lp":"TWO","Required":[],"year":2019,"courseCode":"D0018E","name":"Databasteknik","examiner":"Olov Sch\xe9len","description":"Bygg en hemsida och anv\xe4nd databaser","credit":7.5,"Developed":[]},{"lp":"THREE","Required":[],"year":2019,"courseCode":"M0039M","name":"Matematik III - Differentialekvationer, komplexa tal och transformteori ","examiner":"Ove Edlund","description":"DIFFISAR!","credit":7.5,"Developed":[]},{"lp":"FOUR","Required":[{"taxonomyLevel":2,"taxonomyDescription":"More advanced","name":"Objektorienterad programmering","generalDescription":"Cool"}],"year":2019,"courseCode":"D0002E","name":"Datorkommunikation","examiner":"Evgeny Osipov","description":"Cisco packet tracer","credit":7.5,"Developed":[]},{"lp":"ONE","Required":[{"taxonomyLevel":2,"taxonomyDescription":"More advanced","name":"Objektorienterad programmering","generalDescription":"Cool"}],"year":2018,"courseCode":"D0013E","name":"Mikrodatorteknik","examiner":"Per lindgren","description":"Assembly","credit":7.5,"Developed":[]},{"lp":"TWO","Required":[],"year":2018,"courseCode":"M0034M","name":"Matematik II - Integralkalkyl och linj\xe4r algebra ","examiner":"Ove Edlund","description":"Integraler och matriser","credit":7.5,"Developed":[]},{"lp":"THREE","Required":[],"year":2019,"courseCode":"D0003E","name":"Realtidssystem ","examiner":"Fredrik Bengtsson","description":"Hur man programmerar realtidssystem, C","credit":7.5,"Developed":[]},{"lp":"FOUR","Required":[],"year":2018,"courseCode":"M0038M","name":"Matematik 1 - Differentialkalkyl","examiner":"Ove Edlund","description":"Derivata och s\xe5nt","credit":7.5,"Developed":[]},{"lp":"ONE","Required":[],"year":2017,"courseCode":"D0015E","name":"Datateknik och ingenj\xf6rsvetenskap","examiner":"H\xe5kan Jonsson","description":" H\xe5kan i sitt \xe4sse, LaTex mm","credit":7.5,"Developed":[]},{"lp":"TWO","Required":[],"year":2018,"courseCode":"D0012E","name":"Algoritmer och Datastrukturer","examiner":"Jingsen Chen","description":"Cut in half, throw away","credit":7.5,"Developed":[]},{"lp":"THREE","Required":[],"year":2018,"courseCode":"R0005N","name":"Grundkurs i projekt- och industriell ekonomi","examiner":"Anders Sandberg","description":"Ekonomi","credit":7.5,"Developed":[]},{"lp":"FOUR","Required":[],"year":2018,"courseCode":"D0011E","name":"Digitalteknik","examiner":"Ulf Bodin","description":"MIPS","credit":7.5,"Developed":[]},{"lp":"ONE","Required":[{"taxonomyLevel":1,"taxonomyDescription":"L\xe4ra sig grunderna","name":"LaTeX","generalDescription":"Anv\xe4nda LaTeX f\xf6r att skriva rapporter"},{"taxonomyLevel":2,"taxonomyDescription":"More advanced","name":"Objektorienterad programmering","generalDescription":"Cool"}],"year":2017,"courseCode":"D0009E","name":"Introduktion till programmering","examiner":"Bson","description":"L\xe4r dig Python f\xf6rfan","credit":7.5,"Developed":[{"taxonomyLevel":2,"taxonomyDescription":"More advanced","name":"Objektorienterad programmering","generalDescription":"Cool"}]},{"lp":"TWO","Required":[],"year":2017,"courseCode":"M0009M","name":"Diskret matematik","examiner":"Stefan Eriksson","description":"Anv\xe4ndbara verktyg f\xf6r datapersoner","credit":7.5,"Developed":[]},{"lp":"THREE","Required":[{"taxonomyLevel":1,"taxonomyDescription":"L\xe4ra sig grunderna","name":"LaTeX","generalDescription":"Anv\xe4nda LaTeX f\xf6r att skriva rapporter"}],"year":2018,"courseCode":"D0010E","name":"Objektorienterad profgramering och design","examiner":"H\xe5kan Jonsson","description":"Java OOP","credit":7.5,"Developed":[{"taxonomyLevel":2,"taxonomyDescription":"More advanced","name":"Objektorienterad programmering","generalDescription":"Cool"}]},{"lp":"TWO","Required":[],"year":2017,"courseCode":"F0004T","name":"Fysik 1","examiner":"Magnus Gustavsson","description":"Mekanik och termodynamik","credit":7.5,"Developed":[]}]}';

	console.log(dat);
	
    if(window.initialized == false) {
        window.data = dat;//JSON.parse(dat);
    }
    
    window.program = new Program(dat.name, dat.code, dat.year, dat.lp);
    console.log(program);
    
    
    window.initialized = true;
    var canv = new KAnvas();
    canv.handleResponse(window.data);
    document.getElementById("GKcontainer").style.display = "block";
}

window.initialized = false;


function createCourseBox(name, code, size, x, y, year,lp) {

    var kGroup = new Konva.Group({
        x: x,
        y: y,
        draggable: false,
    });

    var text = new Konva.Text({
        fontSize: 10,
        width: size,
        fontFamily: 'Calibri',
        text: name + "\n" + code,
        fill: 'black',
        padding: 10
    });
    
    var but = new Konva.Circle({
        width: size / 8,
        fill: 'red',
        x: size,
        y: 0
    });

    var textX = new Konva.Text({
        fontSize: 15,
        //width: size / 8,
        fontFamily: 'Calibri',
        text: 'X',
        fill: 'black',
        x: size - 4,
        y: - 8
    });
    
    var rect = new Konva.Rect({
        width: size,
        height: size,
        fill: '#aaf',
        stroke: 'black',
        strokeWidth: 3
    });
    
    textX.on('click', function(evt) {
        window.data.Courses.splice(getIndexOfCourse(window.data.Courses, code), 1);
        
        document.getElementById("command_log").innerHTML += "DELETE;"+code+";"+year+";"+lp+";;;";
        
        kGroup.remove();
        readIn(window.data);
    });

    kGroup.add(rect).add(text);
    kGroup.add(but).add(textX);
    return kGroup;
}

function addCourseBox(year, lp, size, x, y) {

    
    var kGroup = new Konva.Group({
        x: x,
        y: y,
        draggable: false,
    });

    var rect = new Konva.Rect({
        width: size,
        height: size,
        fill: '#90ee90',
        stroke: 'black',
        strokeWidth: 2
    });
    
    var text = new Konva.Text({
        fontSize: 14,
        width: size,
        fontFamily: 'Calibri',
        text: "Add a new Course",
        fill: 'black',
        padding: 10
    });
    
    rect.on('click', function(evt) {
    	
    	if(lp == 1) {
    		lp = "ONE";
    	} else if(lp == 2) {
    		lp = "TWO";
    	} else if (lp == 3) {
    		lp = "THREE";
    	} else if (lp == 4) {
    		lp = "FOUR"
    	}
    	
        getNewCourse(year, lp, kGroup);

    });
    
    kGroup.add(rect).add(text);
    return kGroup;
}

function callback(year, lp, response, kGroup) {
	console.log(response);
	if(response !== "undefined") {
    	document.getElementById("command_log").innerHTML += "ADD;" + response.courseCode + ";" + year + ";"+ lp + ";;;";
        kGroup.remove();
        
        window.data.Courses.push(response);
        
        readIn(window.data);
        
    } else {
    	alert("Course not found in year " + year + " lp " + lp);
    }
}

function getNewCourse(year, lp, kGroup) {
	var code = prompt("Course Code in year " + year + " and LP " + lp);
	
	$.ajax({

		url : 'GetCourse/byCodeYearLP',
		data : {
			courseCode : code,
			year : year,
			lp : lp
		},
		success : function(response) {
			//console.log(response);
			callback(year, lp, response, kGroup);
		}
	});
	
}


function createLine(from, to, size, yChannel, kAnvas) {
    
    var fromX = from[0] + size;
    var fromY = from[1] + 12 + (12 * occurencies(kAnvas.xKCposxD, from[0]));
    kAnvas.xKCposxD.push(from[0]);
    
    var redLine = new Konva.Line({
        points: [fromX , fromY ,fromX + 35, fromY, fromX + 35, yChannel, to[0] + 3 /*+ size/ 2*/, yChannel, to[0] + 3/*+ size / 2*/, to[1] + size / 2],
        stroke: 'red',
        strokeWidth: 3,
        lineCap: 'round',
        lineJoin: 'round'
    });
    
    redLine.on('mouseover', function(e) {
        e.target.stroke('green');
        e.target.draw();
    });
    
    redLine.on('mouseout', function(e) {
        e.target.stroke('red');
        e.target.draw();
    });
    
    return redLine;;
}

/*
function createLineBetweenCourses(fromCourse, toCourse) {
    var redLine = new Konva.Line({
        points: [1,1,100,100],
        stroke: 'red',
        strokeWidth: 3,
        lineCap: 'round',
        lineJoin: 'round'
    });
    return redLine;
}
*/

function occurencies(array, value) {
    var n = 0;
    for(var i = 0; i < array.length; i++) {
        if(array[i] == value) {
            n++;
        }
    }
    return n;
}

function kcExistsIn(array, name, taxLevel) {
    for(var i = 0; i < array.length; i++) {
        if(array[i][0] == name && array[i][1] == taxLevel) {
            return true;
        } 
    }
    return false;
}

function getIndexOfKC(array, name, taxLevel){
    for(var i = 0; i < array.length; i++) {
        if(array[i][0] == name && array[i][1] == taxLevel) {
            return i;
        } 
    }
} 

function getIndexOfCourse(array, code) {
    for(var i = 0; i < array.length; i++) {
        if(array[i].courseCode == code) {
            
            return i;
        }
    }
    console.log("WTF, undefined");
}

