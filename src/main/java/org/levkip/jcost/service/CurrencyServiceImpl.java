package org.levkip.jcost.service;

import java.util.List;

import org.levkip.jcost.controller.CurrencyRestController;
import org.levkip.jcost.domain.Currency;
import org.levkip.jcost.persistance.CurrencyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CurrencyServiceImpl")
public class CurrencyServiceImpl implements CurrencyService {
	
	public static final Logger logger = LoggerFactory.getLogger(CurrencyRestController.class);
	
	private CurrencyDAO currencyDao;
	@Autowired
	@Qualifier("CurrencyDAO")
	public void setCurrencyDao(CurrencyDAO currencyDao) {
		this.currencyDao = currencyDao;
	}
	
	@Override
	public List<Currency> getCurrencies() {
		return currencyDao.getCurrencies();
	}	

	@Override
	public Currency getCurrencyById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Currency id should not be empty");
				
		return currencyDao.getCurrencyById(id);
	}

	@Override
	public Currency saveCurrency(Currency currency) {
		validateCurrency(currency);				
		return currencyDao.saveCurrency(currency);
	}

	@Override
	public Currency updateCurrency(Currency currency) {
		validateCurrency(currency);
		
		if (currency.getId() == null)
			throw new IllegalArgumentException("Currency id should not be empty");
		
		return currencyDao.updateCurrency(currency);		
	}

	@Override
	public void removeCurrency(Long id) {		
		if (id == null)
			throw new IllegalArgumentException("Currency id should not be empty");
		
		currencyDao.removeCurrency(id);
	}

	private void validateCurrency(Currency currency) {
		if (currency == null) 
			throw new IllegalArgumentException("Invalid currency");
		if (currency.getShortName() == null || "".equals(currency.getShortName().trim()))
			throw new IllegalArgumentException("Short name should not be empty");
		if (currencyDao.isCurrencyUsed(currency))
			throw new IllegalArgumentException("Curency \""+currency.getShortName()+"\" already used");
	}
}
