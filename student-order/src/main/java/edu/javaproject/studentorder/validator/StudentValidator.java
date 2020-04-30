package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.student.AnswerStudent;
import edu.javaproject.studentorder.domain.StudentOrder;

public class StudentValidator
{
    public AnswerStudent checkStudent(StudentOrder so) {
        System.out.println("Студенты проверяются");
        return new AnswerStudent();
    }
}
