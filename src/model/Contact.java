package model;

/**This is the contact class. It holds information about the contact associated with appointments.*/
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    //override string to string
    @Override
    public String toString(){
        return(contactName);
    }

    /**This is the get contact Id method. This method gets the ID associated with the contact.
     * @return contactId - the id of the contact*/
    public int getContactId() {
        return contactId;
    }
    /**This is the set contact Id method. This method sets the ID associated with the contact.
     * @param contactId - the id of the contact.*/
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /**This is the get contact name method. This method gets the name of the contact.
     * @return contactName - the name of the contact.*/
    public String getContactName() {
        return contactName;
    }
    /**This is the set contact name method. This method sets the name of the contact.
     * @param contactName - the name of the contact.*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**This is the get email method. This method gets the email address of the contact.
     * @return email - the email address of the contact.*/
    public String getEmail() {
        return email;
    }
    /**This is the set email method. This method sets the email address of the contact.
     * @param email - the email address of the contact.*/
    public void setEmail(String email) {
        this.email = email;
    }
    /**This is the contact constructor.*/
    public Contact(int contactId, String contactName, String email){
        this.contactId=contactId;
        this.contactName=contactName;
        this.email=email;
    }
}
