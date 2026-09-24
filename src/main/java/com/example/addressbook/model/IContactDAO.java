package com.example.addressbook.model;

import java.util.List;

/* Interface for the Contact Data Access Object that
handles the CRUD operations for the Contact class with the database
 */

public interface IContactDAO {
    // Adds a new contact to the database.
    // @param contact The contact to add.
    //public void createTable();
    public void addContact(Contact contact);

    public void updateContact(Contact contact);
    public void deleteContact(Contact contact);
    public Contact getContact(int id);
    public List<Contact> getAllContacts();
}
