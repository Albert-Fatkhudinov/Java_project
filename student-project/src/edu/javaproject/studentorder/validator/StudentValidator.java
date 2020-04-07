package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerStudent;
import edu.javaproject.studentorder.domain.StudentOrder;

public class StudentValidator {

    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        System.out.println("Студенты проверяются");
        return new AnswerStudent();
    }
}
