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
    constructor(data,conf,lp) {
        this.data = data;
        this.x = conf.x;
        this.y = conf.y;
        this.width = conf.width;
        this.height = conf.height;
        this.thickness = conf.thickness;
        this.extended = false;
        this.dockPointsReq = [{x: this.x, y:this.height/2+this.y, KC: null}]; //default point
        this.dockPointsDev =[];
        this.KCs = [];

        // Start of the new solution.
        this.margin_top = 0;
        this.lp = lp;
        this.position = {x:conf.x,y:conf.y};
        /*
        data.Required.forEach((k,i) => {
            this.dockPointsReq.push({x: conf.x, y:conf.y+conf.height+conf.thickness*i+this.thickness,KC:k});
            //this.KCs.REQ.push(new KCObject(conf.thickness, k));//if a dev exist a reqirement version will simply point on exact same points
        });
        data.Developed.forEach((k,i)=>{
            this.dockPointsDev.push({x: conf.x+ conf.width, y:conf.y+conf.height+conf.thickness*i+this.thickness,KC:k});
            this.KCs.push(new KCObject(conf.thickness, k));
        })*/
        this.heightExtension = Math.max(this.dockPointsDev.length,this.dockPointsReq.length-1)*this.thickness+this.thickness;


        // These are outgoing and ingoing KC links.
        // Contains a list of docking points. These points can have several
        // KC links connected to them, in theory. I haven't tested it yet.
        this.inputDockingPoints = [];
        this.outputDockingPoints = [];
        this.pointHeight = 24; // docking point height.

    }

    /**
     * Get the total height of this course object.
     * @returns {*}
     */
    getHeight() {
        return this.height + (this.extended ? 0 : this.heightExtension);
    }

    /**
     * This creates links for all required KCs in this course.
     * Remember to call this only AFTER all courses have been created, since the KC must have been
     * created by a course before a course can add it are required.
     */
    generateAllIngoingKCs() {
        if (this.data.Required.length > 0) {
            let temp = true;
        }
        this.data.Required.forEach((value)=>{
            let requiredDockinpoint = this.addIngoingDockingPoint(value);// Create a docking point even if no KC exists.
            let developedDockingpoint = this.lp.findKCSource(value);     // find starting dock point.
            if (developedDockingpoint != null) {
                let link = new KCLink();
                // Link both docking points to the newly created link.
                developedDockingpoint.addKCLinkConnection(link);
                requiredDockinpoint.addKCLinkConnection(link);
            }
        });
    }

    /**
     * Returns a default ingoing KC dock point for when the course is collapsed.
     * @returns {{x: number, y: number}}
     * @author Robin
     */
    getDefaultIngoingDockingPoint() {
        return {x:0,y:this.height/2};
    }

    /**
     * Returns a default outgoing KC dock point for then the course is collapsed.
     * @returns {{x: *, y: number}}
     * @author Robin
     */
    getDefaultOutGoingDockingPoint() {
        return {x:this.width, y: this.height/2};
    }


    /**
     * Add an ingoing docking (required) point to this course. Docking points are used to connect
     * KC links and courses.
     * @returns DockingPoint - This is the newly created docking point.
     */
    addIngoingDockingPoint(kcData) {
        let point = new DockingPoint(this, this.getDefaultIngoingDockingPoint,
            {x:0,y:this.height + this.dockPointsReq.length*this.pointHeight},kcData);
        this.dockPointsReq.push(point);
        return point;
    }

    /**
     * Add an outgoing (developed) docking point to this course. Docking points are used to connect
     * KC links and courses.
     *
     * This method is called by the CanvasLP when a course is added to the LP.
     *
     * @returns {DockingPoint}
     */
    addOutGoingDockingPoint(kcData) {
        let point = new DockingPoint(this, this.getDefaultOutGoingDockingPoint,
            {x:this.width,y:this.height + this.dockPointsDev.length*this.pointHeight},kcData);
        this.dockPointsDev.push(point);
        return point;
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
            return [{x: this.x + this.width + this.width*0.1, y: this.y + this.height + this.height*0.1},{x: this.x + this.width + this.width*0.1, y: this.y + height + height*0.1}]
        return [{x: this.x + this.width +this.width*0.1, y: this.y + this.height + this.height*0.1}]

    }


    /**
     * Find kc and map point to its required slot.
     * if it is not extended snap all to default position
     * @param kc JSON fetched from database
     * @return {{x: *, y: *}}
     */
    getEndSnapPoint(kc){
        if(this.extended){
            let finder = this.dockPointsReq.find(value => kcEquals(value.KC,kc));
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
            this.height += this.heightExtension;
        else
            this.height -= this.heightExtension;
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

       if (this.extended) {
           this.inputDockingPoints.forEach((value) => {
               value.draw(ctx);
           });
           this.outputDockingPoints.forEach((value) => {
               value.draw(ctx);
           });
       }

      ctx.strokeRect(this.x,this.y+this.margin_top,this.width,this.height);
      restoreMatrix();
      ctx.restore();
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

/**
 * This is just a docking point for the KC link. Nothing fancy going on here (it's actually very fancy :) ).
 *
 * The docking point is for example used by the timestamp when a course requires a KC.
 * The timestamp looks up a KC key and finds the corresponding dock point for that KC. A
 * course can then connect a KC link to that dock point and doesn't have to care about where it
 * came from, because the course will have it's own docking point in the other end that references the
 * same KC link. Fancy, huh?
 * @author Robin
 */
class DockingPoint {
    /**
     *
     * @param courseObject
     * @param defaultPointFunction - This is a function. Ether the default in or out of the course object.
     * @param expandedPoint - A struct with the content {x:some_x,y:some_y};
     */
    constructor(courseObject, defaultPointFunction, expandedPoint, kcData) {
        this.courseObject = courseObject;
        //this.collapsedPoint = {x:courseObject.position.x,y:courseObject.getHeight()/2};
        this.getPosition = defaultPointFunction;
        this.expandedPoint = expandedPoint;
        this.kcLinks = [];
        this.kcData = kcData; // Not used right now but we could need it later on.
    }

    /**
     * Add a kc link to this docking point.
     * Many KC links can be created if one course
     * creates a KC that is needed by several other courses. I.e.
     * we need to draw multiple lines that has the same beginning.
     * @param kcLink
     */
    addKCLinkConnection(kcLink) {
        this.kcLinks.push(kcLink)
    }

    /**
     * Draw every KC link that is connected to this docking point.
     * @param ctx
     */
    draw(ctx) {
        this.kcLinks.forEach((value) => {
            value.drawLink(ctx,this);
        });
    }

    /**
     * This returns a point in space where every KC link should connect to.
     * @param expanded
     * @returns {{x: number, y: number}}
     */
    getPoint() {
        if (this.courseObject.extended) {
            return this.expandedPoint;
        } else {
            return this.getPosition();   // TODO Check if this actually works, or if the course object is lost.
        }
    }
}


