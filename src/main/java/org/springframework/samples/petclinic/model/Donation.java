package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {
	
	public Donation() {
		this.donationDate = LocalDate.now();
	}
	
	@Column(name = "client")
	@NotBlank(message = "El nombre del cliente no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre del cliente debe contener entre 3 y 50 caracteres")
	private String client;

	@Column(name = "amount")
	@NotNull(message = "La cantidad de la donacion no puede ser nulo")
	@Positive(message = "La cantidad de la donacion debe ser superior a 0")
	private Double amount;
	
	@Column(name = "donation_date")
	@NotNull(message = "La fecha de la donacion no puede ser nula")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate donationDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "cause_id")
	@NotNull(message = "La causa de una donacion no puede ser nula")
	private Cause cause;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(LocalDate donationDate) {
		this.donationDate = donationDate;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}
	
}