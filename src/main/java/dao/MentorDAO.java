package dao;

import com.sun.org.apache.bcel.internal.classfile.Code;
import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MentorDAO implements IMentorDAO {
    DBCreator dbCreator;

    public MentorDAO() {
        dbCreator = new DBCreator();

    }

    @Override
    public void createCodecooler(User user, Codecooler codecooler) {
        createUser(user);
        try {
            int userID = getUserIdWithLogin(user);
            codecooler.setId(userID);
            createStudent(codecooler);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to fetch user with this login");
        }

    }

    private void createUser(User user){
        String query = "INSERT INTO users(login, password, usertype) VALUES (?,?,?)";
        PreparedStatement statement = null;

        try {
            dbCreator.connectToDatabase();
            statement = dbCreator.connection.prepareStatement(query);


            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());


            statement.executeUpdate();
            dbCreator.connectToDatabase().close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void createStudent(Codecooler codecooler){
        String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience_points," +
                " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;

        try {
            dbCreator.connectToDatabase();
            statement = dbCreator.connection.prepareStatement(query);


            statement.setString(1, codecooler.getAdress());
            statement.setInt(2, codecooler.getRoomID());
            statement.setInt(3, codecooler.getAmmountOfCoins());
            statement.setString(4, codecooler.getEmail());
            statement.setInt(5, codecooler.getLvlOfExp());
            statement.setString(6, codecooler.getFirstName());
            statement.setString(7, codecooler.getLastName());
            statement.setString(8, codecooler.getPhoneNum());
            statement.setInt(9, codecooler.getId());




            statement.executeUpdate();

            dbCreator.connectToDatabase().close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private int getUserIdWithLogin(User user) throws Exception {
        ResultSet results = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM users WHERE login = ?;";
        try {

            dbCreator.connectToDatabase();


            statement = dbCreator.connection.prepareStatement(query);

            statement.setString(1, user.getLogin());

            results = statement.executeQuery();

            results.next();

            return results.getInt("id");



        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Did not find user with this login SLQ Exception");

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Did not find user with this login");
        }


        }



    public Quest createNewQuest() {
        return null;
    }

    public void addQuestToAvailable() {

    }

    public void addQuestCategory() {

    }

    public Artifact createArtifact() {
        return null;
    }

    public void updateQuest() {

    }

    public void updateArtifact() {

    }

    public void addArtifactCategory() {

    }

    @Override
    public void markAchievedQuests() {

    }

    public void markAchivedQuests() {

    }

    public void markBoughtArtifacts() {

    }

    public Map<Integer, Integer> seeStudentWallet() {
        return null;
    }
}
