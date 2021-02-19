package com.northout.poc.springboot.demoapi.service;

import java.io.IOException;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.northout.poc.springboot.demoapi.json.UserBonus;
import com.northout.poc.springboot.demoapi.model.BonusResponse;
import com.northout.poc.springboot.demoapi.model.User;
import com.northout.poc.springboot.demoapi.model.User.UserType;

@Service
public class UserBonusRuleService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	private static final String RULES_PATH = "bonus.xlsx";
	KieServices kieServices = KieServices.Factory.get();

	private  KieFileSystem getKieFileSystem() throws IOException{
	Resource dt 
	= ResourceFactory
	.newClassPathResource("bonus.xlsx",
			getClass());

	KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
		
	 return kieFileSystem;

    }

	public KieSession getKieSession() throws IOException {
		
		KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem());
		kieBuilder.buildAll();	
		String drl = getDrlFromExcel(RULES_PATH);
		logger.info("=== Begin generated DRL ===");
		logger.info(drl);
		logger.info("=== End generated DRL ===");
		//KieRepository kieRepository = kieServices.getRepository();
		KieModule kieModule = kieBuilder.getKieModule();
		ReleaseId krDefaultReleaseId =kieModule.getReleaseId();
		KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);	
        KieSession kieSession = kieContainer.newKieSession();	
		return kieSession;
		
	}
	
	
	public String getDrlFromExcel(String excelFile) {
		
		DecisionTableConfiguration dtconf = KnowledgeBuilderFactory.newDecisionTableConfiguration();
		dtconf.setInputType( DecisionTableInputType.XLS );

		Resource dt = ResourceFactory.newClassPathResource(RULES_PATH, getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();

        String drl = decisionTableProvider.loadFromResource(dt, dtconf);

        return drl;
       
    }
	
	
	public BonusResponse getBonusPercent(UserBonus userB) throws IOException {		
		
		User user = new User();
		KieSession kSession = getKieSession();		
		BonusResponse bresponse = new BonusResponse();
		user.setYears(Integer.parseInt(userB.getExperienceInYears()));
		user.setType(UserType.valueOf(userB.getUserType()));
		kSession.insert(user);
		kSession.insert(bresponse);
        kSession.fireAllRules();
		
		return bresponse;
	}
	
	



}

