package org.levkip.jcost.persistance;

import java.util.List;

import org.levkip.jcost.domain.Resident;

public interface ResidentDAO extends EntityDAO {

	List<Resident> getResidents();
	
	Resident getResidentById(Long id);
	
	Resident saveResident(Resident resident);
	
	Resident updateResident(Resident resident);
	
	void removeResident(Long id);
	
	boolean isResidentUsed(Resident resident);
}
