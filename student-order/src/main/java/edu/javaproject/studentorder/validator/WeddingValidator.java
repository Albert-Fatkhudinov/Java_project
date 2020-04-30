package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.wedding.AnswerWedding;
import edu.javaproject.studentorder.domain.StudentOrder;

public class WeddingValidator
{
    public AnswerWedding checkWedding(StudentOrder so) {
        System.out.println("Wedding запущен");
        return new AnswerWedding();
    }
}
