
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
const height = 250;
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

canvas.addEventListener('click', function(evt) {
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if(value.isInside(mousePos,dpi)){
      //alert('Course Pressed!');
      //showCourseInfo(value.data);
      //value.setExtended();
    } else{
      console.log("mouse pressed on nothing!");
    }

  });
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
  let currentLPString = "";
  data['Courses'].forEach(function (item, index,arr){


    // This creates a new year with LP and timestamps.
    // We have to create the timestamps in order since every timestamp
    // depend on the previous timestamp.
    if (item.year != currentYear) {
      offsetYear = item.year-year;
      currentYear = item.year;
      let newLP = null;
      for (let i = 0; i < 4; i++) {
        currentLPString = item.year + ";" + i;
        newLP = new CanvasLP(previousTimestamp);
        LPHashmap.set(currentLPString,newLP);
        previousTimestamp = newLP.timestamp;
      }
    }
    // all courses are sorted after year. I.e. no more courses from previous year will pop up.


    // This is where the course is created.
    let lpString = 0;
    let x = width*1.2*offsetYear*4;
    if (item.lp === "TWO") {
      lpString = 1;
      x = width *1.2*(1+offsetYear*4);
    } else if (item.lp === "THREE") {
      lpString = 2;
      x = width *1.2*(2+(offsetYear-1)*4);
    } else if (item.lp === "FOUR") {
      lpString = 3;
      x = width *1.2*(3+(offsetYear-1)*4);
    }
    // TODO Set the LPs to numbers instead of ONE, TWO, THREE, FOUR.

    currentLPString = item.year + ";" + lpString;
    let courseLP = LPHashmap.get(currentLPString);
    let y = courseLP.courses.length*height*1.2;
    let courseObject = new CourseObject(
        item,
        {
          x: x,
          y: y,
          width: width,
          height: height,
          thickness: 24
        },
        courseLP
    );
    courseObject.data.lp = lpString;
    createCourseOverlay(x,y,item, courseObject);
    courseLP.addCourse(courseObject);
  });

  LPHashmap.forEach((value) => {
    value.generateRequiredKCs();
  });

  drawCanvas();
}

function createCourseOverlay(x,y, item, obj) {
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

  obj.dockPointsDev.forEach((value) => {
    let p = document.createElement("p");
    p.setAttribute("style","height: " + obj.thickness + "px;");
    p.innerText = value.KC.name;
    KCout.appendChild(p);
  });

  obj.dockPointsReq.forEach((value) => {
    if (value.KC !== null) {
      let p = document.createElement("p");
      p.setAttribute("style","height: " + obj.thickness + "px;");
      p.innerText = value.KC.name;
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

    for (let i = x; i < x+LPs*(width*1.2); i += width*1.2) {
      let victimHeight = y+height*1.2;
      let victim = document.getElementById(i + ";" + victimHeight);
      if (victim !== null) {
        //victim.style.top = (victimHeight+40) + "px";
        victim.firstChild.style.marginTop = margin + "px";
      }
    }
    courseObject.setExtended();

  });


  course.appendChild(dropDown);
  document.getElementById("canvas_course_container").appendChild(course);
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
function addCourse(data) {
  try{
    courses.set(data["courseCode"]+data["year"]+data["lp"]);
    regenerateCanvas();
  }catch (e) {
    alert(e.message+' Value might already exist!');
  }

}

function reFormatSection(lp,year){
  let key = year+";"+lp;//string
  let oldkey ="";
  let old = {};
  let heightAddition = 0;
  LPHashmap.get(key).courses.forEach((value => {
    value.margin_top = heightAddition;
    if (value.extended) {
      heightAddition += value.heightExtension;
    }
  }));

  drawCanvas();
}
function findCourseByCode(code) {
  let obj = {};
  courses.forEach((v,k)=>{
    if(k.includes(code)){
      obj = v;//cant break for loops apparently
    }
  });
  return obj;
}
function addCourse(data) {
  // TODO this does not wörk.
  try{
    courses.set(data["courseCode"]+data["year"]+data["lp"]);
    regenerateCanvas();
  }catch (e) {
    alert(e.message+' Value might already exist!');
  }

}


function drawCanvas() {
  ctx.clearRect(0,0,canvas.width,canvas.height);
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


