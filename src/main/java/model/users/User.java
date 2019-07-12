package model.users;

public abstract class User {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private String address;
    private String userType;

    User(){}



    User(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String address, String userType) {
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNum(phoneNum);
        this.email = email;
        this.setAddress(address);
        this.userType = userType;
    }

    public User(int id, String login, String password){
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
    }

    public User(int id, String firstName, String lastName, String phoneNum, String address) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNum(phoneNum);
        this.setAddress(address);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

