package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookingController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class BookingControllerTests {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 1;
	private static final int TEST_BOOKING_ID = 1;

//	@Autowired
//	private BookingController bookingController;
	
	@MockBean
	private PetService petService;
	@MockBean
	private BookingService bookingService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
		given(this.bookingService.findBookingById(TEST_BOOKING_ID)).willReturn(new Booking());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitNewBookingForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/bookings/new", TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateBookingForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewBookingFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/bookings/new", TEST_PET_ID)
				.param("startDate", "2021/05/05")
				.param("finishDate", "2021/05/10")
				.with(csrf())
				.param("details", "Booking details"))                                
            .andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessNewBookingFormHasErrors() throws Exception {
	mockMvc.perform(post("/owners/*/pets/{petId}/bookings/new", TEST_PET_ID)
						.with(csrf())
						.param("name", "George"))
			.andExpect(model().attributeHasErrors("booking")).andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdateBookingForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowBookings() throws Exception {
	mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/bookings",TEST_OWNER_ID, TEST_PET_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("bookings")).andExpect(view().name("bookingList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteBookingFromPet() throws Exception {
    	mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/bookings/{bookingId}/delete",
    			TEST_OWNER_ID, TEST_PET_ID, TEST_BOOKING_ID))
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/owners/{ownerId}"));
    }
	
}
