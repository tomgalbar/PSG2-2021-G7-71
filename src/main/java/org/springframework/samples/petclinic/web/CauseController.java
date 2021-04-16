package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {
	
	private final CauseService causeService;
	
	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/causes")
	public String showCauses(Map<String, Object> model) {
		List<Cause> lc = this.causeService.findAll();
		
//		Map<Cause,Double> causes = new HashMap<Cause, Double>();
				
//		for (Cause c: lc) {
//			causes.put(c, c.getBudgetAchieved());
//		}
				
		model.put("causes", lc);
		return "causes/causeList";
	}
	
	@GetMapping(value = "/causes/new")
	public String initCreationForm(Map<String, Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return "causes/createOrUpdateCauseForm";
	}
	
	@PostMapping(value = "/causes/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result) {
		if (result.hasErrors()) {
			return "causes/createOrUpdateCauseForm";
		}
		else {
			this.causeService.save(cause);			
			return "redirect:/causes";
		}
	}
	
	
	@GetMapping(value = "/causes/{causeId}")
	public String showCauseDetails(@PathVariable("causeId") Integer causeId, Map<String, Object> model) {
		Cause cause = this.causeService.findById(causeId);
		model.put("cause", cause);
		return "causes/causeDetails";
	}
	
	
}
