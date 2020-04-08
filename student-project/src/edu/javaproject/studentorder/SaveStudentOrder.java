package edu.javaproject.studentorder;


import edu.javaproject.studentorder.domain.Adult;
import edu.javaproject.studentorder.domain.StudentOrder;



public class SaveStudentOrder {

    public static void main(String[] args) {
        buildStudentOrder(10);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderID(id);

        StudentOrder studentOrder1 = studentOrder;
        printStudentOrder(studentOrder1);

        Adult husband = new Adult("", "", "", null);

        return studentOrder;
    }

    static void printStudentOrder(StudentOrder studentOrder) {
        System.out.println(studentOrder.getStudentOrderID());
    }
}
