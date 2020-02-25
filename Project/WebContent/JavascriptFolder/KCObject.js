class KCObject{
    constructor(kcTarget,data) {
        this.data = data;
        this.x1 = kcTarget.x1;
        this.x2 = kcTarget.x2;
        this.y1 = kcTarget.y1;
        this.y2 = kcTarget.y2;
        this.snapPoints = kcTarget.snapPoints;

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