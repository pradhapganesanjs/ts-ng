package com.pg.springb.front.util;

public class FrontierConstants {
	
	public static final String CLIENT_JOB_RUNNING = "RUNNING";
	public static final String CLIENT_JOB_READY = "READY";
	public static final String CLIENT_REPORT_TYPE_INTERVAL = "INTERVAL";
	public static final String CLIENT_REPORT_TYPE_DAILY = "DAILY";
	public static final String UPLOADED_FILE_EXTENSION = ".xlsx";
	public static final String REPORT_FILE_EXTENSION = ".xlsx";
	public static final String CSV_SEPARATOR="|";
	public static final String CSV_EXTENSION=".csv";
	public static final String PSV_EXTENSION=".psv";
	public static final String XLSX_EXTENSION=".xlsx";
	public static final String TRANSACTION_STATUS_REPORTABLE="REPORTABLE";
	public static final String TRANSACTION_STATUS_REPORTED="REPORTED";
	public static final String ARCHIVE_FOLDER="archive/";
	public static final String ERROR_FOLDER="error/";
	public static final String FILE_NAME_SEPARATOR="-";
	
	public static final String[] CLIENT_CONFIG_XL_HEADER={"CLIENT_ID","GFCID","LEI","ACTIVE"};
	public static final String[] REPORT_CONFIG_XL_HEADER={"CLIENT_ID","ORGANIZATION",
														  "INTERVAL_EMAIL_TO","INTERVAL_EMAIL_CC","INTERVAL_EMAIL_BCC","INTERVAL_FILE","INTERVAL_MIN",
														  "DAILY_EMAIL_TO","DAILY_EMAIL_CC","DAILY_EMAIL_BCC","DAILY_FILE","DAILY_TIME",
														  "WEEKLY_FILE","DAY_OF_WEEK",
														  "HASH_KEY","MTLS"};
	public static final String[] REPORT_CONFIG_OPT_HEADER={"WEEKLY_FILE","DAY_OF_WEEK","HASH_KEY","MTLS"};
	public static final String[] USER_XL_HEADER={"USER_ID","USER_PASSWORD","ACTIVE"};
	public static final String[] DELIVERY_REPORT_HEADER={"CLIENT_ID","REPORT_TYPE","EMAIL_TO","EMAIL_CC","EMAIL_BCC","EMAIL_TS","EMAIL_SUBJECT","ATTACHMENT"};
	public static final String[] RESEND_CONFIG_HEADER={"CLIENT_ID","REPORT_TYPE","REPORT_DATE","REPORT_TIME","FROM_DATE","FROM_TIME"};
	public static final String[] REPORT_FIELD_HEADER={"REPORT_FIELD","ATTRIBUTE_NAME","ATTRIBUTE_LEVEL"};
	public static final String REPORTINT_FIELD_CACHE = "REPORTINT_FIELD_CACHE";
	
	public static final String[] REPORT_FOLDERS={"interval/","daily/","email_delivery/","export/","resend/"};
	public static final String[] UPLOAD_FOLDERS={"client_config/","report_config/","resend_config/","fields_config/","users_config/"};
	public static final String[] SUB_FOLDERS={"archive/","error/"};
	public static final String[] FRONTIER_COLLECTIONS={"client_lei_gfcid_config","reporting_fields","report_schedule_config","email_sent_log","frontier_users","client_email_process_config","active_host","file_process_log","resend_report_config"};
	
	public static final String[] UPLOAD_STATUS={"UPLOADED","PROCESSED","PROCESS_FAILED"};
	public static final String[] UPLOAD_DOC_TYPE={"CLIENT_CONFIG","REPORT_CONFIG","RESEND_CONFIG","REPORT_FIELDS","FRONTIER_USER"};
	public static final String[] CLIENT_REPORT_TYPE={"INTERVAL","DAILY","INTERVAL_RESENT","DAILY_RESENT"};
}
