package edu.javaproject.studentorder.validator.register;


import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.exception.TransportException;

public class RealCityRegisterChecker implements CityRegisterChecker {

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {
        return null;
    }
}
