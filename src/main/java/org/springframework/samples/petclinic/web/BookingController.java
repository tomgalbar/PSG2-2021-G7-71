package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
	
	private final PetService petService;
	
	private final BookingService bookingService;
	
	@Autowired
	public BookingController(PetService petService,
			BookingService bookingService) {
		this.petService = petService;
		this.bookingService = bookingService;
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
	
	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/bookings")
	public String showBookings(@PathVariable int ownerId,
			@PathVariable int petId, Map<String, Object> model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return "bookingList";
	}
	
	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/{bookingId}/delete")
	public String deleteBooking(@PathVariable int petId,@PathVariable int ownerId,
			@PathVariable int bookingId, ModelMap model) throws DataAccessException, DuplicatedPetNameException {
		Booking booking = this.bookingService.findBookingById(bookingId);
		Pet pet = this.petService.findPetById(petId);
		pet.deleteBooking((Booking)model.getAttribute("booking"));
		pet.deleteBooking(booking);
		this.petService.savePet(pet);
		this.bookingService.deleteBooking(booking);
		return "redirect:/owners/{ownerId}";
	}
	
}
