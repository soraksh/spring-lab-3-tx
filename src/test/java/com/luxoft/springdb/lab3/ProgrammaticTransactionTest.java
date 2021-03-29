package com.luxoft.springdb.lab3;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;

import com.luxoft.springdb.lab3.dao.CountryNotFoundException;
import com.luxoft.springdb.lab3.service.ProgrammaticTransactionCountryService;
import com.luxoft.springdb.lab3.service.TransactionLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class ProgrammaticTransactionTest extends JdbcTest {
	
	@Autowired
    private ProgrammaticTransactionCountryService programmaticTransactionCountryService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        TransactionLog.clear();
    }
    
    @After
    public void tearDown() throws Exception {
        TransactionLog.clear();
    }

    @Test
    @DirtiesContext
    public void testSetRollBackProgrammatically() {
        try {
            programmaticTransactionCountryService.getCountryByName("Burkina Faso");
        } catch (Exception e) {
            assertTrue(e instanceof CountryNotFoundException);
        }
    }

    @Test
    @DirtiesContext
    public void testRequiresNewPropagation() {
        programmaticTransactionCountryService.getCountryByCodeName("RU", Propagation.REQUIRES_NEW);
        assertTrue(TransactionLog.getStringValue().contains("Method inside transaction, propagation = REQUIRES_NEW"));
    }

    @Test
    @DirtiesContext
    public void testRequiredPropagation() {
        programmaticTransactionCountryService.getCountryByCodeName("RU", Propagation.REQUIRED);
        assertTrue(TransactionLog.getStringValue().contains("Method inside transaction, propagation = REQUIRED"));
    }
}