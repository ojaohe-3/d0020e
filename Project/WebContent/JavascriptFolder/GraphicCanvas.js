
//get DPI
let dpi = window.devicePixelRatio;
//get canvas
let canvas = $('#graph').get(0);
//get context
let ctx = canvas.getContext('2d');
fix_dpi();
let width = canvas.width * 0.1;
let height = canvas.height * 0.2;
let courses =new Map();

window.addEventListener("resize", fix_dpi);

canvas.addEventListener('click', function(evt) {
  var mousePos = getMousePos(canvas, evt);
  courses.forEach(function (value, key, map) {
    if (value.button.isInside(mousePos,dpi)){
      alert('Button Pressed!');
    }else if(value.isInside(mousePos,dpi)){
      alert('Course Pressed!');
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
  console.log(data);
  data.forEach(function (item, index,arr){
    let x = 10;
    let y = 10;
    if(index > 1){
      if(arr[index-1]["lp"] === item["lp"]){ // if previous item is same lp
        y += height*1.2
      }
    }

    if(item["lp"] == "TWO"){
      x += width *1.2;
    }else if(item["lp"] == "THREE"){
      x += width *1.2*2;
    }else if(item["lp"] == "FOUR"){
      x += width *1.2*3;
    }
    courses.set(item["name"]+item["year"]+item["lp"], new CourseObject(
        item,
        {
          x: x,
          y: y,
          width: width,
          height: height
        }
    ))
  });

  drawCanvas();
}
function drawCanvas() {
  fix_dpi();
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  courses.forEach(function (value) {
    value.draw(ctx);
  })
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
