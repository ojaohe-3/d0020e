//https://stackoverflow.com/questions/24384368/simple-button-in-html5-canvas/24384882
class canvasButton{
    isInside(pos, dpi){
      let wx = this.x+this.width;
      let wy = this.y+this.height;
      console.log("x of button range:" +this.x/dpi+":"+wx/dpi+", y of button range"+this.y/dpi+":"+wy/dpi)
      console.log("mouse x:" +pos.x+", mouse y:"+pos.y);
        return pos.x > this.x/dpi && pos.x < (this.x+this.width)/dpi && pos.y < (this.y+this.height)/dpi && pos.y > this.y/dpi
    }
  constructor(conf){
    this.x = conf.x;
    this.y = conf.y;
    this.width = conf.width;
    this.height = conf.height;
    this.text = conf.text;
  }
  draw(ctx){
      ctx.save();
      ctx.fillStyle="gray";
    ctx.fillRect(this.x,this.y,this.width,this.height);

    if(this.text.length > 1){
      ctx.font = 'italic '+(this.width)*0.1+'pt Calibri';
      ctx.fillText(this.text,
          this.x+this.width/2 -  ctx.measureText(this.text).width/2,
          this.y + this.height*0.1
          );
    }
    ctx.restore();
  }

}
