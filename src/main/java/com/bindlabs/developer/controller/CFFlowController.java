package com.bindlabs.developer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bindlabs.core.utils.CommonUtils;
import com.bindlabs.developer.service.CFFlowService;

@RestController
@RequestMapping("/api/developer")
public class CFFlowController {

	private static final Logger logger = LogManager.getLogger(CFFlowController.class);

	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private CFFlowService cfFlowService;

	@PostMapping("/v1/getCFBankList")
	public ResponseEntity<Object> getBankList() {
		try {
			logger.info("inside getCFBankList");
			return cfFlowService.getCFBankList();
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ResponseEntity<>(commonUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
