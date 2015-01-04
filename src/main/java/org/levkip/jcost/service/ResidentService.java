package org.levkip.jcost.service;

import java.util.List;

import org.levkip.jcost.domain.Resident;

public interface ResidentService {
	List<Resident> getResidents();
	
	Resident getResidentById(Long id);
	
	Resident saveResident(Resident resident);
	
	Resident updateResident(Resident resident);
	
	void removeResident(Long id);
}
