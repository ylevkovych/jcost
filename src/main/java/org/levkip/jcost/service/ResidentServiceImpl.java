package org.levkip.jcost.service;

import java.util.List;

import org.levkip.jcost.domain.Resident;
import org.levkip.jcost.persistance.ResidentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ResidentServiceImpl")
public class ResidentServiceImpl implements ResidentService {

	public static final Logger logger = LoggerFactory.getLogger(ResidentServiceImpl.class);
	
	private ResidentDAO residentDao;
	@Autowired
	@Qualifier("ResidentDAO")
	public void setCurrencyDao(ResidentDAO residentDao) {
		this.residentDao = residentDao;
	}
	
	@Override
	public List<Resident> getResidents() {
		return residentDao.getResidents();
	}

	@Override
	public Resident getResidentById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Resident id should not be empty");
		
		return residentDao.getResidentById(id);
	}

	@Override
	public Resident saveResident(Resident resident) {
		validateResident(resident);
		return residentDao.saveResident(resident);
	}

	@Override
	public Resident updateResident(Resident resident) {
		validateResident(resident);
		return residentDao.updateResident(resident);
	}

	@Override
	public void removeResident(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Resident id should not be empty");
		residentDao.removeResident(id);
	}
	
	private void validateResident(Resident resident) {
		if (resident == null) 
			throw new IllegalArgumentException("Invalid resident");
		if (resident.getName() == null || "".equals(resident.getName().trim()))
			throw new IllegalArgumentException("Name should not be empty");
		if (residentDao.isResidentUsed(resident))
			throw new IllegalArgumentException("Resident \""+resident.getName()+"\" already used");
	}

}
