package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl {

    private static final String GET_STREET = "SELECT street_code, street_name FROM jc_street \n" +
            "WHERE UPPER(street_name) LIKE UPPER(?)";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jc_student",
                "postgres", "123456");
    }

    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(GET_STREET)) {

            statement.setString(1, "%" + pattern + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Street street = new Street(resultSet.getLong("street_code"), resultSet.getString("street_name"));
                result.add(street);
            }
        } catch (SQLException ex) {
            throw new DaoException();
        }

        return result;
    }
}
