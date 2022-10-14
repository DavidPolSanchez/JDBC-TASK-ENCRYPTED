package org.example.dao.impl;

import org.example.dao.Task;
import org.example.dao.TaskDAO;

import java.sql.*;
import java.util.List;

public class TaskDaoImpl implements TaskDAO {


     public static final String URL_DATABASE = "jdbc:mysql://127.0.0.1:3306/javajdbc";
     public static final String USER = "root";
     public static final String PASSWORD = "admin";

    // 1. Conexión a mysql

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public Task findById(Long id) {
        return null;
    }

    @Override
    public List<Task> findByUserId(Long id) {
        return null;
    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void update(Task task) {
        // Si es nulo o no tiene id entonces no se puede actualizar
        if (task == null )
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

            // Update direction

            // Update user
            String sql = """
                    UPDATE tasks
                    SET title = ?, description = ?, id_user = ?
                    WHERE id = ? """;


            statement = connection.prepareStatement(sql);
            statement.setString(2, task.getDescription());
            statement.setLong(4, task.getId()); // Pasar el id para filtrar y no actualizar todos
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

    }



    @Override
    public void delete(Long id) {

    }
}
