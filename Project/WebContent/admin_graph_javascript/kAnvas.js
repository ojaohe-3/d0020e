/**
 * 
 */
class KAnvas {
    
    constructor() {
        /*this.width = window.innerWidth * 5;
        this.height = window.innerHeight * 5;*/
        
        this.width = 8000;
        this.height = 3000;
        
        if(typeof window.stage !== "undefined") {
            window.stage.destroyChildren();
        }
        
        window.stage = new Konva.Stage({
            container: 'g_container',
            width: this.width,
            height: this.height
        });

        this.layer1 = new Konva.Layer();
        this.layer2 = new Konva.Layer();
        
        
        
        /*this.readingPeriods = 12;
        this.inParallel = 3;*/
        this.boxSize = 200;
        
        this.spaceBetweenX = 70;
        this.spaceBetweenY = 50;
        

        /*this.grid = new Array(this.readingPeriods);
        for(var i = 0; i < this.grid.length; i++) {
            this.grid[i] = new Array(this.inParallel);
        }*/
        
        this.xPos = this.spaceBetweenX;
        this.yPos = this.spaceBetweenY;
        this.lineChannelY = 2 * this.spaceBetweenY + this.boxSize - this.spaceBetweenY / 2;
        
        this.xSpace = this.spaceBetweenX;
        this.ySpace = this.spaceBetweenY;
        /*for(var i = 0; i < this.grid.length; i++) {
            for(var j = 0; j < this.inParallel; j++) {
                this.grid[i][j] = [this.xPos, this.yPos];
                this.yPos += this.boxSize + this.ySpace;
            }
            this.xPos += this.boxSize + this.xSpace;
            this.yPos = this.spaceBetweenY;
        }
        */
        //this.xPos = this.spaceBetweenX;
        //this.yPos = this.spaceBetweenY;
        this.position = 0;
        
        this.courses = [];
        this.KCs = [];
        this.xKCposxD = [];
        
    }
    
   
        
        
        
       
          
