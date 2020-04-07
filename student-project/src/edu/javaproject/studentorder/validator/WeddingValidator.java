package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerWedding;
import edu.javaproject.studentorder.domain.StudentOrder;

public class WeddingValidator {

    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        System.out.println("Wedding запущен");
        return new AnswerWedding();
    }
}
