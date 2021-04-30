package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdoptionApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdoptionApplicationController {
	
	private AdoptionApplicationService adoptionApplicationService;
	private PetService petService;
	private OwnerService ownerService;
	private UserService userService;
	
	@Autowired
	public AdoptionApplicationController(AdoptionApplicationService adoptionApplicationService, PetService petService, OwnerService ownerService,
			UserService userService) {
		this.adoptionApplicationService = adoptionApplicationService;
		this.petService = petService;
		this.ownerService = ownerService;
		this.userService = userService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/petsInAdoption")
	public String showAllPetsInAdoption(ModelMap model) {
		
		List<Pet> petsInAdoption = this.adoptionApplicationService.findByInAdoption();
		
        Owner owner = findOwnerLoggedIn();
        
        List<Pet> petsOwner = new ArrayList<>();
        
        if(owner.getId()!=null) {
        	petsInAdoption= petsInAdoption.stream().filter(x->!x.getOwner().equals(owner)).collect(Collectors.toList());
    		
    		List<AdoptionApplication> ownerApplications = owner.getAdoptionApplications();
    		
    		petsOwner = ownerApplications.stream().map(AdoptionApplication::getPet).collect(Collectors.toList());
        }
        
		model.put("pets", petsInAdoption);
		model.put("currentApplications", petsOwner);
		
		return "adoptions/petsInAdoption";
	}
	
	@GetMapping(value = "/petsInAdoption/setPetAdoption/{petId}")
	public String setPetInAdoption(@PathVariable("petId") Integer petId, ModelMap model) throws DataAccessException, DuplicatedPetNameException {
		Pet pet = this.petService.findPetById(petId);
		pet.setInAdoption(true);
		petService.savePet(pet);
		
		return "redirect:/owners/" + pet.getOwner().getId();
	}
	
	@GetMapping(value = "/petsInAdoption/adoptionApplications/{petId}")
	public String adoptionsApplicationsByPet(@PathVariable("petId") Integer id, ModelMap model) {
		List<AdoptionApplication> la = adoptionApplicationService.findByPet(id);
		
		model.put("adoptionApplicationsByPet", la);
		
		return "adoptions/adoptionApplicationsByPetList";
	}
	
	@GetMapping(value = "/petsInAdoption/confirmPetAdoption/{petId}/{ownerId}")
	public String confirmPetAdoptionByOwner(@PathVariable("petId") Integer petId, @PathVariable("ownerId") Integer ownerId, ModelMap model) 
			throws DataAccessException, DuplicatedPetNameException {
		
		Pet petToChange = petService.findPetById(petId);
		Owner oldOwner = petToChange.getOwner();
		oldOwner.removePet(petToChange);
		
		petToChange.setInAdoption(false);
		List<AdoptionApplication> la = petToChange.getAdoptionApplications();
		petToChange.setAdoptionApplications(new ArrayList<>());
		adoptionApplicationService.deleteAll(la);
		
		Owner newOwner = ownerService.findOwnerById(ownerId);
		newOwner.addPet(petToChange);
		
		ownerService.saveOwner(oldOwner);
		ownerService.saveOwner(newOwner);
		petService.savePet(petToChange);
		
		return "redirect:/owners/" + oldOwner.getId();
	}
	
	@GetMapping(value = "/petsInAdoption/adoptionRequest/new/{petId}")
	public String initNewAdoptionApplicationForm(@PathVariable("petId") Integer petId, ModelMap model) {
		
		AdoptionApplication adoptionApplication = new AdoptionApplication();		
		adoptionApplication.setPet(petService.findPetById(petId));
		
        Owner owner = findOwnerLoggedIn();
        String url = "welcome";

        if(owner.getId()!=null) {
        	adoptionApplication.setOwner(owner);
            model.put("adoptionApplication", adoptionApplication);
            url = "adoptions/createOrUpdateAdoptionApplicationForm";
        }
        
		return url;
	}
	
	@PostMapping(value = "/petsInAdoption/adoptionRequest/new/{petId}")
	public String processCreationNewAdoptionApplicationForm(@Valid AdoptionApplication adoptionApplication, BindingResult result, @PathVariable("petId") Integer petId, Integer ownerId, ModelMap model) {
				
		if(result.hasErrors()) {
			model.put("ownerId", ownerId);
			return "adoptions/createOrUpdateAdoptionApplicationForm";
		}
		
		else {	
			adoptionApplication.setPet(petService.findPetById(petId));
			adoptionApplication.setOwner(ownerService.findOwnerById(ownerId));
			
			this.adoptionApplicationService.save(adoptionApplication);
			return "redirect:/petsInAdoption";
		}
	}
	
	private Owner findOwnerLoggedIn() {
		Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Owner owner = new Owner();
        
    	User usuario = this.userService.findUser(userDetail.getUsername()).orElse(null);
    	
    	if(usuario!=null) {
            owner = ownerService.findByUser(usuario);
    	}
        return owner;
	}
	
}