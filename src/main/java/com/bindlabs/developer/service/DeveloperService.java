package com.bindlabs.developer.service;

import org.springframework.http.ResponseEntity;

import com.bindlabs.core.model.FileNameRequestBean;
import com.bindlabs.developer.model.DeveloperDeleteReqBean;
import com.bindlabs.developer.model.DeveloperSchemesReqBean;
import com.bindlabs.developer.model.GroupStrDtlsReqBean;
import com.bindlabs.developer.model.ManagerialPersonalReqBean;
import com.bindlabs.developer.model.PartnerDetailsReqBean;
import com.bindlabs.developer.model.SaveCompletedProjectReqBean;
import com.bindlabs.developer.model.SaveGroupDbtCashFlowsReqBean;
import com.bindlabs.developer.model.SaveKeyManagerialReqBean;
import com.bindlabs.developer.model.SaveLandholdingReqBean;
import com.bindlabs.developer.model.SaveOnGoingProjectReqBean;
import com.bindlabs.developer.model.SaveUpcomingProjectReqBean;
import com.bindlabs.developer.model.UpdateCashFlowsReqBean;
import com.bindlabs.developer.model.UpdateKeyManagerialReqBean;
import com.bindlabs.developer.model.ViewDeveloperReqBean;

public interface DeveloperService {

	ResponseEntity<Object> saveGroupStructure(GroupStrDtlsReqBean requestBean);

	ResponseEntity<Object> saveKeyManagerial(SaveKeyManagerialReqBean requestBean);

	ResponseEntity<Object> saveCompletedProject(SaveCompletedProjectReqBean requestBean);

	ResponseEntity<Object> saveOnGoingProject(SaveOnGoingProjectReqBean requestBean);

	ResponseEntity<Object> saveUpcomingProject(SaveUpcomingProjectReqBean requestBean);

	ResponseEntity<Object> saveLandholding(SaveLandholdingReqBean requestBean);

	ResponseEntity<Object> saveGroupDebtAndCashFlows(SaveGroupDbtCashFlowsReqBean requestBean);

	ResponseEntity<Object> saveBulkCompletedProject(FileNameRequestBean fileNameRequestBean);

	ResponseEntity<Object> addBulkOnGoingProject(FileNameRequestBean fileNameRequestBean);

	ResponseEntity<Object> addBulkUpcomingProject(FileNameRequestBean fileNameRequestBean);

	ResponseEntity<Object> addBulkLandholding(FileNameRequestBean fileNameRequestBean);

	ResponseEntity<Object> addBulkGroupDebt(FileNameRequestBean fileNameRequestBean);

	ResponseEntity<Object> uploadManagerialPersonal(ManagerialPersonalReqBean reqBean);

	ResponseEntity<Object> viewDeveloper(ViewDeveloperReqBean reqBean);

	ResponseEntity<Object> updateGroupStructure(GroupStrDtlsReqBean reqBean);

	ResponseEntity<Object> updatePartnerDetails(PartnerDetailsReqBean reqBean);

	ResponseEntity<Object> getDeveloperSchemes(DeveloperSchemesReqBean reqBean);

	ResponseEntity<Object> deletePartner(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> updateManagerialPersonal(SaveKeyManagerialReqBean reqBean);

	ResponseEntity<Object> deleteManagerialPersonal(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> validateKeyManagerial(UpdateKeyManagerialReqBean reqBean);

	ResponseEntity<Object> updateCompletedProject(SaveCompletedProjectReqBean reqBean);

	ResponseEntity<Object> deleteCompletedProject(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> updateOngoingProject(SaveOnGoingProjectReqBean reqBean);

	ResponseEntity<Object> deleteOngoingProject(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> updateUpcomingProject(SaveUpcomingProjectReqBean reqBean);

	ResponseEntity<Object> deleteUpcomingProject(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> updateLandHolding(SaveLandholdingReqBean reqBean);

	ResponseEntity<Object> deleteLandHolding(DeveloperDeleteReqBean reqBean);

	ResponseEntity<Object> updateGroupDebt(SaveGroupDbtCashFlowsReqBean reqBean);

	ResponseEntity<Object> updateCashFlows(UpdateCashFlowsReqBean reqBean);

	ResponseEntity<Object> deleteGroupDebt(DeveloperDeleteReqBean reqBean);

}
