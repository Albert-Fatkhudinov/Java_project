package edu.javaproject.studentorder;



import edu.javaproject.studentorder.dao.StudentOrderDao;
import edu.javaproject.studentorder.dao.StudentOrderDaoImpl;
import edu.javaproject.studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;


public class SaveStudentOrder {

    public static void main(String[] args) throws Exception {
//        List<Street> d = new DictionaryDaoImpl().findStreets("про");
//        for (Street s : d) {
//            System.out.println(s.getStreetName());
//        }
//
//        List<PassportOffice> passportOffices
//                = new DictionaryDaoImpl().findPassportOffices("010020000000");
//
//        for (PassportOffice passportOffice: passportOffices) {
//            System.out.println(passportOffice.getOfficeName());
//        }
//
//        List<RegisterOffice> registerOffices
//                = new DictionaryDaoImpl().findRegisterOffices("010010000000");
//
//        for (RegisterOffice registerOffice: registerOffices) {
//            System.out.println(registerOffice.getOfficeName());
//        }
//
//        List<CountryArea> countryAreas
//                = new DictionaryDaoImpl().findAreas("");
//
//        for (CountryArea countryArea: countryAreas) {
//            System.out.println(countryArea.getAreaId() + ": " + countryArea.getAreaName());
//        }
//
//        List<CountryArea> countryAreas1
//                = new DictionaryDaoImpl().findAreas("020000000000");
//
//        for (CountryArea countryArea: countryAreas1) {
//            System.out.println(countryArea.getAreaId() + ": " + countryArea.getAreaName());
//        }
//
//        List<CountryArea> countryAreas2
//                = new DictionaryDaoImpl().findAreas("020010000000");
//
//        for (CountryArea countryArea: countryAreas2) {
//            System.out.println(countryArea.getAreaId() + ": " + countryArea.getAreaName());
//        }
//
//        List<CountryArea> countryAreas3
//                = new DictionaryDaoImpl().findAreas("020010010000");
//
//        for (CountryArea countryArea: countryAreas3) {
//            System.out.println(countryArea.getAreaId() + ": " + countryArea.getAreaName());
//        }
          StudentOrder studentOrder = buildStudentOrder(10);
          StudentOrderDao dao = new StudentOrderDaoImpl();
          Long id = dao.saveStudentOrder(studentOrder);
          System.out.println(id);

          List<StudentOrder> soList = dao.getStudentOrders();
          for (StudentOrder studentOrder1 : soList) {
              System.out.println(studentOrder1.getStudentOrderId());
          }
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
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

    static void printStudentOrder(StudentOrder studentOrder) {
        System.out.println(studentOrder.getStudentOrderId());
    }
}
