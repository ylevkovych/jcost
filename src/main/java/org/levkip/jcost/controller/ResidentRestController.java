package org.levkip.jcost.controller;

import java.util.List;

import org.levkip.jcost.domain.Resident;
import org.levkip.jcost.service.ResidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResidentRestController extends Controller{

	public static final Logger logger = LoggerFactory.getLogger(ResidentRestController.class);
	
	@Autowired
	@Qualifier("ResidentServiceImpl")
	private ResidentService residentService;	
	
	@RequestMapping(
		value = "rest/resident", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Resident> getResidents() {
		return residentService.getResidents();
	}
	
	@RequestMapping(
		value = "rest/resident/id/{residentId}", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Resident getResident(@PathVariable Long residentId) {
		return residentService.getResidentById(residentId);
	}
	
	@RequestMapping(
		value = "rest/resident", method = { RequestMethod.POST }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Resident addResident (@RequestBody Resident resident) {		
		return residentService.saveResident(resident);
	}
	
	@RequestMapping(
		value = "rest/resident", method = { RequestMethod.PUT }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Resident updateResident (@RequestBody Resident resident) {
		return residentService.updateResident(resident);
	}
	
	@RequestMapping(
		value = "rest/resident/id/{residentId}", method = { RequestMethod.DELETE }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public boolean deleteResident (@PathVariable Long residentId) {		
		residentService.removeResident(residentId);
		return true;
	}
	
}
