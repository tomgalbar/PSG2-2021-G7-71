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
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Vet;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer>{

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	Collection<Vet> findAll() throws DataAccessException;
	
	Vet findById(int vetId) throws DataAccessException;
	
	void delete(Vet vet) throws DataAccessException;

	@Modifying
	@Query(value = "DELETE FROM VET_SPECIALTIES WHERE VET_ID = ?1", nativeQuery = true)
	void deleteVetSpecialityUnion(@Param("vetId") Integer vetId) throws DataAccessException;

	void save(Vet vet) throws DataAccessException;

	@Query("SELECT s FROM Specialty s")
	Collection<Specialty> findAllSpecialties() throws DataAccessException;
	
	@Query("SELECT s FROM Specialty s WHERE s.name LIKE :name")
	Optional<Specialty> findSpecialtyByName(@Param("name") String name) throws DataAccessException;

}
