package com.pg.springb.front.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pg.springb.front.util.FrontierConstants;
import com.pg.springb.front.documents.FileProcessLogDocument;
import com.pg.springb.front.service.DBFacadeService;
import com.pg.springb.front.service.FileUploadService;

@Controller
@RequestMapping("/frontier")
public class FileUploadController {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Value("${frontier.upload.path}")
	private String uploadPath;
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@RequestMapping("/upload")
	public ModelAndView getUploadForm(HttpServletRequest request, HttpServletResponse response){

		HttpSession session=request.getSession();
		if(session==null)
			return new ModelAndView("loginForm");
		
		Object userId=session.getAttribute("userId");
		if(userId!=null){
			return new ModelAndView("uploadForm","userId",userId.toString());
		}
		return new ModelAndView("loginForm");
	}

	@PostMapping("/upload")
	public ModelAndView fileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file,@RequestParam("filetype") String filetype){
		
		HttpSession session=request.getSession();
		if(session==null || session.getAttribute("userId")==null)
			return new ModelAndView("loginForm");
		
		String userId=session.getAttribute("userId").toString();
		
		if (file.isEmpty())
			return new ModelAndView("uploadForm", "message","Please select a file and try again!");
		
		if(!(filetype.equalsIgnoreCase("clientconfig") || filetype.equalsIgnoreCase("reportconfig") || filetype.equalsIgnoreCase("resendconfig"))) 
			return new ModelAndView("uploadForm", "message","Please select Upload Type and try again!");
		
		String uploadFolder= "";
		String docType="";
		String errMessage ="file uploaded and record saved sucessfully!";
		
		if (filetype.equalsIgnoreCase("clientconfig")) {
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[0];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[0];
		}else if(filetype.equalsIgnoreCase("reportconfig")){ 
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[1];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[1];
		}else if(filetype.equalsIgnoreCase("resendconfig")){
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[2];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[2];
		}
		
		LocalDateTime upldTs=LocalDateTime.now();
		String newFileName = generateFileName(upldTs, userId, file.getOriginalFilename());
		try{
			if(!saveFile(file, uploadFolder, newFileName))
			return new ModelAndView("uploadForm", "message","Uploaded Failed! Please contact administrator.");
			
			FileProcessLogDocument fileProcessLog=new FileProcessLogDocument();
			fileProcessLog.setDocType(docType);
			fileProcessLog.setFileName(newFileName);
			fileProcessLog.setUploadedBy(userId);
			fileProcessLog.setUploadedPath(uploadFolder);
			fileProcessLog.setUploadedTs(upldTs);
			fileProcessLog.setStatus(FrontierConstants.UPLOAD_STATUS[0]);
			fileProcessLog.setHostName(getHostName());
			
			if(FrontierConstants.UPLOAD_DOC_TYPE[2].equalsIgnoreCase(docType)){
				String status=fileUploadService.addResendConfig(newFileName, uploadFolder);
				fileProcessLog.setStatus(status);
				fileProcessLog.setProcessedFilePath(uploadFolder);
				fileProcessLog.setProcessedTs(LocalDateTime.now());
				if(FrontierConstants.UPLOAD_STATUS[2].equals(status))
					errMessage=status+"! Invalid Data in the xl / reportConfig not found with given clientId";
			}
			fileProcessLog=dbFacadeService.saveFileProcessLog(fileProcessLog);
			log.info("file uploaded and record saved:"+fileProcessLog);
		}catch(Exception ex){
			log.info("fileUpload error:"+ex.getMessage());
			return new ModelAndView("uploadForm", "message","processing exception Please contact administrator.");
		}
		return new ModelAndView("uploadForm", "message",errMessage);
	}
	
	@RequestMapping("/configureInitialData")
	public ModelAndView getConfigureInitialDataForm(HttpServletRequest request, HttpServletResponse response){

		HttpSession session=request.getSession();
		if(session==null || session.getAttribute("userId")==null)
			return new ModelAndView("loginForm");
		
		String userId=session.getAttribute("userId").toString();
		
		if(!userId.equalsIgnoreCase("frontier_root"))
			return new ModelAndView("uploadForm");
		
		return new ModelAndView("configureInitialData");
	}
	
	@PostMapping("/saveInitialconfigData")
	public ModelAndView saveInitialconfigData(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file,@RequestParam("filetype") String docType){

		HttpSession session=request.getSession();
		if(session==null || session.getAttribute("userId")==null)
			return new ModelAndView("loginForm");
		
		if(!"reportingFields".equalsIgnoreCase(docType) && !"usersList".equalsIgnoreCase(docType))
			return new ModelAndView("configureInitialData", "message","Please select Upload Type and try again!");

		String uploader=session.getAttribute("userId").toString();
		String errMessage="file uploaded and data processed sucessfully";
		LocalDateTime upldTs=LocalDateTime.now();
		String newFileName = generateFileName(upldTs, uploader, file.getOriginalFilename());
		String upldFolder= "";
		
		if ("reportingFields".equalsIgnoreCase(docType)) {
			upldFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[3];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[3];
		}else if ("usersList".equalsIgnoreCase(docType)) {
			upldFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[4];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[4];
		}
		if(saveFile(file, upldFolder, newFileName)){
			String upldStatus="";
			if(FrontierConstants.UPLOAD_DOC_TYPE[3].equals(docType)){
					upldStatus=fileUploadService.addReportFields(newFileName, upldFolder, upldTs, getHostName());
					if(!upldStatus.equals(FrontierConstants.UPLOAD_STATUS[1]))
						errMessage=file.getOriginalFilename()+" processing failed due to incorrect header/data for reporting field";
				}else if(FrontierConstants.UPLOAD_DOC_TYPE[4].equals(docType)){
					upldStatus=fileUploadService.addUsers(newFileName, upldFolder, upldTs, getHostName());
					if(!upldStatus.equals(FrontierConstants.UPLOAD_STATUS[1]))
						errMessage=file.getOriginalFilename()+" processing failed due to incorrect header/data for users";
				}
		}else{
			errMessage=file.getOriginalFilename()+" could not be saved, please contact administrator!";
			}
		return new ModelAndView("configureInitialData","message",errMessage);
	}
	
	private boolean saveFile(MultipartFile file, String uploadFolder, String fileName){
		try{
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFolder+fileName);
			Files.write(path, bytes);
			return true;
			}catch(IOException e){
				log.error("File Upload Failed, "+e.getMessage());
				}
		return false;
	}
	
	private String getHostName(){
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch(Exception e){
			log.error("getHostName error:"+e.getMessage());
		}
		return "";
	}
	
	private String generateFileName(LocalDateTime upldTs, String upldBy, String origFileName){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		return upldTs.format(formatter)+"-"+upldBy+"-"+origFileName.replace(" ","_");
	}
}
//	@RequestMapping("/export")
//	public String getExportForm(Model model,HttpServletRequest request, HttpServletResponse response)throws Exception{
//		String view="exportForm";
//		
//		HttpSession session=request.getSession();
//		if(session==null || session.getAttribute("userId")==null)
//			return "loginForm";
//		
//		List<ReportScheduleDocument> clientConfigList=dbFacadeService.getReportScheduleDocumentList();
//		if(clientConfigList==null || clientConfigList.size()<=0){
//			model.addAttribute("message","No record found in reportConfig, Please contact Administrator!");
//			return view;
//		}
//		
//		List<String> clientIdList=new ArrayList<String>();
//		for(ReportScheduleDocument rptCfg:clientConfigList)
//		clientIdList.add(rptCfg.getClientId());
//		model.addAttribute("clientIdList",clientIdList);
//		request.getSession().setAttribute("clientIdList", clientIdList);
//		return view;
//	}
//	
//	@RequestMapping(value="/getClientGFC",params="fetch",method=RequestMethod.POST)
//    public String fetch(Model model,HttpServletRequest request, HttpServletResponse response, 
//			@RequestParam(value="clientId", required=false) String clientId)throws Exception{
//		String view="exportForm";
//		if(clientId==null || clientId.length()<=0){
//			model.addAttribute("message","clientId not found, Please select a clientId!");
//        	return view; 
//		}
//		
//        List<ClientConfigDocument> clientConfigList=dbFacadeService.getClientConfigbyClientId(clientId);
//        if(clientConfigList==null || clientConfigList.size()<=0){
//        	model.addAttribute("message","No data found for this clientId");
//        	return view; 
//        }
//        model.addAttribute("clientConfigList",clientConfigList);
//		return view;
//    }
//	
//	@RequestMapping(value="/getClientGFC", params="export", method=RequestMethod.POST)
//    public ModelAndView download(HttpServletRequest request, HttpServletResponse response,
//    		@RequestParam(value="clientId",required=false) String clientId) throws Exception{
//		String view="exportForm";
//		
//		if(clientId==null || clientId.length()<=0)
//			return new ModelAndView(view,"message","clientId not found, Please select a clientId!"); 
//		
//		List<ClientConfigDocument> clientConfigList=dbFacadeService.getClientConfigbyClientId(clientId);
//        if(clientConfigList==null || clientConfigList.size()<=0)
//			return new ModelAndView(view,"message","No data found for this clientId");
//		
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//		String newFileName = "ExportClientConfig-"+clientId+FrontierConstants.FILE_NAME_SEPARATOR+
//				LocalDateTime.now().format(formatter)+FrontierConstants.XLSX_EXTENSION;
//        boolean b=new ExportFileWriter().writeFile(newFileName,outboundPath+ FrontierConstants.REPORT_FOLDERS[3],clientConfigList);
//		if(!b)
//		return new ModelAndView(view,"message","Problem creating export file, Please contact administrator.");
//		
//		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");		
//		response.setHeader("Content-Disposition", "attachment; fileName="+newFileName);
//		response.setContentType("application/octet-stream");
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//		downloadFile(outboundPath+ FrontierConstants.REPORT_FOLDERS[3]+newFileName,response);
//		return null;
//    }
	
