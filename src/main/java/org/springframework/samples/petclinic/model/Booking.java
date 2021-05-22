package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
	
	public Booking() {
		this.startDate = LocalDate.now();
		this.finishDate = LocalDate.now();
	}
	
	@Column(name = "details")
	@NotBlank(message = "Los detalles no pueden estar vacíos")
	private String details;
	
	@Column(name = "start_date")
	@NotNull(message = "La fecha no puede ser nula. Seleccione una fecha por favor")
	@FutureOrPresent(message = "La fecha debe ser futura o presente")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;
	
	@Column(name = "finish_date")
	@NotNull(message = "La fecha no puede ser nula. Seleccione una fecha por favor")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate finishDate;
	
	@ManyToOne //(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}
