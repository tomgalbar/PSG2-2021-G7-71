package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BookingServiceTests {
	
	@Autowired
	protected BookingService bookingService;
	
	@Test
	void shouldFindBookingsByPetId() {
		List<Booking> bookings = this.bookingService.findBookingsByPetId(1);
		assertThat(bookings.size()).isEqualTo(1);

		bookings = this.bookingService.findBookingsByPetId(7);
		assertThat(bookings.isEmpty()).isTrue();
	}

}
