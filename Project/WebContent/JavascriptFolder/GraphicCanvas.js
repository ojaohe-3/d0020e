
//get DPI
const dpi = window.devicePixelRatio;
//get canvas
const canvas = $('#graph').get(0);
//get context
const ctx = canvas.getContext('2d');
fix_dpi();

const screenHeight = window.screen.height * window.devicePixelRatio;
const screenWidth = window.screen.width * window.devicePixelRatio;

const width = 200;
const height = 220;
const period = new Map();
  period.set('ONE',0);
  period.set('TWO',0);
  period.set('THREE',0);
  period.set('FOUR',0);
//let courses =new Map(); // string as key and an array with all courses in one LP.
let REQ = new Map();
  //translations
let matrix=[1,0,0,1,0,0];
let oldMatrix = [];
//window.addEventListener("resize", drawCanvas);

let LPHashmap = new Map();

let largestLP = null;    // the biggest amount of courses in some LP.

canvas.addEventListener('move', (e)=>{
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    value.KCs.forEach((e)=>{
      if(e.isInside(mousePos,dpi)){
         e.onHover();
      }else
        e.onExit();
  });});
 drawCanvas();
},false);
canvas.addEventListener('click', function(evt) {
 /* var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if(value.isInside(mousePos,dpi)){
      //alert('Course Pressed!');
      //showCourseInfo(value.data);
      //value.setExtended();
    } else{
      console.log("mouse pressed on nothing!");
    }

  });*/
},false);

function getMousePos(canvas, event) {
  let rect = canvas.getBoundingClientRect();
  //let mousePos = getXY(event.clientX,event.clientY) // This ain't working, pal. I get a bunch of whacky numbers.
  return {
      x: event.clientX - rect.left, // Changed to clientX and clientY.
      y: event.clientY- rect.top
  };
}

function generateCanvas(data) {
  LPHashmap = new Map();
  let year = data.year;
  let offsetYear = 0;
  let currentYear = 0;
  let previousTimestamp = null;
  let currentLPObject = null;

  let courseContainer = document.getElementById("canvas_course_container");

  while(courseContainer.childElementCount > 0) {
    courseContainer.removeChild(courseContainer.firstElementChild);
  }

  data['Courses'].forEach(function (item, index,arr){
    // This is where the course is created.
    offsetYear = item.year-year;
    currentYear = item.year;
    let currentLPString = 0;
    if (item.lp === "TWO") {
      currentLPString = 1;
    } else if (item.lp === "THREE") {
      currentLPString = 2;
    } else if (item.lp === "FOUR") {
      currentLPString = 3;
    }


    // This creates a new year with LP and timestamps.
    // We have to create the timestamps in order since every timestamp
    // depend on the previous timestamp.
    if (!LPHashmap.has(currentYear + ";" + currentLPString)) {
      if (LPHashmap.has(currentYear + ";" + 3)) {
        currentLPObject = LPHashmap.get(currentYear + ";" + 3);
      }

      let newLP = null;
      for (let i = 0,year = item.year; i < 4; i++) {
        if (i == 2) {
          year ++;
        }
        let tempLPString = year + ";" + i;
        newLP = new CanvasLP(currentLPObject,width *1.2*LPHashmap.size, height*1.2, year,i);
        LPHashmap.set(tempLPString,newLP);
        currentLPObject = newLP;
      }

    }
    // all courses are sorted after year. I.e. no more courses from previous year will pop up.


    let courseLPIdentifier = item.year + ";" + currentLPString;
    currentLPObject = LPHashmap.get(courseLPIdentifier);

    // this creates the course and adds a course overlay.
    let course = currentLPObject.addCourse(item);
    course.setCourseOverlay(createCourseOverlay(item, course));
  });


  generateKcsInAllLPs();
  drawCanvas();
}

