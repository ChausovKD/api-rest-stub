package com.example.apireststub.dao;

import com.example.apireststub.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

@Component
public final class DataBaseWorker {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseWorker.class);

    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    static {
        loadProperties();
    }

    private DataBaseWorker() {
    }

    public User selectUserByLogin(String login) {
        Connection connection = null;
        Statement statement = null;
        String sql = """
                    SELECT ua.login, password, date, email
                    FROM users_auth ua
                    JOIN users_contacts uc ON ua.login = uc.login
                    WHERE ua.login = '%s'
                    """.formatted(login);
        try {
            connection = open();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getObject("date", LocalDateTime.class),
                        resultSet.getString("email")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user: " + e.getMessage(), e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.warn("Failed to close connection", e);
            }
        }
    }

    public int createUser(User user) {
        String sqlAuth = """
                INSERT INTO users_auth (login, password, date)
                VALUES (?, ?, ?)
                """;
        String sqlContacts = """
                INSERT INTO users_contacts (login, email)
                VALUES (?, ?)
                """;
        try (Connection connection = open();
             PreparedStatement psAuth = connection.prepareStatement(sqlAuth);
             PreparedStatement psContacts = connection.prepareStatement(sqlContacts)) {
            int totalAffectedRows = 0;
            psAuth.setString(1, user.getLogin());
            psAuth.setString(2, user.getPassword());
            psAuth.setObject(3, user.getDateTime());
            totalAffectedRows += psAuth.executeUpdate();
            psContacts.setString(1, user.getLogin());
            psContacts.setString(2, user.getEmail());
            totalAffectedRows += psContacts.executeUpdate();
            return totalAffectedRows;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    private static void loadProperties() {
        DB_URL = System.getenv("DB_URL");
        DB_USERNAME = System.getenv("DB_USERNAME");
        DB_PASSWORD = System.getenv("DB_PASSWORD");
        if (DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null) {
            Properties props = new Properties();
            try (InputStream input = DataBaseWorker.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input != null) {
                    props.load(input);
                    DB_URL = props.getProperty("db.url");
                    DB_USERNAME = props.getProperty("db.username");
                    DB_PASSWORD = props.getProperty("db.password");
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot load db.properties", e);
            }
        }

        if (DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null) {
            throw new RuntimeException("Database configuration is missing (check DB_URL, DB_USERNAME, DB_PASSWORD or db.properties)");
        }
    }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
