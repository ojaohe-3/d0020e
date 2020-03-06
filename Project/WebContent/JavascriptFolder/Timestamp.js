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
        //this.courses = [];
        this.predecessor = predecessor;
    }

    /**
     * Add a kc and a course to this timestamp.
     * @param kcData - This is the KC data itself, it's just a struct with all the data.
     * It is not stored for later use, but there is a reference in the docking point.
     * @param dockingPoint - This is the docking point of the course that created the KC.
     */
    addKCSource(kcData, dockingPoint) {
        if (!this.findKCSource(kcData)) {
            // We get here if no other course has already generated the KC.
            let search = kcData.name +";"+ kcData.taxonomyLevel;
            //this.courses.push(courseObject);
            this.KCOut.set(search, dockingPoint);
        }
    }

    deleteKCSource(dockingPoint) {
        this.KCOut.delete(dockingPoint.kcData.name + ";" + dockingPoint.kcData.taxonomyLevel);
    }


    /**
     * This finds a course docking point that can be connected to a KC link.
     *
     * @param kcData - A struct containing all the KC info.
     * @returns {null|V} - Either null or a docking point.
     */
    findKCSource(kcData) {
        let course = null;
        let searchTerm = kcData.name + ";" + kcData.taxonomyLevel;
        if (this.KCOut.has(searchTerm)) {
            return this.KCOut.get(searchTerm);
        } else if (this.predecessor != null) {
            // We could not find a course that created the KC in this LP,
            // but the KC could exist in some older course.
            return this.predecessor.findKCSource(kcData);
        }
        return null;  // We did not find a course that creates the kc.
    }
}