//	@RequestMapping("/downloadFile")
//    public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response,
//    		@RequestParam(value="fileName",required=false) String fileName) throws Exception{
//		
//		HttpSession session=request.getSession();
//		if(session==null || session.getAttribute("userId")==null)
//			return new ModelAndView("loginForm");
//		
//		String view="exportForm";
//		if(fileName==null || fileName.length()<=0)
//			return new ModelAndView(view,"message","fileName not found, Please provide fileName as argument!");
//		
//		File f=new File(fileName);
//		
//		if(!f.exists())
//			return new ModelAndView(view,"message","("+fileName+") Not Found");
//		
//		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");		
//		response.setHeader("Content-Disposition", "attachment; fileName="+f.getName());
//		response.setContentType("application/octet-stream");
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//		downloadFile(fileName,response);
//		return null;
//	}
	
//	private void downloadFile(String fileName, HttpServletResponse response) throws Exception {
//
//		BufferedInputStream input = null;
//		BufferedOutputStream output = null;
//		try {
//			File fileToDownload = new File(fileName);
//			input = new BufferedInputStream(new FileInputStream(fileToDownload));
//			output = new BufferedOutputStream(response.getOutputStream());
//			byte[] buffer = new byte[8192];
//			for (int length = 0; (length = input.read(buffer)) > 0;) {
//				output.write(buffer, 0, length);
//			}
//		} catch (IOException ignore) {
//		} finally {
//			if (output != null)
//				try {
//					output.flush();
//					output.close();
//				} catch (IOException ignore) {
//				}
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException ignore) {
//				}
//			}
//		}
//	}
	
//	private String processJsonUploadFile(MultipartFile file, String userId, String UPLOADED_FOLDER, String fileType) {
//		String message = "SUCCESS";
//		try{
////			saveFile(file,UPLOADED_FOLDER,);
//			byte[] bytes = file.getBytes();
//			String reportingFields = new String(file.getBytes());
//			// convert JSON to DBObject directly
//			DBCollection collection = mongoTemplate.getCollection("reporting_fields");
//			DBObject dbObject = (DBObject) JSON.parse(reportingFields);
//			
//			log.info("old version JSON:"+JSON.parse(reportingFields).toString());
//			
////			if(dbObject!=null){
////				List<ReportingFieldsDocument> reportingFieldsList = dbFacadeService.getReportingFieldsList();
////				if(reportingFieldsList!=null && reportingFieldsList.size()>0) {
////					mongoTemplate.remove(new Query(), "reporting_fields");	
////				}
////			collection.insert(dbObject);
////			}
//			}catch(IOException e){
//				log.error("File Upload Failed, "+e.getMessage());
//				message = "File Upload Failed";
//		}
//		return message;
//	}
	
//	private String processAndSaveUserExcelUploadFile(MultipartFile file, String userId, String UPLOADED_FOLDER, String fileType) {
//		String message = "SUCCESS";
//		try{
//			byte[] bytes = file.getBytes();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//			String newFileName = LocalDateTime.now().format(formatter)+
//					FrontierConstants.FILE_NAME_SEPARATOR+
//					userId+
//					FrontierConstants.FILE_NAME_SEPARATOR+
//					file.getOriginalFilename().replace(" ","_");
//			Path path = Paths.get(UPLOADED_FOLDER +newFileName);
//			Files.write(path, bytes);
//			if(!saveUser(UPLOADED_FOLDER +newFileName)){
//				message = "File Upload Failed";
//				}
//			}catch(IOException e){
//			log.error("File Upload Failed, "+e.getMessage());
//			message = "File Upload Failed";
//		}
//		return message;
//	}
	
//	private boolean saveUser(String fileURI){
//		try{
//			List<UserDocument> usersList=new XLFileProcessing().parseUserListXL(new File(fileURI));
//			if(usersList.size()<=0)return false;
//			List<UserDocument> dbUserList = dbFacadeService.getUsersList();
//			for(UserDocument xlUser:usersList){
//				for(UserDocument dbUser:dbUserList){
//					if(xlUser.getUserId().equalsIgnoreCase(dbUser.getUserId())){
//						xlUser.setId(dbUser.getId());
//						break;
//					}	
//				}	
//			}
//			log.info("usersList before dbUpdate:"+usersList);
//			usersList=dbFacadeService.addUsers(usersList);
//			log.info("usersList after dbUpdate:"+usersList);
//			return true;
//		}catch (Exception ex) {
//			log.error(ex.getMessage());
//		}
//		return false;
//	}