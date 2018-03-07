package com.pg.springb.front.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.citi.frontier"})
@EnableScheduling
@EnableMongoRepositories(basePackages = {"com.citi.frontier"})
public class FrontierBootApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(FrontierBootApplication.class, args);
	}
}
	
//code removed for mongo validation	
// implements ApplicationRunner{	
		
//		private static final Logger log = LoggerFactory.getLogger(FrontierBootApplication.class);
//		
//		@Autowired
//	    MongoTemplate mongoTemplate;

//		@Override
//		public void run(ApplicationArguments args) throws Exception {
//			try{
////				String[] frontierCollections={"client_lei_gfcid_config","reporting_fields","report_schedule_config","report_sent_log","frontier_users","client_email_process_config","active_host"};
//				Set<String> existingCollection=mongoTemplate.getCollectionNames();
//				DBCollection collection = null;
//				log.info("Mongo Collection creation started:"+existingCollection);
//	            for(String fcollection:FrontierConstants.FRONTIER_COLLECTIONS){
//	            	if (!existingCollection.contains(fcollection)) {
//	                    collection = mongoTemplate.createCollection(fcollection);
//	                    log.info("Mongo Collection created:"+collection.getName());
//	                }
//	            }
//	            log.info("Mongo Collection creation finished:"+mongoTemplate.getCollectionNames());
//			}catch(Exception ex){
//				 log.error("Mongo Collection creation error:" + ex);
//			}
//		}

