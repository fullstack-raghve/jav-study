package com.bindlabs.developer.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bindlabs.core.constant.CommonConstants;
import com.bindlabs.core.enums.ErrorType;
import com.bindlabs.core.model.ApiResponse;
import com.bindlabs.core.model.ErrorResponse;
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
import com.bindlabs.developer.service.DeveloperService;
import com.bindlabs.developer.validator.DeveloperValidator;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

	private static final Logger logger = LogManager.getLogger("AdminService");

	@Autowired
	private DeveloperService developerService;

	@Autowired
	private DeveloperValidator developerValidator;

	@PostMapping(value = "v1/saveGroupStructure", produces = { "application/json" })
	public ResponseEntity<Object> saveGroupStructure(@RequestBody GroupStrDtlsReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateGroupStructureReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveGroupStructure(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addKeyManagerial", produces = { "application/json" })
	public ResponseEntity<Object> addKeyManagerial(@RequestBody SaveKeyManagerialReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateKeyManagerialReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveKeyManagerial(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addCompletedProject", produces = { "application/json" })
	public ResponseEntity<Object> addCompletedProject(@RequestBody SaveCompletedProjectReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateCompletedProjectReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveCompletedProject(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addOnGoingProject", produces = { "application/json" })
	public ResponseEntity<Object> addOnGoingProject(@RequestBody SaveOnGoingProjectReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateOnGoingProjectReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveOnGoingProject(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addUpcomingProject", produces = { "application/json" })
	public ResponseEntity<Object> addUpcomingProject(@RequestBody SaveUpcomingProjectReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateUpcomingProjectReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveUpcomingProject(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addLandholding", produces = { "application/json" })
	public ResponseEntity<Object> addLandholding(@RequestBody SaveLandholdingReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateLandholdingReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveLandholding(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addGroupDebtAndCashFlows", produces = { "application/json" })
	public ResponseEntity<Object> addGroupDebtAndCashFlows(@RequestBody SaveGroupDbtCashFlowsReqBean requestBean) {
		try {

			List<ObjectError> fieldError = developerValidator.validateGroupDbtCashFlowsReq(requestBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveGroupDebtAndCashFlows(requestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in saveProjectOverview:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addBulkCompletedProject", produces = { "application/json" })
	public ResponseEntity<Object> addBulkCompletedProject(@Validated FileNameRequestBean fileNameRequestBean,BindingResult result) {
		try {

			developerValidator.validateXlsxCompletedProjectReq(fileNameRequestBean,result);
			if(result.hasErrors())  {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.saveBulkCompletedProject(fileNameRequestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in addBulkCompletedProject:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addBulkOnGoingProject", produces = { "application/json" })
	public ResponseEntity<Object> addBulkOnGoingProject(@Validated FileNameRequestBean fileNameRequestBean,BindingResult result) {
		try {

			developerValidator.validateXlsxOnGoingProjectReq(fileNameRequestBean,result);
			if(result.hasErrors())  {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.addBulkOnGoingProject(fileNameRequestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in addBulkOnGoingProject: { } ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addBulkUpcomingProject", produces = { "application/json" })
	public ResponseEntity<Object> addBulkUpcomingProject(@Validated FileNameRequestBean fileNameRequestBean,BindingResult result) {
		try {

			developerValidator.validateXlsxUpcomingProjectReq(fileNameRequestBean, result);
			if(result.hasErrors())  {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.addBulkUpcomingProject(fileNameRequestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in addBulkOnGoingProject: { } ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addBulkLandholding", produces = { "application/json" })
	public ResponseEntity<Object> addBulkLandholding(@Validated FileNameRequestBean fileNameRequestBean,BindingResult result) {
		try {
			developerValidator.validateXlsxLandholdingReq(fileNameRequestBean,result);
			if(result.hasErrors())  {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.addBulkLandholding(fileNameRequestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in addBulkOnGoingProject:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "v1/addBulkGroupDebt", produces = { "application/json" })
	public ResponseEntity<Object> addBulkGroupDebt(@Validated FileNameRequestBean fileNameRequestBean,BindingResult result) {
		try {
			developerValidator.validateXlsxGroupDebtReq(fileNameRequestBean, result);
			if(result.hasErrors())  {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.addBulkGroupDebt(fileNameRequestBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in addBulkOnGoingProject:  ", e);
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "v1/uploadManagerialPersonal", produces = { "application/json" })
	public ResponseEntity<Object> uploadManagerialPersonal(@Validated ManagerialPersonalReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.uploadManagerialPersonal(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.uploadManagerialPersonal(reqBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in uploadManagerialPersonal:  ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API will return all the data related to Developer.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping(value = "v1/viewDeveloper", produces = { "application/json" })
	public ResponseEntity<Object> viewDeveloper(@RequestBody ViewDeveloperReqBean reqBean,
			BindingResult result) {
		try {
			if (reqBean.getDeveloperId() == null || reqBean.getDeveloperId().isBlank()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION,
						List.of(new ObjectError("developerId", "Developer Id cannot be empty")),
						"failure"), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.viewDeveloper(reqBean);
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in viewDeveloper: ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *  This API is to update Developer Group Structure Details after Edit in CF Flow.
	 *
	 * @param reqBean
	 * @return
	 */
	@PostMapping(value = "v1/updateGroupStructure", produces = { "application/json" })
	public ResponseEntity<Object> updateGroupStructure(@RequestBody GroupStrDtlsReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.validateUpdateGroupStructure(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			} else {
				return developerService.updateGroupStructure(reqBean);
			}
		} catch (Exception e) {
			logger.error("Exception in updateGroupStructure: ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update Partner Details.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping(value = "v1/updatePartnerDetails", produces = { "application/json" })
	public ResponseEntity<Object> updatePartnerDetails(@RequestBody PartnerDetailsReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.validateUpdatePartnerDetails(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			} else {
				return developerService.updatePartnerDetails(reqBean);
			}
		} catch (Exception e) {
			logger.error("Exception in updatePartnerDetails: ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to get Monthly Interest for Scheme related to the input project.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/getDeveloperSchemes")
	public ResponseEntity<Object> getDeveloperSchemes(@RequestBody DeveloperSchemesReqBean reqBean) {
		try {
			if (reqBean.getProjectId() != null && !reqBean.getProjectId().isBlank()) {
				return developerService.getDeveloperSchemes(reqBean);
			} else {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION,
						List.of(new ObjectError("projectId", "Project Id cannot be empty")),
						"failure"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("getDeveloperSchemes ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete a Partner from Developer Details.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/deletePartner")
	public ResponseEntity<Object> deletePartner(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
				return developerService.deletePartner(reqBean);
		} catch (Exception e) {
			logger.error("deletePartner ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update key Managerial Personal.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateManagerialPersonal")
	public ResponseEntity<Object> updateManagerialPersonal(@RequestBody SaveKeyManagerialReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateKeyManagerialValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateManagerialPersonal(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete key Managerial Personal.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/deleteManagerialPersonal")
	public ResponseEntity<Object> deleteManagerialPersonal(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteManagerialPersonal(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to validate Key Managerial Personal documents.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateKeyManagerial")
	public ResponseEntity<Object> updateKeyManagerial(@RequestBody UpdateKeyManagerialReqBean reqBean) {
		try {
			if (reqBean.getDeveloperId() == null || reqBean.getDeveloperId().isBlank()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION,
						List.of(new ObjectError("developerId", "Developer Id cannot be empty")),
						"failure"), HttpStatus.BAD_REQUEST);
			}
			return developerService.validateKeyManagerial(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
			return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update completed project for onboarding developers.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateCompletedProject")
	public ResponseEntity<Object> updateCompletedProject(@RequestBody SaveCompletedProjectReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateCompletedProjectReqValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateCompletedProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete completed projects.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping("/v1/deleteCompletedProject")
	public ResponseEntity<Object> deleteCompletedProject(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteCompletedProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update Ongoing Project.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateOngoingProject")
	public ResponseEntity<Object> updateOngoingProject(@RequestBody SaveOnGoingProjectReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateOnGoingProjectReqValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateOngoingProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete Ongoing Project.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping("/v1/deleteOngoingProject")
	public ResponseEntity<Object> deleteOngoingProject(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteOngoingProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update Upcoming Project of developer.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateUpcomingProject")
	public ResponseEntity<Object> updateUpcomingProject(@RequestBody SaveUpcomingProjectReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateUpcomingProjectValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateUpcomingProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete upcoming project of developer.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping("/v1/deleteUpcomingProject")
	public ResponseEntity<Object> deleteUpcomingProject(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteUpcomingProject(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update Land Holding
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateLandHolding")
	public ResponseEntity<Object> updateLandHolding(@RequestBody SaveLandholdingReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateLandHoldingValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateLandHolding(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to delete Land Holding.
	 * 
	 * @param reqBean
	 * @param result
	 * @return
	 */
	@PostMapping("/v1/deleteLandHolding")
	public ResponseEntity<Object> deleteLandHolding(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteLandHolding(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update Group Debt.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateGroupDebt")
	public ResponseEntity<Object> updateGroupDebt(@RequestBody SaveGroupDbtCashFlowsReqBean reqBean) {
		try {
			List<ObjectError> fieldError = developerValidator.updateGroupDebtValidation(reqBean);
			if (!fieldError.isEmpty()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, fieldError, "failure"),
						HttpStatus.BAD_REQUEST);
			}
			return developerService.updateGroupDebt(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This API is to update cash flow url for developer.
	 * 
	 * @param reqBean
	 * @return
	 */
	@PostMapping("/v1/updateCashFlows")
	public ResponseEntity<Object> updateCashFlows(@RequestBody UpdateCashFlowsReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.updateCashFlowsValidation(result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.updateCashFlows(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/v1/deleteGroupDebt")
	public ResponseEntity<Object> deleteGroupDebt(@RequestBody DeveloperDeleteReqBean reqBean,
			BindingResult result) {
		try {
			developerValidator.deletionValidation(reqBean, result);
			if (result.hasErrors()) {
				return new ResponseEntity<>(new ErrorResponse(ErrorType.VALIDATION, result), HttpStatus.BAD_REQUEST);
			}
			return developerService.deleteGroupDebt(reqBean);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
