package com.bindlabs.developer.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bindlabs.core.constant.CommonConstants;
import com.bindlabs.core.constant.CoreConstants;
import com.bindlabs.core.entity.ProjectPaymentSchemes;
import com.bindlabs.core.enums.ApprovalStatus;
import com.bindlabs.core.enums.EducationalQualification;
import com.bindlabs.core.enums.StatusType;
import com.bindlabs.core.enums.YesOrNo;
import com.bindlabs.core.model.ApiResponse;
import com.bindlabs.core.model.FileNameRequestBean;
import com.bindlabs.core.repository.ProjectPaymentSchemesRepository;
import com.bindlabs.core.utils.CansCrypt;
import com.bindlabs.core.utils.CommonUtils;
import com.bindlabs.developer.entity.DevCompleteProject;
import com.bindlabs.developer.entity.DevCompleteProjectId;
import com.bindlabs.developer.entity.DevGroupDebtDtls;
import com.bindlabs.developer.entity.DevGroupDebtDtlsId;
import com.bindlabs.developer.entity.DevLandholdingDtls;
import com.bindlabs.developer.entity.DevLandholdingDtlsId;
import com.bindlabs.developer.entity.DevManagerialPersonal;
import com.bindlabs.developer.entity.DevManagerialPersonalId;
import com.bindlabs.developer.entity.DevOnGoingProject;
import com.bindlabs.developer.entity.DevOnGoingProjectId;
import com.bindlabs.developer.entity.DevPartnersDtls;
import com.bindlabs.developer.entity.DevPartnersDtlsId;
import com.bindlabs.developer.entity.DevUpcomingProject;
import com.bindlabs.developer.entity.DevUpcomingProjectId;
import com.bindlabs.developer.entity.Developers;
import com.bindlabs.developer.model.CompletedProjectsResponseBean;
import com.bindlabs.developer.model.DeveloperDeleteReqBean;
import com.bindlabs.developer.model.DeveloperSchemesDetailsResponse;
import com.bindlabs.developer.model.DeveloperSchemesReqBean;
import com.bindlabs.developer.model.DeveloperSchemesResponse;
import com.bindlabs.developer.model.GroupDebtResponseBean;
import com.bindlabs.developer.model.GroupStrDtlsReqBean;
import com.bindlabs.developer.model.GroupStructureBean;
import com.bindlabs.developer.model.KeyManagerialResponseBean;
import com.bindlabs.developer.model.LandHoldingResponseBean;
import com.bindlabs.developer.model.ManagerialPersonalModel;
import com.bindlabs.developer.model.ManagerialPersonalReqBean;
import com.bindlabs.developer.model.OnGoingProjectsResponseBean;
import com.bindlabs.developer.model.PartnerDetails;
import com.bindlabs.developer.model.PartnerDetailsReqBean;
import com.bindlabs.developer.model.PartnerDetailsResponseBean;
import com.bindlabs.developer.model.SaveCompletedProjectReqBean;
import com.bindlabs.developer.model.SaveCompletedProjectResBean;
import com.bindlabs.developer.model.SaveGroupDbtCashFlowsReqBean;
import com.bindlabs.developer.model.SaveGroupDbtCashFlowsResBean;
import com.bindlabs.developer.model.SaveKeyManagerialReqBean;
import com.bindlabs.developer.model.SaveLandholdingReqBean;
import com.bindlabs.developer.model.SaveLandholdingResBean;
import com.bindlabs.developer.model.SaveOnGoingProjectReqBean;
import com.bindlabs.developer.model.SaveOnGoingProjectResBean;
import com.bindlabs.developer.model.SaveUpcomingProjectReqBean;
import com.bindlabs.developer.model.SaveUpcomingProjectResBean;
import com.bindlabs.developer.model.UpcomingProjectsResponseBean;
import com.bindlabs.developer.model.UpdateCashFlowsReqBean;
import com.bindlabs.developer.model.UpdateKeyManagerialReqBean;
import com.bindlabs.developer.model.ViewDeveloperReqBean;
import com.bindlabs.developer.model.ViewDeveloperResponse;
import com.bindlabs.developer.repository.DevCompleteProjectRepository;
import com.bindlabs.developer.repository.DevGroupDebtDtlsRepository;
import com.bindlabs.developer.repository.DevLandholdingDtlsRepository;
import com.bindlabs.developer.repository.DevManagerialPersonalRepository;
import com.bindlabs.developer.repository.DevOnGoingProjectRepository;
import com.bindlabs.developer.repository.DevPartnersDtlsRepository;
import com.bindlabs.developer.repository.DevUpcomingProjectRepository;
import com.bindlabs.developer.repository.DevelopersRepository;
import com.bindlabs.developer.service.DeveloperService;
import com.bindlabs.users.service.UserProfileService;

import jakarta.transaction.Transactional;

@Service(value = "DeveloperService")
@Transactional
public class DeveloperServiceImplementation implements DeveloperService {

	private static final Logger logger = LogManager.getLogger(DeveloperServiceImplementation.class);

	@Autowired
	private ProjectPaymentSchemesRepository projectPaymentSchemesRepository;

	@Autowired
	private DevManagerialPersonalRepository devManagerialPersonalRepository;

	@Autowired
	private DevelopersRepository developersRepo;

	@Autowired
	private DevPartnersDtlsRepository devPartnersDtlsRepo;

	@Autowired
	private DevCompleteProjectRepository devCompleteProjectRepo;
	
	@Autowired
	private DevOnGoingProjectRepository devOnGoingProjectRepo;
	
	@Autowired
	private DevUpcomingProjectRepository devUpcomingProjectRepo;
	
	@Autowired
	private DevLandholdingDtlsRepository devLandholdingDtlsRepo;
	
	@Autowired
	private DevGroupDebtDtlsRepository devGroupDebtDtlsRepo;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private CommonUtils commonUtil;
	