function createCourseOverlay( item, obj) {
  let x = obj.getX();
  let y = obj.getY();
  let courseDefinition = item["courseCode"]+item["year"]+item["lp"];

  /*
  "<div class='button'  onclick=\"toggleCourseKC()\" style='width:10px;height:10px;background-color: black;'></div>" +
      "<div class='course_dropdown"+courseDefinition+"_dropdown' style='width:100%; height:150px; background-color:white; display:none'>" +
   */

  /*
      document.getElementById("canvas_course_container").innerHTML +=
      "<div id='"+courseDefinition+"' style='left:"+x+"px; top:"+y+"px;' class='canvas_course'>" +
   */
  let course = document.createElement("div");
  /*course.innerHTML =
      "<div style='height: "+height+"px; width:100%; position:relative; background-color: white; display:inline-block;'><h1>" +item["name"]+"</h1>" +
      "<p>" +item["courseCode"]+"</p>" +
      "<p>"+item["examiner"]+"</p>" +
      "</div>";
*/
  //--------------- course ----------------
  course.setAttribute("style","left:"+x+"px; top:"+y+"px; width:"+width+"px;"+"px;");
  //course.setAttribute("style","left:"+x+"px; top:"+y+"px;");
  course.setAttribute("class","canvas_course");
  course.setAttribute("id", "" + x +";" + y);
  //course.setAttribute("onclick", "showCourseInfo()");


  //--------------- info ------------------
  let info = document.createElement("div");
  info.setAttribute("style","height: "+height+"px; width:100%; position:relative; background-color: white; display:inline-block;");
  info.innerHTML =
      "<h1>" +item["name"]+"</h1>" +
      "<p>" +item["courseCode"]+"</p>" +
      "<p>"+item["examiner"]+"</p>";
  let infoButton = document.createElement("button");
  infoButton.setAttribute("style","width:60px;height:60px");
  info.appendChild(infoButton);
  infoButton.addEventListener("click", function () {
    showCourseInfo(obj.data);
  })
  course.appendChild(info);


  // ---------------- dropdown -------------

  let dropDown = document.createElement("div");
  dropDown.setAttribute("style","height:"+obj.heightExtension+"px;");
  dropDown.setAttribute("class","canvas_course_dropdown");

  let dropdown_table = document.createElement("table");
  dropdown_table.setAttribute("style", "margin-top:" + obj.thickness/2 + "px;");
  let KCin = document.createElement("th");
  KCin.setAttribute("valign", "middle");
  let KCout = document.createElement("th");
  KCout.setAttribute("style","height: " + obj.thickness + "px;");
  KCout.setAttribute("valign", "middle");
  dropdown_table.appendChild(KCin);
  dropdown_table.appendChild(KCout);
  dropDown.appendChild(dropdown_table);

  obj.data.Developed.forEach((value) => {
    let p = document.createElement("p");
    p.setAttribute("style","height: " + obj.thickness + "px;");
    p.innerText = value.name;
    KCout.appendChild(p);
  });

  obj.data.Required.forEach((value) => {
    if (value.KC !== null) {
      let p = document.createElement("p");
      p.setAttribute("style","height: " + obj.thickness + "px;");
      p.innerText = value.name;
      KCin.appendChild(p);
    }

  });

/*
  let dropdown_table = document.createElement("table");
  let KCin = document.createElement("th");
  let KCout = document.createElement("th");


  for (let i=0; i < obj.KCs.REQ.length; i++) {
    let KC = document.createElement("p");
    //p.innerText = obj.KCs.REQ[i].data.
  }

  for (let i = 0;  i < obj.KCs.DEV.length; i++) {

  }
*/
  // ---------------- button ----------------
  let button = document.createElement("div");
  course.firstChild.appendChild(button);
  //button.setAttribute("style", "position: absolute");
  button.setAttribute("class","canvas_course_button");

  button.addEventListener("click",function() {
    let courseObject = obj;
    let LPs = 1; // TODO give every course a width i.e. how many periods the should be.
    //let c = document.getElementById(x +";" + y);

    let margin = obj.heightExtension;
    if (dropDown.style.display !== "inline-block") {
      dropDown.style.display= "inline-block";
    } else {
      dropDown.style.display= "none";
      margin = 0;
    }
/*
    for (let i = x; i < x+LPs*(width*1.2); i += width*1.2) {
      let victimHeight = y+height*1.2;
      let victim = document.getElementById(i + ";" + victimHeight);
      if (victim !== null) {
        //victim.style.top = (victimHeight+40) + "px";
        victim.firstChild.style.marginTop = margin + "px";
      }
    }

 */

    courseObject.setExtended();
/*
    courseObject.myLP.courses.forEach((value)=>{
      let victim = document.getElementById(value.x + ";" + value.y);
      victim.firstChild.style.marginTop = value.margin_top + "px";
    });
*/


  });


  course.appendChild(dropDown);
  document.getElementById("canvas_course_container").appendChild(course);

  return course;
}


function findCourseByCode(code) {
  let courseObject = null;
  courses.forEach((v,k)=>{
    for (let i = 0; k.length; i++) {
      if(v[i].data.courseCode.contains(code)){
        courseObject =  v[i];
      }
    }
  });
  return courseObject;
}

/**
 * This  adds a new course to the canvas.
 * @param data
 */
