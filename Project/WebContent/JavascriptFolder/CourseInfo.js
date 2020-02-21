/**
 * this represents the popup window that is shown when the user wants to see the course info inside canvas.
 *
 * @author Robin
 */
class CourseInfo {


    /**
     * Just a constructor.
     * @param courseInfo - This should contain course infor as well as developed and required KCs.
     * @param position - x and y position in a struct.
     */
    constructor(courseInfo, position) {
        this.info = courseInfo;
        this.x = position.x;
        this.y = position.y;
        this.backgroundColor = "#404040";
        this.lineColor = "#000000";
        this.foregroundColor = "#FFFFFF";
        this.width = 500;
        this.height = 300;

        this.font1 = 'italic '+(this.width)*0.05+'pt Calibri';
        this.font2 = 'italic '+(this.width)*0.02+'pt Calibri';
    }

    /**
     * Draw the course window.
     * @param ctx
     */
    draw (ctx) {
        ctx.save();

        // Draw grey rectangle.
        ctx.fillStyle = this.backgroundColor;
        ctx.fillRect(this.x,this.y,this.width,this.height);

        // Draw outline
        ctx.strokeStyle = this.lineColor;
        ctx.lineWidth = 2;
        ctx.strokeRect(this.x,this.y,this.width,this.height);

        // Draw headline.
        ctx.fillStyle = "#FFFFFF";
        ctx.font = this.font1;
        ctx.fillText(this.info["name"], this.x+this.width/2-ctx.measureText(this.info["name"]).width/2,this.y+this.height*0.1);

        // Draw info box.
        this.drawInfo(ctx);
        ctx.restore();
    }

    /**
     * Draw the window with general information.
     * @param ctx
     */
    drawInfo(ctx) {
        // Draw the white square.
        ctx.save();
        let padding = {left : 0.06,right : 0.3, up : 0.2,down : 0.07};  // x-top, x-bottom, y-top, y-bottom
        ctx.fillStyle = this.foregroundColor;
        ctx.translate(this.x+padding["left"]*this.width,this.y+padding["up"]*this.height);
        ctx.fillRect(
            0,
            0,
            this.width-this.width*(padding["left"]+padding["right"]),
            this.height-this.height*(padding["up"]+padding["down"])
        );
        ctx.fillStyle = this.lineColor;
        ctx.font = this.font2;
        //ctx.fillText(this.info["description"], 0,10);
        this.drawtextBox(ctx, this.info["description"],5);
        ctx.restore();
    }

    drawtextBox(ctx, str, boxWidth) {
        let lineHeight = 10;
        let width = ctx.measureText(str).width;
        let subString = str.substr(0, str.length);
        //ctx.fillText(str, 0,10);
        for (let i = 0, start = 0, lines = 0; i < 2; i++) {
            ctx.fillText(str, 0, 20);
            /*subString = str.substr(start, i);
            if (subString > boxWidth) {
                lines++;
                ctx.fillText(str, 0, 20);
                start = i;
            }*/
        }
    }

}

