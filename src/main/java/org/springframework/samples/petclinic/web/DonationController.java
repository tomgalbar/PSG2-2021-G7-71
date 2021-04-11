package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {
	
	private final DonationService donationService;
	
	public DonationController(DonationService donationService) {
		this.donationService = donationService;
	}
	
	
	
}
