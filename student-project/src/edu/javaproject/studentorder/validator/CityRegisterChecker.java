package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.Person;

public interface CityRegisterChecker {

    public CityRegisterCheckerResponse checkPerson(Person person);
}
