package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerCityRegister;
import edu.javaproject.studentorder.domain.StudentOrder;

public class  CityRegisterValidator {

    public String hostName;
    public int port;
    public String login;
    public String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {

        personChecker.checkPerson(studentOrder.getHusband());
        personChecker.checkPerson(studentOrder.getWife());
        personChecker.checkPerson(studentOrder.getChild());
        AnswerCityRegister answerCityRegister = new AnswerCityRegister();

        return answerCityRegister;
    }

}
