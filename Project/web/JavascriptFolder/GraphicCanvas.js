

//get DPI
let dpi = window.devicePixelRatio;
//get canvas
let canvas = document.getElementById('graph');
//get context
let ctx = canvas.getContext('2d');
fix_dpi();
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


  let obj = new CourseObject({"name": "hej", "code":"800813","examiner":"ulf"}, {
    x: 10, y: 10, width: 300, height: 300
  });

let obj2 = new CourseObject({"name": "hej", "code":"800813","examiner":"ulf"}, {
    x: 450, y: 10, width: 300, height: 300
  });
  obj.draw(ctx);
  obj2.draw(ctx);
  canvas.addEventListener('click', function(evt) {
    var mousePos = getMousePos(canvas, evt);

    if (obj.button.isInside(mousePos, dpi)) {
        alert('clicked inside rect');
    }else{
        //alert('clicked outside rect');
    }
}, false);

function getMousePos(canvas, event) {
  let rect = canvas.getBoundingClientRect();
  return {
      x: event.clientX - rect.left,
      y: event.clientY - rect.top
  };
}

