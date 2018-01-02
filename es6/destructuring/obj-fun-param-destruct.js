class Employee{
    constructor(id,dep,role){
        this._id = id
        this._dep = dep
        this._role = role
    }
    get dep(){
        return this._dep;
    }
    get role(){
        return this._role;
    }
}

devEmp = new Employee("1111","IT","Developer")
leadEmp = new Employee("2222","IT","Lead")
manEmp = new Employee("3333","IT","Manager")

//object parameter can be destructured and assigned to local variables in-line parameter
getEmpLevel_DestructWay = ({dep:empDep, role: empRole} ) => {
    if(empDep != 'IT') return -1;
    switch(empRole){
        case("Developer") : return 1;
        case("Lead"): return 2;
        case("Manager"):return 3;
        default:return -1;
    }
}


//legacy way pass object
function getEmpLevel(emp){
    let department = emp.dep
    let role = emp.role;
    if(department != 'IT') return -1;
    switch(role){
        case("Developer") : return 1;
        case("Lead"): return 2;
        case("Manager"):return 3;
        default:return -1;
    }
}

console.log('lead level' + getEmpLevel(leadEmp));
console.log('getEmpLevel_DestructWay dev level ' + getEmpLevel(devEmp));
