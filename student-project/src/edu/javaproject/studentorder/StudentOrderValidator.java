package edu.javaproject.studentorder;

import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.mail.MailSender;
import edu.javaproject.studentorder.validator.ChildrenValidator;
import edu.javaproject.studentorder.validator.CityRegisterValidator;
import edu.javaproject.studentorder.validator.StudentValidator;
import edu.javaproject.studentorder.validator.WeddingValidator;


public class StudentOrderValidator {

    private CityRegisterValidator cityRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator() {
        cityRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();
    }

    public void checkAll() {
        StudentOrder[] studentOrders = readStudentOrders();
        for (StudentOrder studentOrder : studentOrders) {
            System.out.println();
            checkOneOrder(studentOrder);
        }
    }

    public StudentOrder[] readStudentOrders() {
        StudentOrder[] studentOrders = new StudentOrder[3];
        for (int i = 0; i < studentOrders.length; i++) {
            studentOrders[i] = SaveStudentOrder.buildStudentOrder(i);
        }
        return studentOrders;
    }


    AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterVal.checkCityRegister(studentOrder);
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        return weddingVal.checkWedding(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenVal.checkChildren(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        return studentVal.checkStudent(studentOrder);
    }

    public void sendMail(StudentOrder studentOrder) {
        mailSender.sendMail(studentOrder);
    }

    public void checkOneOrder(StudentOrder studentOrder) {

        AnswerCityRegister cityAnswer = checkCityRegister(studentOrder);

        AnswerWedding weddingAnswer = checkWedding(studentOrder);
        AnswerChildren childrenAnswer = checkChildren(studentOrder);
        AnswerStudent studentAnswer = checkStudent(studentOrder);

        sendMail(studentOrder);
    }
}
