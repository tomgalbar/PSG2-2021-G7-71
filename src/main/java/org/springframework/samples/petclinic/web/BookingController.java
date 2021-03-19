package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
	
	private final BookingService bookingService;
	private final PetService petService;
	
	@Autowired
	public BookingController(BookingService bookingService, PetService petService) {
		this.bookingService = bookingService;
		this.petService = petService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("booking")
	public Booking loadPetWithBooking(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		Booking booking = new Booking();
		pet.addBooking(booking);
		return booking;
	}
	
	// Spring MVC calls method loadPetWithBooking(...) before initNewBookingForm is called
	@GetMapping(value = "/owners/*/pets/{petId}/bookings/new")
	public String initNewBookingForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateBookingForm";
	}
	
	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/new")
	public String processNewBookingForm(@Valid Booking booking, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateBookingForm";
		}
		else {
			this.petService.saveBooking(booking);
			return "redirect:/owners/{ownerId}";
		}
	}
	
	@GetMapping(value = "/owners/*/pets/{petId}/bookings")
	public String showBookings(@PathVariable int petId, Map<String, Object> model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return "bookingList";
	}
	
}
