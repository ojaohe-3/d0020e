/**
 * KC renderer object
 */
class KCObject{
    constructor(thickness,data) {
        this.thickness = thickness;
        this.snapPoints = [];
        this.data = data;
    }
    setSnapPoint(snaps){
        this.snapPoints = snaps;//temporary
    }
    draw(ctx){
        ctx.save();
        saveMatrix();
        translate(0,0);
        ctx.lineWidth = this.thickness;
        ctx.beginPath();
        ctx.moveTo(this.x1,this.y1);
        this.snapPoints.forEach(function (point) {
            ctx.lineTo(point.x, point.y);
        });
        ctx.lineTo(this.x2, this.y2);
        ctx.stroke();
        ctx.restore();
        restoreMatrix();
    }

}