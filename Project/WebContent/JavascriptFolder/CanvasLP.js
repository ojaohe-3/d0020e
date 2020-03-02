class CanvasLP {

    constructor(previousTimestamp) {
        this.courses = [];
        this.timestamp = new Timestamp(previousTimestamp);
    }

    /**
     * Add a course object to this LP.
     * @param courseObject
     */
    addCourse(courseObject) {
        this.courses.push(courseObject);
        if (this.courses.length > 0) {
            let test = true;
        }

        // Add all generated KCs to this timestamp.
        courseObject.dockPointsDev.forEach((value) => {
            this.timestamp.addKCCourseCombo(value.KC,courseObject);
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

    draw(ctx) {
        for (let i = 0; i < this.courses.length; i++) {
            this.courses[i].draw(ctx);
        }
    }
}