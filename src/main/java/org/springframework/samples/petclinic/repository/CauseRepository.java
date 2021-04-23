package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer>{
	
	void save(Cause cause) throws DataAccessException;
	
	List<Cause> findAll() throws DataAccessException;
	
	Cause findById(Integer id) throws DataAccessException;

}
