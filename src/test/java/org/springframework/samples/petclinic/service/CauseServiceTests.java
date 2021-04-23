package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CauseServiceTests {
	
	@Autowired
	protected CauseService causeService;
	
	@Autowired DonationService donationService;
	
	@Test
	void shouldFindCauseById() {
		Cause cause = this.causeService.findById(1);
		assertThat(cause).isNotNull();
	}
	@Test
	@Transactional
	void shouldInsertCause() {
		Collection<Cause> causes = this.causeService.findAll();
		int found = causes.size();
		
		Cause cause = new Cause();
		cause.setName("Test12");
		cause.setBudgetTarget(200.);
		cause.setDescription("Test de prueba");
		cause.setOrganization("Testeo");
		
		this.causeService.save(cause);
		
		
		causes = this.causeService.findAll();
	
		assertThat(causes.size()).isEqualTo(found + 1);
	}
}
