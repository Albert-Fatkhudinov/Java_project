package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.domain.Street;
import edu.javaproject.studentorder.exception.DaoException;

import java.util.List;

public interface DictionaryDao {

    List<Street> findStreets(String pattern) throws DaoException;
}
