package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CauseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class CauseControllerTests {
	
	private static final int TEST_CAUSE_ID = 1;
	private static final int TEST_DONATION_ID = 1;
	
//	@Autowired
//	private CauseController causeController;
	
	@MockBean
	private DonationService donationService;
	
	@MockBean
	private CauseService causeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Cause causaTest;
	
	@BeforeEach
	void setup() {
		causaTest = new Cause();
		causaTest.setId(TEST_CAUSE_ID);
		causaTest.setName("TEST");
		causaTest.setDescription("ESTO ES UN TEST");
		causaTest.setBudgetTarget(300.99);
		causaTest.setOrganization("TESTORG");
		
		
		given(this.causeService.findById(TEST_CAUSE_ID)).willReturn(causaTest);
		given(this.donationService.findById(TEST_DONATION_ID)).willReturn(new Donation());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitNewCauseForm() throws Exception {
		mockMvc.perform(get("/causes/new")).andExpect(status().isOk())
				.andExpect(view().name("causes/createOrUpdateCauseForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewCauseFormSuccess() throws Exception {
		mockMvc.perform(post("/causes/new")
				.param("name", "perros graciosos")
				.param("description", "ayudar a los perros solos de Gothan")
				.param("budgetTarget", "200")
				.param("organization", "Perros de Gothan")
				.with(csrf()))                                
            .andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/causes"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowCauses() throws Exception{
		mockMvc.perform(get("/causes")).andExpect(status().isOk())
		.andExpect(view().name("causes/causeList"));
	}
   
	@WithMockUser(value = "spring")
    @Test
    void testShowCause() throws Exception {
    	mockMvc.perform(get("/causes/{causeId}", TEST_CAUSE_ID)).andExpect(status().isOk())
			.andExpect(model().attribute("cause", hasProperty("name", is("TEST"))))
			.andExpect(model().attribute("cause", hasProperty("description", is("ESTO ES UN TEST"))))
			.andExpect(model().attribute("cause", hasProperty("budgetTarget", is(300.99))))
			.andExpect(model().attribute("cause", hasProperty("organization", is("TESTORG"))))
			.andExpect(model().attribute("cause", hasProperty("budgetAchieved", is(0.0))))
			.andExpect(model().attribute("cause", hasProperty("isClosed", is(false))))
			.andExpect(view().name("causes/causeDetails"));
    }
}
