package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdoptionApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdoptionApplicationController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class AdoptionApplicationControllerTests {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 7;
	private static final int TEST_ADOPT_ID = 6;
	
	@MockBean
	private AdoptionApplicationService adoptionApplicationService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		User user1 = new User();
		user1.setUsername("owner24");
		user1.setPassword("owner24");
		
		Owner owner1 = new Owner();
		owner1.setId(TEST_OWNER_ID);
		owner1.setAddress("Calle puto");
		owner1.setCity("Mis muertos");
		owner1.setFirstName("Anuel");
		owner1.setLastName("Benito");
		owner1.setTelephone("666666666");
		owner1.setUser(user1);
		
		PetType petType = new PetType();
		petType.setName("HAMSTER");
		
		Pet pet1 = new Pet();
		pet1.setBirthDate(LocalDate.now());
		pet1.setId(TEST_PET_ID);
		pet1.setInAdoption(false);
		pet1.setName("PEPE");
		pet1.setOwner(owner1);
		pet1.setType(petType);

		Optional<User> userDef = Optional.of(owner1.getUser());
		
		given(this.adoptionApplicationService.findByInAdoption()).willReturn(new ArrayList<Pet>());
		given(this.userService.findUser(owner1.getUser().getUsername())).willReturn(userDef);
		given(ownerService.findByUser(owner1.getUser())).willReturn(owner1);
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet1);
		given(adoptionApplicationService.findByPet(TEST_ADOPT_ID)).willReturn(new ArrayList<AdoptionApplication>());
		given(ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(owner1);
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void showAllPetsInAdoption() throws Exception {
		mockMvc.perform(get("/petsInAdoption"))
			.andExpect(status().isOk())
			.andReturn().getRequest();
	}
	
	@WithMockUser(value = "spring")
    @Test
    void setPetInAdoption() throws Exception {
		mockMvc.perform(get("/petsInAdoption/setPetAdoption/{petId}", TEST_PET_ID)
			.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/"+TEST_OWNER_ID));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void adoptionsApplicationsByPet() throws Exception {
		mockMvc.perform(get("/petsInAdoption/adoptionApplications/{petId}", TEST_PET_ID)
			.with(csrf()))
			.andExpect(view().name("adoptions/adoptionApplicationsByPetList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void confirmPetAdoptionByOwner() throws Exception {
		mockMvc.perform(get("/petsInAdoption/confirmPetAdoption/{petId}/{ownerId}", TEST_PET_ID, TEST_OWNER_ID)
			.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+TEST_OWNER_ID));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void initNewAdoptionApplicationForm() throws Exception {
		mockMvc.perform(get("/petsInAdoption/adoptionRequest/new/{petId}", TEST_PET_ID)
			.with(csrf()))
			.andExpect(status().isOk())
			.andReturn().getRequest();
	}
	
	@WithMockUser(value = "spring")
    @Test
    void processCreationNewAdoptionApplicationForm() throws Exception {
		mockMvc.perform(post("/petsInAdoption/adoptionRequest/new/{petId}", 2)
			.with(csrf())
			.param("description", "TESSSSSST")
			.param("ownerId", "1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/petsInAdoption"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void processCreationNewAdoptionApplicationFormFails() throws Exception {
		mockMvc.perform(post("/petsInAdoption/adoptionRequest/new/{petId}", TEST_PET_ID)
			.with(csrf())
			.param("description", "TEST")
			.param("ownerId", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("adoptions/createOrUpdateAdoptionApplicationForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void processCreationNewAdoptionApplicationFormFails2() throws Exception {
		mockMvc.perform(post("/petsInAdoption/adoptionRequest/new/{petId}", TEST_PET_ID)
			.with(csrf())
			.param("description", "")
			.param("ownerId", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("adoptions/createOrUpdateAdoptionApplicationForm"));
	}
	
	
}
