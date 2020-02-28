
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
let courses =new Map();
let REQ = new Map();
  //translations
let matrix=[1,0,0,1,0,0];
let oldMatrix = [];
window.addEventListener("resize", drawCanvas);

canvas.addEventListener('click', function(evt) {
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if(value.isInside(mousePos,dpi)){
      //alert('Course Pressed!');
      //showCourseInfo(value.data);
      value.setExtended();
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
  // clear local data
  kcMap = new Map();
  courses = new Map();
  KCs = [];
  // read from course
  let year = data.year;
  let offsetYear = 0;
  let currentYear = year;
  //KC mapping reset
  REQ = new Map();
  let intersection = [];
  data['Courses'].forEach(function (item, index,arr){




    //======================== YEAR PARTITIONING========================
    // All courses should, in theory, be sorted after year. We can therefore reset the study periods when
    // The next course has a new year.
    if (item.year !== currentYear) {
      offsetYear = item.year-year;
      for (let [key,value] of period) {
        period.set(key,0);
      }
      currentYear = item.year;
    }

    //console.log(offsetYear);
    let x = 0;
    let hTemp = period.get(item.lp);
    let y = hTemp*height*1.2;
    period.set(item.lp, hTemp + 1);

    //======================== LP PARTITIONING ========================
    //set x axis
    if(item.lp === "ONE"){
      x+= width*1.2*offsetYear*4;
    }
    else if(item.lp === "TWO"){
      x += width *1.2*(1+offsetYear*4);
    }else if(item.lp === "THREE"){
      x += width *1.2*(2+(offsetYear-1)*4);
    }else if(item.lp === "FOUR"){
      x += width *1.2*(3+(offsetYear-1)*4);
    }



    //========================  KC MAPPING ========================

    //map required kcs
    if(item.Required.length > 0){
      REQ.set(item.courseCode, item.Required);
    }

    //======================== GENERATE GRAPHICS OBJECT ========================

    let obj = new CourseObject(
        item,
        {
          x: x,
          y: y,
          width: width,
          height: height,
          thickness: 24
        }
    );


    courses.set(item["courseCode"]+item["year"]+item["lp"], obj);
  });



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
  try{
    courses.set(data["courseCode"]+data["year"]+data["lp"]);
    regenerateCanvas();
  }catch (e) {
    alert(e.message+' Value might already exist!');
  }

}

function reFormatSection(lp,year){
  let key = year+lp;//string
  let oldkey ="";
  let old = {};
  courses.forEach((v,k)=> {
    if(k.includes(key)){
      if(courses.has(oldkey)){
        old = courses.get(oldkey);
        v.y = old.y + old.height*1.2;
      }
      oldkey = k;
    }
  });
  drawCanvas();
}

function drawCanvas() {
  ctx.clearRect(0,0,canvas.width,canvas.height);
  //==== KC MAPPING ====
  courses.forEach((v)=>{
    REQ.forEach((e,k)=>{
      let obj = findCourseByCode(k);
      if(obj !== null){
        e.filter(value =>v.data.Developed.some(value1 => kcEquals(value1,value))).forEach((x)=>{
          v.setSnapPoints(obj,x);
        });
      }
    });
  });
  //==== DRAWING ====
  saveMatrix();
  ctx.save();
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  //translate(viewportX,viewportY);
  //ctx.scale(width/(window.innerWidth*0.2),height/(window.innerHeight*0.21));
  //console.log('width:'+window.innerWidth*0.2+", height: "+(window.innerHeight*0.21));
  courses.forEach(function (value,index,arr) {
    //console.log('draw nr:'+index);
    value.draw(ctx);
  })
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


