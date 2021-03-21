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
	
	// Spring MVC calls method loadPetWithBooking(...) before processNewBookingForm is called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/new")
	public String processNewBookingForm(@Valid Booking booking, BindingResult result) {
		
		//Primero se comprueban si hay errores para @Valid (entidad y sus anotaciones)
		if (result.hasErrors()) {
			return "pets/createOrUpdateBookingForm";
		}
		
		//Comprobacion para la coherencia de las fechas
		if(booking.getStartDate().isAfter(booking.getFinishDate())) {
			result.rejectValue("finishDate", "startDateAfterFinishDate", "La fecha de fin no puede ser anterior a la de inicio");
			return "pets/createOrUpdateBookingForm";
		}
		
		//Comprobacion para ver si hay mas de 1 reserva dentro de un mismo periodo de tiempo
		Boolean duplicated = petService.duplicatedBooking(booking);
		if(duplicated) {
			result.rejectValue("finishDate", "duplicatedBooking", "Ya dispone de una reserva en dicho periodo de tiempo");
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
