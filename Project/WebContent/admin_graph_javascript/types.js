/**
 * 
 */
class Course {
    constructor(name, code, year, lp, credits, developed, required, x, y) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.year = year;
        this.lp = lp;
        this.developed = developed;
        this.required = required;
        this.x = x;
        this.y = y;
    }
}
        
class KC {
    constructor(name, taxonomyLevel) {
        this.name = name;
        this.taxonomyLevel = taxonomyLevel;
        this.developed_in = [];
        this.required_in = [];
    }
    addDeveloped_in(course) {
        this.developed_in.push(course);
    }
    addRequired_in(course) {
        this.required_in.push(course);
    }
}

/*
class Program {
    constructor(name, readingPeriods) {
        this.name = name;
        this.readingPeriods = readingPeriods;
        this.courses = [];
        this.KCs = [];
    }
}
*/
