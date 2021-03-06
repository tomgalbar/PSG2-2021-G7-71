package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionApplicationRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionApplicationService {
	
	private AdoptionApplicationRepository adoptionApplicationRepository;
	private PetRepository petRepository;
	
	@Autowired
	public AdoptionApplicationService(AdoptionApplicationRepository adoptionApplicationRepository, PetRepository petRepository) {
		this.adoptionApplicationRepository= adoptionApplicationRepository;
		this.petRepository= petRepository;
	}
	
	@Transactional
	public void save(AdoptionApplication adoptionApplication) throws DataAccessException{
		adoptionApplicationRepository.save(adoptionApplication);
	}
	
	public List<Pet> findByInAdoption() throws DataAccessException{
		return petRepository.findByInAdoption();
	}
	
	public List<AdoptionApplication> findByPet(Integer id) throws DataAccessException{
		return adoptionApplicationRepository.findByPetId(id);
	}
	
	public void deleteAll(List<AdoptionApplication> la) throws DataAccessException{
		adoptionApplicationRepository.deleteAll(la);
	}
	
	

}
