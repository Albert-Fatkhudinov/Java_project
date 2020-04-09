package edu.javaproject.studentorder;

import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.domain.children.AnswerChildren;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.student.AnswerStudent;
import edu.javaproject.studentorder.domain.wedding.AnswerWedding;
import edu.javaproject.studentorder.mail.MailSender;
import edu.javaproject.studentorder.validator.ChildrenValidator;
import edu.javaproject.studentorder.validator.CityRegisterValidator;
import edu.javaproject.studentorder.validator.StudentValidator;
import edu.javaproject.studentorder.validator.WeddingValidator;

import java.util.LinkedList;
import java.util.List;


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
        List<StudentOrder> studentOrders = readStudentOrders();
        for (StudentOrder studentOrder : studentOrders) {
            checkOneOrder(studentOrder);
        }
    }

    public List<StudentOrder> readStudentOrders() {
        List<StudentOrder> studentOrders = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            StudentOrder studentOrder = SaveStudentOrder.buildStudentOrder(i);
            studentOrders.add(studentOrder);
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

        //AnswerWedding weddingAnswer = checkWedding(studentOrder);
        //AnswerChildren childrenAnswer = checkChildren(studentOrder);
        //AnswerStudent studentAnswer = checkStudent(studentOrder);
        //sendMail(studentOrder);
    }
}
