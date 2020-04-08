package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerCityRegister;
import edu.javaproject.studentorder.domain.CityRegisterCheckerResponse;
import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.exception.CityRegisterException;

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
            exception.printStackTrace();
        }

        AnswerCityRegister answerCityRegister = new AnswerCityRegister();

        return answerCityRegister;
    }

}
