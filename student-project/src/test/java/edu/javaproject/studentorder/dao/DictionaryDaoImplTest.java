package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.CountryArea;
import edu.javaproject.studentorder.domain.PassportOffice;
import edu.javaproject.studentorder.domain.RegisterOffice;
import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest {

    @BeforeClass
    public static void startUp() throws Exception {
        URL url1 = DictionaryDaoImplTest.class.getClassLoader()
                .getResource("student_project.sql");

        URL url2 = DictionaryDaoImplTest.class.getClassLoader()
                .getResource("student_data.sql");

        List<String> strings1 = Files.readAllLines(Paths.get(url1.toURI()));
        List<String> strings2 = Files.readAllLines(Paths.get(url2.toURI()));

        String sql1 = strings1.stream().collect(Collectors.joining());
        String sql2 = strings2.stream().collect(Collectors.joining());

        try (Connection connection = ConnectionBuilder.getConnection();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }

    }

    @Test
    public void testStreet() throws DaoException {
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