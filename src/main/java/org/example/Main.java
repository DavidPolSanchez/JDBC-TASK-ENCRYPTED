package org.example;

import org.example.dao.Task;

import java.sql.*;
import java.util.ArrayList;

import static org.example.dao.impl.TaskDaoImpl.*;

public class Main {
    public static void main(String[] args) throws SQLException {


        Connection connection = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);

        Statement statement = connection.createStatement();

            var taskinsert = new  Task("encryptedpassword") ;


        //insert(taskinsert);
        selectAll(statement);



        statement.close();
        connection.close();
    }

    public static void selectAll(Statement statement) throws SQLException {
        String sql = "SELECT * FROM task";
        ResultSet resultSet = statement.executeQuery(sql);
        var tasks = new ArrayList<Task>();

        while(resultSet.next()){
            var id = resultSet.getInt("id");
            var description = resultSet.getString("description");


            var direction = new Task(id,description);
            tasks.add(direction);
        }
        System.out.println(tasks);

        resultSet.close();

    }
    public static void insert(Task task){

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
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

    }
}