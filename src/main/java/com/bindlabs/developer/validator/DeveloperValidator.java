package com.bindlabs.developer.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.bindlabs.core.enums.YesOrNo;
import com.bindlabs.core.model.FileNameRequestBean;
import com.bindlabs.developer.model.DeveloperDeleteReqBean;
import com.bindlabs.developer.model.GroupStrDtlsReqBean;
import com.bindlabs.developer.model.ManagerialPersonalReqBean;
import com.bindlabs.developer.model.PartnerDetails;
import com.bindlabs.developer.model.PartnerDetailsReqBean;
import com.bindlabs.developer.model.SaveCompletedProjectReqBean;
import com.bindlabs.developer.model.SaveGroupDbtCashFlowsReqBean;
import com.bindlabs.developer.model.SaveKeyManagerialReqBean;
import com.bindlabs.developer.model.SaveLandholdingReqBean;
import com.bindlabs.developer.model.SaveOnGoingProjectReqBean;
import com.bindlabs.developer.model.SaveUpcomingProjectReqBean;

@Component
public class DeveloperValidator implements Validator {
	private static final Logger logger = LogManager.getLogger(DeveloperValidator.class);

	@Autowired
	private MessageSource messageSource;

	private static String emptyKeyName = "developer.create.empty";
	private static final String INVALID_VAR = "developer.invalid.variable";

	private static final String SEQ_VAR_NAME = "sequence";

	private static final String SEQ_VAR = "Sequence Number";

	@Value("${bind.xlsx.size}")
	private Integer xlsxMaxSize;

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

