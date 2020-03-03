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
    isInside(pos,dpi){
        if(this.snapPoints.length>0){
        let c = this.snapPoints[this.snapPoints.length - 1];
            for (let i = this.snapPoints.length; i > this.snapPoints.length; i--) {
                let wx = this.x+this.thickness/2;
                let wy = this.y+this.thickness/2;

                if(pos.x > c.x/dpi && pos.x < wx/dpi && pos.y < wy/dpi && pos.y > c.y/dpi)
                    return true;
                c = this.snapPoints[i];
            }

        }else
            return false;
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
        if(this.highLight)
            ctx.strokeStyle = "orange";
        else
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