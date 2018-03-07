package com.pg.springb.front.util;

import java.util.List;

import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportingTransDocument;

public interface FileWriter {

	public boolean writeFile(String fileName, String outboundPath, List<ReportFieldsEntityMapping> header, List<ReportingTransDocument> data);
}
