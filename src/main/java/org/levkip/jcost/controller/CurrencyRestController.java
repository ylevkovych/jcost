package org.levkip.jcost.controller;

import java.util.List;

import org.levkip.jcost.domain.Currency;
import org.levkip.jcost.persistance.CurrencyDAO;
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
import org.slf4j.Logger;

@RestController
public class CurrencyRestController extends Controller {
	
	public static final Logger logger = LoggerFactory.getLogger(CurrencyRestController.class);
	
	@Autowired
	@Qualifier("CurrencyDAO")
	private CurrencyDAO currencyDao;	
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Currency> getCurrencies() {
		return currencyDao.getCurrencies();
	}
	
	@RequestMapping(
		value = "/rest/currency/id/{currencyId}", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency getCurrency(@PathVariable Long currencyId) {
		//TODO: Should be implemented
		return null;
	}
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.POST }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency addCurrency (@RequestBody Currency currency) {		
		return currencyDao.saveCurrency(currency);
	}
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.PUT }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency updateCurrency (@RequestBody Currency currency) {
		//TODO: Should be implemented
		return null;
	}
	
	@RequestMapping(
		value = "/rest/currency/{currencyId}", method = { RequestMethod.DELETE }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public boolean deleteCurrency (@PathVariable Integer currencyId) {
		//TODO: Should be implemented
		return true;
	}
}
