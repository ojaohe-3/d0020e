//<script src="https://unpkg.com/konva@4.1.4/konva.min.js"></script>
class CourseObject{
    get data{
        return JSON.parse(this.data);
    }
    set data(data){
        this.data = data;
    }
    constructor(data) {
        this.data = data;
    }
    draw(ctx){
        //draw in canvas
        ctx.drawr
    }

}