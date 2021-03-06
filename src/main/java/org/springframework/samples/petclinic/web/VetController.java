/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.SpecialtyConstructor;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private final VetService vetService;
	
	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
	private static final String VETS_LIST = "redirect:/vets"; 

	@Autowired
	public VetController(VetService clinicService) {
		this.vetService = clinicService;
	}
	
	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialties() {
		return this.vetService.findSpecialties();
	}
	
	@InitBinder("vet")
	public void initVetBinder(WebDataBinder dataBinder) {
	dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, SpecialtyConstructor specialties) {
		if (result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}
		
		else {
			if (specialties!=null) {	
				List<String> lsp = specialties.getSpecialties();
				
				for (int i = 0; i < lsp.size(); i++) {
					Specialty sp = vetService.findSpecialtyByName(lsp.get(i));
					if(sp!=null) {
						vet.addSpecialty(sp);
					}
				}
			}
			
			this.vetService.saveVet(vet);
			return VETS_LIST;
		}
	}
	
	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId);
		model.addAttribute(vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}
	
    @PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateForm(@Valid Vet vet, BindingResult result, SpecialtyConstructor specialties,@PathVariable("vetId") int vetId) {
		if (result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}
		else {
			vet.setId(vetId);
			if (specialties!=null) {	
				List<String> lsp = specialties.getSpecialties();
				
				for (int i = 0; i < lsp.size(); i++) {
					Specialty sp = vetService.findSpecialtyByName(lsp.get(i));
					if(sp!=null) {
						vet.addSpecialty(sp);
					}
				}
			}
			this.vetService.saveVet(vet);
			return VETS_LIST;
		}
    }
    
	@GetMapping(value = "/vets/{vetId}/delete")
	public String deleteVet(@PathVariable("vetId") int vetId) {
		Vet vet = this.vetService.findVetById(vetId);
		this.vetService.deleteVet(vet);
		return VETS_LIST;
	}
}
