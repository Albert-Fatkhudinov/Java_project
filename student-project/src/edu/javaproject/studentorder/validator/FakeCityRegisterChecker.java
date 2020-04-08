package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.Person;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    public CityRegisterCheckerResponse checkPerson(Person person) {
        return null;
    }
}
