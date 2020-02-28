/**
 * KC renderer object
 */
class KCObject{
    constructor(thickness,data) {
        this.thickness = thickness;
        this.snapPoints = [];
        this.data = data;
        this.highLight = false;
    }
    setSnapPoint(snaps){
        this.snapPoints = snaps;//temporary
    }
    isInside(){
        this.snapPoints.f
    }
    onHover(e,ctx){
        //write to html
        this.highLight = true;
        draw(ctx);
    }
    onExit(e,ctx){
        //write to html
        this.highLight = false;
        draw(ctx);
    }

    draw(ctx){
        ctx.save();
        saveMatrix();
        translate(0,0);
        ctx.lineWidth = this.thickness/2;
        ctx.strokeStyle = "orange";
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