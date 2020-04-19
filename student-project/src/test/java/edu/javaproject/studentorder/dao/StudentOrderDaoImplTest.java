package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


public class StudentOrderDaoImplTest {

    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void saveStudentOrder() throws DaoException {
        StudentOrder studentOrder = buildStudentOrder(10);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(studentOrder);
    }

    @Test(expected = DaoException.class)
    public void saveStudentOrderError() throws DaoException {

        StudentOrder studentOrder = buildStudentOrder(10);
        studentOrder.getHusband().setSurName(null);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(studentOrder);

    }

    @Test
    public void getStudentOrders() throws DaoException {
        List<StudentOrder> studentOrders = new StudentOrderDaoImpl().getStudentOrders();

    }

    public StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderId(id);
        studentOrder.setMarriageCertificateId("" + (123456000 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016, 7, 4));
        RegisterOffice registerOffice = new RegisterOffice(1L, "", "");
        studentOrder.setMarriageOffice(registerOffice);
        Street street = new Street(1L, "First street");

        Address address = new Address("195000", street, "12", "", "142");

        // Husband
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 8, 24));
        husband.setPassportSerial("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 15));

        PassportOffice passportOffice = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(passportOffice);
        husband.setStudentId("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, ""));
        husband.setStudentId("HH12345");

        // Wife
        Adult wife = new Adult("Петрова", "Вероника", "Алекссевна", LocalDate.of(1998, 3, 12));
        wife.setPassportSerial("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4, 5));
        PassportOffice passportOffice2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(passportOffice2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L,""));
        wife.setStudentId("WW12345");

        // child 1
        Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11));
        RegisterOffice registerOffice1 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(registerOffice1);
        child1.setAddress(address);

        // child 2
        Child child2 = new Child("Петров", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice registerOffice2 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(registerOffice2);
        child2.setAddress(address);

        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.addChild(child1);
        studentOrder.addChild(child2);

        return studentOrder;
    }
}