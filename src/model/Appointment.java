package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**This is the appointment class. It holds the information for the appointments in the application.*/
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerId;
    private int userId;
    private int contactId;


    /**This is the appointment constructor.*/
    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**This is the get appointment ID method. It returns the appointment ID of the appointment.
     * @return appointmentId.*/
    //Setters and getters created
    public int getAppointmentId() {
        return appointmentId;
    }

    /**This is the set appointment ID method. It sets the ID of the appointment.
     * @param appointmentId */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**This is the get title method. This method gets the title of the appointment.
     * @returns title*/
    public String getTitle() {
        return title;
    }
    /**This is the set title method. This method sets the title of the appointment.
     * @param title - the title of the appointment*/
    public void setTitle(String title) {
        this.title = title;
    }
    /**This is the get description method. It returns the description of the appointment.
     * @return description- description of the appointment.*/
    public String getDescription() {
        return description;
    }
    /**This is  the set description method. It sets the description of the appointment.
     * @param description - the description of the appointment*/
    public void setDescription(String description) {
        this.description = description;
    }
    /**This is the get location method. It gets the location of the appointment
     * @return location- returns the location of the appointment.*/
    public String getLocation() {
        return location;
    }
    /**This is the set location method. It sets the location of the appointment.
     * @param location - the location of the appointment.*/
    public void setLocation(String location) {
        this.location = location;
    }
    /**This is the get type method. It gets the type of the appointment.
     * @return type- the type of the appointment.*/
    public String getType() {
        return type;
    }
    /**This is the set type method. It sets the type of the appointment.
     * @param type- the type of the appointment.*/
    public void setType(String type) {
        this.type = type;
    }
    /**This is the get start method. It gets the start timestamp of the appointment.
     * @return  start- the start timestamp of the appointment.*/
    public Timestamp getStart() {
        return start;
    }
    /**This is the set start method. It sets the start timestamp of the appointment.
     * @param start - the start timestamp of the appointment.*/
    public void setStart(Timestamp start) {
        this.start = start;
    }
    /**This is the get end method. It gets the end timestamp of the appointment.
     * @return end- the end of the appointment.*/
    public Timestamp getEnd() {
        return end;
    }
    /**This is the set end method. It sets the end timestamp of the appointment.
     * @param end -the end timestamp of the appointment.*/
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    /**This is the get customer id method. It gets the id of the customer of the customer associated with the appointment.
     * @return customerId- the customer id.*/
    public int getCustomerId() {
        return customerId;
    }
    /**This is the set customer Id method. It sets the id of the customer associated with the appointment.
     * @param customerId - the customer Id.*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**This is the get user id method. It gets the id of the user associated with the appointment.
     * @return userId.*/
    public int getUserId() {
        return userId;
    }
    /**This is the set user Id method. It sets the id of the user associated with the appointment.
     * @param userId - the id of the user.*/
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**This is the get contact Id method. It gets the Id of the contact associated with the appointment.
     * @return contactId- the id of the contact.*/
    public int getContactId() {
        return contactId;
    }
    /**This is the set contact Id method. It sets the Id of the contact associated with the appointment.
     * @param contactId -the id of the contact.*/
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
