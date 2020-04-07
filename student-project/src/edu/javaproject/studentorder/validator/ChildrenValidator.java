package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.AnswerChildren;
import edu.javaproject.studentorder.domain.StudentOrder;

public class ChildrenValidator {

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("Children check is running");
        return new AnswerChildren();
    }

}
