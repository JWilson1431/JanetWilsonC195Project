package model;

/**This is the first level division class. It contains information about the first class divisions.*/
public class FirstLevelDivision {
    private static int divisionId;
    private String division;
    private int countryId;

    //constructor
    /**This is the first level division constructor.*/
    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }


    //override string to string
    @Override
    public String toString(){
        return(division);
    }
    //setters and getters
    /**This is the get division id method. This method gets the id of the division.
     * @return divisionId - the id of the division*/
    public int getDivisionId() {
        return divisionId;
    }
    /**This is the set division id method. This method sets the id of the division.
     * @param divisionId - the id of the division.*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**This is the get division method. This method gets the division.
     * @return division - the division.*/
    public String getDivision() {
        return division;
    }
    /**This is the set division method. This method sets the division.
     *@param  division - the division.*/
    public void setDivision(String division) {
        this.division = division;
    }
    /**This is the get country Id method. This method gets the id of the country.
     * @return countryId - the id of the country.*/
    public int getCountryId() {
        return countryId;
    }
    /**This is the set country Id method. This method sets the id of the country.
     * @param countryId - the id of the country.*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
