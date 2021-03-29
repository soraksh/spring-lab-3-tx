package com.luxoft.springdb.lab3.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;

import com.luxoft.springdb.lab3.dao.CountryDao;
import com.luxoft.springdb.lab3.dao.CountryNotFoundException;
import com.luxoft.springdb.lab3.model.Country;

public interface ProgrammaticTransactionCountryService {

    public Country getCountryByName(String name) throws CountryNotFoundException;

    public List<Country> getAllCountries();

    public Country getCountryByCodeName(String codeName, Propagation propagation);

    public void setDao(CountryDao dao);

    public CountryDao getDao();

}
