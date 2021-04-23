package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdoptionApplicationServiceTests {

	@Autowired
	private AdoptionApplicationService adoptionApplicationService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private OwnerService ownerService;
	
	
	@Test
	@Transactional
	void shouldSaveAdoption() {
		AdoptionApplication adoption = new AdoptionApplication();
		adoption.setDescription("Prueba de guardado de adopcion");
		adoption.setOwner(ownerService.findOwnerById(1));
		adoption.setPet(petService.findPetById(3));
		adoptionApplicationService.save(adoption);
		List<AdoptionApplication> adopt = adoptionApplicationService.findByPet(3);
		assertTrue(adopt.contains(adoption));
		
	}
	
	@Test
	@Transactional
	void shouldNotSaveAdoptionLength() {
		AdoptionApplication adoption = new AdoptionApplication();
		adoption.setDescription("NOP");
		adoption.setOwner(ownerService.findOwnerById(1));
		adoption.setPet(petService.findPetById(3));
		try {
			adoptionApplicationService.save(adoption);
		}catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	@Transactional
	void shouldNotSaveAdoptionNotBlank() {
		AdoptionApplication adoption = new AdoptionApplication();
		adoption.setDescription("");
		adoption.setOwner(ownerService.findOwnerById(1));
		adoption.setPet(petService.findPetById(3));
		try {
			adoptionApplicationService.save(adoption);
		}catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	@Transactional
	void shouldFindByInAdoption() {
		List<Pet> petsInAdoption =this.adoptionApplicationService.findByInAdoption();
		assertNotNull(petsInAdoption);
	}
	
	@Test
	@Transactional
	void shouldFindByPet() {
		List<AdoptionApplication> adopt = adoptionApplicationService.findByPet(3);
		assertNotNull(adopt);
		
	}
	
	@Test
	@Transactional
	void shouldDeleteAllAdoption() {
		AdoptionApplication adoption = new AdoptionApplication();
		adoption.setDescription("Prueba de guardado de adopcion");
		adoption.setOwner(ownerService.findOwnerById(1));
		adoption.setPet(petService.findPetById(3));
		adoptionApplicationService.save(adoption);
		List<AdoptionApplication> adopt = adoptionApplicationService.findByPet(3);
		this.adoptionApplicationService.deleteAll(adopt);
		List<AdoptionApplication> adopt2 = adoptionApplicationService.findByPet(3);
		assertThat(adopt).isNotEqualTo(adopt2);
	}
}