	@Override
	public ResponseEntity<Object> saveGroupStructure(GroupStrDtlsReqBean requestBean) {
		try {
			if (null != requestBean) {
				Developers developers = new Developers();
				String decryptedPanNo = CansCrypt.decrypt(requestBean.getPanNo());
				String decryptedCinNo = CansCrypt.decrypt(requestBean.getCinNumber());
				String decryptedGstinNo = CansCrypt.decrypt(requestBean.getGstin());

				developers.setName(requestBean.getName());
				developers.setFormationYear(requestBean.getYearOfFormation());
				developers.setStatus(StatusType.ONLINE);
				developers.setCreatedBy(BigInteger.ZERO);
				developers.setModifiedBy(BigInteger.ZERO);
				developers.setApprovalStatus(ApprovalStatus.REQUESTED);
				developers.setBusinessNature(requestBean.getNatureOfBusiness());
				developers.setWebsiteUrl(requestBean.getWebsite());
				developers.setPanNumber(decryptedPanNo);
				developers.setCinNumber(decryptedCinNo);
				developers.setGstinNumber(decryptedGstinNo);
				developers.setKycDocPath(requestBean.getKycDocPath());
				developers.setIncorporationDocPath(requestBean.getIncorporationDocPath());
				developers.setFinancialDocPath(requestBean.getFinancialDocPath());
				developers.setOrgStructureDocPath(requestBean.getOrgStructureDocPath());
				developers.setAbout(requestBean.getAbout());
				developersRepo.saveAndFlush(developers);
				List<PartnerDetails> partnerDtlsList = requestBean.getPartnersDetails();
				if (null != partnerDtlsList) {
					for (PartnerDetails partnerDtls : partnerDtlsList) {
						DevPartnersDtls devPartners = new DevPartnersDtls();
						devPartners.setDeveloperId(developers.getDeveloperId());
						int count = devPartnersDtlsRepo.getcount(developers.getDeveloperId());
						devPartners.setSequence(count + 1);
						devPartners.setStatus(StatusType.ONLINE);
						devPartners.setName(partnerDtls.getName());
						String dycDinNo = CansCrypt.decrypt(partnerDtls.getDin());
						String dycPanNo = CansCrypt.decrypt(partnerDtls.getPanNo());
						devPartners.setDinNumber(dycDinNo);
						devPartners.setPanNumber(dycPanNo);
						devPartners.setAddress(partnerDtls.getPresentAddress());
						devPartnersDtlsRepo.saveAndFlush(devPartners);
					}
				}
				return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
						List.of("Data saved successfully")), HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Exception in saveGroupStructure ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveKeyManagerial(SaveKeyManagerialReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevManagerialPersonal managerialPersonal = new DevManagerialPersonal();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				managerialPersonal.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devManagerialPersonalRepository.getcount(new BigInteger(dycDeveloperId));
				managerialPersonal.setName(requestBean.getName());
				managerialPersonal.setStatus(StatusType.ONLINE);
				managerialPersonal.setSequence(count + 1);
				managerialPersonal.setEmailId(requestBean.getEmail());
				managerialPersonal.setQualification(requestBean.getQualification());
				managerialPersonal.setExperienceYear(requestBean.getYearOfExperience());
				managerialPersonal.setAge(requestBean.getAge());
				List<String> functionsHandleds = requestBean.getFunctionsHandled();
				String functionsHandledsString = String.join(", ", functionsHandleds);
				managerialPersonal.setFunctionsHandler(functionsHandledsString);
				managerialPersonal.setIsShareHolder(requestBean.getIsShareHolder());
				if (requestBean.getIsShareHolder().equals(YesOrNo.YES)) {
					managerialPersonal.setShareValue(requestBean.getShareValue());
				}
				managerialPersonal.setKycDocPath(requestBean.getKycProof());
				managerialPersonal.setWriteDocPath(requestBean.getWriteUp());
				devManagerialPersonalRepository.saveAndFlush(managerialPersonal);
				return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),
						List.of("Data saved successfully")), HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Exception in saveKeyManagerial ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveCompletedProject(SaveCompletedProjectReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevCompleteProject completeProject = new DevCompleteProject();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				completeProject.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devCompleteProjectRepo.getcount(new BigInteger(dycDeveloperId));
				completeProject.setSequence(count + 1);
				String dycReraId = CansCrypt.decrypt(requestBean.getReraId());
				completeProject.setReraId(dycReraId);
				completeProject.setName(requestBean.getProjectName());
				completeProject.setStatus(StatusType.ONLINE);
				completeProject.setFirmName(requestBean.getCompanyName());
				completeProject.setJvPercentage(requestBean.getJvPercentage());
				completeProject.setLocation(requestBean.getLocation());
				completeProject.setProjectType(requestBean.getTypeOfProject());
				completeProject.setSaleableArea(requestBean.getSaleableArea());
				completeProject.setNoOfUnits(requestBean.getNoOfUnits());
				completeProject.setCost(requestBean.getCostOfProject());
				completeProject.setSalesValue(requestBean.getSalesValuePerSq());
				completeProject.setCommencementYear(requestBean.getCommencementYear());
				completeProject.setCompletionYear(requestBean.getCompletionYear());
				completeProject.setIsOcReceived(requestBean.getIsOcReceived());
				completeProject.setUnsoldUnits(requestBean.getUnsoldUnits());
				completeProject.setBankLoan(requestBean.getBankLoan());
				devCompleteProjectRepo.saveAndFlush(completeProject);
				
				SaveCompletedProjectResBean responseBean = new SaveCompletedProjectResBean();
				responseBean.setProjectName(completeProject.getName());
				responseBean.setCompanyName(completeProject.getFirmName());
				responseBean.setLocation(completeProject.getLocation());
				responseBean.setTypeOfProject(completeProject.getProjectType());
				responseBean.setSaleValuePerSq(completeProject.getSalesValue());
				responseBean.setCommencementYear(completeProject.getCommencementYear());
				responseBean.setCompletionYear(completeProject.getCompletionYear());				
				return new ResponseEntity<>(
						new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), responseBean),
						HttpStatus.OK);	

			}
		} catch (Exception e) {
			logger.error("Exception in saveKeyManagerial ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveOnGoingProject(SaveOnGoingProjectReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevOnGoingProject onGoingProject = new  DevOnGoingProject();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				onGoingProject.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devOnGoingProjectRepo.getcount(new BigInteger(dycDeveloperId));
				onGoingProject.setSequence(count + 1);
				String dycReraId = CansCrypt.decrypt(requestBean.getReraId());
				onGoingProject.setReraId(dycReraId);
				onGoingProject.setName(requestBean.getProjectName());
				onGoingProject.setStatus(StatusType.ONLINE);
				onGoingProject.setFirmName(requestBean.getCompanyName());
				onGoingProject.setLocation(requestBean.getLocation());
				onGoingProject.setDevelopmentType(requestBean.getTypeOfDevelopment());
				onGoingProject.setBuildingStructure(requestBean.getStructureOfBuilding());
				onGoingProject.setTotalCarpetArea(requestBean.getTotalCarpetAreaSqFt());
				onGoingProject.setDeveloperShares(requestBean.getDeveloperShareSqFt());
				onGoingProject.setNoOfUnits(requestBean.getNoOfUnits());
				onGoingProject.setDeveloperUnits(requestBean.getDeveloperUnits());
				onGoingProject.setProjectCost(requestBean.getProjectCost());
				onGoingProject.setTillCostIncurred(requestBean.getCostToBeIncurred());
				onGoingProject.setTobeCostIncurred(requestBean.getCostToBeIncurred());	
				onGoingProject.setUnitsSold(requestBean.getUnitsSold());
				onGoingProject.setSoldArea(requestBean.getAreaSold());
				onGoingProject.setUnsoldArea(requestBean.getAreaUnsold());
				onGoingProject.setSoldUnitsAmount(requestBean.getSoldUnitsAmounts());
				onGoingProject.setBalanceSoldUnitsAmt(requestBean.getBalanceSoldUnitAmounts());
				onGoingProject.setUnsoldUnitsAmount(requestBean.getUnsoldUnitsAmounts());
				onGoingProject.setUnsoldUnitsSalePrice(requestBean.getSalePricePerSqFtForUnsoldUnit());
				onGoingProject.setExpectedRentPerMonth(requestBean.getExpectedRentalPerSqFtPerMonth());
				onGoingProject.setTotalReceivableAmt(requestBean.getTotalReceivableAmt());
				onGoingProject.setFreeCashFlow(requestBean.getFreeCashFlow());
				onGoingProject.setTotalSaleValue(requestBean.getTotalSaleValue());				
				onGoingProject.setDebtOutstanding(requestBean.getDebtOutstanding());
				onGoingProject.setProfit(requestBean.getProfit());
				onGoingProject.setTotalDebtInCr(requestBean.getTotalDebtInCrores());
				onGoingProject.setConstructionStage(requestBean.getStageOfConstruction());
				onGoingProject.setStartDate(CommonUtils.getFormatedDateByDate(requestBean.getStartDate(),
						CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				onGoingProject.setCompletionDate(CommonUtils.getFormatedDateByDate(
						requestBean.getExpectedCompletionDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				onGoingProject.setIsConstructionFinance(requestBean.getIsConstructionFinance());
				if (requestBean.getIsConstructionFinance().equals(YesOrNo.YES)) {
					onGoingProject.setFinancialInstitutionName(requestBean.getNameOfFinancialInstitution());
				}
				devOnGoingProjectRepo.saveAndFlush(onGoingProject);
				SaveOnGoingProjectResBean responseBean = new SaveOnGoingProjectResBean();
				responseBean.setProjectName(onGoingProject.getName());
				responseBean.setLocation(onGoingProject.getLocation());
				responseBean.setCompanyName(onGoingProject.getFirmName());
				responseBean.setTypeOfProject(onGoingProject.getDevelopmentType());
				responseBean.setTotalCarpetAreaSqFt(onGoingProject.getTotalCarpetArea());
				responseBean.setDeveloperShareSqFt(onGoingProject.getDeveloperShares());
				responseBean.setNoOfUnits(onGoingProject.getNoOfUnits());
				responseBean.setTotalSaleValue(String.format("%.2f",onGoingProject.getTotalSaleValue()));

				return new ResponseEntity<>(
						new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), responseBean),
						HttpStatus.OK);	
			}
		} catch (Exception e) {
			logger.error("Exception in saveOnGoingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveUpcomingProject(SaveUpcomingProjectReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevUpcomingProject upcomingProject = new DevUpcomingProject();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				upcomingProject.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devUpcomingProjectRepo.getcount(new BigInteger(dycDeveloperId));
				upcomingProject.setSequence(count + 1);
				upcomingProject.setName(requestBean.getProjectName());
				upcomingProject.setStatus(StatusType.ONLINE);
				upcomingProject.setFirmName(requestBean.getCompanyName());
				upcomingProject.setLocation(requestBean.getLocation());
				upcomingProject.setProjectType(requestBean.getTypeOfProject());
				upcomingProject.setProjectUnderJV(requestBean.getProjectIsUnderJv());
				upcomingProject.setJvPartnerShare(requestBean.getJvPartnerShare());
				upcomingProject.setTotalSaleableArea(requestBean.getTotalSaleableArea());
				upcomingProject.setTotalCost(requestBean.getTotalCost());
				upcomingProject.setTotalProjectValue(requestBean.getTotalProjectSalesValue());
				upcomingProject.setProfit(requestBean.getProfit());
				upcomingProject.setStartDate(CommonUtils.getFormatedDateByDate(requestBean.getProposedStartDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				devUpcomingProjectRepo.saveAndFlush(upcomingProject);
				
				SaveUpcomingProjectResBean responseBean = new SaveUpcomingProjectResBean();
				responseBean.setProjectName(upcomingProject.getName());
				responseBean.setCompanyName(upcomingProject.getFirmName());
				responseBean.setLocation(upcomingProject.getLocation());
				responseBean.setTypeOfProject(upcomingProject.getProjectType());
				responseBean.setJvPartnerShare(upcomingProject.getJvPartnerShare());
				responseBean.setTotalSaleableArea(upcomingProject.getTotalSaleableArea());
				responseBean.setTotalCost(String.format("%.2f",upcomingProject.getTotalCost()));
			
				return new ResponseEntity<>(
						new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), responseBean),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Exception in saveOnGoingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveLandholding(SaveLandholdingReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevLandholdingDtls landholdingDtls = new DevLandholdingDtls();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				landholdingDtls.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devLandholdingDtlsRepo.getcount(new BigInteger(dycDeveloperId));
				landholdingDtls.setSequence(count + 1);
				landholdingDtls.setName(requestBean.getOwnerName());
				landholdingDtls.setProjectName(requestBean.getProjectName());
				landholdingDtls.setLandUse(requestBean.getLandUse());
				landholdingDtls.setLocation(requestBean.getLocation());
				landholdingDtls.setArea(requestBean.getArea());
				landholdingDtls.setBookValue(requestBean.getBookValue());
				landholdingDtls.setMarketValue(requestBean.getMarketValue());
				landholdingDtls.setPurchaseYear(CommonUtils.getFormatedDateByDate(requestBean.getPurchaseYear(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				landholdingDtls.setStatus(StatusType.ONLINE);
				devLandholdingDtlsRepo.saveAndFlush(landholdingDtls);

				SaveLandholdingResBean responseBean = new SaveLandholdingResBean(); 
				responseBean.setOwnerName(landholdingDtls.getName());	
				responseBean.setLandUse(landholdingDtls.getLandUse());
				responseBean.setLocation(landholdingDtls.getLocation());
				responseBean.setArea(landholdingDtls.getArea());
				responseBean.setMarketValue(landholdingDtls.getMarketValue());				

				return new ResponseEntity<>(
						new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), responseBean),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Exception in saveOnGoingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> saveGroupDebtAndCashFlows(SaveGroupDbtCashFlowsReqBean requestBean) {
		try {
			if (null != requestBean) {
				DevGroupDebtDtls groupDebtDtls = new DevGroupDebtDtls();
				String dycDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
				groupDebtDtls.setDeveloperId(new BigInteger(dycDeveloperId));
				int count = devGroupDebtDtlsRepo.getcount(new BigInteger(dycDeveloperId));
				groupDebtDtls.setSequence(count + 1);
				groupDebtDtls.setName(requestBean.getInstitutionName());
				groupDebtDtls.setFacilityType(requestBean.getFacilityType());
				groupDebtDtls.setBorrowingEntity(requestBean.getBorrowingEntity());
				groupDebtDtls.setProjectSecurity(requestBean.getProjectSecurity());
				groupDebtDtls.setInterestRate(requestBean.getInterestRate());
				groupDebtDtls.setSanctionAmount(requestBean.getSanctionAmount());
				groupDebtDtls.setSanctionDate(CommonUtils.getFormatedDateByDate(requestBean.getSanctionDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				groupDebtDtls.setSanctionLimits(requestBean.getSanctionLimits());
				groupDebtDtls.setDisbursed(requestBean.getDisbursed());
				groupDebtDtls.setDisbursementDate(CommonUtils.getFormatedDateByDate(requestBean.getDisbursementDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				groupDebtDtls.setTobeDisbursed(requestBean.getTobeDisbursed());
				groupDebtDtls.setCurrentOutstanding(requestBean.getCurrentOutstanding());
				groupDebtDtls.setRepaymentStartDate(CommonUtils.getFormatedDateByDate(requestBean.getRepaymentStartDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				groupDebtDtls.setTenure(requestBean.getTenure());
				groupDebtDtls.setRevisedTenure(requestBean.getRevisedTenure());
				groupDebtDtls.setLoanEndDate(CommonUtils.getFormatedDateByDate(requestBean.getLoanEndDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
				groupDebtDtls.setSanctionLetterPath(requestBean.getSanctionLetterPath());
				groupDebtDtls.setStatus(StatusType.ONLINE);
				devGroupDebtDtlsRepo.saveAndFlush(groupDebtDtls);

				SaveGroupDbtCashFlowsResBean responseBean = new SaveGroupDbtCashFlowsResBean();
				responseBean.setInstitutionName(groupDebtDtls.getName());
				responseBean.setFacilityType(groupDebtDtls.getFacilityType());
				responseBean.setBorrowingEntity(groupDebtDtls.getBorrowingEntity());
				responseBean.setProjectSecurity(groupDebtDtls.getProjectSecurity());
				responseBean.setInterestRate(groupDebtDtls.getInterestRate());
				responseBean.setSanctionAmount(groupDebtDtls.getSanctionAmount());
				responseBean.setDisbursed(groupDebtDtls.getDisbursed());
				responseBean.setCurrentOutstanding(groupDebtDtls.getCurrentOutstanding());
				
				return new ResponseEntity<>(
						new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), responseBean),
						HttpStatus.OK);
				
			}
		} catch (Exception e) {
			logger.error("Exception in saveOnGoingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@Override
	public ResponseEntity<Object> saveBulkCompletedProject(FileNameRequestBean fileNameRequestBean) {
		List<SaveCompletedProjectReqBean> saveCompletedProjectReqBeanList = null;
		int count = 0;
		try {
				saveCompletedProjectReqBeanList = getCompleteProjectXlsData(fileNameRequestBean.getFile());

				List<DevCompleteProject> completeProjectList = new ArrayList<>();

				for (SaveCompletedProjectReqBean saveCompletedProjectReqBean : saveCompletedProjectReqBeanList) {
					DevCompleteProject completeProject = new DevCompleteProject();
					String dycDeveloperId = CansCrypt.decrypt(saveCompletedProjectReqBean.getDeveloperId());
					completeProject.setDeveloperId(new BigInteger(dycDeveloperId));

					if (completeProjectList.isEmpty()) {

						count = devCompleteProjectRepo.getcount(new BigInteger(dycDeveloperId));
						count++;
						completeProject.setSequence(count);
					} else {
						completeProject.setSequence(count);
					}

					String dycReraId = CansCrypt.decrypt(saveCompletedProjectReqBean.getReraId());
					completeProject.setReraId(dycReraId);
					completeProject.setName(saveCompletedProjectReqBean.getProjectName());
					completeProject.setStatus(StatusType.ONLINE);
					completeProject.setFirmName(saveCompletedProjectReqBean.getCompanyName());
					completeProject.setJvPercentage(saveCompletedProjectReqBean.getJvPercentage());
					completeProject.setLocation(saveCompletedProjectReqBean.getLocation());
					completeProject.setProjectType(saveCompletedProjectReqBean.getTypeOfProject());
					completeProject.setSaleableArea(saveCompletedProjectReqBean.getSaleableArea());
					completeProject.setNoOfUnits(saveCompletedProjectReqBean.getNoOfUnits());
					completeProject.setCost(saveCompletedProjectReqBean.getCostOfProject());
					completeProject.setSalesValue(saveCompletedProjectReqBean.getSalesValuePerSq());
					completeProject.setCommencementYear(saveCompletedProjectReqBean.getCommencementYear());
					completeProject.setCompletionYear(saveCompletedProjectReqBean.getCompletionYear());
					completeProject.setIsOcReceived(saveCompletedProjectReqBean.getIsOcReceived());
					completeProject.setUnsoldUnits(saveCompletedProjectReqBean.getUnsoldUnits());
					completeProject.setBankLoan(saveCompletedProjectReqBean.getBankLoan());
					completeProjectList.add(completeProject);
					count++;
				}

				saveCompletedProjectReqBeanList.forEach(requestBean -> {

				});
				if (!completeProjectList.isEmpty()) {
					devCompleteProjectRepo.saveAll(completeProjectList);
					return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
				}
		} catch (Exception e) {
			logger.error("Exception in saveBulkCompletedProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	private List<SaveCompletedProjectReqBean> getCompleteProjectXlsData(MultipartFile multipartFile) {
		Sheet sheet = null;
		List<SaveCompletedProjectReqBean> saveCompletedProjectReqBeanList = new ArrayList<>();
		SaveCompletedProjectReqBean saveCompletedProjectReqBean;
		try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());) {

			sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				saveCompletedProjectReqBean = new SaveCompletedProjectReqBean();
				Row row = sheet.getRow(rowIndex);

				saveCompletedProjectReqBean.setDeveloperId(row.getCell(0).getStringCellValue());
				saveCompletedProjectReqBean.setReraId(row.getCell(1).getStringCellValue());
				saveCompletedProjectReqBean.setProjectName(row.getCell(2).getStringCellValue());
				saveCompletedProjectReqBean.setCompanyName(row.getCell(3).getStringCellValue());
				saveCompletedProjectReqBean.setJvPercentage(row.getCell(4).toString());
				saveCompletedProjectReqBean.setLocation(row.getCell(5).getStringCellValue());
				saveCompletedProjectReqBean.setTypeOfProject(row.getCell(6).getStringCellValue());
				saveCompletedProjectReqBean.setSaleableArea(row.getCell(7).toString());
				saveCompletedProjectReqBean.setNoOfUnits((int) (row.getCell(8).getNumericCellValue()));
				saveCompletedProjectReqBean.setCostOfProject(row.getCell(9).getNumericCellValue());
				saveCompletedProjectReqBean.setSalesValuePerSq(row.getCell(10).getNumericCellValue());
				saveCompletedProjectReqBean.setCommencementYear(row.getCell(11).toString());
				saveCompletedProjectReqBean.setCompletionYear(row.getCell(12).toString());
				saveCompletedProjectReqBean.setIsOcReceived(YesOrNo.valueOf(row.getCell(13).getStringCellValue()));
				saveCompletedProjectReqBean.setUnsoldUnits((int) (row.getCell(14).getNumericCellValue()));
				saveCompletedProjectReqBean.setBankLoan(row.getCell(15).getNumericCellValue());
				saveCompletedProjectReqBeanList.add(saveCompletedProjectReqBean);
			}
		} catch (Exception e) {
			logger.error("Exception in getCompleteProjectXlsData ", e);
		}
		return saveCompletedProjectReqBeanList;
	}

	@Override
	public ResponseEntity<Object> addBulkOnGoingProject(FileNameRequestBean fileNameRequestBean) {
		List<SaveOnGoingProjectReqBean> saveOnGoingProjectReqBeanList = null;
		List<DevOnGoingProject> devOnGoingProjectList = null;
		int count = 0;
		try {
				saveOnGoingProjectReqBeanList = getOnGoingProjectXlsData(fileNameRequestBean.getFile());

				devOnGoingProjectList = new ArrayList<>();
				for (SaveOnGoingProjectReqBean saveOnGoingProjectReqBean : saveOnGoingProjectReqBeanList) {
					DevOnGoingProject onGoingProject = new DevOnGoingProject();
					String dycDeveloperId = CansCrypt.decrypt(saveOnGoingProjectReqBean.getDeveloperId());
					onGoingProject.setDeveloperId(new BigInteger(dycDeveloperId));

					if (devOnGoingProjectList.isEmpty()) {

						count = devOnGoingProjectRepo.getcount(new BigInteger(dycDeveloperId));
						count++;
						onGoingProject.setSequence(count);
					} else {
						onGoingProject.setSequence(count);
					}
					String dycReraId = CansCrypt.decrypt(saveOnGoingProjectReqBean.getReraId());
					onGoingProject.setReraId(dycReraId);
					onGoingProject.setName(saveOnGoingProjectReqBean.getProjectName());
					onGoingProject.setStatus(StatusType.ONLINE);
					onGoingProject.setFirmName(saveOnGoingProjectReqBean.getCompanyName());
					onGoingProject.setLocation(saveOnGoingProjectReqBean.getLocation());
					onGoingProject.setDevelopmentType(saveOnGoingProjectReqBean.getTypeOfDevelopment());
					onGoingProject.setBuildingStructure(saveOnGoingProjectReqBean.getStructureOfBuilding());
					onGoingProject.setTotalCarpetArea(saveOnGoingProjectReqBean.getTotalCarpetAreaSqFt());
					onGoingProject.setDeveloperShares(saveOnGoingProjectReqBean.getDeveloperShareSqFt());
					onGoingProject.setNoOfUnits(saveOnGoingProjectReqBean.getNoOfUnits());
					onGoingProject.setDeveloperUnits(saveOnGoingProjectReqBean.getDeveloperUnits());
					onGoingProject.setProjectCost(saveOnGoingProjectReqBean.getProjectCost());
					onGoingProject.setTillCostIncurred(saveOnGoingProjectReqBean.getCostToBeIncurred());
					onGoingProject.setTobeCostIncurred(saveOnGoingProjectReqBean.getCostToBeIncurred());
					onGoingProject.setUnitsSold(saveOnGoingProjectReqBean.getUnitsSold());
					onGoingProject.setSoldArea(saveOnGoingProjectReqBean.getAreaSold());
					onGoingProject.setUnsoldArea(saveOnGoingProjectReqBean.getAreaUnsold());
					onGoingProject.setSoldUnitsAmount(saveOnGoingProjectReqBean.getSoldUnitsAmounts());
					onGoingProject.setBalanceSoldUnitsAmt(saveOnGoingProjectReqBean.getBalanceSoldUnitAmounts());
					onGoingProject.setUnsoldUnitsAmount(saveOnGoingProjectReqBean.getUnsoldUnitsAmounts());
					onGoingProject
							.setUnsoldUnitsSalePrice(saveOnGoingProjectReqBean.getSalePricePerSqFtForUnsoldUnit());
					onGoingProject
							.setExpectedRentPerMonth(saveOnGoingProjectReqBean.getExpectedRentalPerSqFtPerMonth());
					onGoingProject.setTotalReceivableAmt(saveOnGoingProjectReqBean.getTotalReceivableAmt());
					onGoingProject.setFreeCashFlow(saveOnGoingProjectReqBean.getFreeCashFlow());
					onGoingProject.setTotalSaleValue(saveOnGoingProjectReqBean.getTotalSaleValue());
					onGoingProject.setDebtOutstanding(saveOnGoingProjectReqBean.getDebtOutstanding());
					onGoingProject.setProfit(saveOnGoingProjectReqBean.getProfit());
					onGoingProject.setTotalDebtInCr(saveOnGoingProjectReqBean.getTotalDebtInCrores());
					onGoingProject.setConstructionStage(saveOnGoingProjectReqBean.getStageOfConstruction());
					onGoingProject.setStartDate(saveOnGoingProjectReqBean.getStartDate());
					onGoingProject.setCompletionDate(saveOnGoingProjectReqBean.getExpectedCompletionDate());
					onGoingProject.setIsConstructionFinance(saveOnGoingProjectReqBean.getIsConstructionFinance());
					if (saveOnGoingProjectReqBean.getIsConstructionFinance().equals(YesOrNo.YES)) {
						onGoingProject
								.setFinancialInstitutionName(saveOnGoingProjectReqBean.getNameOfFinancialInstitution());
					}
					devOnGoingProjectList.add(onGoingProject);
					count++;
				}
				if (!devOnGoingProjectList.isEmpty()) {
					devOnGoingProjectRepo.saveAll(devOnGoingProjectList);
					return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
				}

		} catch (Exception e) {
			logger.error("Exception in addBulkOnGoingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<SaveOnGoingProjectReqBean> getOnGoingProjectXlsData(MultipartFile multipartFile) {
		Sheet sheet = null;
		SaveOnGoingProjectReqBean saveOnGoingProjectReqBean = null;
		List<SaveOnGoingProjectReqBean> saveOnGoingProjectReqBeanList = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());) {

			sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				saveOnGoingProjectReqBean = new SaveOnGoingProjectReqBean();
				Row row = sheet.getRow(rowIndex);

				saveOnGoingProjectReqBean.setDeveloperId(row.getCell(0).getStringCellValue());
				saveOnGoingProjectReqBean.setReraId(row.getCell(1).getStringCellValue());
				saveOnGoingProjectReqBean.setProjectName(row.getCell(2).getStringCellValue());
				saveOnGoingProjectReqBean.setLocation(row.getCell(3).getStringCellValue());
				saveOnGoingProjectReqBean.setTypeOfDevelopment(row.getCell(4).getStringCellValue());
				saveOnGoingProjectReqBean.setCompanyName(row.getCell(5).getStringCellValue());
				saveOnGoingProjectReqBean.setStructureOfBuilding(row.getCell(6).getStringCellValue());
				saveOnGoingProjectReqBean.setTotalCarpetAreaSqFt(row.getCell(7).getStringCellValue());
				saveOnGoingProjectReqBean.setDeveloperShareSqFt(row.getCell(8).getStringCellValue());
				saveOnGoingProjectReqBean.setNoOfUnits((int) row.getCell(9).getNumericCellValue());
				saveOnGoingProjectReqBean.setDeveloperUnits((int) row.getCell(10).getNumericCellValue());
				saveOnGoingProjectReqBean.setProjectCost(row.getCell(10).getNumericCellValue());
				saveOnGoingProjectReqBean.setCostIncurredTillDate(row.getCell(11).getNumericCellValue());
				saveOnGoingProjectReqBean.setCostToBeIncurred(row.getCell(12).getNumericCellValue());
				saveOnGoingProjectReqBean.setUnitsSold((int) row.getCell(13).getNumericCellValue());
				saveOnGoingProjectReqBean.setAreaSold(row.getCell(14).getStringCellValue());
				saveOnGoingProjectReqBean.setAreaUnsold(row.getCell(15).getStringCellValue());
				saveOnGoingProjectReqBean.setSoldUnitsAmounts(row.getCell(16).getNumericCellValue());
				saveOnGoingProjectReqBean.setBalanceSoldUnitAmounts(row.getCell(17).getNumericCellValue());
				saveOnGoingProjectReqBean.setUnsoldUnitsAmounts(row.getCell(18).getNumericCellValue());
				saveOnGoingProjectReqBean.setSalePricePerSqFtForUnsoldUnit(row.getCell(19).getNumericCellValue());
				saveOnGoingProjectReqBean.setExpectedRentalPerSqFtPerMonth(row.getCell(20).getNumericCellValue());
				saveOnGoingProjectReqBean.setTotalReceivableAmt(row.getCell(21).getNumericCellValue());
				saveOnGoingProjectReqBean.setFreeCashFlow(row.getCell(22).getNumericCellValue());
				saveOnGoingProjectReqBean.setTotalSaleValue(row.getCell(23).getNumericCellValue());
				saveOnGoingProjectReqBean.setDebtOutstanding(row.getCell(24).getNumericCellValue());
				saveOnGoingProjectReqBean.setProfit(row.getCell(25).getNumericCellValue());
				saveOnGoingProjectReqBean.setTotalDebtInCrores(row.getCell(26).getNumericCellValue());
				saveOnGoingProjectReqBean.setStageOfConstruction(row.getCell(27).getStringCellValue());
				saveOnGoingProjectReqBean.setStartDate(row.getCell(28).getDateCellValue());
				saveOnGoingProjectReqBean.setExpectedCompletionDate(row.getCell(28).getDateCellValue());
				saveOnGoingProjectReqBean
						.setIsConstructionFinance(YesOrNo.valueOf(row.getCell(30).getStringCellValue()));

				if (YesOrNo.valueOf(row.getCell(30).getStringCellValue()).equals(YesOrNo.YES)) {
					saveOnGoingProjectReqBean.setNameOfFinancialInstitution(row.getCell(31).getStringCellValue());
				}
				saveOnGoingProjectReqBeanList.add(saveOnGoingProjectReqBean);
			}

		} catch (Exception e) {
			logger.error("Exception in getOnGoingProjectXlsData ", e);
		}
		return saveOnGoingProjectReqBeanList;
	}

	@Override
	public ResponseEntity<Object> addBulkUpcomingProject(FileNameRequestBean fileNameRequestBean) {
		List<SaveUpcomingProjectReqBean> saveUpcomingProjectReqBeanList = null;
		List<DevUpcomingProject> devUpcomingProjectList = null;
		int count = 0;
		try {
				saveUpcomingProjectReqBeanList = getUpcomingProjectXlsData(fileNameRequestBean.getFile());

				devUpcomingProjectList = new ArrayList<>();

				for (SaveUpcomingProjectReqBean saveUpcomingProjectReqBean : saveUpcomingProjectReqBeanList) {
					DevUpcomingProject devUpcomingProject = new DevUpcomingProject();
					String dycDeveloperId = CansCrypt.decrypt(saveUpcomingProjectReqBean.getDeveloperId());
					devUpcomingProject.setDeveloperId(new BigInteger(dycDeveloperId));

					if (devUpcomingProjectList.isEmpty()) {

						count = devUpcomingProjectRepo.getcount(new BigInteger(dycDeveloperId));
						count++;
						devUpcomingProject.setSequence(count);
					} else {
						devUpcomingProject.setSequence(count);
					}

					devUpcomingProject.setStatus(StatusType.ONLINE);
					devUpcomingProject.setName(saveUpcomingProjectReqBean.getProjectName());
					devUpcomingProject.setStatus(StatusType.ONLINE);
					devUpcomingProject.setFirmName(saveUpcomingProjectReqBean.getCompanyName());
					devUpcomingProject.setLocation(saveUpcomingProjectReqBean.getLocation());
					devUpcomingProject.setProjectType(saveUpcomingProjectReqBean.getTypeOfProject());
					devUpcomingProject.setProjectUnderJV(saveUpcomingProjectReqBean.getProjectIsUnderJv());
					devUpcomingProject.setJvPartnerShare(saveUpcomingProjectReqBean.getJvPartnerShare());
					devUpcomingProject.setTotalSaleableArea(saveUpcomingProjectReqBean.getTotalSaleableArea());
					devUpcomingProject.setTotalCost(saveUpcomingProjectReqBean.getTotalCost());
					devUpcomingProject.setTotalProjectValue(saveUpcomingProjectReqBean.getTotalProjectSalesValue());
					devUpcomingProject.setProfit(saveUpcomingProjectReqBean.getProfit());
					devUpcomingProject.setStartDate(CommonUtils.getFormatedDateByDate(
							saveUpcomingProjectReqBean.getProposedStartDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devUpcomingProjectList.add(devUpcomingProject);
					count++;

				}
				if (!devUpcomingProjectList.isEmpty()) {
					devUpcomingProjectRepo.saveAll(devUpcomingProjectList);
					return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
				}
		} catch (Exception e) {
			logger.error("Exception in addBulkUpcomingProject ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<SaveUpcomingProjectReqBean> getUpcomingProjectXlsData(MultipartFile multipartFile) {
		Sheet sheet = null;
		SaveUpcomingProjectReqBean saveUpcomingProjectReqBean = null;
		List<SaveUpcomingProjectReqBean> saveUpcomingProjectReqBeanList = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());) {

			sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				saveUpcomingProjectReqBean = new SaveUpcomingProjectReqBean();
				Row row = sheet.getRow(rowIndex);

				saveUpcomingProjectReqBean.setDeveloperId(row.getCell(0).getStringCellValue());
				saveUpcomingProjectReqBean.setCompanyName(row.getCell(1).getStringCellValue());
				saveUpcomingProjectReqBean.setProjectName(row.getCell(2).getStringCellValue());
				saveUpcomingProjectReqBean.setLocation(row.getCell(3).getStringCellValue());
				saveUpcomingProjectReqBean.setTypeOfProject(row.getCell(4).getStringCellValue());
				saveUpcomingProjectReqBean.setProjectIsUnderJv(row.getCell(5).toString());
				saveUpcomingProjectReqBean.setJvPartnerShare(row.getCell(6).toString());
				saveUpcomingProjectReqBean.setTotalSaleableArea(row.getCell(6).toString());
				saveUpcomingProjectReqBean.setTotalCost(row.getCell(7).getNumericCellValue());
				saveUpcomingProjectReqBean.setTotalProjectSalesValue(row.getCell(8).getNumericCellValue());
				saveUpcomingProjectReqBean.setProfit(row.getCell(9).getNumericCellValue());
				saveUpcomingProjectReqBean.setProposedStartDate(row.getCell(10).getDateCellValue());
				saveUpcomingProjectReqBeanList.add(saveUpcomingProjectReqBean);
			}
		} catch (Exception e) {
			logger.error("Exception in addBulkUpcomingProject ", e);
		}
		return saveUpcomingProjectReqBeanList;
	}

	@Override
	public ResponseEntity<Object> addBulkLandholding(FileNameRequestBean fileNameRequestBean) {
		List<SaveLandholdingReqBean> saveLandholdingReqBeanList = null;
		DevLandholdingDtls devLandholdingDtls = null;
		List<DevLandholdingDtls> devLandholdingDtlsList = null;
		int count = 0;
		try {

				saveLandholdingReqBeanList = getLandholdingXlsData(fileNameRequestBean.getFile());

				devLandholdingDtlsList = new ArrayList<>();
				for (SaveLandholdingReqBean saveLandholdingReqBean : saveLandholdingReqBeanList) {
					devLandholdingDtls = new DevLandholdingDtls();
					String dycDeveloperId = CansCrypt.decrypt(saveLandholdingReqBean.getDeveloperId());
					devLandholdingDtls.setDeveloperId(new BigInteger(dycDeveloperId));

					if (devLandholdingDtlsList.isEmpty()) {

						count = devLandholdingDtlsRepo.getcount(new BigInteger(dycDeveloperId));
						count++;
						devLandholdingDtls.setSequence(count);
					} else {
						devLandholdingDtls.setSequence(count);
					}

					devLandholdingDtls.setName(saveLandholdingReqBean.getOwnerName());
					devLandholdingDtls.setProjectName(saveLandholdingReqBean.getProjectName());
					devLandholdingDtls.setLandUse(saveLandholdingReqBean.getLandUse());
					devLandholdingDtls.setLocation(saveLandholdingReqBean.getLocation());
					devLandholdingDtls.setArea(saveLandholdingReqBean.getArea());
					devLandholdingDtls.setBookValue(saveLandholdingReqBean.getBookValue());
					devLandholdingDtls.setMarketValue(saveLandholdingReqBean.getMarketValue());
					devLandholdingDtls.setPurchaseYear(CommonUtils.getFormatedDateByDate(
							saveLandholdingReqBean.getPurchaseYear(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devLandholdingDtlsList.add(devLandholdingDtls);
					count++;
				}
				if (!devLandholdingDtlsList.isEmpty()) {
					devLandholdingDtlsRepo.saveAll(devLandholdingDtlsList);
					return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
				}
		} catch (Exception e) {
			logger.error("Exception in addBulkLandholding ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<SaveLandholdingReqBean> getLandholdingXlsData(MultipartFile multipartFile) {
		Sheet sheet = null;
		List<SaveLandholdingReqBean> saveLandholdingReqBeanList = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());) {

			sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				SaveLandholdingReqBean saveLandholdingReqBean = new SaveLandholdingReqBean();
				Row row = sheet.getRow(rowIndex);

				saveLandholdingReqBean.setDeveloperId(row.getCell(0).getStringCellValue());
				saveLandholdingReqBean.setOwnerName(row.getCell(1).getStringCellValue());
				saveLandholdingReqBean.setProjectName(row.getCell(2).getStringCellValue());
				saveLandholdingReqBean.setLandUse(row.getCell(3).getStringCellValue());
				saveLandholdingReqBean.setLocation(row.getCell(4).getStringCellValue());
				saveLandholdingReqBean.setArea(row.getCell(5).toString());
				saveLandholdingReqBean.setBookValue(row.getCell(6).getNumericCellValue());
				saveLandholdingReqBean.setMarketValue(row.getCell(7).getNumericCellValue());
				saveLandholdingReqBean.setPurchaseYear(row.getCell(8).getDateCellValue());
				saveLandholdingReqBeanList.add(saveLandholdingReqBean);
				
			}

		} catch (Exception e) {
			logger.error("Exception in getLandholdingXlsData ", e);
		}
		return saveLandholdingReqBeanList;
	}

	@Override
	public ResponseEntity<Object> addBulkGroupDebt(FileNameRequestBean fileNameRequestBean) {
		int count = 0;
		List<SaveGroupDbtCashFlowsReqBean> saveGroupDbtCashFlowsReqBeanList = null;
		DevGroupDebtDtls devGroupDebtDtls = null;
		List<DevGroupDebtDtls> devGroupDebtDtlsList = null;
		try {
				saveGroupDbtCashFlowsReqBeanList = getGroupDebtXlsData(fileNameRequestBean.getFile());

				devGroupDebtDtlsList = new ArrayList<>();

				for (SaveGroupDbtCashFlowsReqBean saveGroupDbtCashFlowsReqBean : saveGroupDbtCashFlowsReqBeanList) {
					devGroupDebtDtls = new DevGroupDebtDtls();

					String dycDeveloperId = CansCrypt.decrypt(saveGroupDbtCashFlowsReqBean.getDeveloperId());
					devGroupDebtDtls.setDeveloperId(new BigInteger(dycDeveloperId));

					if (devGroupDebtDtlsList.isEmpty()) {

						count =  devGroupDebtDtlsRepo.getcount(new BigInteger(dycDeveloperId));
						count++;
						devGroupDebtDtls.setSequence(count);
					} else {
						devGroupDebtDtls.setSequence(count);
					}

					devGroupDebtDtls.setName(saveGroupDbtCashFlowsReqBean.getInstitutionName());
					devGroupDebtDtls.setFacilityType(saveGroupDbtCashFlowsReqBean.getFacilityType());
					devGroupDebtDtls.setBorrowingEntity(saveGroupDbtCashFlowsReqBean.getBorrowingEntity());
					devGroupDebtDtls.setProjectSecurity(saveGroupDbtCashFlowsReqBean.getProjectSecurity());
					devGroupDebtDtls.setInterestRate(saveGroupDbtCashFlowsReqBean.getInterestRate());
					devGroupDebtDtls.setSanctionAmount(saveGroupDbtCashFlowsReqBean.getSanctionAmount());
					devGroupDebtDtls.setSanctionDate(CommonUtils.getFormatedDateByDate(
							saveGroupDbtCashFlowsReqBean.getSanctionDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devGroupDebtDtls.setSanctionLimits(saveGroupDbtCashFlowsReqBean.getSanctionLimits());
					devGroupDebtDtls.setDisbursed(saveGroupDbtCashFlowsReqBean.getDisbursed());
					devGroupDebtDtls.setDisbursementDate(CommonUtils.getFormatedDateByDate(
							saveGroupDbtCashFlowsReqBean.getDisbursementDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devGroupDebtDtls.setTobeDisbursed(saveGroupDbtCashFlowsReqBean.getTobeDisbursed());
					devGroupDebtDtls.setCurrentOutstanding(saveGroupDbtCashFlowsReqBean.getCurrentOutstanding());
					devGroupDebtDtls.setRepaymentStartDate(
							CommonUtils.getFormatedDateByDate(saveGroupDbtCashFlowsReqBean.getRepaymentStartDate(),
									CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devGroupDebtDtls.setTenure(saveGroupDbtCashFlowsReqBean.getTenure());
					devGroupDebtDtls.setRevisedTenure(saveGroupDbtCashFlowsReqBean.getRevisedTenure());
					devGroupDebtDtls.setLoanEndDate(CommonUtils.getFormatedDateByDate(
							saveGroupDbtCashFlowsReqBean.getLoanEndDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
					devGroupDebtDtls.setSanctionLetterPath(saveGroupDbtCashFlowsReqBean.getSanctionLetterPath());
					devGroupDebtDtlsList.add(devGroupDebtDtls);
					count++;
				}
				if (!devGroupDebtDtlsList.isEmpty()) {
					devGroupDebtDtlsRepo.saveAll(devGroupDebtDtlsList);
					return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
				}
			return new ResponseEntity<>(commonUtil.getFailureMessage("File Not Found"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception in addBulkGroupDebt ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	

	private List<SaveGroupDbtCashFlowsReqBean> getGroupDebtXlsData(MultipartFile multipartFile) {
		Sheet sheet = null;
		SaveGroupDbtCashFlowsReqBean saveGroupDbtCashFlowsReqBean = null;
		List<SaveGroupDbtCashFlowsReqBean> saveGroupDbtCashFlowsReqBeanList = null;
		try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());) {
			sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			saveGroupDbtCashFlowsReqBeanList = new ArrayList<>();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				saveGroupDbtCashFlowsReqBean = new SaveGroupDbtCashFlowsReqBean();
				Row row = sheet.getRow(rowIndex);

				saveGroupDbtCashFlowsReqBean.setDeveloperId(row.getCell(0).getStringCellValue());
				saveGroupDbtCashFlowsReqBean.setInstitutionName(row.getCell(2).getStringCellValue());
				saveGroupDbtCashFlowsReqBean.setFacilityType(row.getCell(2).getStringCellValue());
				saveGroupDbtCashFlowsReqBean.setBorrowingEntity(row.getCell(3).getStringCellValue());
				saveGroupDbtCashFlowsReqBean.setProjectSecurity(row.getCell(4).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setInterestRate(row.getCell(5).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setSanctionAmount(row.getCell(6).getNumericCellValue());				
				saveGroupDbtCashFlowsReqBean.setSanctionDate(row.getCell(7).getDateCellValue());
				saveGroupDbtCashFlowsReqBean.setSanctionLimits(row.getCell(8).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setDisbursed(row.getCell(9).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setDisbursementDate(row.getCell(10).getDateCellValue());
				saveGroupDbtCashFlowsReqBean.setTobeDisbursed(row.getCell(11).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setCurrentOutstanding(row.getCell(12).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setRepaymentStartDate(row.getCell(13).getDateCellValue());
				saveGroupDbtCashFlowsReqBean.setTenure((int) row.getCell(14).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setRevisedTenure((int) row.getCell(15).getNumericCellValue());
				saveGroupDbtCashFlowsReqBean.setLoanEndDate(row.getCell(16).getDateCellValue());
				saveGroupDbtCashFlowsReqBean.setSanctionLetterPath(row.getCell(17).getStringCellValue());
				saveGroupDbtCashFlowsReqBeanList.add(saveGroupDbtCashFlowsReqBean);
			}
		} catch (Exception e) {
			logger.error("Exception in getGroupDebtXlsData ", e);
		}
		return saveGroupDbtCashFlowsReqBeanList;
	}

	@Override
	public ResponseEntity<Object> uploadManagerialPersonal(ManagerialPersonalReqBean reqBean) {
		try {
			List<ManagerialPersonalModel> managerialModelList = getXMLDataForManagerialPersonal(reqBean.getFile());
			if (!managerialModelList.isEmpty()) {
				BigInteger developerId = BigDecimal.valueOf(managerialModelList.get(0).getDeveloperId()).toBigInteger();
				AtomicInteger seq = new AtomicInteger(devManagerialPersonalRepository
						.getcount(developerId) + 1);
				managerialModelList.forEach(e -> {
					DevManagerialPersonal personal = new DevManagerialPersonal();

					personal.setDeveloperId(developerId);
					personal.setSequence(seq.getAndIncrement());
					personal.setName(e.getName());
					personal.setStatus(StatusType.ONLINE);
					personal.setEmailId(e.getEmail());
					personal.setQualification(EducationalQualification.valueOf(e.getQualification()));
					personal.setExperienceYear(e.getExperienceYear().intValue());
					personal.setAge(e.getAge().intValue());
					YesOrNo isShareHolder = YesOrNo.values()[e.getIsShareHolder().intValue()];
					personal.setIsShareHolder(isShareHolder);
					personal.setShareValue(String.valueOf(e.getShareValue()));
					personal.setFunctionsHandler(e.getFunctionsHandled());

					devManagerialPersonalRepository.save(personal);
				});
				return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Exception in uploadManagerialPersonal ", e);
		}
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), List.of(CommonConstants.INTERNAL_SERVER_ERROR)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<ManagerialPersonalModel> getXMLDataForManagerialPersonal(MultipartFile file) {
		List<ManagerialPersonalModel> managerialModelList = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);
			int num = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex <= num - 1; rowIndex++) {
				ManagerialPersonalModel personalModel = new ManagerialPersonalModel();
				Row row = sheet.getRow(rowIndex);

				personalModel.setDeveloperId(row.getCell(0).getNumericCellValue());
				personalModel.setName(row.getCell(1).getStringCellValue());
				personalModel.setEmail(row.getCell(2).getStringCellValue());
				personalModel.setQualification(row.getCell(3).getStringCellValue());
				personalModel.setExperienceYear(row.getCell(4).getNumericCellValue());
				personalModel.setAge(row.getCell(5).getNumericCellValue());
				personalModel.setIsShareHolder(row.getCell(6).getNumericCellValue());
				personalModel.setShareValue(row.getCell(7).getNumericCellValue());
				personalModel.setFunctionsHandled(row.getCell(8).getStringCellValue());

				managerialModelList.add(personalModel);
			}

		} catch (Exception e) {
			logger.error("Exception in getXMLDataForManagerialPersonal ", e);
		}
		return managerialModelList;
	}

	@Override
	public ResponseEntity<Object> viewDeveloper(ViewDeveloperReqBean reqBean) {
		try {
			ViewDeveloperResponse response = new ViewDeveloperResponse();
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			Optional<Developers> optional = developersRepo.findById(developerId);
			Developers developers = new Developers();
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Developer details with this id."),
						HttpStatus.BAD_REQUEST);
			}
			developers = optional.get();

			setGroupStructureDetails(developers, response);
			setPartnerDetails(developerId, response);
			setKeyManagerialDetails(developerId, response);
			setCompletedProjects(developerId, response);
			setOnGoingProjects(developerId, response);
			setUpcomingProjects(developerId, response);
			setLandHoldingDetails(developerId, response);
			setGroupDebtDetails(developerId, response);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in viewDeveloper ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void setGroupStructureDetails(Developers developers, ViewDeveloperResponse response) {
		GroupStructureBean groupStructureDetails = new GroupStructureBean();

		groupStructureDetails.setDeveloperId(developers.getDeveloperId());
		groupStructureDetails.setName(developers.getName());
		groupStructureDetails.setYearOfFormation(developers.getFormationYear());
		groupStructureDetails.setNatureOfBusiness(developers.getBusinessNature());
		groupStructureDetails.setWebsite(developers.getWebsiteUrl());
		groupStructureDetails.setPanNo(developers.getPanNumber());
		groupStructureDetails.setCinNumber(developers.getCinNumber());
		groupStructureDetails.setGstin(developers.getGstinNumber());
		groupStructureDetails.setKycDocPath(developers.getKycDocPath());
		groupStructureDetails.setIncorporationDocPath(developers.getIncorporationDocPath());
		groupStructureDetails.setFinancialDocPath(developers.getFinancialDocPath());
		groupStructureDetails.setOrgStructureDocPath(developers.getOrgStructureDocPath());
		groupStructureDetails.setCashFlowDocPath(developers.getCashFlowsDocPath());

		response.setGroupStructureDetails(groupStructureDetails);
	}

	private void setPartnerDetails(BigInteger developerId, ViewDeveloperResponse response) {
		List<PartnerDetailsResponseBean> partnerDetails = new ArrayList<>();

		devPartnersDtlsRepo.getAllById(developerId).forEach(e -> {
			PartnerDetailsResponseBean responseBean = new PartnerDetailsResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setName(e.getName());
			responseBean.setDin(e.getDinNumber());
			responseBean.setPanNo(e.getPanNumber());
			responseBean.setPresentAddress(e.getAddress());

			partnerDetails.add(responseBean);
		});

		response.setPartnerDetails(partnerDetails);
	}

	private void setKeyManagerialDetails(BigInteger developerId, ViewDeveloperResponse response) {
		List<KeyManagerialResponseBean> keyManagerialDetails = new ArrayList<>();

		devManagerialPersonalRepository.getAllById(developerId).forEach(e -> {
			KeyManagerialResponseBean responseBean = new KeyManagerialResponseBean();

			responseBean.setName(e.getName());
			responseBean.setEmail(e.getEmailId());
			responseBean.setQualification(e.getQualification());
			responseBean.setYearOfExperience(e.getExperienceYear());
			responseBean.setAge(e.getAge());
			responseBean.setFunctionsHandled(Arrays.asList(e.getFunctionsHandler().split(",")));
			responseBean.setIsShareHolder(e.getIsShareHolder());
			responseBean.setShareValue(e.getShareValue());
			responseBean.setKycProof(e.getKycDocPath());
			responseBean.setWriteUp(e.getWriteDocPath());
			responseBean.setSequence(e.getSequence());
			responseBean.setDeveloperId(e.getDeveloperId());

			keyManagerialDetails.add(responseBean);
		});

		response.setKeyManagerialDetails(keyManagerialDetails);
	}

	private void setCompletedProjects(BigInteger developerId, ViewDeveloperResponse response) {
		List<CompletedProjectsResponseBean> completedProjects = new ArrayList<>();

		devCompleteProjectRepo.getAllById(developerId).forEach(e -> {
			CompletedProjectsResponseBean responseBean = new CompletedProjectsResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setReraId(e.getReraId());
			responseBean.setProjectName(e.getName());
			responseBean.setCompanyName(e.getFirmName());
			responseBean.setJvPercentage(e.getJvPercentage());
			responseBean.setLocation(e.getLocation());
			responseBean.setTypeOfProject(e.getProjectType());
			responseBean.setSaleableArea(e.getSaleableArea());
			responseBean.setNoOfUnits(e.getNoOfUnits());
			responseBean.setCostOfProject(e.getCost());
			responseBean.setSalesValuePerSq(e.getSalesValue());
			responseBean.setCommencementYear(e.getCommencementYear());
			responseBean.setCompletionYear(e.getCompletionYear());
			responseBean.setIsOcReceived(e.getIsOcReceived());
			responseBean.setUnsoldUnits(e.getUnsoldUnits());
			responseBean.setBankLoan(e.getBankLoan());

			completedProjects.add(responseBean);
		});

		response.setCompletedProjects(completedProjects);
	}

	private void setOnGoingProjects(BigInteger developerId, ViewDeveloperResponse response) {
		List<OnGoingProjectsResponseBean> onGoingProjects = new ArrayList<>();

		devOnGoingProjectRepo.getAllById(developerId).forEach(e -> {
			OnGoingProjectsResponseBean responseBean = new OnGoingProjectsResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setReraId(e.getReraId());
			responseBean.setProjectName(e.getName());
			responseBean.setLocation(e.getLocation());
			responseBean.setTypeOfDevelopment(e.getDevelopmentType());
			responseBean.setCompanyName(e.getFirmName());
			responseBean.setStructureOfBuilding(e.getBuildingStructure());
			responseBean.setTotalCarpetAreaSqFt(e.getTotalCarpetArea());
			responseBean.setDeveloperShareSqFt(e.getDeveloperShares());
			responseBean.setNoOfUnits(e.getNoOfUnits());
			responseBean.setDeveloperUnits(e.getDeveloperUnits());
			responseBean.setProjectCost(e.getProjectCost());
			responseBean.setCostIncurredTillDate(e.getTillCostIncurred());
			responseBean.setCostToBeIncurred(e.getTobeCostIncurred());
			responseBean.setUnitsSold(e.getUnitsSold());
			responseBean.setAreaSold(e.getSoldArea());
			responseBean.setAreaUnsold(e.getUnsoldArea());
			responseBean.setSoldUnitsAmounts(e.getSoldUnitsAmount());
			responseBean.setBalanceSoldUnitAmounts(e.getBalanceSoldUnitsAmt());
			responseBean.setUnsoldUnitsAmounts(e.getUnsoldUnitsAmount());
			responseBean.setSalePricePerSqFtForUnsoldUnit(e.getUnsoldUnitsSalePrice());
			responseBean.setExpectedRentalPerSqFtPerMonth(e.getExpectedRentPerMonth());
			responseBean.setTotalReceivableAmt(e.getTotalReceivableAmt());
			responseBean.setFreeCashFlow(e.getFreeCashFlow());
			responseBean.setTotalSaleValue(e.getTotalSaleValue());
			responseBean.setDebtOutstanding(e.getDebtOutstanding());
			responseBean.setProfit(e.getProfit());
			responseBean.setTotalDebtInCrores(e.getTotalDebtInCr());
			responseBean.setStageOfConstruction(e.getConstructionStage());
			responseBean.setStartDate(e.getStartDate());
			responseBean.setExpectedCompletionDate(e.getCompletionDate());
			responseBean.setIsConstructionFinance(e.getIsConstructionFinance());
			responseBean.setNameOfFinancialInstitution(e.getFinancialInstitutionName());

			onGoingProjects.add(responseBean);
		});

		response.setOnGoingProjects(onGoingProjects);
	}

	private void setUpcomingProjects(BigInteger developerId, ViewDeveloperResponse response) {
		List<UpcomingProjectsResponseBean> upcomingProjects = new ArrayList<>();

		devUpcomingProjectRepo.getAllById(developerId).forEach(e -> {
			UpcomingProjectsResponseBean responseBean = new UpcomingProjectsResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setProjectName(e.getName());
			responseBean.setCompanyName(e.getFirmName());
			responseBean.setLocation(e.getLocation());
			responseBean.setTypeOfProject(e.getProjectType());
			responseBean.setProjectIsUnderJv(e.getProjectUnderJV());
			responseBean.setJvPartnerShare(e.getJvPartnerShare());
			responseBean.setTotalSaleableArea(e.getTotalSaleableArea());
			responseBean.setTotalCost(e.getTotalCost());
			responseBean.setTotalProjectSalesValue(e.getTotalProjectValue());
			responseBean.setProfit(e.getProfit());
			responseBean.setProposedStartDate(e.getStartDate());

			upcomingProjects.add(responseBean);
		});

		response.setUpcomingProjects(upcomingProjects);
	}

	private void setLandHoldingDetails(BigInteger developerId, ViewDeveloperResponse response) {
		List<LandHoldingResponseBean> landHoldingDetails = new ArrayList<>();

		devLandholdingDtlsRepo.getAllById(developerId).forEach(e -> {
			LandHoldingResponseBean responseBean = new LandHoldingResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setOwnerName(e.getName());
			responseBean.setProjectName(e.getProjectName());
			responseBean.setLandUse(e.getLandUse());
			responseBean.setLocation(e.getLocation());
			responseBean.setArea(e.getArea());
			responseBean.setBookValue(e.getBookValue());
			responseBean.setMarketValue(e.getMarketValue());
			responseBean.setPurchaseYear(e.getPurchaseYear());

			landHoldingDetails.add(responseBean);
		});

		response.setLandHoldingDetails(landHoldingDetails);
	}

	private void setGroupDebtDetails(BigInteger developerId, ViewDeveloperResponse response) {
		List<GroupDebtResponseBean> groupDebtDetails = new ArrayList<>();

		devGroupDebtDtlsRepo.getAllById(developerId).forEach(e -> {
			GroupDebtResponseBean responseBean = new GroupDebtResponseBean();

			responseBean.setDeveloperId(e.getDeveloperId());
			responseBean.setSequence(e.getSequence());
			responseBean.setInstitutionName(e.getName());
			responseBean.setFacilityType(e.getFacilityType());
			responseBean.setBorrowingEntity(e.getBorrowingEntity());
			responseBean.setProjectSecurity(e.getProjectSecurity());
			responseBean.setInterestRate(e.getInterestRate());
			responseBean.setSanctionAmount(e.getSanctionAmount());
			responseBean.setSanctionDate(e.getSanctionDate());
			responseBean.setSanctionLimits(e.getSanctionLimits());
			responseBean.setDisbursed(e.getDisbursed());
			responseBean.setDisbursementDate(e.getDisbursementDate());
			responseBean.setTobeDisbursed(e.getTobeDisbursed());
			responseBean.setCurrentOutstanding(e.getCurrentOutstanding());
			responseBean.setRepaymentStartDate(e.getRepaymentStartDate());
			responseBean.setTenure(e.getTenure());
			responseBean.setRevisedTenure(e.getRevisedTenure());
			responseBean.setLoanEndDate(e.getLoanEndDate());
			responseBean.setSanctionLetterPath(e.getSanctionLetterPath());

			groupDebtDetails.add(responseBean);
		});

		response.setGroupDebtDetails(groupDebtDetails);
	}

	@Override
	public ResponseEntity<Object> updateGroupStructure(GroupStrDtlsReqBean requestBean) {
		try {
			BigInteger loginUserId = userProfileService.findLoggedInUserId();

			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			Optional<Developers> developerOptional = developersRepo.findById(new BigInteger(decryptedDeveloperId));
			if (developerOptional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Developer details with this id."),
						HttpStatus.BAD_REQUEST);
			}
			Developers developers = developerOptional.get();

			String decryptedPanNo = CansCrypt.decrypt(requestBean.getPanNo());
			String decryptedCinNo = CansCrypt.decrypt(requestBean.getCinNumber());
			String decryptedGstinNo = CansCrypt.decrypt(requestBean.getGstin());

			developers.setName(requestBean.getName());
			developers.setFormationYear(requestBean.getYearOfFormation());
			developers.setModifiedBy(loginUserId);
			developers.setApprovalStatus(ApprovalStatus.REQUESTED);
			developers.setBusinessNature(requestBean.getNatureOfBusiness());
			developers.setWebsiteUrl(requestBean.getWebsite());
			developers.setPanNumber(decryptedPanNo);
			developers.setCinNumber(decryptedCinNo);
			developers.setGstinNumber(decryptedGstinNo);
			developers.setKycDocPath(requestBean.getKycDocPath());
			developers.setIncorporationDocPath(requestBean.getIncorporationDocPath());
			developers.setFinancialDocPath(requestBean.getFinancialDocPath());
			developers.setOrgStructureDocPath(requestBean.getOrgStructureDocPath());
			developers.setAbout(requestBean.getAbout());
			developersRepo.saveAndFlush(developers);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateGroupStructure ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updatePartnerDetails(PartnerDetailsReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			DevPartnersDtlsId devPartnersDtlsId = new DevPartnersDtlsId(new BigInteger(decryptedDeveloperId),
					reqBean.getSequence());

			Optional<DevPartnersDtls> optional = devPartnersDtlsRepo.findById(devPartnersDtlsId);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Partner details with this id."),
						HttpStatus.BAD_REQUEST);
			}
			DevPartnersDtls partnersDtls = optional.get();
			partnersDtls.setName(reqBean.getName());
			String dycDinNo = CansCrypt.decrypt(reqBean.getDin());
			partnersDtls.setDinNumber(dycDinNo);
			partnersDtls.setAddress(reqBean.getPresentAddress());
			devPartnersDtlsRepo.saveAndFlush(partnersDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updatePartnerDetails ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> getDeveloperSchemes(DeveloperSchemesReqBean reqBean) {
		try {
			List<DeveloperSchemesResponse> response = new ArrayList<>();
			String decryptedProjectId = CansCrypt.decrypt(reqBean.getProjectId());
			BigInteger projectId = new BigInteger(decryptedProjectId);

			List<ProjectPaymentSchemes> paymentSchemeList = projectPaymentSchemesRepository.getAllByProjectId(projectId);
			if (paymentSchemeList.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Scheme details with this project id."),
						HttpStatus.BAD_REQUEST);
			}

			paymentSchemeList.forEach(e -> {
				DeveloperSchemesResponse developerSchemesResponse = new DeveloperSchemesResponse();

				developerSchemesResponse.setSchemeId(e.getSchemeId());
				developerSchemesResponse.setSchemeName(e.getSchemeName());
				developerSchemesResponse.setAvailabilityDate(commonUtil.getFormattedDate(e.getAvailabilityDate(),
						CoreConstants.VIEW_DATE_FORMAT));
				List<DeveloperSchemesDetailsResponse> schemesDetails = new ArrayList<>();

				e.getSchemeDetails().forEach(s -> {
					DeveloperSchemesDetailsResponse detailsResponse = new DeveloperSchemesDetailsResponse();

					detailsResponse.setMonth(s.getMonth());
					detailsResponse.setPercentages(s.getPercentage());

					schemesDetails.add(detailsResponse);
				});

				developerSchemesResponse.setSchemesDetails(schemesDetails);
				response.add(developerSchemesResponse);
			});

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getDeveloperSchemes ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deletePartner(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevPartnersDtlsId devPartnersDtlsId = new DevPartnersDtlsId(developerId, reqBean.getSequence());
			Optional<DevPartnersDtls> optional = devPartnersDtlsRepo.findById(devPartnersDtlsId);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevPartnersDtls devPartnersDtls = optional.get();
			devPartnersDtls.setStatus(StatusType.DELETED);
			devPartnersDtlsRepo.save(devPartnersDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deletePartner ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateManagerialPersonal(SaveKeyManagerialReqBean requestBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevManagerialPersonalId id = new DevManagerialPersonalId(developerId, requestBean.getSequence());

			Optional<DevManagerialPersonal> optional = devManagerialPersonalRepository.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Key Managerial Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevManagerialPersonal devManagerialPersonal = optional.get();

			devManagerialPersonal.setName(requestBean.getName());
			devManagerialPersonal.setStatus(StatusType.ONLINE);
			devManagerialPersonal.setEmailId(requestBean.getEmail());
			devManagerialPersonal.setQualification(requestBean.getQualification());
			devManagerialPersonal.setExperienceYear(requestBean.getYearOfExperience());
			devManagerialPersonal.setAge(requestBean.getAge());
			List<String> functionsHandleds = requestBean.getFunctionsHandled();
			String functionsHandledsString = String.join(", ", functionsHandleds);
			devManagerialPersonal.setFunctionsHandler(functionsHandledsString);
			devManagerialPersonal.setIsShareHolder(requestBean.getIsShareHolder());
			if (requestBean.getIsShareHolder().equals(YesOrNo.YES)) {
				devManagerialPersonal.setShareValue(requestBean.getShareValue());
			}
			devManagerialPersonal.setKycDocPath(requestBean.getKycProof());
			devManagerialPersonal.setWriteDocPath(requestBean.getWriteUp());
			devManagerialPersonalRepository.saveAndFlush(devManagerialPersonal);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateManagerialPersonal ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteManagerialPersonal(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevManagerialPersonalId id = new DevManagerialPersonalId(developerId, reqBean.getSequence());

			Optional<DevManagerialPersonal> optional = devManagerialPersonalRepository.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Key Managerial Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevManagerialPersonal devManagerialPersonal = optional.get();
			devManagerialPersonal.setStatus(StatusType.DELETED);

			devManagerialPersonalRepository.saveAndFlush(devManagerialPersonal);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteManagerialPersonal ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> validateKeyManagerial(UpdateKeyManagerialReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			Boolean flag = false;

			List<DevManagerialPersonal> devManagerialPersonalList = devManagerialPersonalRepository.getAllById(developerId);
			flag = devManagerialPersonalList.stream().anyMatch(e -> e.getKycDocPath() == null ||
					e.getKycDocPath().isBlank() || e.getWriteDocPath() == null || e.getWriteDocPath().isBlank());
			if (Boolean.TRUE.equals(flag)) {
				return new ResponseEntity<>(commonUtil
						.getFailureMessage("Please upload KYC and Write up document for all."),
						HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in validateKeyManagerial ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateCompletedProject(SaveCompletedProjectReqBean requestBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);

			DevCompleteProjectId id = new DevCompleteProjectId(developerId, requestBean.getSequence());
			Optional<DevCompleteProject> optional = devCompleteProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Completed Project Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevCompleteProject completeProject = optional.get();

			String dycReraId = CansCrypt.decrypt(requestBean.getReraId());
			completeProject.setReraId(dycReraId);
			completeProject.setName(requestBean.getProjectName());
			completeProject.setFirmName(requestBean.getCompanyName());
			completeProject.setJvPercentage(requestBean.getJvPercentage());
			completeProject.setLocation(requestBean.getLocation());
			completeProject.setProjectType(requestBean.getTypeOfProject());
			completeProject.setSaleableArea(requestBean.getSaleableArea());
			completeProject.setNoOfUnits(requestBean.getNoOfUnits());
			completeProject.setCost(requestBean.getCostOfProject());
			completeProject.setSalesValue(requestBean.getSalesValuePerSq());
			completeProject.setCommencementYear(requestBean.getCommencementYear());
			completeProject.setCompletionYear(requestBean.getCompletionYear());
			completeProject.setIsOcReceived(requestBean.getIsOcReceived());
			completeProject.setUnsoldUnits(requestBean.getUnsoldUnits());
			completeProject.setBankLoan(requestBean.getBankLoan());
			devCompleteProjectRepo.saveAndFlush(completeProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateCompletedProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteCompletedProject(DeveloperDeleteReqBean requestBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);

			DevCompleteProjectId id = new DevCompleteProjectId(developerId, requestBean.getSequence());
			Optional<DevCompleteProject> optional = devCompleteProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Completed Project Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevCompleteProject completeProject = optional.get();
			completeProject.setStatus(StatusType.DELETED);
			devCompleteProjectRepo.save(completeProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteCompletedProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateOngoingProject(SaveOnGoingProjectReqBean requestBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevOnGoingProjectId id = new DevOnGoingProjectId(developerId, requestBean.getSequence());

			Optional<DevOnGoingProject> optional = devOnGoingProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Ongoing Project Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevOnGoingProject onGoingProject = optional.get();
			String dycReraId = CansCrypt.decrypt(requestBean.getReraId());
			onGoingProject.setReraId(dycReraId);
			onGoingProject.setName(requestBean.getProjectName());
			onGoingProject.setFirmName(requestBean.getCompanyName());
			onGoingProject.setLocation(requestBean.getLocation());
			onGoingProject.setDevelopmentType(requestBean.getTypeOfDevelopment());
			onGoingProject.setBuildingStructure(requestBean.getStructureOfBuilding());
			onGoingProject.setTotalCarpetArea(requestBean.getTotalCarpetAreaSqFt());
			onGoingProject.setDeveloperShares(requestBean.getDeveloperShareSqFt());
			onGoingProject.setNoOfUnits(requestBean.getNoOfUnits());
			onGoingProject.setDeveloperUnits(requestBean.getDeveloperUnits());
			onGoingProject.setProjectCost(requestBean.getProjectCost());
			onGoingProject.setTillCostIncurred(requestBean.getCostToBeIncurred());
			onGoingProject.setTobeCostIncurred(requestBean.getCostToBeIncurred());	
			onGoingProject.setUnitsSold(requestBean.getUnitsSold());
			onGoingProject.setSoldArea(requestBean.getAreaSold());
			onGoingProject.setUnsoldArea(requestBean.getAreaUnsold());
			onGoingProject.setSoldUnitsAmount(requestBean.getSoldUnitsAmounts());
			onGoingProject.setBalanceSoldUnitsAmt(requestBean.getBalanceSoldUnitAmounts());
			onGoingProject.setUnsoldUnitsAmount(requestBean.getUnsoldUnitsAmounts());
			onGoingProject.setUnsoldUnitsSalePrice(requestBean.getSalePricePerSqFtForUnsoldUnit());
			onGoingProject.setExpectedRentPerMonth(requestBean.getExpectedRentalPerSqFtPerMonth());
			onGoingProject.setTotalReceivableAmt(requestBean.getTotalReceivableAmt());
			onGoingProject.setFreeCashFlow(requestBean.getFreeCashFlow());
			onGoingProject.setTotalSaleValue(requestBean.getTotalSaleValue());				
			onGoingProject.setDebtOutstanding(requestBean.getDebtOutstanding());
			onGoingProject.setProfit(requestBean.getProfit());
			onGoingProject.setTotalDebtInCr(requestBean.getTotalDebtInCrores());
			onGoingProject.setConstructionStage(requestBean.getStageOfConstruction());
			onGoingProject.setStartDate(CommonUtils.getFormatedDateByDate(requestBean.getStartDate(),
					CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			onGoingProject.setCompletionDate(CommonUtils.getFormatedDateByDate(
					requestBean.getExpectedCompletionDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			onGoingProject.setIsConstructionFinance(requestBean.getIsConstructionFinance());
			if (requestBean.getIsConstructionFinance().equals(YesOrNo.YES)) {
				onGoingProject.setFinancialInstitutionName(requestBean.getNameOfFinancialInstitution());
			}
			devOnGoingProjectRepo.save(onGoingProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateOngoingProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteOngoingProject(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevOnGoingProjectId id = new DevOnGoingProjectId(developerId, reqBean.getSequence());

			Optional<DevOnGoingProject> optional = devOnGoingProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Ongoing Project Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevOnGoingProject onGoingProject = optional.get();
			onGoingProject.setStatus(StatusType.DELETED);
			devOnGoingProjectRepo.save(onGoingProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteOngoingProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateUpcomingProject(SaveUpcomingProjectReqBean requestBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(requestBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevUpcomingProjectId id = new DevUpcomingProjectId(developerId, requestBean.getSequence());
			Optional<DevUpcomingProject> optional = devUpcomingProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Upcoming Project Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevUpcomingProject upcomingProject = optional.get();
			upcomingProject.setName(requestBean.getProjectName());
			upcomingProject.setFirmName(requestBean.getCompanyName());
			upcomingProject.setLocation(requestBean.getLocation());
			upcomingProject.setProjectType(requestBean.getTypeOfProject());
			upcomingProject.setProjectUnderJV(requestBean.getProjectIsUnderJv());
			upcomingProject.setJvPartnerShare(requestBean.getJvPartnerShare());
			upcomingProject.setTotalSaleableArea(requestBean.getTotalSaleableArea());
			upcomingProject.setTotalCost(requestBean.getTotalCost());
			upcomingProject.setTotalProjectValue(requestBean.getTotalProjectSalesValue());
			upcomingProject.setProfit(requestBean.getProfit());
			upcomingProject.setStartDate(CommonUtils.getFormatedDateByDate(requestBean.getProposedStartDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			devUpcomingProjectRepo.saveAndFlush(upcomingProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateUpcomingProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteUpcomingProject(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevUpcomingProjectId id = new DevUpcomingProjectId(developerId, reqBean.getSequence());

			Optional<DevUpcomingProject> optional = devUpcomingProjectRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Upcoming Project Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevUpcomingProject devUpcomingProject = optional.get();
			devUpcomingProject.setStatus(StatusType.DELETED);
			devUpcomingProjectRepo.saveAndFlush(devUpcomingProject);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteUpcomingProject ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateLandHolding(SaveLandholdingReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevLandholdingDtlsId id = new DevLandholdingDtlsId(developerId, reqBean.getSequence());

			Optional<DevLandholdingDtls> optional = devLandholdingDtlsRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Land Holding Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevLandholdingDtls landholdingDtls = optional.get();
			landholdingDtls.setName(reqBean.getOwnerName());
			landholdingDtls.setProjectName(reqBean.getProjectName());
			landholdingDtls.setLandUse(reqBean.getLandUse());
			landholdingDtls.setLocation(reqBean.getLocation());
			landholdingDtls.setArea(reqBean.getArea());
			landholdingDtls.setBookValue(reqBean.getBookValue());
			landholdingDtls.setMarketValue(reqBean.getMarketValue());
			landholdingDtls.setPurchaseYear(CommonUtils.getFormatedDateByDate(reqBean.getPurchaseYear(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			devLandholdingDtlsRepo.saveAndFlush(landholdingDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateLandHolding ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteLandHolding(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevLandholdingDtlsId id = new DevLandholdingDtlsId(developerId, reqBean.getSequence());

			Optional<DevLandholdingDtls> optional = devLandholdingDtlsRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Land Holding Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevLandholdingDtls landholdingDtls = optional.get();
			landholdingDtls.setStatus(StatusType.DELETED);
			devLandholdingDtlsRepo.saveAndFlush(landholdingDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteLandHolding ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateGroupDebt(SaveGroupDbtCashFlowsReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);
			DevGroupDebtDtlsId id = new DevGroupDebtDtlsId(developerId, reqBean.getSequence());

			Optional<DevGroupDebtDtls> optional = devGroupDebtDtlsRepo.findById(id);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Group Debt Data."),
						HttpStatus.BAD_REQUEST);
			}
			DevGroupDebtDtls groupDebtDtls = optional.get();

			groupDebtDtls.setName(reqBean.getInstitutionName());
			groupDebtDtls.setFacilityType(reqBean.getFacilityType());
			groupDebtDtls.setBorrowingEntity(reqBean.getBorrowingEntity());
			groupDebtDtls.setProjectSecurity(reqBean.getProjectSecurity());
			groupDebtDtls.setInterestRate(reqBean.getInterestRate());
			groupDebtDtls.setSanctionAmount(reqBean.getSanctionAmount());
			groupDebtDtls.setSanctionDate(CommonUtils.getFormatedDateByDate(reqBean.getSanctionDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			groupDebtDtls.setSanctionLimits(reqBean.getSanctionLimits());
			groupDebtDtls.setDisbursed(reqBean.getDisbursed());
			groupDebtDtls.setDisbursementDate(CommonUtils.getFormatedDateByDate(reqBean.getDisbursementDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			groupDebtDtls.setTobeDisbursed(reqBean.getTobeDisbursed());
			groupDebtDtls.setCurrentOutstanding(reqBean.getCurrentOutstanding());
			groupDebtDtls.setRepaymentStartDate(CommonUtils.getFormatedDateByDate(reqBean.getRepaymentStartDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			groupDebtDtls.setTenure(reqBean.getTenure());
			groupDebtDtls.setRevisedTenure(reqBean.getRevisedTenure());
			groupDebtDtls.setLoanEndDate(CommonUtils.getFormatedDateByDate(reqBean.getLoanEndDate(), CoreConstants.DEFAULT_DATE_MIN_FORMAT));
			groupDebtDtls.setSanctionLetterPath(reqBean.getSanctionLetterPath());
			devGroupDebtDtlsRepo.saveAndFlush(groupDebtDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateGroupDebt ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> updateCashFlows(UpdateCashFlowsReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);

			Optional<Developers> optional = developersRepo.findById(developerId);
			if (optional.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Developer Data."),
						HttpStatus.BAD_REQUEST);
			}
			Developers developers = optional.get();
			developers.setCashFlowsDocPath(reqBean.getUrlPath());

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateCashFlows ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> deleteGroupDebt(DeveloperDeleteReqBean reqBean) {
		try {
			String decryptedDeveloperId = CansCrypt.decrypt(reqBean.getDeveloperId());
			BigInteger developerId = new BigInteger(decryptedDeveloperId);

			DevGroupDebtDtlsId id = new DevGroupDebtDtlsId(developerId, reqBean.getSequence());

			Optional<DevGroupDebtDtls> debtDtls = devGroupDebtDtlsRepo.findById(id);
			if (debtDtls.isEmpty()) {
				return new ResponseEntity<>(commonUtil.getFailureMessage("Unable to find Group Debt Data."),
						HttpStatus.BAD_REQUEST);
			}

			DevGroupDebtDtls devGroupDebtDtls = debtDtls.get();
			devGroupDebtDtls.setStatus(StatusType.DELETED);
			devGroupDebtDtlsRepo.saveAndFlush(devGroupDebtDtls);

			return new ResponseEntity<>(commonUtil.getSuccessMessage(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in deleteGroupDebt ", e);
		}
		return new ResponseEntity<>(commonUtil.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
