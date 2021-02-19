package com.northout.poc.springboot.demoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.northout.poc.springboot.demoapi.json.UserBonus;
import com.northout.poc.springboot.demoapi.model.BonusResponse;
import com.northout.poc.springboot.demoapi.service.UserBonusRuleService;


@RestController
@CrossOrigin
public class BonusController {
	
	@Autowired
	private UserBonusRuleService userBonusRuleService;
	
	@RequestMapping(value = "/getBonusPercent", method = RequestMethod.POST)
    public ResponseEntity<?> getUserBonus(@RequestBody UserBonus userbonus) throws Exception {    
		BonusResponse bs = userBonusRuleService.getBonusPercent(userbonus);
		return new ResponseEntity<>(bs, HttpStatus.OK);
    }

}
