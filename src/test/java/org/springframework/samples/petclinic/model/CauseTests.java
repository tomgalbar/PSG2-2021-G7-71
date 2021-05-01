package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CauseTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameIsBlankOrSizeMysMatch() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cause cause = new Cause();
		cause.setName("");
		cause.setBudgetTarget(200.);
		cause.setDescription("Test de prueba");
		cause.setOrganization("Test1234");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(cause);

		assertThat(constraintViolations.size()).isEqualTo(2);
		
		ConstraintViolation<Cause> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("name");
	}
	
	@Test
	void shouldNotValidateWhenDescriptionIsBlankOrSizeMysMatch() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cause cause = new Cause();
		cause.setName("Test");
		cause.setBudgetTarget(200.);
		cause.setDescription("");
		cause.setOrganization("Test1234");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(cause);
		
		assertThat(constraintViolations.size()).isEqualTo(2);
		
		ConstraintViolation<Cause> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("description");
	}
	
	@Test
	void shouldNotValidateWhenBudgetTargetIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cause cause = new Cause();
		cause.setName("Test");
		cause.setBudgetTarget(null);
		cause.setDescription("Test de prueba");
		cause.setOrganization("Test1234");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(cause);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		
		ConstraintViolation<Cause> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("budgetTarget");
	}
	
	@Test
	void shouldNotValidateWhenOrganizationIsBlankOrSizeMysMatch() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cause cause = new Cause();
		cause.setName("Test");
		cause.setBudgetTarget(200.);
		cause.setDescription("Test de prueba");
		cause.setOrganization("");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(cause);
		
		assertThat(constraintViolations.size()).isEqualTo(2);
		
		ConstraintViolation<Cause> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("organization");
	}
	
}
