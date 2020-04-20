package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StudentOrderDaoImpl  implements StudentOrderDao {

    private static final Logger logger
            = LoggerFactory.getLogger(StudentOrderDaoImpl.class);

    public static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, h_sur_name, " +
                    " h_given_name, h_patronymic, h_date_of_birth, h_passport_serial, " +
                    " h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
                    " h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, " +
                    " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_serial, " +
                    " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, " +
                    " w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
                    " certificate_id, register_office_id, marriage_date)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?);";

    public static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(" +
                    "student_order_id, c_sur_name, c_given_name, " +
                    "c_patronymic, c_date_of_birth, " +
                    "c_certificate_number, c_certificate_date, " +
                    "c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartment)" +
                    "VALUES (?, ?, ?, " +
                    "?, ?, ?, ?, " +
                    "?, ?, ?, ?, " +
                    "?, ?);";

    public static final String SELECT_ORDERS =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "po_h.p_office_area_id as h_p_office_area_id, po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";

    public static final String SELECT_CHILD =
            "SELECT soc.*, ro.r_office_area_id, ro.r_office_name " +
                    "FROM jc_student_child soc " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id " +
                    "WHERE soc.student_order_id IN ";

    public static final String SELECT_ORDERS_FULL =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "po_h.p_office_area_id as h_p_office_area_id, po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name, " +
                    "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
                    "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id " +
                    "WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DaoException {

        Long result = -1L;

        logger.debug("Student order:{}", studentOrder);

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})) {

            con.setAutoCommit(false);

            try {

                //HEADER
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                // Husband
                setParamsForAdult(statement, 3, studentOrder.getHusband());

                // Wife
                setParamsForAdult(statement, 18, studentOrder.getWife());

                // Marriage date
                statement.setString(33, studentOrder.getMarriageCertificateId());
                statement.setLong(34, studentOrder.getMarriageOffice().getOfficeId());
                statement.setDate(35, Date.valueOf(studentOrder.getMarriageDate()));

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                }
                generatedKeys.close();

                saveChildren(con, studentOrder, result);


                con.commit();

            } catch (SQLException exception) {
                con.rollback();
                throw exception;
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }
        return result;
    }

    private void saveChildren(Connection con, StudentOrder studentOrder, Long studentOrderId) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_CHILD)) {

            for (Child child : studentOrder.getChildren()) {
                preparedStatement.setLong(1, studentOrderId);
                setParamsForChild(preparedStatement ,child);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private void setParamsForAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {

        setParamsForPerson(statement, start, adult);
        statement.setString(start + 4, adult.getPassportSerial());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(statement, start + 8, adult);
        statement.setLong(start + 13, adult.getUniversity().getUniversityId());
        statement.setString(start + 14, adult.getStudentId());

    }

    private void setParamsForPerson(PreparedStatement statement, int start, Person person) throws SQLException {
        statement.setString(start, person.getSurName());
        statement.setString(start + 1, person.getGivenName());
        statement.setString(start + 2, person.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private void setParamsForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        Address address = person.getAddress();
        statement.setString(start, address.getPostCode());
        statement.setLong(start + 1, address.getStreet().getStreetCode());
        statement.setString(start + 2, address.getBuilding());
        statement.setString(start + 3, address.getExtension());
        statement.setString(start + 4, address.getApartment());
    }

    private void setParamsForChild(PreparedStatement preparedStatement, Child child) throws SQLException {
        setParamsForPerson(preparedStatement, 2, child);
        preparedStatement.setString(6, child.getCertificateNumber());
        preparedStatement.setDate(7, Date.valueOf(child.getIssueDate()));
        preparedStatement.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(preparedStatement, 9, child);
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        return getStudentOrdersOneSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();


        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SELECT_ORDERS_FULL)) {

            Map<Long, StudentOrder> maps = new HashMap<>();
            statement.setInt(1, StudentOrderStatus.START.ordinal());
            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            int counter = 0;

            while (resultSet.next()) {

                Long studentOrderId = resultSet.getLong("student_order_id");
                if(!maps.containsKey(studentOrderId)) {
                    StudentOrder studentOrder = getFullStudentOrder(resultSet);

                    result.add(studentOrder);
                    maps.put(studentOrderId, studentOrder);
                }
                StudentOrder studentOrder = maps.get(studentOrderId);
                studentOrder.addChild(fillChild(resultSet));
                counter++;
            }

            if (counter >= limit) {
                result.remove(result.size() - 1);
            }

            resultSet.close();

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }

        return result;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SELECT_ORDERS)) {

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StudentOrder studentOrder = getFullStudentOrder(resultSet);

                result.add(studentOrder);
            }
            findChildren(con, result);


            resultSet.close();

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            throw new DaoException(exception);
        }

        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet resultSet) throws SQLException {
        StudentOrder studentOrder = new StudentOrder();

        fillStudentOrder(resultSet, studentOrder);
        fillMarriage(resultSet, studentOrder);

        studentOrder.setHusband(fillAdult(resultSet, "h_"));
        studentOrder.setWife(fillAdult(resultSet, "w_"));
        return studentOrder;
    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setStudentOrderId(resultSet.getLong("student_order_id"));
        studentOrder.setStudentOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));

    }

    private void fillMarriage(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificateId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());

        Long registerOfficeId = resultSet.getLong("register_office_id");
        String areaId = resultSet.getString("r_office_area_id");
        String name = resultSet.getString("r_office_name");
        RegisterOffice registerOffice = new RegisterOffice(registerOfficeId, areaId, name);
        studentOrder.setMarriageOffice(registerOffice);
    }

    private Adult fillAdult(ResultSet resultSet, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(resultSet.getString(pref + "sur_name"));
        adult.setGivenName(resultSet.getString(pref + "given_name"));
        adult.setPatronymic(resultSet.getString(pref + "patronymic"));
        adult.setDateOfBirth(resultSet.getDate(pref + "date_of_birth").toLocalDate());
        adult.setPassportSerial(resultSet.getString(pref+ "passport_serial"));
        adult.setPassportNumber(resultSet.getString(pref+ "passport_number"));
        adult.setIssueDate(resultSet.getDate(pref + "passport_date").toLocalDate());


        Long passportOfficeId = resultSet.getLong(pref + "passport_office_id");
        String passportOfficeArea = resultSet.getString(pref + "p_office_area_id");
        String passportOfficeName = resultSet.getString(pref + "p_office_name");
        PassportOffice passportOffice =
                new PassportOffice(passportOfficeId, passportOfficeArea, passportOfficeName);
        adult.setIssueDepartment(passportOffice);

        Address address = new Address();
        address.setPostCode(resultSet.getString(pref + "post_index"));
        address.setBuilding(resultSet.getString(pref + "building"));
        address.setExtension(resultSet.getString(pref + "extension"));
        address.setApartment(resultSet.getString(pref + "apartment"));
        Street street = new Street(resultSet.getLong(pref + "street_code"), "");
        address.setStreet(street);
        address.setApartment(resultSet.getString(pref + "apartment"));
        adult.setAddress(address);

        University university = new University(resultSet.getLong(pref + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentId(resultSet.getString(pref + "student_number"));

        return adult;
    }

    private void findChildren(Connection con, List<StudentOrder> result) throws SQLException{

        String str = "(" + result.stream().map(studentOrder -> String.valueOf(studentOrder.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors.
                toMap(StudentOrder::getStudentOrderId, studentOrder -> studentOrder));

        try (PreparedStatement statement = con.prepareStatement(SELECT_CHILD + str)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Child child = fillChild(resultSet);
                StudentOrder studentOrder = maps.get(resultSet.getLong("student_order_id"));
                studentOrder.addChild(child);
            }
        }
    }

    private Child fillChild(ResultSet resultSet) throws SQLException {
        String surName = resultSet.getString("c_sur_name");
        String givenName = resultSet.getString("c_given_name");
        String patronymic = resultSet.getString("c_patronymic");
        LocalDate dateOfBirth = resultSet.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surName, givenName, patronymic, dateOfBirth);

        child.setCertificateNumber(resultSet.getString("c_certificate_number"));
        child.setIssueDate(resultSet.getDate("c_certificate_date").toLocalDate());

        Long registerOfficeID = resultSet.getLong("c_register_office_id");
        String registerOfficeArea = resultSet.getString("r_office_area_id");
        String registerOfficeName = resultSet.getString("r_office_name");
        RegisterOffice registerOffice =
                new RegisterOffice(registerOfficeID, registerOfficeArea, registerOfficeName);
        child.setIssueDepartment(registerOffice);

        Address address = new Address();
        Street street = new Street(resultSet.getLong("c_street_code"), "");
        address.setStreet(street);
        address.setPostCode(resultSet.getString("c_post_index"));
        address.setBuilding(resultSet.getString("c_building"));
        address.setExtension(resultSet.getString("c_extension"));
        address.setApartment(resultSet.getString("c_apartment"));
        child.setAddress(address);
        return child;
    }
}
