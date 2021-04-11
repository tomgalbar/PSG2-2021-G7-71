package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {
	
	@Column(name = "name")
	@NotBlank(message = "El nombre no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre de la causa debe contener entre 3 y 50 caracteres")
	private String name;

	@Column(name = "description")
	@NotBlank(message = "La descripcion no puede ser vacía")
    @Size(min = 5, message = "La descripcion de la causa debe contener al menos 5 caracteres")
	private String description;
	
	@Column(name = "budget_target")
	@NotNull(message = "El objetivo de la causa no puede ser nulo")
	@Positive(message = "El objetivo de la causa debe ser superior a 0")
	private Double budgetTarget;
	
	@Column(name = "organization")
	@NotBlank(message = "El nombre de la organizacion no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre de la organizacion debe contener entre 3 y 50 caracteres")
	private String organization;
	
//	@Column(name = "budget_achieved")
//	@NotNull(message = "El objetivo acumulado de la causa no puede ser nulo")
//	@Positive(message = "El objetivo acumulado de la causa debe ser superior a 0")
//	private Double budgetAchieved;
	
	@Column(name = "is_closed")
	@NotNull(message = "La causa debe estar abierta o cerrada")
	private Boolean isClosed;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
	private List<Donation> donations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

//	public Double getBudgetAchieved() {
//		return budgetAchieved;
//	}
//
//	public void setBudgetAchieved(Double budgetAchieved) {
//		this.budgetAchieved = budgetAchieved;
//	}

	public Double getBudgetAchieved() {
		List<Donation> ld = getDonations();
		Double budgetAchieved = 0.0;
		
		if(!ld.isEmpty() && ld !=null) {
			budgetAchieved = ld.stream().mapToDouble(x->x.getAmount()).sum();
		}
		
		return budgetAchieved;
	}

//	public Boolean getIsClosed() {
//		return isClosed;
//	}
//
//	public void setIsClosed(Boolean isClosed) {
//		this.isClosed = isClosed;
//	}

	public Boolean getIsClosed() {
		Boolean isClosed = false;
		if(getBudgetAchieved()>=getBudgetTarget()) {
			isClosed=true;
		}
		return isClosed;
	}
	
	protected List<Donation> getDonationsInternal() {
		if (this.donations == null) {
			this.donations = new ArrayList<Donation>();
		}
		return this.donations;
	}
	
	protected void setDonationsInternal(List<Donation> donations) {
		this.donations = donations;
	}
	
	public List<Donation> getDonations() {
		List<Donation> sortedDonations = new ArrayList<Donation>(getDonationsInternal());
		PropertyComparator.sort(sortedDonations, new MutableSortDefinition("donationDate", false, false));
		return Collections.unmodifiableList(sortedDonations);
	}
	
	public void addDonation(Donation donation) {
		getDonationsInternal().add(donation);
		donation.setCause(this);
	}
	
	public void deleteDonation(Donation donation) {
		getDonationsInternal().remove(donation);
		donation.setCause(this);
	}
	
}