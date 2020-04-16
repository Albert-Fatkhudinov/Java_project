package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class StudentOrderDaoImpl  implements StudentOrderDao {

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
            "SELECT * FROM jc_student_order WHERE student_order_status = 0 ORDER BY student_order_date";

    //TODO refactoring  - make one method
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
    }

    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DaoException {

        Long result = -1L;

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

        } catch (SQLException ex) {
            throw new DaoException();
        }
        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SELECT_ORDERS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentOrder studentOrder = new StudentOrder();
                fillStudentOrder(resultSet, studentOrder);
                fillMarriage(resultSet, studentOrder);
                Adult husband = fillAdult(resultSet, "h_");
                Adult wife = fillAdult(resultSet, "w_");

                studentOrder.setHusband(husband);
                studentOrder.setWife(wife);

                result.add(studentOrder);
            }

            resultSet.close();

        } catch (SQLException exception) {
            throw new DaoException(exception);
        }

        return result;
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

        PassportOffice passportOffice = new PassportOffice(resultSet.getLong(pref + "passport_office_id"),"", "");
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


    private void fillStudentOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setStudentOrderId(resultSet.getLong("student_order_id"));
        studentOrder.setStudentOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));

    }

    private void fillMarriage(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificateId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());

        Long registerOfficeId = resultSet.getLong("register_office_id");
        RegisterOffice registerOffice = new RegisterOffice(registerOfficeId, "", "");
        studentOrder.setMarriageOffice(registerOffice);
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

}
