package model;

/**This is the customer class. It holds information about the customer.*/
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String country;
    private String division;
    private int countryId;

    public Customer(String customerName) {

    }

    public Customer(String customerName, int customerId) {

    }

    //override string to string
    @Override
    public String toString(){
        return(customerName);
    }

    /**This is the default constructor for the customer class.*/
    //default constructor
    public Customer(){
    }

    /**This is another constructor for the customer class.*/
    //constructor
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }
    /**This is another constructor for the customer class.*/
    //constructor
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.country = country;
    }


    //setters and getters
    /**This is the get customer Id method. This method gets the id associated with the customer.
     * @return customerId - the id of the customer.*/
    public int getCustomerId() {
        return customerId;
    }
    /**This is the set customer Id method. This method sets the id associated with the customer.
     @param customerId - the id of the customer.*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**This is the get customer name method. This method gets the name of the customer.
     * @return customerName - the name of the customer.*/
    public String getCustomerName() {
        return customerName;
    }
    /**This is the set customer name method. This method sets the name of the customer.
     * @param customerName - the name of the customer.*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**This is the get address method. This method gets the address of the customer.
     * @return address - the address of the customer.*/
    public String getAddress() {
        return address;
    }
    /**This is the set address method. This method sets the address of the customer.
     * @param address - the address of the customer.*/
    public void setAddress(String address) {
        this.address = address;
    }
    /**This is the get postal code method. This method gets the postal code of the customer.
     * @ return postalCode - the postal code of the customer.*/
    public String getPostalCode() {
        return postalCode;
    }
    /**This is the set postal code method. This method sets the postal code of the customer.
     * @param postalCode - the postal code of the customer.*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**This is the get phone number method. This method gets the phone number of the customer.
     * @return phoneNumber- the phone number of the customer.*/
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**This is the set phone number method. This method sets the phone number of the customer.
     * @param phoneNumber - the phone number of the customer.*/
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**This is the get divisionid method. This method gets the division id of the customer.
     * @return divisionId - the division id of the customer.*/
    public int getDivisionId() {
        return divisionId;
    }
    /**This is the set divisionid method. This method sets the division id of the customer.
     *@param  divisionId - the division id of the customer.*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**This is the get country method. This method gets the country associated with the customer.
     * @return country- the country of the customer.*/
    public String getCountry() {
        return country;
    }
    /**This is the set country method. This method sets the country associated with the customer.
     *@param  country- the country of the customer.*/
    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
