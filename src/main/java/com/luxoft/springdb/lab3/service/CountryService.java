package com.luxoft.springdb.lab3.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;

import com.luxoft.springdb.lab3.model.Country;

public interface CountryService {

    public List<Country> getAllCountriesInsideTransaction(Propagation propagation);

    public List<Country> getAllCountriesRequired();

    public List<Country> getAllCountriesRequiresNew();

    public List<Country> getAllCountriesSupports();

    public List<Country> getAllCountriesNever();

    public List<Country> getAllCountriesMandatory();

    public List<Country> getAllCountriesNotSupported();

    public List<Country> getAllCountries();
}
