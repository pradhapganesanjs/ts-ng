package com.pg.springb.front.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.pg.springb.front.util.FrontierConstants;
import com.mongodb.DBCollection;

/* This approach can be used for running logic after the Spring context has been initialized, 
so we are not focusing on any particular bean, but waiting for all of them to initialize.*/

@Component
public class FrontierBootInitializer implements ApplicationListener<ContextRefreshedEvent> {
 
	private static final Logger log = LoggerFactory.getLogger(FrontierBootInitializer.class);
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Value("${frontier.upload.path}")
	private String uploadPath;
	
	@Value("${frontier.export.path}")
	private String exportPath;
    
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
    	log.info("validating upload folders.."+uploadPath);
    	createDiectory(uploadPath);
		for (int i = 0; i < FrontierConstants.UPLOAD_FOLDERS.length; i++) {
			createDiectory(uploadPath + FrontierConstants.UPLOAD_FOLDERS[i]);
			for (int j = 0; j < FrontierConstants.SUB_FOLDERS.length; j++) {
				createDiectory(uploadPath
						+ FrontierConstants.UPLOAD_FOLDERS[i]
						+ FrontierConstants.SUB_FOLDERS[j]);
			}
		}
		
		log.info("validating export folders.."+exportPath);
    	createDiectory(exportPath);
		for (int i = 0; i < FrontierConstants.REPORT_FOLDERS.length; i++) {
			createDiectory(exportPath + FrontierConstants.REPORT_FOLDERS[i]);
			for (int j = 0; j < FrontierConstants.SUB_FOLDERS.length; j++) {
				createDiectory(exportPath
						+ FrontierConstants.REPORT_FOLDERS[i]
						+ FrontierConstants.SUB_FOLDERS[j]);
			}
		}
		
		log.info("validating mongo colelctions..");
		try{
			Set<String> existingCollection=mongoTemplate.getCollectionNames();
            for(String fcollection:FrontierConstants.FRONTIER_COLLECTIONS){
            	if (!existingCollection.contains(fcollection)) {
            		DBCollection collection = mongoTemplate.createCollection(fcollection);
                    log.info("Collection created:"+collection.getName());
                }
            }
		}catch(Exception ex){
			 log.error("Mongo Collection validation error:" + ex);
		}
    }
    
	private void createDiectory(String folder) {
		try {
			Path path = Paths.get(folder);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
				log.info("directory created:"+folder);
			}
		} catch (IOException ex) {
			log.error("create diectory failed for:" + folder + "::" + ex.getMessage());
		}
	}
}