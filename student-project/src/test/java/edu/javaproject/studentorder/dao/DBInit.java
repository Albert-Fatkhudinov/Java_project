package edu.javaproject.studentorder.dao;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBInit {

    public static void startUp() throws Exception {
        URL url1 = DictionaryDaoImplTest.class.getClassLoader()
                .getResource("student_project.sql");

        URL url2 = DictionaryDaoImplTest.class.getClassLoader()
                .getResource("student_data.sql");

        List<String> strings1 = Files.readAllLines(Paths.get(url1.toURI()));
        List<String> strings2 = Files.readAllLines(Paths.get(url2.toURI()));

        String sql1 = strings1.stream().collect(Collectors.joining());
        String sql2 = strings2.stream().collect(Collectors.joining());

        try (Connection connection = ConnectionBuilder.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }
    }
}