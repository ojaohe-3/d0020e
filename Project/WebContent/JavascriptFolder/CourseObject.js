/**
 * Get a course At a specific date, and is on equal heigh as the caller
 * @param date {year:*,lp:*}
 * @param c1 referense caller, CourseObject
 * @return {null|*} resulting CourseObject
 */
function getCourseAt(date,c1) {
    let obj = null;
    courses.forEach((v,k)=>{
       if(k.includes(date.year+date.lp)){
           if(v.y < c1.y*1.5 && v.y>c1.y*0.5){
               obj = v;// again stupid js cannot break foreach loops.
           }
       }
    });

    if(obj === null){
        // empty space found
        let temp = new CourseObject(c1.data,
            {
                x:c1.x,y:c1.y,
                width: c1.width,
                height: c1.height,
                thickness: c1.thickness
            });
        temp.x += width;
        temp.y = 0;
        return temp;
    }else{
        return obj;
    }
}

/**
 * wander points by the middle from one point to another
 * @param c1 CourseObject 1
 * @param c2 CourseObject 2
 * @return array of snapPoints ending at intermittent
 */
function goToPeriod(c1, c2) {
    let current = {year:c1.data.year, lp:c1.data.lp};
    let res = [];
    let cCourse = c1;
    let step = 0;
    switch (current.lp) {
        case "ONE":step =0;break;
        case "TWO":step=1;break;
        case "THREE":step=2;break;
        case "FOUR":step=3;break
    }
    while (current.year !== c2.data.year && current.lp !== c2.data.lp){

        if(cCourse !== c1){
            res.push(...cCourse.getFirstIntermittenPoint());
            res.push(cCourse.getMiddleSnap());
            res.push(...cCourse.getEndIntermittenPoint());
        }
        step = (step+1);
        if(step>3){
            current.year += 1;
            step %= 4;
        }
        switch (step) {
            case 0:current.lp="ONE";break;
            case 1:current.lp="TWO";break;
            case 2:current.lp="THREE";break;
            case 3:current.lp="FOUR";break;
        }

        cCourse = getCourseAt(current,cCourse);
        if(cCourse === null)
            return res;
    }
    return res;
}

/**
 * Object For each graphical and logical position of Courses
 */
class CourseObject{
    /**
     * Generate Object
     * @param data JSON fetched from database
     * @param conf {x,y,width,heigh,thickness}
     */
    constructor(data,conf) {
        this.data = data;
        this.x = conf.x;
        this.y = conf.y;
        this.width = conf.width;
        this.height = conf.height;
        this.thickness = conf.thickness;
        this.extended = false;
        this.dockPointsReq = [{x: this.x, y:this.height/2+this.y, KC: null}]; //default point
        this.dockPointsDev =[];
        this.KCs = [{}];
        data.Required.forEach((k,i) => {
            this.dockPointsReq.push({x: conf.x, y:conf.y+conf.height+conf.thickness*i,KC:k});
            //this.KCs.REQ.push(new KCObject(conf.thickness, k));//if a dev exist a reqirement version will simply point on exact same points
        });
        data.Developed.forEach((k,i)=>{
            this.dockPointsDev.push({x: conf.x+ conf.width, y:conf.y+conf.height+conf.thickness*i,KC:k});
            this.KCs.push(new KCObject(conf.thickness, k));
        })

    }

    /**
     * Generate Snap points going to course object
     * @param courseTarget CourseObject
     * @param kc KC JSON
     */
    setSnapPoints(courseTarget,kc){
        if(this.extended && this.dockPointsDev.length>0){
            let endPos = courseTarget.getEndSnapPoint(kc);
            let startPos = this.dockPointsDev.find(value => kcEquals(value.KC,kc));
            let snapPoints = [{x:startPos.x,y:startPos.y}];
            snapPoints.push({x:this.x+this.width+this.width*0.1,y:startPos.y});
            snapPoints.push(...goToPeriod(this, courseTarget));
            snapPoints.push({x:courseTarget.x-courseTarget.width*0.1,y:endPos.y});
            snapPoints.push(endPos);

            this.KCs[this.dockPointsDev.findIndex(value => kcEquals(value.KC,kc))].setSnapPoint(snapPoints);
        }
    }

    /**
     * Get the first point before meeting the object, to the left of the object going towards right
     * if extended return 2 points, one incoming from middle and one outgoing
     * @return [{x: number, y: number}|{x: number, y: number}]
*/
    getFirstIntermittenPoint(){
        if(this.extended)
            return [{x: this.x - this.width*0.1, y: this.y + height + height*0.1},{x: this.x -  this.width*0.1, y: this.y + this.height + this.height*0.1}]
        return [{x:  this.x - this.width*0.1, y: this.y + this.height + this.height*0.1}]
    }

