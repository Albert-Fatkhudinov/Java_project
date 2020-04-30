package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.StudentOrder;
import edu.javaproject.studentorder.exception.DaoException;

import java.util.List;

public interface StudentOrderDao
{
    Long saveStudentOrder(StudentOrder so) throws DaoException;

    List<StudentOrder> getStudentOrders() throws DaoException;
}
