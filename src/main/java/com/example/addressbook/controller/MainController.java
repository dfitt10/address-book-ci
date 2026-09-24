package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.model.ContactManager;
import com.example.addressbook.model.SqliteContactDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;



public class MainController {
    @FXML
    private ListView<Contact> contactsListView;
    private ContactManager contactManager;

    @FXML
    private VBox contactContainer;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField searchTextField;

    public MainController(){
        contactManager = new ContactManager(new SqliteContactDAO());
        //contactDAO.addContact(new Contact("Jerry", "Doe", "jerrydoe@example.com", "0423423426"));
    }

    /* Renders a cell in the contacts list view by setting the text to the contact's full name.
    @param contactListView The list view to render the cell for.
    @return The rendered cell
     */

    private ListCell<Contact> renderCell(ListView<Contact> contactListView){
        return new ListCell<>(){
            /* Updates the item in the cell by setting the text to the contact's full name.
            @param contact The contact to update the empty cell with
            @param empty Whether the cell is empty
             */

            private void onContactSelected(MouseEvent mouseEvent){
                ListCell<Contact> clickedCell = (ListCell<Contact>) mouseEvent.getSource();
                Contact selectedContact = clickedCell.getItem();
                if (selectedContact != null) selectContact(selectedContact);
            }

            @Override
            protected void updateItem(Contact contact, boolean empty){
                super.updateItem(contact, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if(empty || contact == null || contact.getContactSummary() == null){
                    setText(null);
                    super.setOnMouseClicked(this::onContactSelected);
                } else{
                    setText(contact.getContactSummary());
                }
            }
        };
    }

    private void syncContacts(){
        Contact currentContact = contactsListView.getSelectionModel().getSelectedItem();
        contactsListView.getItems().clear();

        String query = searchTextField.getText();
        List<Contact> contacts = contactManager.searchContacts(query);

        // Show or hide the text fields based on whether there are contacts
        // hide text fields when all contacts are deleted
        boolean hasContact = !contacts.isEmpty();
        if (hasContact){
            contactsListView.getItems().addAll(contacts);
            // If the current contact is still in the list, re-select it
            // Otherwise, select the first contact in the list
            Contact nextContact = contacts.contains(currentContact) ? currentContact : contacts.get(0);
            contactsListView.getSelectionModel().select(nextContact);
            selectContact(nextContact);

        }
        // Show / hide based on whether there are contacts
        contactContainer.setVisible(hasContact);
    }

    @FXML
    public void initialize(){
        contactsListView.setCellFactory(this::renderCell);
        syncContacts();
        // Select the first contact and display its information
        contactsListView.getSelectionModel().selectFirst();
        Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
        if (firstContact!= null){
            selectContact(firstContact);
        }
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> syncContacts());

    }

    // Programmaticaly selects a contact in the list view and updates the text field with the contact's information
    // @param contact The contact to select

    private void selectContact(Contact contact){
        contactsListView.getSelectionModel().select(contact);
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        emailTextField.setText(contact.getEmail());
        phoneTextField.setText(contact.getPhone());
    }

    @FXML
    private void onEditConfirm(){
        // Get the selected contact from the list view
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if(selectedContact != null){
            selectedContact.setFirstName(firstNameTextField.getText());
            selectedContact.setLastName(lastNameTextField.getText());
            selectedContact.setEmail(emailTextField.getText());
            selectedContact.setPhone(phoneTextField.getText());
            contactManager.updateContact(selectedContact);
            syncContacts();
        }
    }

    @FXML
    private void onDelete(){
        // Get the selected contact from the list view
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null){
            contactManager.deleteContact(selectedContact);
            syncContacts();
        }
    }

    @FXML
    private void onAdd(){
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Contact";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE);

        // Add the new contact to the database
        contactManager.addContact(newContact);
        syncContacts();
        // Select the new contact in the list view
        // and focus the first name text field
        selectContact(newContact);
        firstNameTextField.requestFocus();
    }

    @FXML
    private void onCancel(){
        // Find the selected contact
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null){
            // Since the contact hasn't been modified, we can just re-select it to refresh the text fields
            selectContact(selectedContact);
        }
    }




}
