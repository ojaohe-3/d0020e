class CourseObject extends Konva.Rect{
    SNAP = 100;
    get data(){
        return JSON.parse(this.data);
    }
    set data(data){
        this.data = data;
        if(data["lp"] === "ONE"){
            this.x = 0;
        }else if(data["lp"] === "TWO"){
            this.x = this.SNAP;
        }else if(data["lp"] === "THREE"){
            this.x = this.SNAP*2;
        }else if(data["lp"] === "FOUR"){
            this.x = this.SNAP*3;
        }
    }
    constructor(data,conf) {
        super(conf);
        this.data = data;

    }

}