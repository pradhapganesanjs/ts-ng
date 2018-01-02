class Point{
    constructor(x,y){
        this.x = x;
        this.y = y;
    }
    toString(){
        return `${this.x}, ${this.y}`;
    }
}

class ColorPoint extends Point{
    constructor(x,y,color){
        super(x,y);
        this.color = color;
    }
    toString(){
        return super.toString()+' '+this.color;
    }

    testInhertiProp(){
        console.log(`${this.toString()}`);
        console.log('ColorPoint instanceOf Point '+ (this instanceof Point))
    }
}

const cp  = new ColorPoint(10,10,"Green");
cp.testInhertiProp();
