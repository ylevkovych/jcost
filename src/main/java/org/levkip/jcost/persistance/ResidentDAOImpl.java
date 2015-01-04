package org.levkip.jcost.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.levkip.jcost.domain.Resident;
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

@Repository("ResidentDAO")
public class ResidentDAOImpl implements ResidentDAO {

	static Logger logger = LoggerFactory.getLogger(ResidentDAOImpl.class.getName());
	
	private final String DB_NAME = "resident";
	
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
	public List<Resident> getResidents() {
		String sql = "SELECT * FROM "+DB_NAME+" ORDER BY name";
		
		logger.debug("sql ["+sql+"]");
		
		try {		
			return jdbcTemplate.query(sql, new ResultSetExtractor< List<Resident>>() {
				@Override
				public List<Resident> extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					List<Resident> residents = new ArrayList<Resident>();					
					while(rs.next()) {
						residents.add(getResidentFromResultSet(rs));
					}
					return residents;
				}				
			});
		} catch (DataAccessException e) {
			logger.debug("There are no data.");
			return new ArrayList<Resident>();
		} 
	}

	@Override
	public Resident getResidentById(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Resident id should not be empty");
		
		String sql = "SELECT * FROM " + DB_NAME + " WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
		
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Resident>() {
				@Override
				public Resident mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return getResidentFromResultSet(rs);
				}
			});
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("There is no resident with id "+id, 1);
		}
	}

	@Override
	public Resident saveResident(Resident resident) {
		if (resident == null)
			throw new IllegalArgumentException("Resident should not be empty");
		
		Long id = this.getNextId();
		
		String sql = "INSERT INTO " + DB_NAME + " (id, name, comment) VALUES (?,?,?)";
				
		logger.debug("sql ["+sql+"]; id="+id+"; name="+resident.getName()+"; comment="+resident.getComment());
		try {
			jdbcTemplate.update(sql, id, resident.getName(), resident.getComment());
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Currency was not saved", 1);
		}
		resident.setId(id);
		return resident;	
	}

	@Override
	public Resident updateResident(Resident resident) {
		if (resident == null)
			throw new IllegalArgumentException("Resident should not be empty");
		
		String sql = "UPDATE " + DB_NAME + " SET name=?, comment=? WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
				
		try {
			jdbcTemplate.update(sql, resident.getName(), resident.getComment(), resident.getId());
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Resident ID: "+resident.getId()+" was not updated", 1);
		}
		return resident;	
	}

	@Override
	public void removeResident(Long id) {
		if (id == null)
			throw new IllegalArgumentException("Resident id should not be empty");
		
		String sql = "DELETE FROM "+ DB_NAME + " WHERE id=?";
		
		logger.debug("sql ["+sql+"]");
		
		try {
			jdbcTemplate.update(sql, id);
		} catch (DataAccessException dae) {
			throw new EmptyResultDataAccessException("Resident ID: "+id+" was not deleted", 1);
		}
	}

	@Override
	public boolean isResidentUsed(Resident resident) {
		String sql = "SELECT TOP 1 1 FROM " + DB_NAME + " WHERE name=? ";
		
		if (resident.getId() != null) {
			sql += " AND id <> " + resident.getId();
		}
		
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[] {resident.getName()}, Boolean.class);
		} catch (DataAccessException dae) { }
		
		return false;
	}
	
	private Resident getResidentFromResultSet(ResultSet rs) throws SQLException {
		Resident resident = new Resident();
		resident.setId(rs.getLong("id"));
		resident.setName(
			rs.getString("name") == null ? "" : rs.getString("name")
		);
		resident.setComment(
			rs.getString("comment") == null ? "" : rs.getString("comment")
		);
		return resident;
	}

}
