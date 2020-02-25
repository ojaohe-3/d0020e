class CourseObject{

    constructor(data,conf) {
        this.data = data;
        this.x = conf.x;
        this.y = conf.y;
        this.width = conf.width;
        this.height = conf.height;


    }

    drawExtended(ctx){

      this.button = new canvasButton({x: this.x + this.width*0.8,
        y : this.y+this.height*0.8,
        width : this.width*0.2,
        height : this.height*0.2,
        text:""})

      ctx.strokeRect(this.x,this.y,this.width,this.height+data["KC"].length);
      ctx.fillRect(this.x,this.y,this.width,this.height*0.2);
      ctx.fillStyle = "white";
      ctx.font = 'italic '+(this.width)*0.12+'pt Calibri';
      ctx.fillText(this.data["name"],
        this.x+this.width/2 -  ctx.measureText(this.data["name"]).width/2,
        this.y + this.height*0.14
        );
      ctx.fillStyle = "black";
      ctx.strokeRect(this.x+this.width*0.05,this.y+this.height*.3,this.width-this.width*0.1,this.height-this.height*0.5);

      this.drawInRect(this.x+this.width*0.1 ,this.y+this.height*0.35 ,0,0,0.70717,ctx);
      this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.35,this.data["code"],ctx);
      this.drawInRect(this.x + this.width*0.1 ,this.y+this.height*0.6 ,0,0,0.70717,ctx);
      this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.6,this.data["examiner"],ctx);
      ctx.restore();

      console.log("im here");
      this.button.draw(ctx);
    }

  draw(ctx){
      //saveMatrix();
      ctx.save();
      ctx.translate(0,0);
       this.button = new canvasButton({x: this.x + this.width*0.8,
        y : this.y+this.height*0.8,
        width : this.width*0.2,
        height : this.height*0.2,
        text:"▲"});
      ctx.strokeRect(this.x,this.y,this.width,this.height);
      ctx.fillRect(this.x,this.y,this.width,this.height*0.2);
      ctx.fillStyle = "white";
      ctx.font = 'italic '+(this.width)*4/ctx.measureText(this.data["name"]).width+'pt Calibri';
      ctx.fillText(this.data["name"],
        this.x+this.width/2 -  ctx.measureText(this.data["name"]).width/2,
        this.y + this.height*0.14
        );
      ctx.fillStyle = "black";
      ctx.strokeRect(this.x+this.width*0.05,this.y+this.height*.3,this.width-this.width*0.1,this.height-this.height*0.5);

      this.drawInRect(this.x+this.width*0.1 ,this.y+this.height*0.35 ,0,0,0.70717,ctx);
      this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.35,this.data["courseCode"],ctx);
      this.drawInRect(this.x + this.width*0.1 ,this.y+this.height*0.6 ,0,0,0.70717,ctx);
      this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.6,this.data["examiner"],ctx);
      //restoreMatrix();
      ctx.restore();
      this.button.draw(ctx);
    }

  /**
   * Draw A rectangle inside a rectangle ! And maybe transform it and w/e
   * @param xstart start lol
   * @param ystart is this end? no start lol
   * @param targetx the translated x target
   * @param targety the translated y target
   * @param rotation rotations in radians
   * @param ctx canvas context
   */
    drawInRect(xstart,ystart,targetx,targety,rotation,ctx){
      //saveMatrix();
      ctx.save();
      ctx.translate(xstart,ystart);
      ctx.rotate(rotation);
      ctx.fillRect(0,0,this.width*0.05,this.width*0.05);
      ctx.rotate(0 - rotation);
      //restoreMatrix();
      ctx.restore();
    }

  /**
   *
   * @param xstart relative canvas position
   * @param ystart relative canvas position
   * @param text Text to write, will auto scale
   * @param ctx canvas context
   */
    writeInRect(xstart,ystart,text,ctx){
      //saveMatrix();
      ctx.save();
      ctx.translate(xstart,ystart);
      ctx.font = 'italic '+(this.width)*2/ctx.measureText(text)+'pt Calibri';
      ctx.fillText(text,this.width*0.1,this.height*0.1-1);
     //restoreMatrix();
     ctx.restore();
    }


    isInside(pos, dpi){
        let wx = this.x+this.width;
        let wy = this.y+this.height;

        return pos.x > this.x/dpi && pos.x < (this.x+this.width)/dpi && pos.y < (this.y+this.height)/dpi && pos.y > this.y/dpi
    }
}


