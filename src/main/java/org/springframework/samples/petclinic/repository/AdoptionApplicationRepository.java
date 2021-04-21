package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.AdoptionApplication;

public interface AdoptionApplicationRepository extends CrudRepository<AdoptionApplication, Integer>{
	
	List<AdoptionApplication> findByPetId(Integer id);	
}
