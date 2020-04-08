package edu.javaproject.studentorder;


import edu.javaproject.studentorder.other.Adult;
import edu.javaproject.studentorder.domain.StudentOrder;



public class SaveStudentOrder {

    public static void main(String[] args) {

    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderID(id);

        return studentOrder;
    }
}
