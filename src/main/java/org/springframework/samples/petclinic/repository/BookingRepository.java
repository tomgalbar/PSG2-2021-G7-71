package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Booking;

public interface BookingRepository extends Repository<Booking, Integer> {

	List<Booking> findByPetId(Integer id);
	
	Booking findById(Integer id);
	
	void delete(Booking booking);
	
	void save(Booking booking);
}
