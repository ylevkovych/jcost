package org.levkip.jcost.persistance;

import java.util.List;

import org.levkip.jcost.domain.Currency;

public interface CurrencyDAO extends EntityDAO{
	
	List<Currency> getCurrencies();
	
	Currency getCurrencyById(Long id);
	
	Currency saveCurrency(Currency currency);
	
	Currency updateCurrency(Currency currency);
	
	void removeCurrency(Long id) throws Exception;
}
