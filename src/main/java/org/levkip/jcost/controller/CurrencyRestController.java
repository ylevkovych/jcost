package org.levkip.jcost.controller;

import java.util.List;

import org.levkip.jcost.domain.Currency;
import org.levkip.jcost.persistance.CurrencyDAO;
import org.levkip.jcost.service.CurrencyService;
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
	@Qualifier("CurrencyServiceImpl")
	private CurrencyService currencyService;	
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Currency> getCurrencies() {
		return currencyService.getCurrencies();
	}
	
	@RequestMapping(
		value = "/rest/currency/id/{currencyId}", method = { RequestMethod.GET }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency getCurrency(@PathVariable Long currencyId) {
		return currencyService.getCurrencyById(currencyId);
	}
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.POST }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency addCurrency (@RequestBody Currency currency) {		
		return currencyService.saveCurrency(currency);
	}
	
	@RequestMapping(
		value = "/rest/currency", method = { RequestMethod.PUT }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Currency updateCurrency (@RequestBody Currency currency) {
		return currencyService.updateCurrency(currency);
	}
	
	@RequestMapping(
		value = "/rest/currency/id/{currencyId}", method = { RequestMethod.DELETE }
	)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public boolean deleteCurrency (@PathVariable Long currencyId) {		
		currencyService.removeCurrency(currencyId);
		return true;
	}
}
