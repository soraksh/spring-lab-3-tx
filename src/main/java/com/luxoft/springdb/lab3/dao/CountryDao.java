package com.luxoft.springdb.lab3.dao;

import com.luxoft.springdb.lab3.model.Country;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountryDao extends JdbcDaoSupport {
    private static final String LOAD_COUNTRIES_SQL = "insert into country (name, code_name) values (:name, :codeName)";

    private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = :name";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = :codeName";

    private static final String UPDATE_COUNTRY_NAME_SQL = "update country SET name= :name  where code_name= :codeName";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
            {"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
            {"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
            {"Russian Federation", "RU"}, {"Sweden", "SE"},
            {"Switzerland", "CH"}, {"United Kingdom", "GB"},
            {"United States", "US"}};

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    public List<Country> getCountryList() {
        List<Country> countryList = getJdbcTemplate().query(
                GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);

        return countryList;
    }

    public List<Country> getCountryListStartWith(String name) {
        return getNamedParameterJdbcTemplate().query(GET_COUNTRIES_BY_NAME_SQL,
                Collections.singletonMap("name", name + "%"), COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", newCountryName);
        params.put("codeName", codeName);
        getNamedParameterJdbcTemplate().update(UPDATE_COUNTRY_NAME_SQL, params);
    }

    public void loadCountries() {
        for (String[] countryData : COUNTRY_INIT_DATA) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", countryData[0]);
            params.put("codeName", countryData[1]);
            getNamedParameterJdbcTemplate().update(LOAD_COUNTRIES_SQL, params);
        }
    }

    public Country getCountryByCodeName(String codeName) {
        return getNamedParameterJdbcTemplate().query(
                GET_COUNTRY_BY_CODE_NAME_SQL,
                Collections.singletonMap("codeName", codeName),
                COUNTRY_ROW_MAPPER)
                .get(0);
    }

    public Country getCountryByName(String name)
            throws CountryNotFoundException {
        List<Country> countryList = getNamedParameterJdbcTemplate()
                .query(GET_COUNTRY_BY_NAME_SQL, Collections.singletonMap("name", name), COUNTRY_ROW_MAPPER);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }
        return countryList.get(0);
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        }
        return jdbcTemplate;
    }
}
