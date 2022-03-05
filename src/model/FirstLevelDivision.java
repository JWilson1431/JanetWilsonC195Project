package model;

public class FirstLevelDivision {
    private static int divisionId;
    private String division;
    private int countryId;

    //constructor

    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    //constructor
    //public FirstLevelDivision(String division){
    //    this.division = division;
    //}

    //override string to string
    @Override
    public String toString(){
        return(division);
    }
    //setters and getters

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
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
