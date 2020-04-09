package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.Child;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.register.AnswerCityRegisterItem;

import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.validator.register.CityRegisterChecker;
import edu.javaproject.studentorder.validator.register.FakeCityRegisterChecker;

import java.util.List;

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
        AnswerCityRegister answerCityRegister = new AnswerCityRegister();

        answerCityRegister.addItem(checkPerson(studentOrder.getHusband()));
        answerCityRegister.addItem(checkPerson(studentOrder.getWife()));


        for (Child child : studentOrder.getChildren()) {
            answerCityRegister.addItem(checkPerson(child));
        }

        return answerCityRegister;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {

        try {
            CityRegisterResponse cans = personChecker.checkPerson(person);
        } catch (CityRegisterException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
