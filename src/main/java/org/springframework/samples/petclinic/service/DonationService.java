package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {
	
private DonationRepository donationRepository;
	
	@Autowired
	public DonationService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}
	
	@Transactional
	public void save(Donation donation) throws DataAccessException{
		donationRepository.save(donation);
	}
	
	@Transactional(readOnly = true)
	public List<Donation> findAll() throws DataAccessException{
		return this.donationRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Donation findById(Integer id) throws DataAccessException{
		return this.donationRepository.findById(id);
	}

}
