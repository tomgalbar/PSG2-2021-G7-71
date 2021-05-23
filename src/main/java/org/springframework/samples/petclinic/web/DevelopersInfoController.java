package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevelopersInfoController {

	public DevelopersInfoController() {
	}
	
	@GetMapping(value = "/developers")
	public String showDevelopersInfo() {
		return "developers/list";
	}
}