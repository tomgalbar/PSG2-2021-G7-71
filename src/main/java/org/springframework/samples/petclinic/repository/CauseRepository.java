package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer>{
	
	void save(Cause cause);
	
	void delete(Cause cause);

	List<Cause> findAll();

}
