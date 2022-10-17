package org.example;

import org.example.dao.Task;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import static org.example.dao.impl.TaskDaoImpl.*;

public class Main {
    public static void main(String[] args) throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {


        Connection connection = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);

        Statement statement = connection.createStatement();

        var taskinsert = new Task("encryptedpassword");


        //insert(taskinsert);
        List<Task> foundvalues = taskinsert.findAll();
        System.out.println(foundvalues);

        statement.close();
        connection.close();
    }



    public static void insert(Task task) {

        // Si es nulo o ya existe entonces no se crea
        if (task == null)
            return;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);


            String sql = """
                    INSERT INTO task
                    (description)
                    VALUES (?)
                    """;


            statement = connection.prepareStatement(sql);

            statement.setString(1, task.getDescription());
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (Exception e) {
            }
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }

    }
}