function addCourse(data) {
  let lpString = 0;

  if (data.lp === "TWO") {
    lpString = 1;
  } else if (data.lp === "THREE") {
    lpString = 2;
  } else if (data.lp === "FOUR") {
    lpString = 3;
  }

  let lp = LPHashmap.get(data.year + ";" + lpString);
  if (lp == null) {
    console.log("course is out of bounds");
    return;
  }

  // Add the course to the lp and create overlay.
  let course = lp.addCourse(data);
  course.setCourseOverlay(createCourseOverlay(data,course));



  // Regenerate all required KCs just in case the new course created something that is needed later.
  generateKcsInAllLPs();
  reFormatSection(lpString, data.year);
}

function reFormatSection(lp,year){
  let key = year+";"+lp;//string
  let heightAddition = 0;
  LPHashmap.get(key).courses.forEach((value => {
    value.margin_top = heightAddition;
    value.courseOverlay.firstChild.style.marginTop = value.margin_top + "px";
    if (value.extended) {
      heightAddition += value.heightExtension;
    }
  }));
  drawCanvas();
}


function drawCanvas() {
  resizeTofitCourses();
  //==== KC MAPPING ====
  /*courses.forEach((v)=>{
    REQ.forEach((e,k)=>{
      let obj = findCourseByCode(k);
      if(obj !== null){
        e.filter(value =>v.data.Developed.some(value1 => kcEquals(value1,value))).forEach((x)=>{
          v.setSnapPoints(obj,x);
        });
      }
    });
  });*/
  //==== DRAWING ====
  saveMatrix();
  ctx.save();
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  //translate(viewportX,viewportY);
  //ctx.scale(width/(window.innerWidth*0.2),height/(window.innerHeight*0.21));
  //console.log('width:'+window.innerWidth*0.2+", height: "+(window.innerHeight*0.21));

  LPHashmap.forEach((lp)=>{
    lp.draw(ctx);
  });
  restoreMatrix();
  ctx.restore();
}

function fix_dpi() {
//get CSS height
//the + prefix casts it to an integer
//the slice method gets rid of "px"
  let style_height = +getComputedStyle(canvas).getPropertyValue("height").slice(0, -2);
//get CSS width
  let style_width = +getComputedStyle(canvas).getPropertyValue("width").slice(0, -2);
//scale the canvas
  canvas.setAttribute('height', style_height * dpi);
  canvas.setAttribute('width', style_width * dpi);
}

function kcEquals(kc1,kc2) {
  if(kc1 === null || kc2 === null){
    return false;
  }
  return kc1.name === kc2.name && kc1.taxonomyLevel === kc2.taxonomyLevel;
}
//https://stackoverflow.com/questions/21717001/html5-canvas-get-coordinates-after-zoom-and-translate
function translate(x,y){
  //console.log('x: '+x+', y: '+y);
  matrix[4] += matrix[0] * x + matrix[2] * y;
  matrix[5] += matrix[1] * x + matrix[3] * y;
  ctx.translate(x,y);
  //console.log(matrix);
}
function scale(x,y){
  matrix[0] *= x;
  matrix[1] *= x;
  matrix[2] *= y;
  matrix[3] *= y;
  ctx.scale(x,y);
}
function getXY(mouseX,mouseY){
  let newX = mouseX * matrix[0] + mouseY * matrix[2] + matrix[4];
  let newY = mouseX * matrix[1] + mouseY * matrix[3] + matrix[5];
  return({x:newX,y:newY});
}

function rotate(radians){
  var cos = Math.cos(radians);
  var sin = Math.sin(radians);
  var m11 = matrix[0] * cos + matrix[2] * sin;
  var m12 = matrix[1] * cos + matrix[3] * sin;
  var m21 = -matrix[0] * sin + matrix[2] * cos;
  var m22 = -matrix[1] * sin + matrix[3] * cos;
  matrix[0] = m11;
  matrix[1] = m12;
  matrix[2] = m21;
  matrix[3] = m22;
}

function saveMatrix() {
  oldMatrix.push(matrix);


}
function restoreMatrix() {
  if(oldMatrix.length>0)
    matrix = oldMatrix.pop();


}

function generateKcsInAllLPs() {
  LPHashmap.forEach((value) => {
    if (largestLP == null || value.courses.length > largestLP.courses) {
      largestLP = value;
    }
    value.generateRequiredKCs();
  });
}

/**
 * Dynamically resize the canvas to fit all courses.
 */
function resizeTofitCourses() {
  let canvasHeight = 0;
  LPHashmap.forEach((value)=>{
    let tempHeight = value.getHeight();
    if (tempHeight > canvasHeight) {
      canvasHeight = tempHeight;
    }
  });
  canvas.setAttribute('height',  canvasHeight + "px");
  canvas.setAttribute('width', (LPHashmap.size*width*1.2) + "px");
  let container = document.getElementById("canvas_container");
  container.setAttribute("height", canvasHeight + "px");
  fix_dpi();
}


