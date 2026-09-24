package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteContactDAO implements IContactDAO {

    private Connection connection;

    public SqliteContactDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
        insertSampleData();

    }

    public void createTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS contacts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (SQLException ex){
            System.err.println(ex);
        }
    }

    public void insertSampleData(){
        try {
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM contacts";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO contacts (firstName, lastName, phone, email) VALUES "
                    + "('John', 'Doe', '0412345678', 'johndoe@example.com'),"
                    + "('Jane', 'Doe', '0412345679', 'janedoe@example.com'),"
                    + "('Jay', 'Doe', '0412345670', 'jaydoe@example.com')";
            insertStatement.execute(insertQuery);

        } catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void addContact(Contact contact) {
        try{
            PreparedStatement insertContact = connection.prepareStatement(
                    "INSERT INTO contacts (firstName, lastName, phone, email) VALUES (?,?,?,?)");

            insertContact.setString(1, contact.getFirstName());
            insertContact.setString(2, contact.getLastName());
            insertContact.setString(3, contact.getPhone());
            insertContact.setString(4, contact.getEmail());
            insertContact.executeUpdate();
            ResultSet generatedKeys = insertContact.getGeneratedKeys();
            if(generatedKeys.next()){
                contact.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex){
            System.err.println(ex);
        }
    }

    @Override
    public void updateContact(Contact contact) {
        try {
            PreparedStatement updateContacts = connection.prepareStatement(
                    "UPDATE contacts SET firstName = ?, lastName = ?, phone = ?, email=? WHERE id = ?"
            );
            updateContacts.setString(1, contact.getFirstName());
            updateContacts.setString(2, contact.getLastName());
            updateContacts.setString(3, contact.getPhone());
            updateContacts.setString(4, contact.getEmail());
            updateContacts.setInt(5, contact.getId());
            updateContacts.executeUpdate();

        } catch(SQLException ex){
            System.err.println(ex);
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        try {
            int id = contact.getId();
            PreparedStatement deleteaccount = connection.prepareStatement(" DELETE FROM contacts WHERE id = ?");
            deleteaccount.setInt(1, id);
            deleteaccount.executeUpdate();
        } catch(SQLException ex){
            System.err.println(ex);
        }
    }

    @Override
    public Contact getContact(int id) {
        try{
            PreparedStatement GetContact = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
            GetContact.setInt(1, id);
            ResultSet rs = GetContact.executeQuery();
            if(rs.next()) {
                return new Contact(
                        //rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
            } catch (SQLException ex){
                System.err.println(ex);
            }
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");
            while(rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                Contact contact = new Contact(firstName, lastName, phone, email);
                contact.setId(id);
                contactList.add(contact);
            }
        } catch (SQLException ex){
            System.err.println(ex);
        }
        return contactList;
    }
}
