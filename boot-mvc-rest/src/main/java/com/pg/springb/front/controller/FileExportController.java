package com.pg.springb.front.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.citi.frontier.util.ExportFileWriter;
import com.citi.frontier.util.FrontierConstants;
import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.service.DBFacadeService;

@Controller
@RequestMapping("/frontier")
public class FileExportController {
	
	@Value("${frontier.export.path}")
	private String outboundPath;
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@RequestMapping("/export")
	public String handleExportForm(Model model,HttpServletRequest request, HttpServletResponse response)throws Exception{
		String view="exportForm";
		
		HttpSession session=request.getSession();
		if(session==null || session.getAttribute("userId")==null)
			return "loginForm";
		
		List<ReportScheduleDocument> clientConfigList=dbFacadeService.getReportScheduleDocumentList();
		if(clientConfigList==null || clientConfigList.size()<=0){
			model.addAttribute("message","No record found in reportConfig, Please contact Administrator!");
			return view;
		}
		
		List<String> clientIdList=new ArrayList<String>();
		for(ReportScheduleDocument rptCfg:clientConfigList)
		clientIdList.add(rptCfg.getClientId());
		model.addAttribute("clientIdList",clientIdList);
		request.getSession().setAttribute("clientIdList", clientIdList);
		return view;
	}
	
	@RequestMapping(value="/getClientGFC",params="fetch",method=RequestMethod.POST)
    public String handleRetrieveGfcId(Model model,HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="clientId", required=false) String clientId)throws Exception{
		String view="exportForm";
		if(clientId==null || clientId.length()<=0){
			model.addAttribute("message","clientId not found, Please select a clientId!");
        	return view; 
		}
		
        List<ClientConfigDocument> clientConfigList=dbFacadeService.getClientConfigbyClientId(clientId);
        if(clientConfigList==null || clientConfigList.size()<=0){
        	model.addAttribute("message","No data found for this clientId");
        	return view; 
        }
        model.addAttribute("clientConfigList",clientConfigList);
		return view;
    }
	
	@RequestMapping(value="/getClientGFC", params="export", method=RequestMethod.POST)
    public ModelAndView handleExportGfcId(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="clientId",required=false) String clientId) throws Exception{
		String view="exportForm";
		
		if(clientId==null || clientId.length()<=0)
			return new ModelAndView(view,"message","clientId not found, Please select a clientId!"); 
		
		List<ClientConfigDocument> clientConfigList=dbFacadeService.getClientConfigbyClientId(clientId);
        if(clientConfigList==null || clientConfigList.size()<=0)
			return new ModelAndView(view,"message","No data found for this clientId");
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String newFileName = "ExportClientConfig-"+clientId+FrontierConstants.FILE_NAME_SEPARATOR+
				LocalDateTime.now().format(formatter)+FrontierConstants.XLSX_EXTENSION;
        boolean b=new ExportFileWriter().writeFile(newFileName,outboundPath+ FrontierConstants.REPORT_FOLDERS[3],clientConfigList);
		if(!b)
		return new ModelAndView(view,"message","Problem creating export file, Please contact administrator.");
		
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");		
		response.setHeader("Content-Disposition", "attachment; fileName="+newFileName);
		response.setContentType("application/octet-stream");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileDownload(outboundPath+ FrontierConstants.REPORT_FOLDERS[3]+newFileName,response);
		return null;
    }
	
	@RequestMapping("/fileDownload")
    public ModelAndView handleFileDownload(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="fileName",required=false) String fileName) throws Exception{
		
		HttpSession session=request.getSession();
		if(session==null || session.getAttribute("userId")==null)
			return new ModelAndView("loginForm");
		
		String view="exportForm";
		if(fileName==null || fileName.length()<=0)
			return new ModelAndView(view,"message","fileName not found, Please provide fileName as argument!");
		
		File f=new File(fileName);
		
		if(!f.exists())
			return new ModelAndView(view,"message","("+fileName+") Not Found");
		
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");		
		response.setHeader("Content-Disposition", "attachment; fileName="+f.getName());
		response.setContentType("application/octet-stream");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileDownload(fileName,response);
		return null;
	}
	
	@RequestMapping("/templateDownload")
    public ModelAndView handleTemplateDownload(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="fileName",required=false) String fileName) throws Exception{
		
		return null;
	}
	
	private void fileDownload(String fileName, HttpServletResponse response) throws Exception {

		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			File fileToDownload = new File(fileName);
			input = new BufferedInputStream(new FileInputStream(fileToDownload));
			output = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[8192];
			for (int length = 0; (length = input.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}
		} catch (IOException ignore) {
		} finally {
			if (output != null)
				try {
					output.flush();
					output.close();
				} catch (IOException ignore) {
				}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignore) {
				}
			}
		}
	}
}
