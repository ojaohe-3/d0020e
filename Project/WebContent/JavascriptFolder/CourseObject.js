
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

        this.heightExtension = Math.max(this.data.Developed.length,this.data.Required.length)*this.thickness+this.thickness/2;


        // These are outgoing and ingoing KC links.
        // Contains a list of docking points. These points can have several
        // KC links connected to them, in theory. I haven't tested it yet.
        this.dockPointsReq = [];
        this.dockPointsDev = [];

        this.data.Required.forEach((value)=>{
            this.addIngoingDockingPoint(value);
        });

        this.courseOverlay = null;
    }

    setCourseOverlay(overlay) {
        this.courseOverlay = overlay;
    }

    /**
     * This creates links for all required KCs in this course.
     * Remember to call this only AFTER all courses have been created, since the KC must have been
     * created by a course before a course can add it are required.
     */
    generateAllIngoingKCs(myLP) {
        this.dockPointsReq.forEach((dockingPoint)=>{
            this.generateIngoingKCForDockingPoint(dockingPoint);
        });
    }

    generateIngoingKCForDockingPoint(dockingPoint) {
        if (!dockingPoint.hasLink()) {
            let developedDockingpoint = this.myLP.findKCSource(dockingPoint.kcData);     // find starting dock point.
            if (developedDockingpoint != null) {
                let link = new KCLink();
                // Link both docking points to the newly created link.
                developedDockingpoint.addKCLinkConnection(link);
                link.setIngoingDockingPoint(developedDockingpoint);
                dockingPoint.addKCLinkConnection(link);
                link.setOutgoingDockingPoint(dockingPoint);
            }
        }
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
            {x:0,y:this.height + this.dockPointsReq.length*this.thickness+this.thickness/2},kcData);
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
            {x:this.width,y:this.height + this.dockPointsDev.length*this.thickness+this.thickness/2},kcData);
        this.dockPointsDev.push(point);
        return point;
    }

    /**
     * Generate Snap points going to course object
     * @param courseTarget CourseObject go to
     * @param kc KC JSON
     */
    // setSnapPoints(courseTarget,kc){
    //     if(this.extended && this.dockPointsDev.length>1){
    //         let endPos = courseTarget.getEndSnapPoint(kc);
    //         let startPos = this.dockPointsDev.find(value => kcEquals(value.KC,kc));
    //         let snapPoints = [{x:startPos.x,y:startPos.y}];
    //         snapPoints.push({x:this.x+this.width+this.width*0.1,y:startPos.y});
    //         snapPoints.push(...goToPeriod(this, courseTarget));
    //         snapPoints.push({x:courseTarget.x-courseTarget.width*0.1,y:endPos.y});
    //         snapPoints.push(endPos);
    //
    //         let KC = this.KCs[this.dockPointsDev.findIndex(value => kcEquals(value.KC,kc))];
    //         if(KC != null)
    //             KC.setSnapPoint(snapPoints);
    //     }
    // }

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

    getY() {
        return this.y + this.margin_top;
    }

    getX() {
        return this.x;
    }

    /**
     * Get the total height of this course object.
     * @returns {*}
     */
    getHeight() {
        return this.height + (this.extended ? this.heightExtension : 0);
    }

    /**
     * A required docking point has lost its only link. We must find a link that can
     * replace it.
     * @param dockingPoint
     */
    requiredWasUnlinked(dockingPoint) {
        this.generateIngoingKCForDockingPoint(dockingPoint);
    }

    /**
     * Just unlink all docking points. This should be done when the course is deleted from its LP.
     */
    unlinkDockingPoints() {
        this.dockPointsDev.forEach((value, index) => {
            this.myLP.timestamp.deleteKCSource(value);
            value.unlinkAll();
        });
        this.dockPointsReq.forEach((value, index) => {
            value.unlinkAll();
        });
    }

    /**
     * Move a course and its overlay.
     * @param newY
     */
    moveCourseY(newY) {
        this.courseOverlay.setAttribute("id", this.x + ";" + newY);
        this.courseOverlay.setAttribute("style","left:"+this.x+"px; top:"+newY+"px; width:"+this.width+"px;");
        this.y = newY;
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

    isExtended() {
        return this.courseObject.extended;
    }

    hasLink() {
        return this.kcLinks.length > 0;
    }

    /**
     * Unlink all connections.
     */
    unlinkAll() {
        this.kcLinks.forEach((value)=>{
            // Also unlink the other end so it doesn't think there is still a connection.
            value.unlinkOtherEnd(this);
        });
    }

    /**
     * The link was removed in the other end. This will check if the course object should be notified.
     * @param kcLink
     */
    unlink(kcLink) {
        let i = this.kcLinks.indexOf(kcLink);
        this.kcLinks.splice(i,1);
        if (this === kcLink.outPoint) {
            // this is a required docking point and someone just removed the link :(
            this.courseObject.requiredWasUnlinked(this);
        }
    }

    /**
     * This returns a point in space where every KC link should connect to.
     * @param expanded
     * @returns {{x: number, y: number}}
     */
    getPosition() {
        if (this.isExtended()) {
            return {x: this.courseObject.getX() + this.expandedPoint.x, y:  this.courseObject.getY() + this.expandedPoint.y};
        } else {
            return this.position(this.courseObject);
        }
    }
}


