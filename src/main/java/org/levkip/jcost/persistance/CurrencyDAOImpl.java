package org.levkip.jcost.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.levkip.jcost.domain.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("CurrencyDAO")
class CurrencyDAOImpl implements CurrencyDAO {
	
	static Logger logger = LoggerFactory.getLogger(CurrencyDAOImpl.class.getName());
	
	private final String DB_NAME = "currency";
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Long getNextId() {
		String sql = "SELECT id FROM "+DB_NAME+" ORDER BY id DESC LIMIT 1";
		
		logger.debug("Getting next id: sql ["+sql+"]");
		
		try {
			Long result = this.jdbcTemplate.queryForObject(sql, Long.class);
			return ++result;
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			return 1L;
		}
	}
	
	@Override
	public List<Currency> getCurrencies() {
		String sql = "SELECT * FROM "+DB_NAME+" ORDER BY name";
		
		logger.debug("sql ["+sql+"]");
		
		try {		
			return jdbcTemplate.query(sql, new ResultSetExtractor< List<Currency>>() {
				@Override
				public List<Currency> extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					List<Currency> currencies = new ArrayList<Currency>();					
					while(rs.next()) {
						currencies.add(getCurrencyFromResultSet(rs));
					}
					return currencies;
				}				
			});
		} catch (DataAccessException e) {
			logger.debug("There are no data.");
			return new ArrayList<Currency>();
		} 
	}

	@Override
	public Currency getCurrencyById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Currency id should not be empty");
		
		String sql = "SELECT * FROM " + DB_NAME + " WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
		
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Currency>() {
				@Override
				public Currency mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return getCurrencyFromResultSet(rs);
				}
			});
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("There is no currency with id "+id, 1);
		}
	}

	@Override
	public Currency saveCurrency(Currency currency) {
		if (currency == null)
			throw new IllegalArgumentException("Currency should not be empty");
		
		Long id = this.getNextId();
		
		String sql = "INSERT INTO " + DB_NAME + " (id, name, short_name) VALUES (?,?,?)";
				
		logger.debug("sql ["+sql+"]; id="+id+"; name="+currency.getName()+"; shortName="+currency.getShortName());
		try {
			jdbcTemplate.update(sql, id, currency.getName(), currency.getShortName());
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Currency was not saved", 1);
		}
		currency.setId(id);
		return currency;			
	}

	@Override
	public Currency updateCurrency(Currency currency) {
		if (currency == null)
			throw new IllegalArgumentException("Currency should not be empty");
		
		String sql = "UPDATE " + DB_NAME + " SET name=?, short_name=? WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
				
		try {
			jdbcTemplate.update(sql, currency.getName(), currency.getShortName(), currency.getId());
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Currency ID: "+currency.getId()+" was not updated", 1);
		}
		return currency;		
	}

	@Override
	public void removeCurrency(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Currency id should not be empty");
		
		String sql = "DELETE FROM " + DB_NAME + " WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
		
		try {
			jdbcTemplate.update(sql, id);
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Currency ID: "+id+" was not deleted", 1);
		}
	}
	
	private Currency getCurrencyFromResultSet(ResultSet rs) throws SQLException {
		Currency currency = new Currency();
		currency.setId(rs.getLong("id"));
		currency.setName(
			rs.getString("name") == null ? "" : rs.getString("name")
		);
		currency.setShortName(
			rs.getString("short_name") == null ? "" : rs.getString("short_name")
		);
		return currency;
	}
	
	public boolean isCurrencyUsed(Currency currency) {
		
		String sql = "SELECT TOP 1 1 FROM currency WHERE short_name=? ";
		
		if (currency.getId() != null) {
			sql += " AND id <> " + currency.getId();
		}
		
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[] {currency.getShortName()}, Boolean.class);
		} catch (DataAccessException dae) { }
		
		return false;
	}
}
