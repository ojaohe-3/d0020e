/**
 * The KC link is just a link between two course docking points that can be drawn on the screen.
 * Since the link is mutual, both ends can draw the line.
 *
 * In theory, it should be possible to create one-to-many and many-to-one links since
 * one link can be created for every course that produces the same KC at the same level.
 * TODO implement many-to-one KCs.
 */
class KCLink {



    /**
     *  Create A KC link between two docking points. There is actually no point in having an entire object
     *  dedicated to this stuff, but I like it and it's a neat solution.
     * @param developingCourse - The course that produces this KC.
     * @param requiringCourse - The course that requires this KC
     */
    constructor() {
        this.inPoint = null;
        this.outPoint = null;
    }

    /**
     * Connect the ingoing end of this KC link to a docking point.
     * @param dockingPoint
     */
    setIngoingDockingPoint(dockingPoint) {
        this.inPoint = dockingPoint;
    }

    /**
     * Connect the outgoing end of this KC link to a docking point.
     * @param dockingPoint
     */
    setOutgoingDockingPoint(dockingPoint) {
        this.outPoint = dockingPoint;
    }

    /**
     * Draw this KC link. It should start drawing from the receiving docking point, since it can then
     * traverse back through every LP until it reaches the course that created the KC.
     * @param ctx
     * @param caller - The docking point that called this method.
     */
    drawLink(ctx, caller) {
        // Note to self: The line should only be drawn if:
        //      Only one of the docking points are expanded.
        //      Both docking points are expanded and the docking point that called draw was
        //          in the developed end.
        // TODO add caller to the draw method.
        // TODO path finding algorithm.
        // 1: take the output
        // 2: find a way out of the LP
        // 3: find a way into the previous LP
        // 4: if the input belongs to current LP: enter the input and Bob's your uncle.
        // 5: repeat 2.
    }
}

