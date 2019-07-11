package model.users;

import model.items.Quest;


import java.util.List;

public class Student extends User {

    private int amountOfCoins;

    private int roomID;
    private List<Quest> questList;
    private int lvlOfExp;

    public Student(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String address, String userType, int amountOfCoins, int roomID, List<Quest> questList, int lvlOfExp) {
        super(id, login, password, firstName, lastName, phoneNum, email, address, userType);
        this.amountOfCoins = amountOfCoins;

        this.roomID = roomID;
        this.questList = questList;
        this.lvlOfExp = lvlOfExp;
    }


    public Student(int id, int amountOfCoins, int roomID, List<Quest> questList, int lvlOfExp) {
        setId(id);
        this.amountOfCoins = amountOfCoins;

        this.roomID = roomID;
        this.questList = questList;
        this.lvlOfExp = lvlOfExp;
    }

    public Student(int id, String login, String password, String userType) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setUserType(userType);
    }


    public Student(int id, String firstName, String lastName, String phoneNum, String email, String address, int roomID) {

        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        setEmail(email);

        setAddress(address);
        this.roomID = roomID;
    }

    public Student(int id, String login, String password, String firstName, String lastName, String phoneNumber, String email, String address, int classID, int experiencePoints, int coolcoins){
        setId(id);
        setLogin(login);
        setPassword(password);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNumber);
        setEmail(email);

        setAddress(address);
        this.roomID = classID;
        this.lvlOfExp = experiencePoints;
        this.amountOfCoins = coolcoins;
    }

    public int getAmountOfCoins() {
        return amountOfCoins;

    }

    public int getRoomID() {
        return roomID;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public int getLvlOfExp() {
        return lvlOfExp;
    }
}
