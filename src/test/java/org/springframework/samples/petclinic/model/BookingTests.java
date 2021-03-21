package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class BookingTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenDetailsBlank() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Booking booking = new Booking();
		booking.setDetails("");
		booking.setStartDate(LocalDate.of(2021, 01, 01));
		booking.setFinishDate(LocalDate.of(2021, 01, 04));
		booking.setPet(new Pet());

		Validator validator = createValidator();
		Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(booking);

		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Booking> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("details");
	}
	
	@Test
	void shouldNotValidateWhenStartDateNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Booking booking = new Booking();
		booking.setDetails("TEST TEST");
		booking.setStartDate(null);
		booking.setFinishDate(LocalDate.of(2021, 01, 04));
		booking.setPet(new Pet());

		Validator validator = createValidator();
		Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(booking);

		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Booking> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("startDate");
	}
	
	@Test
	void shouldNotValidateWhenFinishDateNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Booking booking = new Booking();
		booking.setDetails("TEST TEST");
		booking.setFinishDate(LocalDate.of(2021, 01, 04));
		booking.setFinishDate(null);
		booking.setPet(new Pet());

		Validator validator = createValidator();
		Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(booking);

		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Booking> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("finishDate");
	}
	
}
