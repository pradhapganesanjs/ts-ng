package com.pg.sboot.mvc.core.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyAppController {
	List<ReportingTransDocument> reportList = null;
	List<UserDocument> userList = null;
	{
		reportList = createReportTrans(100);
		userList = createUsers();
	}

	@RequestMapping("/hello")
	public @ResponseBody Map<String, List<Manager>> sayHello() {
		Map<String, List<Manager>> mp = new HashMap<>();
		// mp.put("Greet_EN", "Hello");
		mp.put("Cmplx", createEmps());
		return mp;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public UserDocument signin(@RequestBody SigninCred scred) {
		String userName = scred.getUserName();
		String password = scred.getPassword();

		System.out.println("updated signin " + userName + " " + password);
		UserDocument userD = validateSignin(userName, password);

		return userD;
	}

	private UserDocument validateSignin(String userName, String password) {

		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return null;
		}

		try {
			Optional<UserDocument> optUserDoc = userList.stream()
					.filter(user -> userName.equals(user.getUserId()) && password.equals(user.getUserPassword())).findAny();
			if (optUserDoc.isPresent()) {
				return optUserDoc.get();
			}
		} catch (Exception e) {
			System.err.println("exception e" + e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Map<String, String> login(String username, String password) throws IOException {
		Map<String, String> mp = new HashMap<String, String>();
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

	/*
	 * @RequestMapping(value = "/reports", method = RequestMethod.GET) public
	 * Map<String, List<ReportingTransDocument>> reports() throws IOException {
	 * 
	 * Map<String, List<ReportingTransDocument>> mp = new HashMap<String,
	 * List<ReportingTransDocument>>(); List<ReportingTransDocument> reportList =
	 * createReportTrans();
	 * 
	 * if (reportList == null || reportList.size() == 0) { mp.put("result", null);
	 * return mp; }
	 * 
	 * mp.put("result", reportList); return mp;
	 * 
	 * }
	 */

	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public List<ReportingTransDocument> reports() throws IOException {

		Map<String, List<ReportingTransDocument>> mp = new HashMap<String, List<ReportingTransDocument>>();

		if (reportList == null || reportList.size() == 0) {
			mp.put("result", null);
			return null;
		}

		mp.put("result", reportList);
		return reportList;

	}

	@RequestMapping(value = "/reportsbycrit", method = RequestMethod.POST)
	public List<ReportingTransDocument> reportsByCrit(@RequestBody(required = false) ReportingTransDocument report)
			throws IOException {

		if (null == report)
			return reportList;
		System.out.format("report crit %s", report.toString());

		List<ReportingTransDocument> returnReportList = reportList.parallelStream().filter(each -> {
			return checkMatch(report.getId(), each.getId()) || checkMatch(report.getFlow(), each.getFlow())
					|| checkMatch(report.getSourceId(), each.getSourceId())
					|| checkMatch(report.getStatus(), each.getStatus())
					|| checkMatch(report.getSourceStatus(), each.getSourceStatus())
					|| checkMatch(report.getSourceSystem(), each.getSourceSystem())
					|| checkMatch(report.getSourceUId(), each.getSourceUId())
					|| checkMatch(report.getSourceVersion(), each.getSourceVersion());
		}).collect(Collectors.toList());
		System.out.println("return list count " + returnReportList.size());
		return returnReportList;
	}

	private boolean checkMatch(String repKey, String itemKey) {
		System.out.format(" repkey: %s , itemKey: %s", repKey, itemKey);
		return null != repKey && repKey.equalsIgnoreCase(itemKey);
	}

	private List<UserDocument> createUsers() {
		List<UserDocument> userList = new ArrayList<>();
		userList.add(new UserDocument("001", "pradhap", "pradhap"));
		userList.add(new UserDocument("002", "abc", "abc"));
		userList.add(new UserDocument("003", "asdf", "asdf"));
		userList.add(new UserDocument("004", "pg", "pg"));
		return userList;
	}

	static List<ReportingTransDocument> createReportTrans(final int COUNT_THRESHOLD_PARAM) {
		List<ReportingTransDocument> repTransList = new ArrayList<>();

		final int COUNT_THRESHOLD = COUNT_THRESHOLD_PARAM <= 0 ? 1000 : COUNT_THRESHOLD_PARAM;

		for (int i = 0; i < COUNT_THRESHOLD; i++) {
			repTransList.add(new ReportingTransDocument("ID_" + i, genRandString(), genRandString(), genRandString(),
					genRandString(), genRandString(), genRandString(), genRandString(), genRandString(),
					genRandString(), genRandBoolean()));
		}

		return repTransList;
	}

	private static boolean genRandBoolean() {
		return Math.random() > 0.5 ? true : false;
	}

	private static String genRandString() {
		int strLen = (int) (Math.random() * 10.0);
		strLen = strLen == 0 ? 10 : strLen;

		StringBuffer strBuf = new StringBuffer();

		for (int c = 0; c < strLen; c++) {
			char chr = (char) (65 + (int) (Math.random() * 26.0));
			strBuf.append(chr);
		}
		return strBuf.toString();
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

class SigninCred {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
