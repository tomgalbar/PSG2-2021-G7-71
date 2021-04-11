package org.springframework.samples.petclinic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class CauseController {
	
	private final CauseService causeService;
	
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
		Map<Cause,Double> causes = new HashMap<Cause, Double>();
				
		for (Cause c: lc) {
			causes.put(c, c.getBudgetAchieved());
		}
				
		model.put("causes", causes);
		return "causes/causeList";
	}
	
	
}
