package com.pg.sboot.mvc.core.controller;

import java.io.IOException;
import java.time.LocalDateTime;
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
		// mp.put("Greet_EN", "Hello");
		mp.put("Cmplx", createEmps());
		return mp;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Map<String, String> login(String username, String password) throws IOException {
		Map<String, String> mp = new HashMap<String, String>();
		List<UserDocument> userList = createUsers();
		if (userList == null || userList.size() == 0) {
			mp.put("error_code", "-100");
			mp.put("error_msg", "Invalid UserId");
			mp.put("result", "Invalid UserId");
			return mp;
		}

		for (UserDocument user : userList) {
			if ((username.equals(user.getUserId())) && (password.equals(user.getUserPassword()))) {
				mp.put("error_code", "0");
				mp.put("error_msg", "success");
				mp.put("result", "Valid User");
				return mp;
			}
		}
		mp.put("error_code", "-100");
		mp.put("error_msg", "Invalid User details");
		mp.put("result", "Invalid User details");
		return mp;
	}
	
	/*@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public Map<String, List<ReportingTransDocument>> reports() throws IOException {
		
		Map<String, List<ReportingTransDocument>> mp = new HashMap<String, List<ReportingTransDocument>>();
		List<ReportingTransDocument> reportList = createReportTrans();

		if (reportList == null || reportList.size() == 0) {
			mp.put("result", null);
			return mp;
		}

		mp.put("result", reportList);
		return mp;

	}	*/
	
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public List<ReportingTransDocument> reports() throws IOException {
		
		Map<String, List<ReportingTransDocument>> mp = new HashMap<String, List<ReportingTransDocument>>();
		List<ReportingTransDocument> reportList = createReportTrans();

		if (reportList == null || reportList.size() == 0) {
			mp.put("result", null);
			return null;
		}

		mp.put("result", reportList);
		return reportList;

	}		

	private List<UserDocument> createUsers() {
		List<UserDocument> userList = new ArrayList<>();
		userList.add(new UserDocument("001", "pradhap", "pradhap"));
		userList.add(new UserDocument("002", "abc", "abc"));
		userList.add(new UserDocument("003", "asdf", "asdf"));
		userList.add(new UserDocument("004", "pg", "pg"));
		return userList;
	}

	static List<ReportingTransDocument> createReportTrans(){
		List<ReportingTransDocument> repTransList = new ArrayList<>();
		
		repTransList.add(new ReportingTransDocument("AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", true/*, LocalDateTime.now()*/));
		repTransList.add(new ReportingTransDocument("BBB", "BBB", "BBB", "BBB", "BBB", "BBB", "BBB", "BBB", "BBB", "BBB", true/*, LocalDateTime.now()*/));		
		repTransList.add(new ReportingTransDocument("C", "C", "C", "C", "C", "C", "C", "C", "C", "C", true/*, LocalDateTime.now()*/));		
		repTransList.add(new ReportingTransDocument("DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", true/*, LocalDateTime.now()*/));		
		repTransList.add(new ReportingTransDocument("EE", "EE", "EE", "EE", "EE", "EE", "EE", "EE", "EE", "EE", true/*, LocalDateTime.now()*/));		
		repTransList.add(new ReportingTransDocument("FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", "FFFFF", true/*, LocalDateTime.now()*/));				
		repTransList.add(new ReportingTransDocument("GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", "GGGGGG", true/*, LocalDateTime.now()*/));			
		repTransList.add(new ReportingTransDocument("HHHH", "HHHH", "HHHH", "HHHH", "HHHH", "HHHH", "HHHH", "HHHH", "HHHH", "HHHH", true/*, LocalDateTime.now()*/));					
		repTransList.add(new ReportingTransDocument("IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", "IIIIIIIIIIIIII", true/*, LocalDateTime.now()*/));					
		repTransList.add(new ReportingTransDocument("JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", "JJJJ", true/*, LocalDateTime.now()*/));							
		repTransList.add(new ReportingTransDocument("KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", "KKKKK", true/*, LocalDateTime.now()*/));			
		repTransList.add(new ReportingTransDocument("LLL", "LLL", "LLL", "LLL", "LLL", "LLL", "LLL", "LLL", "LLL", "LLL", true/*, LocalDateTime.now()*/));					
		repTransList.add(new ReportingTransDocument("MM", "MM", "MM", "MM", "MM", "MM", "MM", "MM", "MM", "MM", true/*, LocalDateTime.now()*/));				
		repTransList.add(new ReportingTransDocument("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", true/*, LocalDateTime.now()*/));				
		repTransList.add(new ReportingTransDocument("OOOO", "OOOO", "OOOO", "OOOO", "OOOO", "OOOO", "OOOO", "OOOO", "OOOO", "OOOO", true/*, LocalDateTime.now()*/));						
		repTransList.add(new ReportingTransDocument("PP", "PP", "PP", "PP", "PP", "PP", "PP", "PP", "PP", "PP", true/*, LocalDateTime.now()*/));						
		repTransList.add(new ReportingTransDocument("QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", "QQQQQQ", true/*, LocalDateTime.now()*/));						
		repTransList.add(new ReportingTransDocument("RR", "RR", "RR", "RR", "RR", "RR", "RR", "RR", "RR", "RR", true/*, LocalDateTime.now()*/));		
		
		return repTransList;
	}
	
	public class UserDocument {

		private String id;
		private String userId;
		private String userPassword;
		private String active;

		public UserDocument(String id, String userId, String userPassword) {
			this.id = id;
			this.userId = userId;
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
			return "_Id:" + this.id + " userId:" + this.userId + " userPassword:" + this.userPassword + " active:"
					+ this.active;
		}
	}

	private List<Manager> createEmps() {
		Employee dev1 = new Employee("DEV_001", "Dev");
		dev1.name = "Pradhap";
		Map aMap1 = new HashMap();
		aMap1.put("primary", new Address("9100", null, "Miami", "33178"));
		dev1.addressMap = aMap1;

		Employee dev2 = new Employee("DEV_002", "Dev");
		dev2.name = "Ganesan";
		Map aMap2 = new HashMap();
		aMap2.put("secondary", new Address("9805", null, "Doral", "33178"));
		dev2.addressMap = aMap2;

		Employee dev3 = new Employee("DEV_003", "Dev");
		dev3.name = "Pradhap";
		Map aMap3 = new HashMap();
		aMap3.put("primary", new Address("1402", null, "Brandon", "33511"));
		dev3.addressMap = aMap3;

		List<Employee> devs1 = new ArrayList<Employee>();
		devs1.add(dev1);
		devs1.add(dev2);

		List<Employee> devs2 = new ArrayList<Employee>();
		devs2.add(dev3);

		Manager mgr1 = new Manager("MGR_001", "Mgr", devs1);
		Manager mgr2 = new Manager("MGR_002", "Mgr", devs2);

		SuperManager smgr = new SuperManager("SMGR_001", "SuperMGR");

		mgr1.setReportManager(smgr);
		mgr2.setReportManager(smgr);

		List<Manager> mgrs = new ArrayList<>();
		mgrs.add(mgr1);
		mgrs.add(mgr2);

		smgr.setReportManager(null);
		// smgr.setSubs(mgrs);

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

class ReportingTransDocument {

	public static final String[] TOP_LEVEL_FIELDS = { "id", "status", "reasonCodes", "stream", "flow", "sourceUId",
			"sourceId", "sourceStatus", "regReportingRef", "receivedTs", "publishedTs", "executionTs", "lastUpdatedTs",
			"instructions", "rdsEligible" };

	public String id; // reghub generated unique id for each messages
	public String status; // reghub reporting status e.g. REPORTABLE, NON_REPORTABLE, EXCEPTION, PENDING,
	public String stream; // reporting stream (always 4 char) e.g. M2TR, M2PR, M2PO
	public String flow; // reporting asset class / product (always 3 char) e.g. CEQ (cash equities), CFI
	// source record fields
	public String sourceSystem; // upstream source system generated the message e.g. TPS, PRIMO
	public String sourceUId; // unique id for a message received from source e.g. OceanId for transaction
	public String sourceId; // trade/quote/order id
	public String sourceStatus; // always normalised to NEW, AMEND and CANCEL
	public String sourceVersion; // trade/quote/order version
	public String regReportingRef; // used as reporting id for trade/quote/order/transaction e.g. stream + flow +

	// date fields
	public LocalDateTime receivedTs; // timestamp when entity is received in Reghub
	public LocalDateTime publishedTs; // trade activity timestamp .i.e when upstream published on RIO
	public LocalDateTime executionTs; // trade generation/execution/booked timestamp
	public LocalDateTime lastUpdatedTs; // timestamp of last activity in reghub, classical updated timestamp for reghub

	private boolean rdsEligible; // flag that holds RDS Eligibility used by RDS Stream

	public Map<String, Object> info = new HashMap<String, Object>();

	public List<String> instructions = new ArrayList<String>();
	public List<String> reasonCodes = new ArrayList<String>();
	
	
	public ReportingTransDocument() {
	}
	
	public ReportingTransDocument(String id, String status, String stream, String flow, String sourceSystem,
			String sourceId, String sourceUId, String sourceStatus, String sourceVersion, String regReportingRef,
			boolean rdsEligible/*, LocalDateTime receivedTs*/) {
		this.id = id;
		this.status = status;
		this.stream = stream;
		this.flow = flow;
		this.sourceSystem = sourceSystem;
		this.sourceId = sourceId;
		this.sourceUId = sourceUId;
		this.sourceStatus = sourceStatus;
		this.sourceVersion = sourceVersion;
		this.regReportingRef = regReportingRef;
		this.rdsEligible = rdsEligible;
//		this.receivedTs = receivedTs;
				
	}

	public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}

	public boolean isRdsEligible() {
		return rdsEligible;
	}

	public void setRdsEligible(boolean rdsEligible) {
		this.rdsEligible = rdsEligible;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<String> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getSourceUId() {
		return sourceUId;
	}

	public void setSourceUId(String sourceUId) {
		this.sourceUId = sourceUId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public String getSourceVersion() {
		return sourceVersion;
	}

	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}

	public String getRegReportingRef() {
		return regReportingRef;
	}

	public void setRegReportingRef(String regReportingRef) {
		this.regReportingRef = regReportingRef;
	}

	public LocalDateTime getReceivedTs() {
		return receivedTs;
	}

	public void setReceivedTs(LocalDateTime receivedTs) {
		this.receivedTs = receivedTs;
	}

	public LocalDateTime getPublishedTs() {
		return publishedTs;
	}

	public void setPublishedTs(LocalDateTime publishedTs) {
		this.publishedTs = publishedTs;
	}

	public LocalDateTime getExecutionTs() {
		return executionTs;
	}

	public void setExecutionTs(LocalDateTime executionTs) {
		this.executionTs = executionTs;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return "_Id:" + this.id + " status:" + this.status + " lastUpdatedTs:" + this.lastUpdatedTs;
	}
}
