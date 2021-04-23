package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class DonationServiceTests {

	@Autowired
	protected DonationService donationService;
	
	@Autowired
	CauseService causeService;
	
	@Test
	@Transactional
	void shouldInsertDonation() {
		Collection<Donation> donations = this.donationService.findAll();
		int found = donations.size();
		
		Cause cause = new Cause();
		cause.setName("Test12");
		cause.setBudgetTarget(200.);
		cause.setDescription("Test de prueba");
		cause.setOrganization("Testeo");
		
		Donation donation = new Donation();
		donation.setClient("Test1");
		donation.setAmount(10.);
		donation.setCause(cause);
		donation.setDonationDate(LocalDate.now());
		
		this.causeService.save(cause);
		
		this.donationService.save(donation);
		
		donations = this.donationService.findAll();
	
		assertThat(donations.size()).isEqualTo(found + 1);
	}

}
