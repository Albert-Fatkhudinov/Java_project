package edu.javaproject.studentorder.validator;

import edu.javaproject.studentorder.domain.children.AnswerChildren;
import edu.javaproject.studentorder.domain.StudentOrder;

public class ChildrenValidator
{
    public AnswerChildren checkChildren(StudentOrder so) {
        System.out.println("Children Check is running");
        return new AnswerChildren();
    }
}