	public List<ObjectError> validateGroupStructureReq(GroupStrDtlsReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null == requestBean.getName() || requestBean.getName().isEmpty()) {
			result.add(new ObjectError("name",
					messageSource.getMessage(emptyKeyName, new Object[] { "Group Name" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getYearOfFormation()) {
			result.add(new ObjectError("yearOfFormation",
					messageSource.getMessage(emptyKeyName, new Object[] { "Year Of Formation" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getNatureOfBusiness() || requestBean.getNatureOfBusiness().isEmpty()) {
			result.add(new ObjectError("natureOfBusiness", messageSource.getMessage(emptyKeyName,
					new Object[] { "Nature Of Business Name" }, Locale.ENGLISH)));
		}		
		if ((null == requestBean.getPanNo() || requestBean.getPanNo().isEmpty()) &&
				(null == requestBean.getCinNumber()	|| requestBean.getCinNumber().isEmpty()) &&
				(null == requestBean.getGstin() || requestBean.getGstin().isEmpty())) {
			result.add(new ObjectError("panNo", messageSource.getMessage(emptyKeyName,
					new Object[] { "All three details (Pan No, Cin No and Gstin No)" }, Locale.ENGLISH)));
		}
		List<PartnerDetails> partnerDtlsList = requestBean.getPartnersDetails();
		if (null != partnerDtlsList) {
			for (PartnerDetails partnerDtls : partnerDtlsList) {
				if (null != partnerDtls.getName() && partnerDtls.getName().isEmpty()) {
					result.add(new ObjectError("name",
							messageSource.getMessage(emptyKeyName, new Object[] { "Partner Name" }, Locale.ENGLISH)));
				}
				if (null != partnerDtls.getDin() && partnerDtls.getDin().isEmpty()) {
					result.add(new ObjectError("din", messageSource.getMessage(emptyKeyName,
							new Object[] { "Partner DIN Number" }, Locale.ENGLISH)));
				}
				if (null != partnerDtls.getPanNo() && partnerDtls.getPanNo().isEmpty()) {
					result.add(new ObjectError("panNo", messageSource.getMessage(emptyKeyName,
							new Object[] { "Partner PAN Number" }, Locale.ENGLISH)));
				}
				if (null != partnerDtls.getPresentAddress() && partnerDtls.getPresentAddress().isEmpty()) {
					result.add(new ObjectError("presentAddress", messageSource.getMessage(emptyKeyName,
							new Object[] { "Partner Present Address" }, Locale.ENGLISH)));
				}
			}
		}

		if (null == requestBean.getKycDocPath() || requestBean.getKycDocPath().isEmpty()) {
			result.add(new ObjectError("kycDocPath",
					messageSource.getMessage(emptyKeyName, new Object[] { "Kyc Doc Path Url" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getIncorporationDocPath() || requestBean.getIncorporationDocPath().isEmpty()) {
			result.add(new ObjectError("incorporationDocPath",
					messageSource.getMessage(emptyKeyName, new Object[] { "Incorporation Doc Path" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getFinancialDocPath() || requestBean.getFinancialDocPath().isEmpty()) {
			result.add(new ObjectError("financialDocPath",
					messageSource.getMessage(emptyKeyName, new Object[] { "Financial Doc Path Url" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getOrgStructureDocPath() || requestBean.getOrgStructureDocPath().isEmpty()) {
			result.add(new ObjectError("orgStructureDocPath", messageSource.getMessage(emptyKeyName,
					new Object[] { "OrgStructure Doc Path Url" }, Locale.ENGLISH)));
		}

		return result;
	}

	public List<ObjectError> validateKeyManagerialReq(SaveKeyManagerialReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getName() && requestBean.getName().isEmpty()) {
			result.add(new ObjectError("name",
					messageSource.getMessage(emptyKeyName, new Object[] { "Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getEmail() && requestBean.getEmail().isEmpty()) {
			result.add(new ObjectError("email",
					messageSource.getMessage(emptyKeyName, new Object[] { "email Id" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getYearOfExperience()) {
			result.add(new ObjectError("yearOfExperience",
					messageSource.getMessage(emptyKeyName, new Object[] { "Year Of Experience" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getAge()) {
			result.add(new ObjectError("age",
					messageSource.getMessage(emptyKeyName, new Object[] { "Age" }, Locale.ENGLISH)));
		}
		if (requestBean.getIsShareHolder() == null || requestBean.getIsShareHolder().equals(YesOrNo.YES)
				&& (null != requestBean.getShareValue() && requestBean.getShareValue().isEmpty())) {
			result.add(new ObjectError("shareValue", "Invalid Value"));

		}
		if (null != requestBean.getKycProof() && requestBean.getKycProof().isEmpty()) {
			result.add(new ObjectError("kycProof",
					messageSource.getMessage(emptyKeyName, new Object[] { "kyc Proof Url" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getWriteUp() && requestBean.getWriteUp().isEmpty()) {
			result.add(new ObjectError("writeUp",
					messageSource.getMessage(emptyKeyName, new Object[] { "write Up Url" }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> validateCompletedProjectReq(SaveCompletedProjectReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getReraId() && requestBean.getReraId().isEmpty()) {
			result.add(new ObjectError("reraId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Rera Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getProjectName() && requestBean.getProjectName().isEmpty()) {
			result.add(new ObjectError("name",
					messageSource.getMessage(emptyKeyName, new Object[] { "Project Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getCompanyName() && requestBean.getCompanyName().isEmpty()) {
			result.add(new ObjectError("companyName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Company Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getJvPercentage() && requestBean.getJvPercentage().isEmpty()) {
			result.add(new ObjectError("jvPercentage",
					messageSource.getMessage(emptyKeyName, new Object[] { "JV Percentage Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getLocation() && requestBean.getLocation().isEmpty()) {
			result.add(new ObjectError("location",
					messageSource.getMessage(emptyKeyName, new Object[] { "Location" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getTypeOfProject() && requestBean.getTypeOfProject().isEmpty()) {
			result.add(new ObjectError("typeOfProject",
					messageSource.getMessage(emptyKeyName, new Object[] { "Type Of Project" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getNoOfUnits()) {
			result.add(new ObjectError("noOfUnits",
					messageSource.getMessage(emptyKeyName, new Object[] { "Number Of Units" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getCostOfProject()) {
			result.add(new ObjectError("costOfProject",
					messageSource.getMessage(emptyKeyName, new Object[] { "Cost Of Project" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSalesValuePerSq()) {
			result.add(new ObjectError("salesValuePerSq",
					messageSource.getMessage(emptyKeyName, new Object[] { "sales Value Per Sq" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getCommencementYear() && requestBean.getCommencementYear().isEmpty()) {
			result.add(new ObjectError("commencementYear",
					messageSource.getMessage(emptyKeyName, new Object[] { "Commencement Year" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getCompletionYear() && requestBean.getCompletionYear().isEmpty()) {
			result.add(new ObjectError("completionYear",
					messageSource.getMessage(emptyKeyName, new Object[] { "Completion Year" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getUnsoldUnits()) {
			result.add(new ObjectError("unsoldUnits",
					messageSource.getMessage(emptyKeyName, new Object[] { "Unsold Units" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getBankLoan()) {
			result.add(new ObjectError("bankLoan",
					messageSource.getMessage(emptyKeyName, new Object[] { "Bank Loan" }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> validateOnGoingProjectReq(SaveOnGoingProjectReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getReraId() && requestBean.getReraId().isEmpty()) {
			result.add(new ObjectError("reraId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Rera Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getProjectName() && requestBean.getProjectName().isEmpty()) {
			result.add(new ObjectError("projectName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Project Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getLocation() && requestBean.getLocation().isEmpty()) {
			result.add(new ObjectError("location",
					messageSource.getMessage(emptyKeyName, new Object[] { "Location" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getTypeOfDevelopment() && requestBean.getTypeOfDevelopment().isEmpty()) {
			result.add(new ObjectError("typeOfDevelopment",
					messageSource.getMessage(emptyKeyName, new Object[] { "Type Of Development" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getCompanyName() && requestBean.getCompanyName().isEmpty()) {
			result.add(new ObjectError("companyName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Company Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getStructureOfBuilding() && requestBean.getStructureOfBuilding().isEmpty()) {
			result.add(new ObjectError("structureOfBuilding",
					messageSource.getMessage(emptyKeyName, new Object[] { "Structure Of Building" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getTotalCarpetAreaSqFt() && requestBean.getTotalCarpetAreaSqFt().isEmpty()) {
			result.add(new ObjectError("totalCarpetAreaSqFt", messageSource.getMessage(emptyKeyName,
					new Object[] { "Total Carpet Area Sq Ft" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getDeveloperShareSqFt() && requestBean.getDeveloperShareSqFt().isEmpty()) {
			result.add(new ObjectError("developerShareSqFt",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Share Sq Ft" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getNoOfUnits()) {
			result.add(new ObjectError("noOfUnits",
					messageSource.getMessage(emptyKeyName, new Object[] { "No Of Units" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getDeveloperUnits()) {
			result.add(new ObjectError("developerUnits",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Units" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getProjectCost()) {
			result.add(new ObjectError("projectCost",
					messageSource.getMessage(emptyKeyName, new Object[] { "projectCost" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getCostIncurredTillDate()) {
			result.add(new ObjectError("costIncurredTillDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "costIncurredTillDate" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getCostToBeIncurred()) {
			result.add(new ObjectError("costToBeIncurred",
					messageSource.getMessage(emptyKeyName, new Object[] { "costToBeIncurred" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getUnitsSold()) {
			result.add(new ObjectError("unitsSold",
					messageSource.getMessage(emptyKeyName, new Object[] { "unitsSold" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getAreaSold() && requestBean.getAreaSold().isEmpty()) {
			result.add(new ObjectError("areaSold",
					messageSource.getMessage(emptyKeyName, new Object[] { "areaSold" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getAreaUnsold() && requestBean.getAreaUnsold().isEmpty()) {
			result.add(new ObjectError("areaUnsold",
					messageSource.getMessage(emptyKeyName, new Object[] { "areaUnsold" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSoldUnitsAmounts()) {
			result.add(new ObjectError("soldUnitsAmounts",
					messageSource.getMessage(emptyKeyName, new Object[] { "soldUnitsAmounts" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getBalanceSoldUnitAmounts()) {
			result.add(new ObjectError("balanceSoldUnitAmounts",
					messageSource.getMessage(emptyKeyName, new Object[] { "balanceSoldUnitAmounts" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getUnsoldUnitsAmounts()) {
			result.add(new ObjectError("unsoldUnitsAmounts",
					messageSource.getMessage(emptyKeyName, new Object[] { "unsoldUnitsAmounts" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getCostIncurredTillDate()) {
			result.add(new ObjectError("costIncurredTillDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "costIncurredTillDate" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSalePricePerSqFtForUnsoldUnit()) {
			result.add(new ObjectError("salePricePerSqFtForUnsoldUnit", messageSource.getMessage(emptyKeyName,
					new Object[] { "salePricePerSqFtForUnsoldUnit" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getExpectedRentalPerSqFtPerMonth()) {
			result.add(new ObjectError("expectedRentalPerSqFtPerMonth", messageSource.getMessage(emptyKeyName,
					new Object[] { "expectedRentalPerSqFtPerMonth" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTotalReceivableAmt()) {
			result.add(new ObjectError("totalReceivableAmt",
					messageSource.getMessage(emptyKeyName, new Object[] { "totalReceivableAmt" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getFreeCashFlow()) {
			result.add(new ObjectError("freeCashFlow",
					messageSource.getMessage(emptyKeyName, new Object[] { "freeCashFlow" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTotalSaleValue()) {
			result.add(new ObjectError("totalSaleValue",
					messageSource.getMessage(emptyKeyName, new Object[] { "totalSaleValue" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getDebtOutstanding()) {
			result.add(new ObjectError("debtOutstanding",
					messageSource.getMessage(emptyKeyName, new Object[] { "debtOutstanding" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getProfit()) {
			result.add(new ObjectError("profit",
					messageSource.getMessage(emptyKeyName, new Object[] { "Profit" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTotalDebtInCrores()) {
			result.add(new ObjectError("totalDebtInCrores",
					messageSource.getMessage(emptyKeyName, new Object[] { "Total DebtIn Crores" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getStageOfConstruction() && requestBean.getStageOfConstruction().isEmpty()) {
			result.add(new ObjectError("stageOfConstruction",
					messageSource.getMessage(emptyKeyName, new Object[] { "stageOfConstruction" }, Locale.ENGLISH)));
		}

		if (null == requestBean.getStartDate()) {
			result.add(new ObjectError("startDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "startDate" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getExpectedCompletionDate()) {
			result.add(new ObjectError("expectedCompletionDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "expectedCompletionDate" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getIsConstructionFinance()) {
			result.add(new ObjectError("isConstructionFinance",
					messageSource.getMessage(emptyKeyName, new Object[] { "isConstructionFinance" }, Locale.ENGLISH)));
		}
		if (requestBean.getIsConstructionFinance() != null && requestBean.getIsConstructionFinance().equals(YesOrNo.YES)
				&& (null != requestBean.getNameOfFinancialInstitution()
						&& requestBean.getNameOfFinancialInstitution().isEmpty())) {
			result.add(new ObjectError("nameOfFinancialInstitution", messageSource.getMessage(emptyKeyName,
					new Object[] { "nameOfFinancialInstitution" }, Locale.ENGLISH)));

		}
		return result;
	}

	public List<ObjectError> validateUpcomingProjectReq(SaveUpcomingProjectReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getProjectName() && requestBean.getProjectName().isEmpty()) {
			result.add(new ObjectError("projectName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Project Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getCompanyName() && requestBean.getCompanyName().isEmpty()) {
			result.add(new ObjectError("companyName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Company Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getLocation() && requestBean.getLocation().isEmpty()) {
			result.add(new ObjectError("location",
					messageSource.getMessage(emptyKeyName, new Object[] { "Location" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getTypeOfProject() && requestBean.getTypeOfProject().isEmpty()) {
			result.add(new ObjectError("typeOfProject",
					messageSource.getMessage(emptyKeyName, new Object[] { "typeOfProject" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getProjectIsUnderJv() && requestBean.getProjectIsUnderJv().isEmpty()) {
			result.add(new ObjectError("projectIsUnderJv",
					messageSource.getMessage(emptyKeyName, new Object[] { "projectIsUnderJv" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getJvPartnerShare() && requestBean.getJvPartnerShare().isEmpty()) {
			result.add(new ObjectError("jvPartnerShare",
					messageSource.getMessage(emptyKeyName, new Object[] { "JV Partner Share" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getTotalSaleableArea() && requestBean.getTotalSaleableArea().isEmpty()) {
			result.add(new ObjectError("totalSaleableArea",
					messageSource.getMessage(emptyKeyName, new Object[] { "totalSaleableArea" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTotalCost()) {
			result.add(new ObjectError("totalCost",
					messageSource.getMessage(emptyKeyName, new Object[] { "TotalCost" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTotalProjectSalesValue()) {
			result.add(new ObjectError("totalProjectSalesValue",
					messageSource.getMessage(emptyKeyName, new Object[] { "totalProjectSalesValue" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getProfit()) {
			result.add(new ObjectError("profit",
					messageSource.getMessage(emptyKeyName, new Object[] { "profit" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getProposedStartDate()) {
			result.add(new ObjectError("proposedStartDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "proposedStartDate" }, Locale.ENGLISH)));
		}
		return result;

	}

	public List<ObjectError> validateLandholdingReq(SaveLandholdingReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getOwnerName() && requestBean.getOwnerName().isEmpty()) {
			result.add(new ObjectError("ownerName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Owner Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getProjectName() && requestBean.getProjectName().isEmpty()) {
			result.add(new ObjectError("projectName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Project Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getLandUse() && requestBean.getLandUse().isEmpty()) {
			result.add(new ObjectError("landUse",
					messageSource.getMessage(emptyKeyName, new Object[] { "LandUse" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getLocation() && requestBean.getLocation().isEmpty()) {
			result.add(new ObjectError("location",
					messageSource.getMessage(emptyKeyName, new Object[] { "Location" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getArea() && requestBean.getArea().isEmpty()) {
			result.add(new ObjectError("area",
					messageSource.getMessage(emptyKeyName, new Object[] { "Area" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getBookValue()) {
			result.add(new ObjectError("bookValue",
					messageSource.getMessage(emptyKeyName, new Object[] { "BookValue" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getMarketValue()) {
			result.add(new ObjectError("marketValue",
					messageSource.getMessage(emptyKeyName, new Object[] { "Market Value" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getPurchaseYear()) {
			result.add(new ObjectError("purchaseYear",
					messageSource.getMessage(emptyKeyName, new Object[] { "Purchase Year" }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> validateGroupDbtCashFlowsReq(SaveGroupDbtCashFlowsReqBean requestBean) {
		List<ObjectError> result = new ArrayList<>();
		if (null != requestBean.getDeveloperId() && requestBean.getDeveloperId().isEmpty()) {
			result.add(new ObjectError("developerId",
					messageSource.getMessage(emptyKeyName, new Object[] { "Developer Id" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getInstitutionName() && requestBean.getInstitutionName().isEmpty()) {
			result.add(new ObjectError("institutionName",
					messageSource.getMessage(emptyKeyName, new Object[] { "Institution Name" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getFacilityType() && requestBean.getFacilityType().isEmpty()) {
			result.add(new ObjectError("facilityType",
					messageSource.getMessage(emptyKeyName, new Object[] { "Facility Type" }, Locale.ENGLISH)));
		}
		if (null != requestBean.getBorrowingEntity() && requestBean.getBorrowingEntity().isEmpty()) {
			result.add(new ObjectError("borrowingEntity",
					messageSource.getMessage(emptyKeyName, new Object[] { "Borrowing Entity" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getProjectSecurity()) {
			result.add(new ObjectError("projectSecurity",
					messageSource.getMessage(emptyKeyName, new Object[] { "Project Security" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getInterestRate()) {
			result.add(new ObjectError("interestRate",
					messageSource.getMessage(emptyKeyName, new Object[] { "Interest Rate" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSanctionAmount()) {
			result.add(new ObjectError("sanctionAmount",
					messageSource.getMessage(emptyKeyName, new Object[] { "Sanction Amount" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSanctionDate()) {
			result.add(new ObjectError("sanctionDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "Sanction Date" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSanctionLimits()) {
			result.add(new ObjectError("sanctionLimits",
					messageSource.getMessage(emptyKeyName, new Object[] { "Sanction Limits" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getDisbursed()) {
			result.add(new ObjectError("disbursed",
					messageSource.getMessage(emptyKeyName, new Object[] { "Disbursed" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getDisbursementDate()) {
			result.add(new ObjectError("disbursementDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "Disbursement Date" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTobeDisbursed()) {
			result.add(new ObjectError("tobeDisbursed",
					messageSource.getMessage(emptyKeyName, new Object[] { "Tobe Disbursed" }, Locale.ENGLISH)));
		}		
		if (null == requestBean.getCurrentOutstanding()) {
			result.add(new ObjectError("currentOutstanding",
					messageSource.getMessage(emptyKeyName, new Object[] { "Current Outstanding" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getRepaymentStartDate()) {
			result.add(new ObjectError("repaymentStartDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "Repayment Start Date" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getTenure()) {
			result.add(new ObjectError("tenure",
					messageSource.getMessage(emptyKeyName, new Object[] { "Tenure" }, Locale.ENGLISH)));
		}		
		if (null == requestBean.getLoanEndDate()) {
			result.add(new ObjectError("loanEndDate",
					messageSource.getMessage(emptyKeyName, new Object[] { "Loan End Date" }, Locale.ENGLISH)));
		}
		if (null == requestBean.getSanctionLetterPath() || requestBean.getSanctionLetterPath().isEmpty()) {
			result.add(new ObjectError("sanctionLetterPath",
					messageSource.getMessage(emptyKeyName, new Object[] { "SanctionLetterPath" }, Locale.ENGLISH)));
		}

		return result;
	}

	public void validateXlsxCompletedProjectReq(Object target, Errors errors) {
		FileNameRequestBean fileNameRequestBean = (FileNameRequestBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "file", "Please upload a file");

		validateFileExtensionAndFileSize(fileNameRequestBean.getFile(), errors);

		try (Workbook workbook = WorkbookFactory.create(fileNameRequestBean.getFile().getInputStream());) {

			String[] expectedHeader = { "DEVELOPER_ID", "RERA_ID", "NAME_OF_THE_PROJECT", "NAME_OF_THE_COMPANY",
					"IN_CASE_OF_JV", "LOCATION", "TYPE_OF_PROJECT", "SALEABLE_AREA", "NUMBER_OF_UNITS",
					"COST_OF_PROJECT", "SALES_VALUE_AS_PER_SQ.FT", "COMMENCEMENT_YEAR", "COMPLETION_YEAR",
					"WHETHER_OC_RECIVED", "UNSOLD_UNITS", "BANK_LOAN" };

			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}

		} catch (Exception e) {
			logger.error("@@@ Exception in validatexlsxCompletedProjectReq:  ", e);
		}
	}

	private void validateFileExtensionAndFileSize(MultipartFile multipartFile, Errors errors) {
		
		if(multipartFile != null) {
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			if(!extension.equalsIgnoreCase("xlsx")) {
				errors.rejectValue("file", "Please upload a xlsx file");
			}
			else if(multipartFile.getSize()>xlsxMaxSize ) {
				errors.rejectValue("file", "Please upload a file of size Max 10 MB");
			}
			
			String fileMimeType = MimeMappings.DEFAULT.get(extension);
	        String dataMimeType = getMimeTypeByFileData(multipartFile);

	        if (!isSupportedContentType(dataMimeType)) {
	        	errors.rejectValue("file", dataMimeType + " mime Type not allowed.");
	        }

	        if (!fileMimeType.equals(dataMimeType)) {
	        	errors.rejectValue("file", "File Content Type not match with extension");
	          
	        }
		}
	}

	    private boolean isSupportedContentType(String contentType) {
	        return contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	                
	    }

	    private String getMimeTypeByFileData(MultipartFile file) {
	        String mimeType="";
	        try {
	            Tika tika = new Tika();
	            mimeType = tika.detect(file.getInputStream());
	            
	        } catch (Exception e) {
	            logger.error("", e);
	        }
	        
	        return mimeType;
	    }	
	

	public void validateXlsxOnGoingProjectReq(Object target, Errors errors) {
		
		FileNameRequestBean fileNameRequestBean = (FileNameRequestBean) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "file", "Please upload a file");
		
		validateFileExtensionAndFileSize(fileNameRequestBean.getFile(), errors);

		try (Workbook workbook = WorkbookFactory.create(fileNameRequestBean.getFile().getInputStream());) {

			String[] expectedHeader = {"DEVELOPER_ID","RERA_ID","NAME_OF_THE_PROJECT","LOCATION","DEVELOPMENT_TYPE","FIRM_NAME","BUILDING_STRUCTURE",
					"TOTAL_CARPET_AREA","DEVELOPER_SHARES","NO_OF_UNITS","DEVELOPER_SHARE_UNITS","PROJECT_COST","TILL_COST_INCURRED","TOBE_COST_INCURRED",
					"UNITS_SOLD","AREA_SOLD","AREA_UNSOLD","SOLD_UNITS_AMOUNT","BALANCE_SOLD_UNITS_AMT","UNSOLD_UNITS_AMOUNT","UNSOLD_UNITS_SALE_PRICE",
					"EXPECTED_RENT_PER_MONTH","TOTAL_RECEIVABLE_AMT","FREE_CASH_FLOW","TOTAL_SALE_VALUE","DEBT_OUTSTANDING","PROFIT","TOTAL_DEBT_IN_CR",
					"CONSTRUCTION_STAGE","START_DATE","COMPLETION_DATE","IS_CONSTRUCTION_FINANCE","FINANCIAL_INSTITUTION_NAME"};
			
			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}
			
		}catch (Exception e) {
			logger.error("@@@ Exception in validatexlsxOnGoingProjectReq:  ", e);
		}
	}

	public void validateXlsxUpcomingProjectReq(Object target, Errors errors) {
		
		FileNameRequestBean fileNameRequestBean = (FileNameRequestBean) target;
		
		validateFileExtensionAndFileSize(fileNameRequestBean.getFile(), errors);

		try (Workbook workbook = WorkbookFactory.create(fileNameRequestBean.getFile().getInputStream());) {

			String[] expectedHeader = {"DEVELOPER_ID","NAME_OF_COMPANY","PROJECT_NAME","LOCATION","TYPE_OF_PROJECT","PROJECT_ID_UNDER_JV",
					"JV_PARTNER_SHARE","TOTAL_SALEABLE_AREA","TOTAL_COST","TOTAL_PROJECT_SALES_VALUE","PROFIT","PROPOSED_START_DATE"};
			
			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}
		}catch (Exception e) {
			logger.error("@@@ Exception in validateXlsxUpcomingProjectReq:  ", e);
		}
	}

	public void validateXlsxLandholdingReq(Object target, Errors errors) {
		
		FileNameRequestBean fileNameRequestBean = (FileNameRequestBean) target;
		
		validateFileExtensionAndFileSize(fileNameRequestBean.getFile(), errors);

		try (Workbook workbook = WorkbookFactory.create(fileNameRequestBean.getFile().getInputStream());) {

			String[] expectedHeader = { "DEVELOPER_ID", "NAME_OF_THE_OWNER", "PROJECT_NAME", "LAND_USE", "LOCATION",
					"AREA", "BOOK_VALUE", "MARKET_VALUE", "PURCHASE_YEAR" };

			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}
		} catch (Exception e) {
			logger.error("@@@ Exception in validateXlsxLandholdingReq:  ", e);
		}
	}

	public void validateXlsxGroupDebtReq(Object target, Errors errors) {
		
		FileNameRequestBean fileNameRequestBean = (FileNameRequestBean) target;
		

		try (Workbook workbook = WorkbookFactory.create(fileNameRequestBean.getFile().getInputStream());) {

			String[] expectedHeader = { "DEVELOPER_ID", "INSTITUTION_NAME", "FACILITY_TYPE", "BORROWING_ENTITY",
					"PROJECT_SECURITY", "INTRESET_RATE", "SANCTION_AMOUNT", "SANCTION_DATE", "SANCTION_LIMITS",
					"DISBURSED", "DISBURSEMENT_DATE", "TOBE_DISBURSED", "CURRENT_OUTSTANDING", "REPAYMENT_STARTDATE",
					"TENURE", "REVISED_TENUR", "LOAN_END_DATE", "SANCTION_LETTER_PATH" };

			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}

		} catch (Exception e) {
			logger.error("@@@ Exception in validateXlsxGroupDebtReq:  ", e);
		}
	}


	public void uploadManagerialPersonal(ManagerialPersonalReqBean reqBean, Errors errors) {
		if (reqBean.getFile() == null) {
			errors.rejectValue("file", "Please upload a file.");
		}
		validateFileExtensionAndFileSize(reqBean.getFile(), errors);
		try (Workbook workbook = WorkbookFactory.create(reqBean.getFile().getInputStream());) {
			String[] expectedHeader = {"DEVELOPER_ID", "NAME","EMAIL_ID","QUALIFICATION","YEARS_OF_EXPERIENCE",
					"AGE","IS_SHARE_HOLDER","SHARE_VALUE","FUNCTIONS_HANDELED"};

			Sheet sheet = workbook.getSheetAt(0);
			Row headerRow = sheet.getRow(0);

			if (headerRow != null) {
				int cellCount = headerRow.getLastCellNum();
				if (cellCount != expectedHeader.length) {
					errors.rejectValue("file", "Number of columns in the header doesn't match expected.");
				}

				for (int i = 0; i < cellCount; i++) {
					Cell cell = headerRow.getCell(i);
					if (cell == null || cell.getCellType() != CellType.STRING
							|| !cell.getStringCellValue().equals(expectedHeader[i])) {
						errors.rejectValue("file", "Header mismatch at column.");
					}
				}
			} else {
				errors.rejectValue("file", "Header row not found.");
			}
			
		}catch (Exception e) {
			logger.error("@@@ Exception in validatexlsxOnGoingProjectReq:  ", e);
		}
	}

	public List<ObjectError> validateUpdateGroupStructure(GroupStrDtlsReqBean reqBean) {
		List<ObjectError> result = new ArrayList<>();
		if (reqBean.getDeveloperId() == null || reqBean.getDeveloperId().isBlank()) {
			result.add(new ObjectError("developerId", "Developer id cannot be blank."));
		}
		result.addAll(validateGroupStructureReq(reqBean));
		return result;
	}

	public void validateUpdatePartnerDetails(PartnerDetailsReqBean reqBean, Errors errors) {
		if (reqBean.getSequence() == null || reqBean.getSequence() == 0) {
			errors.rejectValue("sequence", "Invalid sequence value.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "developerId", "Developer Id cannot be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Name cannot be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "din", "Din Number cannot be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "presentAddress", "Address cannot be empty.");
	}

	public void deletionValidation(DeveloperDeleteReqBean reqBean, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "developerId", "Developer Id cannot be empty.");
		if (reqBean.getSequence() == null || reqBean.getSequence().equals(0)) {
			errors.rejectValue(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH));
		}
	}

	public List<ObjectError> updateKeyManagerialValidation(SaveKeyManagerialReqBean requestBean) {
		List<ObjectError> result = validateKeyManagerialReq(requestBean);
		if (null == requestBean.getSequence() || requestBean.getSequence().equals(0)) {
			result.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> updateCompletedProjectReqValidation(SaveCompletedProjectReqBean reqBean) {
		List<ObjectError> result = validateCompletedProjectReq(reqBean);
		if (null == reqBean.getSequence() || reqBean.getSequence().equals(0)) {
			result.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> updateOnGoingProjectReqValidation(SaveOnGoingProjectReqBean requestBean) {
		List<ObjectError> errorList = validateOnGoingProjectReq(requestBean);
		if (null == requestBean.getSequence() || requestBean.getSequence().equals(0)) {
			errorList.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return errorList;
	}

	public List<ObjectError> updateUpcomingProjectValidation(SaveUpcomingProjectReqBean reqBean) {
		List<ObjectError> result = validateUpcomingProjectReq(reqBean);
		if (null == reqBean.getSequence() || reqBean.getSequence().equals(0)) {
			result.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> updateLandHoldingValidation(SaveLandholdingReqBean reqBean) {
		List<ObjectError> result = validateLandholdingReq(reqBean);
		if (null == reqBean.getSequence() || reqBean.getSequence().equals(0)) {
			result.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return result;
	}

	public List<ObjectError> updateGroupDebtValidation(SaveGroupDbtCashFlowsReqBean reqBean) {
		List<ObjectError> list = validateGroupDbtCashFlowsReq(reqBean);
		if (null == reqBean.getSequence() || reqBean.getSequence().equals(0)) {
			list.add(new ObjectError(SEQ_VAR_NAME, messageSource.getMessage(INVALID_VAR,
					new Object[] { SEQ_VAR }, Locale.ENGLISH)));
		}
		return list;
	}

	public void updateCashFlowsValidation(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "developerId", "Developer Id cannot be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "urlPath", "URL Path cannot be empty.");
	}

}
