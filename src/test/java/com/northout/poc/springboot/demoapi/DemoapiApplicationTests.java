package com.northout.poc.springboot.demoapi;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.northout.poc.springboot.demoapi.model.BonusResponse;
import com.northout.poc.springboot.demoapi.model.User;
import com.northout.poc.springboot.demoapi.model.User.UserType;
import com.northout.poc.springboot.demoapi.service.UserBonusRuleService;

@SpringBootTest
class DemoapiApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private KieSession kSession;

    @Before
    public void setup() throws IOException {
//    kSession = new CustomerValidationRuleService().getKieSession();
    }

    @Test
    public void CorrectDiscount() throws Exception {    	
    	kSession = new UserBonusRuleService().getKieSession();
        User user = new User(UserType.DEVELOPER, 5);
        BonusResponse bs = new BonusResponse();
        kSession.insert(user);
        kSession.insert(bs);

        kSession.fireAllRules();

        assertEquals(bs.getBonusPct(), "20");
    }


}
