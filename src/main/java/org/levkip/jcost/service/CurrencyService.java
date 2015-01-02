package org.levkip.jcost.service;

import java.util.List;

import org.levkip.jcost.domain.Currency;

public interface CurrencyService {
	
	List<Currency> getCurrencies();
	
	Currency getCurrencyById(Long id);
	
	Currency saveCurrency(Currency currency);
	
	Currency updateCurrency(Currency currency);
	
	void removeCurrency(Long id);

}
