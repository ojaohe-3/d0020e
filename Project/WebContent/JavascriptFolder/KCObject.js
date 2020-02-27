/**
 * KC renderer object
 */
class KCObject{
    constructor(thickness,data) {
        this.thickness = thickness;
        this.snapPoints = [];
        this.data = data;


    }
    generateSnappoints(snaps){
        this.snapPoints = snaps;//temporary
    }
    draw(ctx){
        ctx.beginPath();
        ctx.moveTo(this.x1,this.y1);
        this.snapPoints.forEach(function (point) {
            ctx.lineTo(point.x, point.y);
        });
        ctx.lineTo(this.x2, this.y2);
        ctx.stroke();
    }

}