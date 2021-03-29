package com.luxoft.springdb.lab3.service;

import org.springframework.transaction.support.TransactionTemplate;

import com.luxoft.springdb.lab3.dao.CountryDao;
import com.luxoft.springdb.lab3.dao.CountryNotFoundException;
import com.luxoft.springdb.lab3.model.Country;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

public class ProgrammaticTransactionCountryServiceImpl implements ProgrammaticTransactionCountryService {
    private TransactionTemplate transactionTemplate;
    private CountryDao dao;

    public ProgrammaticTransactionCountryServiceImpl(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Country getCountryByName(final String name) {
        transactionTemplate.setIsolationLevel(Isolation.REPEATABLE_READ.value());
        return (Country) transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                try {
                    return dao.getCountryByName(name);
                } catch (CountryNotFoundException e) {


                }
                return null;
            }
        });
    }

    public Country getCountryByCodeName(final String codeName, final Propagation propagation) {
        return (Country) transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                return getCountryByCodeNameInTransaction(codeName, propagation);
            }
        });
    }

    private Country getCountryByCodeNameInTransaction(final String codeName, final Propagation propagation) {

        return (Country) transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                TransactionLog.append("Method inside transaction, propagation = " + propagation.toString() + "\n");
                return dao.getCountryByCodeName(codeName);
            }
        });
    }


    public List<Country> getAllCountries() {
        return dao.getCountryList();
    }

    public CountryDao getDao() {
        return dao;
    }

    public void setDao(CountryDao dao) {
        this.dao = dao;
    }
}
