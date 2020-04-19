package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.CountryArea;
import edu.javaproject.studentorder.domain.PassportOffice;
import edu.javaproject.studentorder.domain.RegisterOffice;
import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao{

    private static final String GET_STREET = "SELECT street_code, street_name FROM jc_street \n" +
            "WHERE UPPER(street_name) LIKE UPPER(?)";

    public static final String GET_PASSPORT = "SELECT * " +
            "FROM jc_passport_office WHERE p_office_area_id = ?";

    public static final String GET_REGISTER = "SELECT * " +
            "FROM jc_register_office WHERE r_office_area_id = ?";

    private static final String GET_AREA = "SELECT * " +
            "FROM jc_country_struct WHERE  area_id like ? and area_id <> ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
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

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(GET_PASSPORT)) {

            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassportOffice passportOffice = new PassportOffice(
                        resultSet.getLong("p_office_id"),
                        resultSet.getString("p_office_area_id"),
                        resultSet.getString("p_office_name"));
                result.add(passportOffice);
            }
        } catch (SQLException ex) {
            throw new DaoException();
        }

        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(GET_REGISTER)) {

            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RegisterOffice registerOffice = new RegisterOffice(
                        resultSet.getLong("r_office_id"),
                        resultSet.getString("r_office_area_id"),
                        resultSet.getString("r_office_name"));
                result.add(registerOffice);
            }
        } catch (SQLException ex) {
            throw new DaoException();
        }

        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(GET_AREA)) {

            String param1 = buildParam(areaId);

            statement.setString(1, param1);
            statement.setString(2, areaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CountryArea countryArea = new CountryArea(
                        resultSet.getString("area_id"),
                        resultSet.getString("area_name"));
                result.add(countryArea);
            }
        } catch (SQLException ex) {
            throw new DaoException();
        }

        return result;
    }

    private String buildParam(String areaId) throws SQLException {

        if (areaId == null || areaId.trim().isEmpty()) {
            return "__0000000000";
        }else if (areaId.endsWith("0000000000")) {
            return areaId.substring(0, 2) + "___0000000";
        } else if (areaId.endsWith("0000000")) {
            return areaId.substring(0, 5) + "___0000";
        } else if (areaId.endsWith("0000")) {
            return  areaId.substring(0, 8) + "____";
        } else {
            throw new SQLException("Invalid parametr 'areaID': " + areaId);
        }
    }
}

