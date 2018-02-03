package com.citi.frontier.rest.helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingTransDocument {

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
			boolean rdsEligible/* , LocalDateTime receivedTs */) {
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
		// this.receivedTs = receivedTs;

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
