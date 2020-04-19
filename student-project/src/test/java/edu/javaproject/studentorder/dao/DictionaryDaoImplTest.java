package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.CountryArea;
import edu.javaproject.studentorder.domain.PassportOffice;
import edu.javaproject.studentorder.domain.RegisterOffice;
import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest {

    private static final Logger logger
            = LoggerFactory.getLogger(DictionaryDao.class);

    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void testStreet() throws DaoException {
        LocalDateTime dateTime = LocalDateTime.now();
        logger.info("TEST: {}", dateTime);
        List<Street> streets = new DictionaryDaoImpl().findStreets("про");
        Assert.assertEquals(2, streets.size());
    }

    @Test
    public void testPassportOffice() throws DaoException {
        List<PassportOffice> passportOffices
                = new DictionaryDaoImpl().findPassportOffices("010020000000");
        Assert.assertEquals(2, passportOffices.size());
    }

    @Test
    public void testRegisterOffice() throws DaoException {
        List<RegisterOffice> registerOffices
                = new DictionaryDaoImpl().findRegisterOffices("010010000000");
        Assert.assertEquals(2, registerOffices.size());
    }

    @Test
    public void testArea() throws DaoException {
        List<CountryArea> countryAreas = new DictionaryDaoImpl().findAreas("");
        Assert.assertEquals(2, countryAreas.size());

        List<CountryArea> countryAreas1 = new DictionaryDaoImpl().findAreas("020000000000");
        Assert.assertEquals(2, countryAreas1.size());

        List<CountryArea> countryAreas2 = new DictionaryDaoImpl().findAreas("020010000000");
        Assert.assertEquals(2, countryAreas2.size());

        List<CountryArea> countryAreas3 = new DictionaryDaoImpl().findAreas("020010010000");
        Assert.assertEquals(2, countryAreas3.size());
    }
}