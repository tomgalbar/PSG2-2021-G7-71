package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BookingServiceTests {
	
	@Autowired
	protected BookingService bookingService;
	
	@Autowired PetService petService;
	
	@Test
	void shouldFindBookingsByPetId() {
		List<Booking> bookings = this.bookingService.findBookingsByPetId(1);
		assertThat(bookings.size()).isEqualTo(1);

		bookings = this.bookingService.findBookingsByPetId(7);
		assertThat(bookings.isEmpty()).isTrue();
	}

	@Test
	@Transactional
	void shouldDeleteBooking() {
		Booking booking = this.bookingService.findBookingById(2);
		Pet pet = this.petService.findPetById(2);
		pet.deleteBooking(booking);
		this.bookingService.deleteBooking(booking);
		Booking bookingDeleted = this.bookingService.findBookingById(2);
		assertThat(bookingDeleted).isEqualTo(null);
	}
	

@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingIdentico() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBookingIdentico = new Booking();
		newBookingIdentico.setStartDate(LocalDate.of(2021, 3, 9));
		newBookingIdentico.setFinishDate(LocalDate.of(2021, 3, 16));
		newBookingIdentico.setDetails("Hotel PetClinic habitacion doble");
		newBookingIdentico.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBookingIdentico);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBookingIdentico);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioAntesFinIgual() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 2, 7));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 16));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioAntesFinAntes() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 2, 7));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 10));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioAntesFinDespues() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 2, 7));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 18));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioDespuesFinIgual() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 3, 10));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 16));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioDespuesFinAntes() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 3, 10));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 14));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldNotSaveBookingInicioDespuesFinDespues() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 3, 10));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 27));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(true);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(false);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldSaveBookingInicioAntesFinIgualAAntiguoInicio() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 2, 7));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 9));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(false);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(true);
		}
		
	}
	
	@Test//Inicio:'2021-03-09', Fin:'2021-03-16' (en la base de datos)
	@Transactional
	void shouldSaveBookingInicioIgualAAntiguoFinYFinDespues() {
		Pet pet = this.petService.findPetById(3);
		
		Booking newBooking = new Booking();
		newBooking.setStartDate(LocalDate.of(2021, 3, 16));
		newBooking.setFinishDate(LocalDate.of(2021, 3, 23));
		newBooking.setDetails("Hotel PetClinic habitacion doble");
		newBooking.setPet(pet);
		Boolean duplicado = this.petService.duplicatedBooking(newBooking);
		
		if(duplicado) {
			assertTrue(false);
		}else {
			this.petService.saveBooking(newBooking);
			assertTrue(true);
		}
		
	}


}
