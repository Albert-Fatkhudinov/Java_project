package edu.javaproject.studentorder.validator.register;

import edu.javaproject.studentorder.domain.register.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.exception.CityRegisterException;

public class RealCityRegisterChecker implements CityRegisterChecker {

    public CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException {
        return null;
    }
}
