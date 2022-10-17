package org.example.dao;

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

public class Task implements TaskDAO {
    Connection connection = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);

    Statement statement = connection.createStatement();
    private int id;

    private String description;

    public Task() throws SQLException {
    }

    public Task(int id, String description) throws SQLException {
        this.id = id;
        this.description = description;
    }
    public Task( String description) throws SQLException {

        this.description = description;
    }

    public int getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\''+
                '}';
    }

    @Override
    public List<Task> findAll() throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String sql = "SELECT * FROM task";
        ResultSet resultSet = statement.executeQuery(sql);
        var tasks = new ArrayList<Task>();

        while(resultSet.next()){
            var id = resultSet.getInt("id");
            var description = parsearPassword(resultSet.getString("description"));


            var direction = new Task(id,description);
            tasks.add(direction);
        }
        resultSet.close();
        return tasks;


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

    }

    @Override
    public void delete(Long id) {

    }
    public static String parsearPassword(String realPass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        // 1. Generador de claves
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);

        // 2. Crear clave privada
        SecretKey key = keyGen.generateKey();

        // 3. Crear Cipher (encriptador/cifrador)
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // 4. Configuración Cipher con Vector de inicialización (IV) / nonce
        byte[] iv = new byte[cipher.getBlockSize()];
        SecureRandom random = SecureRandom.getInstanceStrong();
        random.nextBytes(iv);

        int bits = cipher.getBlockSize() * 8;
        GCMParameterSpec gcmParam = new GCMParameterSpec(bits, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParam); // MODO CIFRADO

        // 5. Realizar cifrado
        byte[] cipherBytes = cipher.doFinal(realPass.getBytes());
        String cipherText = HexFormat.of().formatHex(cipherBytes);




        return cipherText;
    }

}