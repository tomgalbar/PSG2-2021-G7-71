package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {
	
	private CauseRepository causeRepository;
	
	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Cause> findAll() throws DataAccessException{
		return causeRepository.findAll();
	}
	
	@Transactional
	public void save(Cause cause) throws DataAccessException{
		causeRepository.save(cause);
	}
	
	@Transactional(readOnly = true)
	public Cause findById(Integer id) throws DataAccessException{
		return causeRepository.findById(id);
	}
}
