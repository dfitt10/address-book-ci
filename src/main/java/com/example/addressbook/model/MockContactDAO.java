package com.example.addressbook.model;

import java.util.ArrayList;
import java.util.List;

// Responsible for emulating a database and it implements the IContactDAO interface
// To declare a class that implements an interface you include an implements clause in the class decleration
// Your class can implement more than one interface so the implements keyword is followed by a comma-seperated list
// By default, the implements clause follows the extends clause if there is one
public class MockContactDAO implements IContactDAO {
    //IntelliJ will have the class decleration underlind in red?
    // Place cursor over class declartion to see IntelliJ reports class must be declared abstract or implement Methods
    // Click on implements methods to write a default implementation in this class
    // Ensure you include default @Override before the methods are added

    public final ArrayList<Contact> contacts = new ArrayList<>();
    private int autoIncrementID = 0;

    //public MockContactDAO(){
        //addContact(new Contact("John", "Doe", "johndoe@example.com", "04234423423"));
        //addContact(new Contact("Jane", "Doe", "janedoe@example.com", "04234423424"));
        //addContact(new Contact("Jay", "Doe", "jaydoe@example.com", "04234423425"));
    //}

    @Override
    public void addContact(Contact contact){
        contact.setId(autoIncrementID);
        autoIncrementID++;
        contacts.add(contact);
    }

    @Override
    public void updateContact(Contact contact){
        for (int i = 0; i< contacts.size(); i++){
            if (contacts.get(i).getId() == contact.getId()) {
                contacts.set(i, contact);
                break;
            }
        }
    }

    @Override
    public void deleteContact(Contact contact){
        contacts.remove(contact);
    }

    @Override
    public Contact getContact(int id){
        for (Contact contact : contacts){
            if (contact.getId() == id){
                return contact;
            }
        }
        return null;
    }

    @Override
    public List<Contact> getAllContacts(){
        return new ArrayList<>(contacts);
    }
}
