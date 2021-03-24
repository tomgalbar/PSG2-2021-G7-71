package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyConstructor {
	
	private List<String> specialties;
	
	public SpecialtyConstructor() {
		this.specialties = new ArrayList<String>();
	}
	
	public SpecialtyConstructor(List<String> specialties){
		this.specialties=specialties;
	}

	public List<String> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<String> specialties) {
		this.specialties = specialties;
	}
	
}
