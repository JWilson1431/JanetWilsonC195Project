package model;

public class country {
    private int countryId;
    private String country;

    //constructor

    public country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    //setters and getters

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
