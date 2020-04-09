package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.register.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.validator.register.CityRegisterChecker;
import edu.javaproject.studentorder.validator.register.FakeCityRegisterChecker;

public class  CityRegisterValidator {

    private String hostName;
    private int port;
    private String login;
    private String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        try {
            CityRegisterCheckerResponse hans = personChecker.checkPerson(studentOrder.getHusband());
            CityRegisterCheckerResponse wans = personChecker.checkPerson(studentOrder.getWife());
            CityRegisterCheckerResponse child = personChecker.checkPerson(studentOrder.getChild());
        } catch (CityRegisterException exception) {
            exception.printStackTrace(System.out);
        }

        AnswerCityRegister answerCityRegister = new AnswerCityRegister();

        return answerCityRegister;
    }

}
