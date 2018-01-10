package com.pg.sboot.mvc.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAppController {

	@RequestMapping("/hello")
	public @ResponseBody Map<String, List<Manager>> sayHello() {
		Map<String, List<Manager>> mp = new HashMap<>();
		//mp.put("Greet_EN", "Hello");
		mp.put("Cmplx", createEmps());
		return mp;
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public Map<String,String> login(String username, String password) throws IOException {
			Map<String,String> mp = new HashMap<String,String>();		
			List<UserDocument> userList = createUsers();
			if(userList==null || userList.size()==0){
					mp.put("result", "Invalid UserId");
					return mp;
				}
			
			for(UserDocument user:userList){
				if( (username.equals(user.getUserId())) && (password.equals(user.getUserPassword())) ){
					mp.put("result", "Valid User");
					return mp;
				} 
			}
			mp.put("result", "Invalid UserId");
			return mp;
	}		

	private List<UserDocument> createUsers(){
		List<UserDocument> userList = new ArrayList<>();
		userList.add(new UserDocument("001", "pradhap", "pradhap"));
		userList.add(new UserDocument("002", "abc", "abc"));
		userList.add(new UserDocument("003", "asdf", "asdf"));
		userList.add(new UserDocument("004", "pg", "pg"));
		return userList;
	}
	
	public class UserDocument {

		private String id;
		private String userId;
		private String userPassword;
		private String active;
		
		public UserDocument(String id, String userId, String userPassword) {
			this.id = id;
			this.userId=userId;
			this.userPassword = userPassword;
					
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
		public String getActive() {
			return active;
		}
		public void setActive(String active) {
			this.active = active;
		}
		
		@Override
		public String toString() {		
			return  "_Id:"+ this.id+
					" userId:"+ this.userId+
					" userPassword:"+ this.userPassword+
					" active:"+ this.active;
		}
	}
	
	private List<Manager> createEmps() {
		Employee dev1 = new Employee("DEV_001", "Dev");
		dev1.name = "Pradhap";
		Map aMap1 = new HashMap();
		aMap1.put("primary",new Address("9100",null,"Miami","33178"));
		dev1.addressMap=aMap1;
		
		Employee dev2 = new Employee("DEV_002", "Dev");
		dev2.name = "Ganesan";
		Map aMap2 = new HashMap();
		aMap2.put("secondary",new Address("9805",null,"Doral","33178"));
		dev2.addressMap=aMap2;
		
		Employee dev3 = new Employee("DEV_003", "Dev");
		dev3.name = "Pradhap";
		Map aMap3 = new HashMap();
		aMap3.put("primary",new Address("1402",null,"Brandon","33511"));
		dev3.addressMap=aMap3;

		
		List<Employee> devs1 = new ArrayList<Employee>();
		devs1.add(dev1);
		devs1.add(dev2);

		List<Employee> devs2 = new ArrayList<Employee>();
		devs2.add(dev3);

		Manager mgr1 = new Manager("MGR_001", "Mgr",devs1);
		Manager mgr2 = new Manager("MGR_002", "Mgr",devs2);

		SuperManager smgr = new SuperManager("SMGR_001", "SuperMGR");

		mgr1.setReportManager(smgr);
		mgr2.setReportManager(smgr);

		List<Manager> mgrs = new ArrayList<>();
		mgrs.add(mgr1);
		mgrs.add(mgr2);

		smgr.setReportManager(null);
		//smgr.setSubs(mgrs);
		
		List<SuperManager> smgrs = new ArrayList<>();
		smgrs.add(smgr);
		return mgrs;
	}

}

class Person {
	String name = "";
	Integer age = 0;
	Map<String, Address> addressMap = new HashMap<>();

}

class Employee extends Person {
	String empId;
	String role;

	public Employee(String empId, String role) {
		this.empId = empId;
		this.role = role;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

class Manager extends Employee {
	List<? extends Employee> subs = null;

	public List<? extends Employee> getSubs() {
		return subs;
	}

	public void setSubs(List<? extends Employee> subs) {
		this.subs = subs;
	}

	Employee reportManager = null;

	public Manager(String empId, String role, List<Employee> subs) {
		super(empId, role);
		this.subs = subs;
	}

	public Employee getReportManager() {
		return reportManager;
	}

	public void setReportManager(Employee reportManager) {
		this.reportManager = reportManager;
	}
}

class SuperManager extends Manager {
	public SuperManager(String empId, String role) {
		super(empId, role, null);
	}
}

class Address {
	String street1;
	String street2;
	String city;
	String zip;

	public Address(String str1, String str2, String city, String zip) {
		this.street1 = str1;
		this.street2 = str2;
		this.city = city;
		this.zip = zip;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}