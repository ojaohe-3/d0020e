
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
const height = 200;
const period = new Map();
  period.set('ONE',0);
  period.set('TWO',0);
  period.set('THREE',0);
  period.set('FOUR',0);
let courses =new Map();
  //translations
let matrix=[1,0,0,1,0,0];
let oldMatrix = [];
let viewportX = 0;
let viewportY = 0;


window.addEventListener("resize", drawCanvas);

canvas.addEventListener('click', function(evt) {
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if (value.button.isInside(mousePos,dpi)){
      alert('Button Pressed!');
    }else if(value.isInside(mousePos,dpi)){
      alert('Course Pressed!');
      showCourseInfo(value.data);
    } else{
      console.log("mouse pressed on nothing!");
    }

  });
},false);

function getMousePos(canvas, event) {
  let rect = canvas.getBoundingClientRect();
  console.log(rect.left);
  let mousePos = getXY(event.clientX,event.clientY) // This ain't working, pal. I get a bunch of whacky numbers.
  return {
      x: event.clientX - rect.left, // Changed to clientX and clientY.
      y: event.clientY- rect.top
  };
}

function generateCanvas(data) {
  courses = new Map();
  let year = data.year;
  let offsetYear = 0;

  let currentYear = year;
  data['Courses'].forEach(function (item, index,arr){

    // All courses should, in theory, be sorted after year. We can therefore reset the study periods when
    // The next course has a new year.
    if (item.year != currentYear) {
      offsetYear = item.year-year;
      for (let [key,value] of period) {
        period.set(key,0);
      }
      currentYear = item.year;
    }

    console.log(offsetYear);
    let x = 0;
    let hTemp = period.get(item.lp);
    let y = hTemp*height*1.2;
    period.set(item.lp, hTemp + 1);

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

    courses.set(item["courseCode"]+item["year"]+item["lp"], new CourseObject(
        item,
        {
          x: x,
          y: y,
          width: width,
          height: height
        }
    ))
    //console.log("added: "+ JSON.stringify(item))
  });

  drawCanvas();
}

function drawCanvas() {
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


