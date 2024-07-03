package com.bindlabs.developer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bindlabs.core.constant.CommonConstants;
import com.bindlabs.core.enums.BankOnBoardStatus;
import com.bindlabs.core.enums.ProductsOffered;
import com.bindlabs.core.model.ApiResponse;
import com.bindlabs.core.repository.FinancialInstitutionRepository;
import com.bindlabs.developer.model.CFBankResponseModel;
import com.bindlabs.developer.service.CFFlowService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CFFlowServiceImplementation implements CFFlowService {

	private static final Logger logger = LogManager.getLogger(CFFlowServiceImplementation.class);

	@Autowired
	private FinancialInstitutionRepository financialInstitutionRepository;

	@Override
	public ResponseEntity<Object> getCFBankList() {
		try {
			List<CFBankResponseModel> resultList = new ArrayList<>();

			financialInstitutionRepository.getCFBankList(BankOnBoardStatus.ONLINE, ProductsOffered.CF).forEach(e -> {
				CFBankResponseModel model = new CFBankResponseModel();

				BeanUtils.copyProperties(e, model);

				resultList.add(model);
			});
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getCFBankList: ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
