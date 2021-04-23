package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {
	
	private final DonationService donationService;
	private final CauseService causeService;
	
	@Autowired
	public DonationController(DonationService donationService, CauseService causeService) {
		this.donationService = donationService;
		this.causeService = causeService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("cause")
	public Cause findCauseById(@PathVariable("causeId") Integer causeId) {
		return this.causeService.findById(causeId);
	}
	
	@GetMapping(value = "/donations/new")
	public String initCreationForm(Map<String, Object> model, Cause cause) {
		Donation donation = new Donation();
		cause.addDonation(donation);
		model.put("donation", donation);
		return "donations/createOrUpdateDonationForm";
	}
	
	@PostMapping(value = "/donations/new")
	public String processCreationForm(@Valid Donation donation, BindingResult result, Cause cause) {		
		if (result.hasErrors()) {
			return "donations/createOrUpdateDonationForm";
		}
		else {
			cause.addDonation(donation);
			this.donationService.save(donation);
			
			this.causeService.save(cause);

			return "redirect:/causes";
		}
	}
	
}