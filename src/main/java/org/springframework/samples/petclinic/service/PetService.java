/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;
	private VisitRepository visitRepository;
	private BookingRepository bookingRepository;
  
	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository, BookingRepository bookingRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.bookingRepository = bookingRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
			Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && otherPet.getId()!=pet.getId())) {            	
            	throw new DuplicatedPetNameException();
            }else
                petRepository.save(pet);                
	}
	
	@Transactional
	public void deletePet(Pet pet) throws DataAccessException {
		this.petRepository.delete(pet);
	}
  
	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
	
	@Transactional
	public void saveBooking(Booking booking) throws DataAccessException {
		bookingRepository.save(booking);
	}
	
	@Transactional(readOnly = true)
	public List<Booking> findBookingsByPetId(int petId){
		return bookingRepository.findByPetId(petId);
	}
	
	//Funcion para comprobar si hay mas de una reserva en un mismo periodo de tiempo
	public Boolean duplicatedBooking(Booking booking) {
		List<Booking> lb = findBookingsByPetId(booking.getPet().getId());
		LocalDate inicioN = booking.getStartDate();
		LocalDate finN = booking.getFinishDate();
		Boolean duplicated = false;
		
		for (int i = 0; i < lb.size(); i++) {
			Booking b = lb.get(i);
			LocalDate inicioA = b.getStartDate();
			LocalDate finA = b.getFinishDate();
			if((inicioN.isEqual(inicioA) || ((inicioN.isAfter(inicioA)) && inicioN.isBefore(finA)))
				|| (finN.isEqual(finA) || (finN.isAfter(inicioA) && finN.isBefore(finA))) || 
				(inicioA.isAfter(inicioN) && inicioA.isBefore(finN)) || (finA.isAfter(inicioN) && finA.isBefore(finN))){
				duplicated = true;
				break;
			}
		}
		return duplicated;
	}

}
