package com.citi.frontier.rest.helper;

import java.util.ArrayList;
import java.util.List;

import com.citi.frontier.rest.helper.ReportingTransDocument;

public class DummyMockData {
	
	List<ReportingTransDocument> reportList = null;

	{
		reportList = createReportTrans(100);
	}

	public static List<ReportingTransDocument> createReportTrans(final int COUNT_THRESHOLD_PARAM) {
		List<ReportingTransDocument> repTransList = new ArrayList<>();

		final int COUNT_THRESHOLD = COUNT_THRESHOLD_PARAM <= 0 ? 1000 : COUNT_THRESHOLD_PARAM;

		for (int i = 0; i < COUNT_THRESHOLD; i++) {
			repTransList.add(newReportingTransDocument("ID_" + i, genRandString(), genRandString(), genRandString(),
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

	private static ReportingTransDocument newReportingTransDocument(String id, String status, String stream, String flow, String sourceSystem,
			String sourceId, String sourceUId, String sourceStatus, String sourceVersion, String regReportingRef,
			boolean rdsEligible/* , LocalDateTime receivedTs */) {
		ReportingTransDocument rep = new ReportingTransDocument();
		rep.setId(id);;
		rep.setStatus(status);
		rep.setStream(stream);
		rep.setFlow(flow);
		rep.setSourceSystem(sourceSystem);
		rep.setSourceId(sourceId);
		rep.setSourceStatus(sourceStatus);
		rep.setSourceUId(sourceUId);
		rep.setSourceVersion(sourceVersion);
		rep.setRegReportingRef(regReportingRef);
		rep.setRdsEligible(rdsEligible);
		return rep;
	}
}
