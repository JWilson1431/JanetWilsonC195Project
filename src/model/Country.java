package model;

/**This is the country class. This class contains information about the countries.*/
public class Country {
    private int countryId;
    private String countryName;

    //constructor
    /**This is the country constructor for the country class.*/
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    //override string to string
    @Override
    public String toString(){
        return(countryName);
    }
    /**This is another constructor for the country class.*/
    public Country(String countryName){
        this.countryName = countryName;
    }

    //setters and getters
/**This is the get country id method. It gets the id associated with the country.
 * @return countryId - the id of the country.*/
    public int getCountryId() {
        return countryId;
    }
    /**This is the set country id method. It sets the id associated with the country.
     * @param countryId - the id of the country.*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /**This is the get country name method. It gets the name of the country.
     * @return countryName - the name of the country.*/
    public String getCountryName() {
        return countryName;
    }
    /**This is the set country name method. It sets the name of the country.
     * @param countryName - the name of the country.*/
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
