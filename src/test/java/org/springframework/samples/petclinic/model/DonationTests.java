package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class DonationTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenClientIsBlankOrSizeMysMatch() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Donation donation = new Donation();
		donation.setClient("");
		donation.setAmount(10.);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(donation);

		assertThat(constraintViolations.size()).isEqualTo(2);
		
		ConstraintViolation<Donation> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("client");
	}
	
	@Test
	void shouldNotValidateWhenAmountNegative() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Donation donation = new Donation();
		donation.setClient("Enrique");
		donation.setAmount(-10.);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(donation);

		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Donation> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("amount");
	}
	
	@Test
	void shouldNotValidateWhenAmountNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Donation donation = new Donation();
		donation.setClient("Enrique");
		donation.setAmount(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(donation);

		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Donation> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("amount");
	}
	
	@Test
	void shouldNotValidateWhenDateNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Donation donation = new Donation();
		donation.setClient("Enrique");
		donation.setAmount(10.);
		donation.setDonationDate(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(donation);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Donation> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("donationDate");
	}
	
	@Test
	void shouldNotValidateWhenCauseNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Donation donation = new Donation();
		donation.setClient("Enrique");
		donation.setAmount(10.);
		donation.setCause(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(donation);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Donation> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("cause");
	}

}
