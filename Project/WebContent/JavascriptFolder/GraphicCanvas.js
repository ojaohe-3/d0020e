
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
  period.set('ONE',[]);
  period.set('TWO',[]);
  period.set('THREE',[]);
  period.set('FOUR',[]);
let courses =new Map();
let viewportX = 0;
let viewportY = 0;


window.addEventListener("resize", drawCanvas);

canvas.addEventListener('click', function(evt) {
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if (value.button.isInside(mousePos,dpi)){
      alert('Button Pressed!');
    }else if(value.isInside(mousePos,dpi)){
      //alert('Course Pressed!');
      showCourseInfo(value.data);
    } else
      console.log("mouse pressed on nothing!");
  });
},false);

function getMousePos(canvas, event) {
  let rect = canvas.getBoundingClientRect();
  return {
      x: event.clientX - rect.left,
      y: event.clientY - rect.top
  };
}

function generateCanvas(data) {
  courses = new Map();
  let periodItem = [];
  data['Courses'].forEach(function (item, index,arr){
    let x = 0;
    let y = 0;
      //check if we have collisions
      periodItem = period.get(item.lp);
      console.log(periodItem);
      if(periodItem.length > 0){
        periodItem.forEach(function () {
            console.log(item.courseCode + ": "+y);
            y += height*1.2;
        });
      }

       periodItem.push(item);
      //might not be needed
      period.set(item.lp,periodItem);

    //set x axis
    if(item.lp === "TWO"){
      x += width *1.2;
    }else if(item.lp === "THREE"){
      x += width *1.2*2;
    }else if(item.lp === "FOUR"){
      x += width *1.2*3;
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
  ctx.save();
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.translate(viewportX,viewportY);
  //ctx.scale(width/(window.innerWidth*0.2),height/(window.innerHeight*0.21));
  //console.log('width:'+window.innerWidth*0.2+", height: "+(window.innerHeight*0.21));
  courses.forEach(function (value,index,arr) {
    //console.log('draw nr:'+index);
    value.draw(ctx);
  })
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
