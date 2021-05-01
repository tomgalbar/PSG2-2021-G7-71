package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@WebMvcTest(controllers = DonationController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class DonationControllerTests {
	
	private static final int TEST_CAUSE_ID = 1;
	private static final int TEST_DONATION_ID = 1;
	
//	@Autowired
//	private DonationController donationController;
	
	@MockBean
	private DonationService donationService;
	
	@MockBean
	private CauseService causeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		given(this.causeService.findById(TEST_CAUSE_ID)).willReturn(new Cause());
		given(this.donationService.findById(TEST_DONATION_ID)).willReturn(new Donation());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitNewDonationForm() throws Exception {
		mockMvc.perform(get("/causes/{causeId}/donations/new", TEST_CAUSE_ID)).andExpect(status().isOk())
				.andExpect(view().name("donations/createOrUpdateDonationForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewDonationFormSuccess() throws Exception {
		mockMvc.perform(post("/causes/{causeId}/donations/new", TEST_CAUSE_ID)
				.param("client", "Enrique Gonzalez")
				.param("amount", "1000.")
				.with(csrf()))                                
            .andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/causes"));
	}
	
}