    handleResponse(data) {
        
    	console.log("Handle response is running");
    	
        //var data = JSON.parse(data);
        
        let year = data['year']; // Don't know why year sometimes is find using data.name... 
        let offsetYear = 0;
        let currentYear = year;
        /*REQ = new Map();
        let intersection = [];*/

        

        //console.log(data);
        //console.log(typeof data + " year : " + year);
        
        const t = this;
        
        var position = this.position;
        
        var takenXpos = [];
        var uniqueXpos = [];
        
        data['Courses'].forEach(function (item, index,arr){
            const period = new Map();
            period.set('ONE',0);
            period.set('TWO',0);
            period.set('THREE',0);
            period.set('FOUR',0);

            //======================== YEAR PARTITIONING========================
            // All courses should, in theory, be sorted after year. We can therefore reset the study periods when
            // The next course has a new year.
            if (item.year !== currentYear) {
              offsetYear = item.year-year;
              for (let [key,value] of period) {
                period.set(key,0);
              }
              currentYear = item.year;
            }

            //let x = xPos;
            //let hTemp = period.get(item.lp);
            //let y = yPos;
            //period.set(item.lp, hTemp + 1);

            var x = t.spaceBetweenX;
            var y = 0;

            //======================== LP PARTITIONING ========================
            //set x axis
            if(item.lp === "ONE"){
              x+= t.boxSize* 1.4 * offsetYear*4;
            }else if(item.lp === "TWO"){
              x += t.boxSize * 1.4 * (1+offsetYear*4);
            }else if(item.lp === "THREE"){
              x += t.boxSize * 1.4 * (2+(offsetYear-1)*4);
            }else if(item.lp === "FOUR"){
              x += t.boxSize * 1.4 * (3+(offsetYear-1)*4);
            }
            
            y = t.spaceBetweenY;
            if(takenXpos.includes(x)) {
                y += (t.boxSize + t.spaceBetweenY) * occurencies(takenXpos, x) ; 
            } else {
                uniqueXpos.push([x, item.year, item.lp]);
            }
            
            takenXpos.push(x.valueOf());
            

            let obj = new Course(item.name, item.courseCode, item.year, item.lp, item.credit, item.Developed, item.Required, x.valueOf(),y.valueOf());
            
            /*console.log(obj.name + " x:" + obj.x + " y:" + obj.y);
            console.log(obj.developed);
            console.log(obj.required);
            console.log(" ---------------------");
            */
            t.layer1.add(createCourseBox(obj.name, obj.code, t.boxSize, obj.x, obj.y, obj.year, obj.lp));
            t.courses.push([obj, item.Developed, item.Required]);
            
            for(var i = 0; i < obj.developed.length; i++) {
                //console.log("in loop dev" + obj.name);
                var item = obj.developed[i];
                if(kcExistsIn(t.KCs, item.name, item.taxonomyLevel)) {
                    t.KCs[getIndexOfKC(t.KCs, item.name, item.taxonomyLevel)][2].push([obj.x, obj.y]);
                } else{
                    t.KCs.push([item.name, item.taxonomyLevel, new Array(), new Array()]);
                    t.KCs[getIndexOfKC(t.KCs, item.name, item.taxonomyLevel)][2].push([obj.x, obj.y]);
                }
            }
            
            for(var i = 0; i < obj.required.length; i++) {
                //console.log("in loop req " + obj.name);
                var item = obj.required[i];
                if(kcExistsIn(t.KCs, item.name, item.taxonomyLevel)) {
                    t.KCs[getIndexOfKC(t.KCs, item.name, item.taxonomyLevel)][3].push([obj.x, obj.y]);
                } else{
                    t.KCs.push([item.name, item.taxonomyLevel, new Array(), new Array()]);
                    t.KCs[getIndexOfKC(t.KCs, item.name, item.taxonomyLevel)][3].push([obj.x, obj.y]);
                }
            }
            
        });
        
        
        // for each KC
        t.KCs.forEach(function (item, index,arr){
            var i = index;
            var a = arr;
            
            // for each Developed
            item[2].forEach(function(item, index, array) {
                var dev = item;
                // for each Required
                a[i][3].forEach(function(item, index, array) {
                    //console.log(item);
                    t.layer2.add(createLine(dev, item, t.boxSize, t.lineChannelY, t, a[i][0], a[i][1]));
                });

            });
            
            /*// For each required, check that it is developed somewhere
            item[3].forEach(function(item, index, array) {
                var req = item;
                
            
            });  */       
        });
        console.log("KCs should have been redrawn");
        console.log(t.KCs);
        
        // Checking that required KCs is developed
        var str = "";
        t.KCs.forEach(function (item, index, arr){
            var i = index;
            var a = arr;
            
            // for each Required
            item[3].forEach(function(item, index, array) {
                var req = item;
                //console.log(req);
                if(a[i][2].length == 0) {
                    str += a[i][0] + ": " + a[i][1] + ", is required"+/*in " + req.courseCode + "*/" but is not developed </br>";
                }

            });     

        });
        
        document.getElementById("lost_kcs").innerHTML = str;
        
        
        
        //console.log(t.KCs);
        
        // Adding the green add course box was not easy... 
        var readingPeriods = data.credits / 15;
        var cYear = data.year;
        var cLP = data.lp;
        var eLP = 1;
        for(var i = 0; i < readingPeriods + 1; i++) {
            var oYear = cYear - data.year;
            var x = t.spaceBetweenX;
            if(cLP === "ONE"){
                x+= t.boxSize* 1.4 * oYear * 4;
                cLP = "TWO";
                eLP = 1;
                //console.log("here x= "+ x + " clp : "+ cLP);
            }else if(cLP === "TWO"){
                x += t.boxSize * 1.4 * (1+oYear*4);
                cLP = "THREE"
                eLP = 2;
            }else if(cLP === "THREE"){
                x += t.boxSize * 1.4 * (2+(oYear-1)*4);
                cLP = "FOUR";
                eLP = 3;
                //cYear++;
            }else if(cLP === "FOUR"){
                x += t.boxSize * 1.4 * (3+(oYear-1)*4);
                cLP = "ONE"
                eLP = 4;
            }
            //console.log(cLP + " " + x);
            
            
            t.layer1.add(addCourseBox(cYear, eLP, t.boxSize, x, t.spaceBetweenY + (t.boxSize + t.spaceBetweenY) * occurencies(takenXpos, x)));
            if(cLP === "THREE") {
                cYear++;
            }
        }
    
        
        window.stage.add(t.layer2);
        window.stage.add(t.layer1);
        
    }
                                
                                
}