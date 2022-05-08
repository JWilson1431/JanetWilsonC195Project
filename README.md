# JanetWilsonC195Project
Software 2 Project-Scheduling software

This is an application that allows users to schedule appointments. It is connected to a MySQL database. 
A user can log in to the application using their unique username and password. If the user has any appointments within 15 minutes of the log in time, an alert reminds them of the appointment.
The log in form can also be displayed in French if the user's default language is French. 
After logging in, a table of customers and a table of appointments is displayed. The user can go to the customer page or the application page with the click of a button.
In the customer section the user can view a table of all customers, add a new customer, delete a customer, or update a customer. When update is chosen, all of the customer's information is automatically populated in the update screen. A customer cannot be deleted if they currently have appointments scheduled.
From the appointment section, the user can view a table of all appointments, appointments by month, or appointments by week. The user can also add a new appointment, update an appointment, or delete an appointment. Appointments for a customer cannot overlap to ensure accuracy. The appointments can only be scheduled during office hours in Eastern time. Appointments are saved in the database in UTC and automatically adjust to the user's time zone.
There is also a report page that allows the user to view all appointments during each month by type. On the reports page, appointments can be searched either by user ID or contact ID.
