function getInputPoint(courseObject) {
    return courseObject.getDefaultIngoingDockingPoint();
}

function getOutputPoint(courseObject) {
    return courseObject.getDefaultOutGoingDockingPoint();
}




/**
 * Object For each graphical and logical position of Courses
 */
class CourseObject{
    /**
     * Generate Object
     * @param data JSON fetched from database
     * @param conf {x,y,width,heigh,thickness}
     * @param lp
     */
    constructor(data,conf, myLP) {
        this.data = data;
        this.x = conf.x;
        this.y = conf.y;
        this.width = conf.width;
        this.height = conf.height;
        this.thickness = conf.thickness;
        this.extended = false;
        this.myLP = myLP;

        // Start of the new solution.
        this.margin_top = 0;

        this.heightExtension = Math.max(this.data.Developed.length,this.data.Required.length-1)*this.thickness+this.thickness;


        // These are outgoing and ingoing KC links.
        // Contains a list of docking points. These points can have several
        // KC links connected to them, in theory. I haven't tested it yet.
        this.dockPointsReq = [];
        this.dockPointsDev = [];
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
    generateAllIngoingKCs(myLP) {
        this.data.Required.forEach((value)=>{
            let requiredDockinpoint = this.addIngoingDockingPoint(value);// Create a docking point even if no KC exists.
            let developedDockingpoint = myLP.findKCSource(value);     // find starting dock point.
            if (developedDockingpoint != null) {
                let link = new KCLink();
                // Link both docking points to the newly created link.
                developedDockingpoint.addKCLinkConnection(link);
                link.setIngoingDockingPoint(developedDockingpoint);
                requiredDockinpoint.addKCLinkConnection(link);
                link.setOutgoingDockingPoint(requiredDockinpoint);
            }
        });
    }

    /**
     * Returns a default ingoing KC dock point for when the course is collapsed.
     * @returns {{x: number, y: number}}
     * @author Robin
     */
    getDefaultIngoingDockingPoint() {
        return {x:this.x,y:this.y + this.height/2};
    }

    /**
     * Returns a default outgoing KC dock point for then the course is collapsed.
     * @returns {{x: *, y: number}}
     * @author Robin
     */
    getDefaultOutGoingDockingPoint() {
        return {x:this.x + this.width, y: this.y + this.height/2};
    }


    /**
     * Add an ingoing docking (required) point to this course. Docking points are used to connect
     * KC links and courses.
     * @returns DockingPoint - This is the newly created docking point.
     */
    addIngoingDockingPoint(kcData) {
        let point = new DockingPoint(this, getInputPoint,
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
        let point = new DockingPoint(this, getOutputPoint,
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
     * Get the first lower point before meeting the object, left faced
     * @return {x: number, y: number}
*/
    getFirstIntermittenPoint(){
            return {x:  this.x - this.width*0.1, y: this.y + this.margin_top + this.height+this.height*0.1};
    }

    /**
     * Get right lower faced point
     * @return {x: number, y: number}
     */
    getEndIntermittenPoint(){
            return {x: this.x + this.width +this.width*0.1, y:this.y + this.margin_top + this.height+this.height*0.1};

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
           this.dockPointsReq.forEach((value) => {
               value.draw(ctx);
           });
           this.dockPointsDev.forEach((value) => {
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
        this.position = defaultPointFunction;
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

    getLP() {
        return this.courseObject.myLP;
    }

    /**
     * This returns a point in space where every KC link should connect to.
     * @param expanded
     * @returns {{x: number, y: number}}
     */
    getPosition() {
        if (this.courseObject.extended) {
            return {x: this.courseObject.x + this.expandedPoint.x, y:  this.courseObject.y + this.expandedPoint.y};
        } else {
            return this.position(this.courseObject);   // TODO Check if this actually works, or if the course object is lost.
        }
    }
}


