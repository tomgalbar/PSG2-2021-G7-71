package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends Repository<Donation, Integer>{
	
	void save(Donation cause);
	
	void delete(Donation cause);

	List<Donation> findAll();
	
}
