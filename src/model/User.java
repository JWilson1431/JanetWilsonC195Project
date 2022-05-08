package model;

/**This is the user class. It contains information about the user.*/
public class User {
    private int userId;
    private String userName;
    private String password;

    public User(int userId, String userName) {
    }

    //overrides string to string
    @Override
    public String toString(){
        return(userName);
    }

    //constructor
    /**This is the user constructor.*/
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    //setters and getters
    /**This is the get user id method. This method gets the id of the user.
     * @return userId - the id of the user.*/
    public int getUserId() {
        return userId;
    }
    /**This is the set user id method. This method sets the id of the user.
     * @param userId - the id of the user.*/
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**This is the get user name method. It gets the username of the user.
     * @return userName*/
    public String getUserName() {
        return userName;
    }
    /**This is the set username method. This method sets the username of the user.
     * @param userName - the username of the user.*/
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**This is the get password method. This method gets the password of the user.
     * @return password.*/
    public String getPassword() {
        return password;
    }
    /**This is the set password method. This method sets the password of the user.
     * @param password - the password of the user.*/
    public void setPassword(String password) {
        this.password = password;
    }
}
