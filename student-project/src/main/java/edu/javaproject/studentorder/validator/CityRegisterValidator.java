package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.Child;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.register.AnswerCityRegisterItem;
import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.exception.CityRegisterException;
import edu.javaproject.studentorder.validator.register.CityRegisterChecker;
import edu.javaproject.studentorder.validator.register.RealCityRegisterChecker;


public class  CityRegisterValidator {

    public static final String IN_CODE = "NO_GRN";

    private final CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new RealCityRegisterChecker();
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

        AnswerCityRegisterItem.CityStatus status;
        AnswerCityRegisterItem.CityError error = null;

        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isRegistered() ?
                    AnswerCityRegisterItem.CityStatus.YES :
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }

        return new AnswerCityRegisterItem(status, person, error);
    }

}