    /**
     * Get points after leaving the middle part, if extended return 2, one outgoing and one going back to middle
     * @return [{x: number, y: number}|{x: number, y: number}]
     */
    getEndIntermittenPoint(){
        if(this.extended)
            return [{x: this.x + this.width + this.width*0.1, y: this.y + height + height*0.1},{x: this.x + this.width + this.width*0.1, y: this.y + this.height + this.height*0.1}]
        return [{x: this.x + this.width +this.width*0.1, y: this.y + this.height + this.height*0.1}]

    }

    /**
     * get lower middle
     * @return {{x: *, y: *}}
     */
    getMiddleSnap(){
        return {x: this.x + this.width/2, y: this.y + this.height+this.height*0.1};
    }

    /**
     * Find kc and map point to its required slot.
     * if it is not extended snap all to default position
     * @param kc JSON fetched from database
     * @return {{x: *, y: *}}
     */
    getEndSnapPoint(kc){
        if(this.extended){
            let finder = this.dockPointsReq.find(value => kcEquals(value,kc));
            return {x:finder.x,y:finder.y};
        }else
        {
            return {x:this.dockPointsReq[0].x,y:this.dockPointsReq[0].y};
        }
    }

    /**
     * Extend to show KCs, will toggle when called again.
     */
    setExtended(){
        this.extended = this.extended == true ? false : true;
        if(this.extended)
            this.height += Math.max(this.dockPointsDev.length,this.dockPointsReq.length-1)*this.thickness;
        else
            this.height -= Math.max(this.dockPointsDev.length,this.dockPointsReq.length-1)*this.thickness;
        reFormatSection(this.data.lp,this.data.year);
    }

    /**
     * Draw Course Object, Is required
     * Draws KCs objects
     * @param ctx draw context
     */
    draw(ctx){
      saveMatrix();
      ctx.save();
      translate(0,0);
       // this.button = new canvasButton({x: this.x + this.width*0.8,
       //  y : this.y+this.height*0.8,
       //  width : this.width*0.2,
       //  height : this.height*0.2,
       //  text:"▲"});
        if (this.extended) {
            this.KCs.DEV.forEach((v)=>{v.draw(ctx);});
        }


      ctx.strokeRect(this.x,this.y,this.width,this.height);
      //obsolete
      //ctx.fillRect(this.x,this.y,this.width,this.height*0.2);
      //ctx.fillStyle = "white";
      //ctx.font = 'italic '+(this.width)*4/ctx.measureText(this.data["name"]).width+'pt Calibri';
     // ctx.fillText(this.data["name"],
       // this.x+this.width/2 -  ctx.measureText(this.data["name"]).width/2,
        //this.y + this.height*0.14
       // );
     // ctx.fillStyle = "black";
     // ctx.strokeRect(this.x+this.width*0.05,this.y+this.height*.3,this.width-this.width*0.1,this.height-this.height*0.5);

     // this.drawInRect(this.x+this.width*0.1 ,this.y+this.height*0.35 ,0,0,0.70717,ctx);
      //this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.36,this.data["courseCode"],ctx);
     // this.drawInRect(this.x + this.width*0.1 ,this.y+this.height*0.6 ,0,0,0.70717,ctx);
      //this.writeInRect(this.x+this.width*0.1,this.y+this.height*0.59,this.data["examiner"],ctx);
      restoreMatrix();
      ctx.restore();
      // this.button.draw(ctx);
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
      saveMatrix();
      ctx.save();
      translate(xstart,ystart);
      ctx.rotate(rotation);
      ctx.fillRect(0,0,this.width*0.05,this.width*0.05);
      ctx.rotate(0 - rotation);
      restoreMatrix();
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
      saveMatrix();
      ctx.save();
      translate(xstart,ystart);
      ctx.font = 'italic '+(this.width)*2/ctx.measureText(text)+'pt Calibri';
      ctx.fillText(text,this.width*0.1,this.height*0.1-1);
     restoreMatrix();
     ctx.restore();
    }

    /**
     * Check if collides with pos
     * @param pos {x:number,y:number}
     * @param dpi dpi from canvas, if unsure just set to 1.5
     * @return {boolean|boolean}
     */
    isInside(pos, dpi){
        let wx = this.x+this.width;
        let wy = this.y+this.height;

        return pos.x > this.x/dpi && pos.x < wx/dpi && pos.y < wy/dpi && pos.y > this.y/dpi
    }
}


