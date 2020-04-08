package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.exception.CityRegisterException;

public interface CityRegisterChecker {

    public CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException;
}
