package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VisitServiceTests {

	@Autowired
	protected VisitService visitService;
	
	@Autowired
	protected PetService pettService;
	
	@Test
	@Transactional
	void shouldDeleteVisit() {
		Pet pet = this.pettService.findPetById(7);
		Visit visit = this.visitService.findVisitById(4);
		pet.deleteVisit(visit);
		this.visitService.deleteVisit(visit);
		Visit visitDeleted = this.visitService.findVisitById(4);
		assertThat(visitDeleted).isNull();
	}
	
}
