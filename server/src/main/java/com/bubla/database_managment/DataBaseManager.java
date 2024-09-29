package com.bubla.database_managment;

import com.bubla.classes.Coordinates;
import com.bubla.classes.Person;
import com.bubla.classes.Product;
import com.bubla.classes.UnitOfMeasure;
import com.bubla.exceptions.NoSuchUserException;
import com.bubla.exceptions.SuchUserExist;
import com.bubla.exceptions.WrongPasswordException;
import com.google.common.hash.Hashing;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class DataBaseManager {
    private static Connection connection;
    private static Session session;
    private static String pepper;

    static {
        try {
            pepper = (new Scanner(new FileInputStream("Pepper"))).nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSch jsch = new JSch();

        String host = "se.ifmo.ru";
        String user = "s409321";
        String privateKey = "~/.ssh/id_rsa";
        int port = 2222;

        String jdbcURL = "jdbc:postgresql://localhost:5432/studs";
        String databaseHost = "pg";
        String databaseUser = "s409321";
        String databasePassword = "9GNTHDXZGB2tkubK";

        int localPort = 5432;

        try {
            session = jsch.getSession(user, host, port);
            session.setPassword("xhkr&8371");
//            session.setConfig("PreferredAuthentications", "publickey");
//            jsch.setKnownHosts("~/.ssh/known_hosts");
//            jsch.addIdentity(privateKey);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setConfig("LogLevel", "DEBUG");
            session.connect();

            session.setPortForwardingL(localPort, databaseHost, 5432);

            System.setProperty("jdbc.url", jdbcURL);
            System.setProperty("jdbc.user", databaseUser);

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(jdbcURL, databaseUser, databasePassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(){
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
    private static String generatePassword(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            return new String(md.digest((password + salt + pepper).getBytes(StandardCharsets.UTF_8)), Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException e){
            System.out.println("OOOOPS");
            return "OOps";
        }
    }

    public static int addUser(String name, String passwword) throws SQLException {
        try{
            checkUser(name, passwword);
            throw new SuchUserExist();
        }catch (WrongPasswordException e){
            throw new SuchUserExist();
        }catch (NoSuchUserException e) {
            String query =
                    "Insert into users (name, password_digest, salt) values (?, ?, ?)";
            String salt = getRandomString();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, generatePassword(passwword, salt));
            preparedStatement.setString(3, salt);
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    resultSet.close();
                    return id;
                }
                resultSet.close();
            }
            preparedStatement.close();
            return -1;
        }
    }

    public static int checkUser(String name, String password) throws SQLException {
        String query =
                "Select id, password_digest, salt from users where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String salt = resultSet.getString("salt");
            String expectedPassword = resultSet.getString("password_digest");
            String actualPassword = generatePassword(password, salt);
            if(!expectedPassword.equals(actualPassword)){
                resultSet.close();
                throw new WrongPasswordException();
            }else{
                int id = resultSet.getInt("id");
                resultSet.close();
                return id;
            }
        }
        else {
            resultSet.close();
            throw new NoSuchUserException();
        }
    }

    public static void addProduct(Product product, String key) throws SQLException {
        String query =
                "Insert into products  (name, x, y, creation_date, price, unit_of_measure, owner_id, creator_id, key) values (?, ?, ?, ?, ?, ?::unit_of_measure, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, product.getName());
        preparedStatement.setFloat(2, product.getCoordinates().getX());
        preparedStatement.setInt(3, product.getCoordinates().getY());
        preparedStatement.setDate(4, new Date(product.getCreationDate().getTime()));
        preparedStatement.setLong(5, product.getPrice());
        preparedStatement.setString(6, product.getUnitOfMeasure().name());
        if(product.getOwner() != null){
        preparedStatement.setInt(7, getOwnerId(product));
        }
        else{
            preparedStatement.setNull(7, Types.INTEGER);
        }
        preparedStatement.setInt(8, product.getCreatorID());
        preparedStatement.setString(9, key);

        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected > 0){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                product.setId(resultSet.getLong(1));
            }
            resultSet.close();
        }

        preparedStatement.close();
    }

    private static int getOwnerId(Product product) throws SQLException {
        String getOwnerQuery = "select id from owners where name = ? and birthday = ? and weight = ?";
        int owner_id = 0;
        PreparedStatement getOwnerPreparedStatement = connection.prepareStatement(getOwnerQuery);
        getOwnerPreparedStatement.setString(1, product.getOwner().getName());
        getOwnerPreparedStatement.setTimestamp(2, Timestamp.valueOf(product.getOwner().getBirthday()));
        getOwnerPreparedStatement.setLong(3, product.getOwner().getWeight());
        ResultSet resultSet = getOwnerPreparedStatement.executeQuery();
        if(resultSet.next()){
            owner_id = resultSet.getInt("id");
            resultSet.close();
            getOwnerPreparedStatement.close();
        }
        else{
            resultSet.close();
            getOwnerPreparedStatement.close();
            String addOwnerQuery =
                    "Insert into owners (name, birthday, weight, creator_id) values (?, ?, ?, ?)";
            PreparedStatement addOwnerPreparedStatement = connection.prepareStatement(addOwnerQuery, Statement.RETURN_GENERATED_KEYS);
            addOwnerPreparedStatement.setString(1, product.getOwner().getName());
            if(product.getOwner().getBirthday() != null) {
                addOwnerPreparedStatement.setTimestamp(2, Timestamp.valueOf(product.getOwner().getBirthday()));
            }else {
                addOwnerPreparedStatement.setNull(2, Types.TIMESTAMP);
            }
            addOwnerPreparedStatement.setLong(3, product.getOwner().getWeight());
            addOwnerPreparedStatement.setInt(4, product.getCreatorID());
            int rowsAffected = addOwnerPreparedStatement.executeUpdate();
            ResultSet generatedKeys = addOwnerPreparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                owner_id = generatedKeys.getInt(1);
                resultSet.close();
                addOwnerPreparedStatement.close();
            }
        }
        return owner_id;
    }

    public static void updateProduct(Product product, String key) throws SQLException {
        String query =
                "UPDATE products SET name = ?, x = ?, y = ?, creation_date = ?, price = ?, unit_of_measure = ?, owner_id = ?, creator_id = ?, key = ?, WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, product.getName());
        preparedStatement.setFloat(2, product.getCoordinates().getX());
        preparedStatement.setInt(3, product.getCoordinates().getY());
        preparedStatement.setDate(4, new Date(product.getCreationDate().getTime()));
        preparedStatement.setLong(5, product.getPrice());
        preparedStatement.setString(6, product.getUnitOfMeasure().name());
        preparedStatement.setInt(7, getOwnerId(product));
        preparedStatement.setInt(8, product.getCreatorID());
        preparedStatement.setString(9, key);
        preparedStatement.setLong(10, product.getId());


        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public static LinkedHashMap<String, Product> refill() throws SQLException {
        LinkedHashMap<String, Product> products = new LinkedHashMap<>();
        String query = "Select products.id as id, products.name as name, x, y, creation_date, price, unit_of_measure, owners.name as owner_name, owners.birthday as owner_birthday, owners.weight as owner_weight, users.name as user_name, products.creator_id as creator_id, key from products " +
                "left join owners on owner_id = owners.id " +
                "left join users on products.creator_id = users.id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            try {
                Person owner = new Person(resultSet.getString("owner_name"), resultSet.getString("owner_birthday"), resultSet.getLong("owner_weight"));
                Coordinates coordinates = new Coordinates(resultSet.getFloat("x"), resultSet.getInt("y"));
                Product product = new Product(resultSet.getLong("id"), resultSet.getString("name"), coordinates, resultSet.getDate("creation_date"), resultSet.getLong("price"), UnitOfMeasure.valueOf(resultSet.getString("unit_of_measure")), owner, resultSet.getString("user_name"), resultSet.getInt("creator_id"));
                products.put(resultSet.getString("key"), product);
            }
            catch (NullPointerException e){
                return products;
            }
        }
        resultSet.close();
        preparedStatement.close();
        return products;
    }

    public static void remove(Product product) throws SQLException {
        String query =
                "Delete from products * where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, product.getId());
        preparedStatement.executeUpdate();
    }
    public static void removeAll(int userID) throws SQLException{
        String query =
                "delete from products * where creator_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userID);
        preparedStatement.executeUpdate();
    }
}
