package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User{
	@Id
	@NotBlank(message = "El nombre de usuario no puede ser vacío")
	String username;
	
	@NotBlank(message = "La contraseña no puede ser vacía")
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Authorities> getAuthorities() {
		return authorities;
	}
	

	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean value) {
		this.enabled=value;
	}
}
