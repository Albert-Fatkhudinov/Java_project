package edu.javaproject.studentorder.validator.register;

import edu.javaproject.studentorder.domain.Adult;
import edu.javaproject.studentorder.domain.Child;

import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BED_1 = "1001";
    private static final String BED_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERROR_T_1 = "1003";
    private static final String ERROR_T_2 = "2003";

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {

        CityRegisterResponse response = new CityRegisterResponse();
        if (person instanceof Adult) {
            Adult adult = (Adult) person;
            String passportNumber = adult.getPassportSerial();
            if (passportNumber.equals(GOOD_1) || passportNumber.equals(GOOD_2)) {
                response.setExisting(true);
                response.setTemporal(false);
            }

            if (passportNumber.equals(BED_1) || passportNumber.equals(BED_2)) {
                response.setExisting(false);
            }

            if (passportNumber.equals(ERROR_1) || passportNumber.equals(ERROR_2)) {
                throw new CityRegisterException("1", "GRN Error " + passportNumber);
            }

            if (passportNumber.equals(ERROR_T_1) || passportNumber.equals(ERROR_T_2)) {
                throw new TransportException("Transport ERROR " + passportNumber);
            }
        }

        if (person instanceof Child) {
            response.setExisting(true);
            response.setTemporal(true);
        }

        System.out.println(response);
        return response;
    }
}