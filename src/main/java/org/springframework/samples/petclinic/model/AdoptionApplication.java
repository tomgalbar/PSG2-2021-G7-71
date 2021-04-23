package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class AdoptionApplication extends BaseEntity{
	
	@NotBlank
	@Length(min = 5, max = 100)
	private String description;
	
	@ManyToOne()
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne()
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
}
