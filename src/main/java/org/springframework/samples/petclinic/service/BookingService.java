package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
	
	private BookingRepository bookingRepository;

	@Autowired
	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Booking> findBookingsByPetId(Integer id){
		return bookingRepository.findByPetId(id);
	}

}
