class CanvasLP {

    constructor(previousTimestamp) {
        this.courses = [];  // A list of ALL courses
        this.timestamp = new Timestamp(previousTimestamp); // A hash map of all KC dock point.
    }

    getMiddlePoint() {
        let point1 = this.courses[0].getFirstIntermittenPoint();
        let point2 = this.courses[0].getEndIntermittenPoint();
        return [point1, point2];
    }

    /**
     * Add a course object to this LP.
     * @param courseObject
     */
    addCourse(courseObject) {
        this.courses.push(courseObject);

        // Add all generated KCs to this timestamp.
        courseObject.data.Developed.forEach((value) => {
            let dockingPoint = courseObject.addOutGoingDockingPoint(value);

            // This function will only create A KC if no docking point exist already.
            this.timestamp.addKCSource(value,dockingPoint);
        });
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
}