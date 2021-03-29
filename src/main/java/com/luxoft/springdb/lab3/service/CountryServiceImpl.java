package com.luxoft.springdb.lab3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import com.luxoft.springdb.lab3.dao.CountryDao;
import com.luxoft.springdb.lab3.model.Country;
import org.springframework.transaction.annotation.Transactional;

//@Repository is more convenient declaration for such a class than general @Service
@Repository
public class CountryServiceImpl implements CountryService {

	
	@Autowired
	private CountryDao countryDao;

	public List<Country> getAllCountriesInsideTransaction(
			Propagation propagation) {
		if (Propagation.REQUIRED.equals(propagation)) {
			return getAllCountriesRequired();
		} else if (Propagation.REQUIRES_NEW.equals(propagation)) {
			return getAllCountriesRequiresNew();
		} else if (Propagation.SUPPORTS.equals(propagation)) {
			return getAllCountriesSupports();
		} else if (Propagation.NEVER.equals(propagation)) {
			return getAllCountriesNever();
		} else if (Propagation.MANDATORY.equals(propagation)) {
			return getAllCountriesMandatory();
		} else if (Propagation.NOT_SUPPORTED.equals(propagation)) {
			return getAllCountriesNotSupported();
		} else {
			return getAllCountries();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Country> getAllCountriesRequired() {
		return countryDao.getCountryList();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Country> getAllCountriesRequiresNew() {
		return countryDao.getCountryList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Country> getAllCountriesSupports() {
		return countryDao.getCountryList();
	}

	@Transactional(propagation = Propagation.NEVER)
	public List<Country> getAllCountriesNever() {
		return countryDao.getCountryList();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public List<Country> getAllCountriesMandatory() {
		return countryDao.getCountryList();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Country> getAllCountriesNotSupported() {
		return countryDao.getCountryList();
	}

	@Transactional()
	public List<Country> getAllCountries() {
		return countryDao.getCountryList();
	}

	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

}