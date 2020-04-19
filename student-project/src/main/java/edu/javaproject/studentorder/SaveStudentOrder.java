package edu.javaproject.studentorder;



import edu.javaproject.studentorder.dao.StudentOrderDao;
import edu.javaproject.studentorder.dao.StudentOrderDaoImpl;
import edu.javaproject.studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;


public class SaveStudentOrder {

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder: ");
        return answer;
    }

    static void printStudentOrder(StudentOrder studentOrder) {
        System.out.println(studentOrder.getStudentOrderId());
    }
}
