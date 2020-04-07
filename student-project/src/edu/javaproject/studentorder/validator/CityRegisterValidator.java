package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerCityRegister;
import edu.javaproject.studentorder.domain.StudentOrder;

public class CityRegisterValidator {

    public String hostName;
    public int port;
    public String login;
    public String password;

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        System.out.println("CityRegister is running: " + hostName +
                ", " + login + ", " + password);
        AnswerCityRegister answerCityRegister = new AnswerCityRegister();
        answerCityRegister.success = false;
        return answerCityRegister;
    }

}
