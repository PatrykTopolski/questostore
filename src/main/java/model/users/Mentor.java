package model.users;



public class Mentor extends User {
    private int roomID;

    public Mentor(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String address, String userType, int roomID) {
        super(id, login, password, firstName, lastName, phoneNum, email, address, userType);
        this.roomID = roomID;
    }
    public Mentor(int id,  String firstName, String lastName, String phoneNum, String email, String address,  int roomID) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        setEmail(email);
        setAddress(address);
        this.roomID = roomID;
    }

    public Mentor(int id, String login, String password, String userType){
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setUserType(userType);
    }

    public Mentor(String login, String password, String userType){
        setLogin(login);
        setPassword(password);
        setUserType(userType);
    }

    public Mentor(int id,  String firstName, String lastName, String phoneNum, String email, String address) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        setEmail(email);
        setAddress(address);

    }

    public Mentor(int id, int roomID){
        super();
        setId(id);
        this.roomID = roomID;
    }

    public Mentor(int user_id, String  login,String  password,String firstName,String lastName,String  phoneNumber,String  email,String address){
        setId(user_id);
        setLogin(login);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNumber);
        setEmail(email);
        setAddress(address);
    }

    public int getRoomID() {
        return roomID;
    }
}
