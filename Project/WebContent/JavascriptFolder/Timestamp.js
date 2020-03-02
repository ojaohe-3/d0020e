/**
 * This class represents a collection of all KCs that has been generated
 * until a certain LP. The timestamps keeps a hash map of all KCs in its own LP
 * and a pointer to the previous LP.
 *
 * Searching for a course that generated a KC should take approx. O(n) time.
 */
class Timestamp {

    constructor(predecessor) {
        this.KCOut = new Map();
        this.courses = [];
        this.predecessor = predecessor;
    }

    /**
     * Add a kc and a course to this timestamp.
     * @param kcObject - Of type KCObject
     * @param courseObject - Of type CourseObject
     */
    addKCCourseCombo(kcObject, courseObject) {
        if (!this.findKCCreator(kcObject)) {
            // We get here if no other course has already generated the KC.
            let search = kcObject.name +";"+ kcObject.taxonomyLevel;
            this.courses.push(courseObject);
            this.KCOut.set(search, courseObject);
        }
    }


    /**
     * This finds a course that created a KC at a specific level.
     */
    findKCCreator(kcObject) {
        let course = null;
        let searchTerm = kcObject.name + ";" + kcObject.taxonomyLevel;
        if (this.KCOut.has(searchTerm)) {
            return this.courses[this.KCOut.get(searchTerm)];
        } else if (this.predecessor != null) {
            // We could not find a course that created the KC in this LP,
            // but the KC could exist in some older course.
            return this.predecessor.findKCCreator(kcObject);
        }
        return null;  // We did not find a course that creates the kc.
    }
}