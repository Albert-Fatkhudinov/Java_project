package edu.javaproject.studentorder;


import edu.javaproject.studentorder.other.Adult;
import edu.javaproject.studentorder.domain.StudentOrder;



public class SaveStudentOrder {

    public static void main(String[] args) {
        StudentOrder studentOrder = new StudentOrder();
        buildStudentOrder();
        long ans = saveStudentOrder(studentOrder);
        System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        Adult husband = new Adult();


//        husband.setGivenName("Альберт");
////        husband.setSurName("Фатхудинов");
////        husband.setPassportNumber("какие то числа");
////        studentOrder.setHusband(husband);
////
////        String ans = husband.getPersonString();
////        System.out.println(ans);

        return studentOrder;
    }
}
