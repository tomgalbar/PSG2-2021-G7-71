package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public List<Booking> findBookingsByPetId(Integer id) throws DataAccessException{
		return bookingRepository.findByPetId(id);
	}
	
	@Transactional(readOnly = true)
	public Booking findBookingById(Integer id) throws DataAccessException{
		return bookingRepository.findById(id);
	}
	
	@Transactional
	public void deleteBooking(Booking booking) throws DataAccessException{
		this.bookingRepository.delete(booking);
	}

}
