class CanvasLP {

    constructor(precedingLP, xPosition, courseHeight, year, lp) {
        this.x = xPosition;
        this.courseHeight = courseHeight;
        this.year = year;
        this.lp = lp;
        this.courses = [];  // A list of ALL courses
        this.timestamp = null;
        this.precendingLP = precedingLP;
        if (precedingLP != null) {
            this.timestamp = new Timestamp(precedingLP.timestamp); // A hash map of all KC dock point.
        } else {
            this.timestamp = new Timestamp(null);
        }
    }

    getPrecendingLP() {
        return this.precendingLP;
    }

    /**
     * Returns 0 if there are no courses in this LP.
     * @returns {({x: number, y: *}|{x: *, y: *})[]|number}
     */
    getMiddlePoint(courseLP) {
        if (courseLP.courses.length === 0) {
            return null;
        }
        let point1 = courseLP.courses[0].getFirstIntermittenPoint();
        let point2 = courseLP.courses[0].getEndIntermittenPoint();
        return [point1, point2];
    }

    /**
     * Remove a specific course
     * @param course JSON of course
     */
    removeCourse(course){
        let removedObject = this.courses.filter(value => course.courseCode+course.lp+course.year == value.data.courseCode+value.data.lp+value.data.year ).pop();
        removedObject.unlinkDockingPoints();
        removedObject.setCourseOverlay(null);
        this.courses = this.courses.filter(value => course.courseCode+course.lp+course.year !== value.data.courseCode+value.data.lp+value.data.year );
        this.reevaluateCourses();
    }

    reevaluateCourses() {
        let i = 0;
        this.courses.forEach((value)=> {
            value.moveCourseY(i*this.courseHeight);
            i++;
        });
    }

    /**
     * Add a course object to this LP.
     * @param courseObject
     * @return - The newly created course.
     */
    addCourse(courseData) {
        let courseObject = new CourseObject(
            courseData,
            {
                x: this.x,
                y: this.courses.length*this.courseHeight,
                width: width,
                height: height,
                thickness: 24
            },
            this
        );
        courseObject.data.lp = this.lp;
        this.courses.push(courseObject);

        // Add all generated KCs to this timestamp.
        courseObject.data.Developed.forEach((value) => {
            let dockingPoint = courseObject.addOutGoingDockingPoint(value);

            // This function will only create A KC if no docking point exist already.
            this.timestamp.addKCSource(value,dockingPoint);
        });
        return courseObject;
    }

    /**
     * This goes through all the courses and creates links for all
     * required KCs (if there are any).
     */
    generateRequiredKCs() {
        this.courses.forEach((value)=>{
            // Generate all required KCs for every course.
            value.generateAllIngoingKCs(this);
        });
    }

    /**
     * Find a course based on a course code.
     * @param courseCode - The code in string format.
     */
    findCourse(courseCode) {
        for (let i = 0; i < this.courses.length; i++) {
            if (this.courses[i].data.courseCode.contains(courseCode)) {
                return this.courses[i];
            }
        }
    }

    getNumberOfCourses() {
        return this.courses.length;
    }

    /**
     * Find the docking point of a course that created a KC.
     * @param kcData - The raw KC data we want to find.
     * @returns {null|*} - Either null or a docking point.
     */
    findKCSource(kcData) {
        if (this.timestamp != null) {
            return this.timestamp.findKCSource(kcData);
        }
        return null;
    }

    draw(ctx) {
        for (let i = 0; i < this.courses.length; i++) {
            this.courses[i].draw(ctx);
        }
    }

    getHeight() {
        if (this.courses.length == 0) {
            return 0;
        }
        return this.courses[this.courses.length-1].getHeight() + this.courses[this.courses.length-1].getY();
    }
}