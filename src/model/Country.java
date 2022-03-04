package model;

public class Country {
    private int countryId;
    private String countryName;

    //constructor

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    //override string to string
    @Override
    public String toString(){
        return(countryName);
    }

    public Country(String countryName){
        this.countryName = countryName;
    }

    //setters and getters

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
