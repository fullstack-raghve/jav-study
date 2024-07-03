package com.bindlabs.developer.model;

import java.util.List;

import lombok.Data;

@Data
public class ViewDeveloperResponse {

	private GroupStructureBean groupStructureDetails;
	private List<PartnerDetailsResponseBean> partnerDetails;
	private List<KeyManagerialResponseBean> keyManagerialDetails;
	private List<CompletedProjectsResponseBean> completedProjects;
	private List<OnGoingProjectsResponseBean> onGoingProjects;
	private List<UpcomingProjectsResponseBean> upcomingProjects;
	private List<LandHoldingResponseBean> landHoldingDetails;
	private List<GroupDebtResponseBean> groupDebtDetails;

}
