package edu.javaproject.studentorder;

import edu.javaproject.studentorder.dao.StudentOrderDaoImpl;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.domain.children.AnswerChildren;
import edu.javaproject.studentorder.domain.register.AnswerCityRegister;
import edu.javaproject.studentorder.domain.student.AnswerStudent;
import edu.javaproject.studentorder.domain.wedding.AnswerWedding;
import edu.javaproject.studentorder.exception.DaoException;
import edu.javaproject.studentorder.mail.MailSender;
import edu.javaproject.studentorder.validator.ChildrenValidator;
import edu.javaproject.studentorder.validator.CityRegisterValidator;
import edu.javaproject.studentorder.validator.StudentValidator;
import edu.javaproject.studentorder.validator.WeddingValidator;

import java.util.List;


public class StudentOrderValidator {

    private final CityRegisterValidator cityRegisterVal;
    private final WeddingValidator weddingVal;
    private final ChildrenValidator childrenVal;
    private final StudentValidator studentVal;
    private final MailSender mailSender;

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
        try {
            List<StudentOrder> studentOrders = readStudentOrders();
            for (StudentOrder studentOrder : studentOrders) {
                checkOneOrder(studentOrder);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<StudentOrder> readStudentOrders() throws DaoException {
        return new StudentOrderDaoImpl().getStudentOrders();
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
