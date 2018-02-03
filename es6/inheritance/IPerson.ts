class TestPerson{

    p: IPerson;
    constructor(){
        //this.p = new Person();
    }

    test(){
        const ip: IPerson = {fname:''};
        const props: string[] = Object.getOwnPropertyNames(ip);
        props.forEach( p => console.log(p));
    }


}

export interface IPerson {

    fname?: string;
    lname?: string;
    dob?: string;

}

export class Person implements IPerson {

    constructor(){
        this.dob = undefined;
        this.fname = undefined;
        this.lname = undefined;
    }

    get dob(){
        return this.dob;
    }
    set dob(dob){
        this.dob = dob;
    }

    get fname(){
        return this.fname;
    }
    set fname(fn){
        this.fname = fn;
    }

    get lname(){
        return this.lname;
    }
    set lname(ln){
        this.lname = ln;
    }
}



const t = new TestPerson();
t.test();