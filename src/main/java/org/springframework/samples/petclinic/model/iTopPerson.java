package org.springframework.samples.petclinic.model;

public class iTopPerson {
	
	public iTopPerson() {
	}
	
	public iTopPerson(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	private String name;
	
	private String email;
	
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "iTopPerson [name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}
	
}
