package dao;

import com.sun.org.apache.bcel.internal.classfile.Code;
import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
        try {
            PreparedStatement statement = null;
            Connection connection = dbCreator.connectToDatabase();

            String query = "INSERT INTO users(login, password) VALUES (?,?)";

            connection.prepareStatement(query);


            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());


            statement.executeUpdate();

            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    private void createUser(User user){
        try {
            PreparedStatement statement = null;
            Connection connection = dbCreator.connectToDatabase();

            String query = "INSERT INTO users(login, password) VALUES (?,?)";

            connection.prepareStatement(query);


            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());


            statement.executeUpdate();

            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void createCodecooler(Codecooler cooler){
        Codecooler codecooler;
        List<Quest> list = new ArrayList<>();
        codecooler = new Codecooler(1,"login", "password","name","surname", 1,"address", "codecooler", 0, 1, list, 0 );
        try {
            PreparedStatement statement = null;
            Connection connection = dbCreator.connectToDatabase();

            String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience points," +
                    " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";

            connection.prepareStatement(query);


            statement.setString(1, codecooler.getAdress());
            statement.setInt(2, codecooler.getRoomID());
            statement.setInt(3, codecooler.getAmmountOfCoins());
            statement.setString(4, codecooler.get);
            statement.setString(6, codecooler.getPhoneNum());  // has to be string
            statement.setString(7, codecooler.getAdress());
            statement.setString(8, codecooler.getUserType());

            statement.setInt(9, codecooler.getAmmountOfCoins());
            statement.setInt(10, )



            statement.executeUpdate();

            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private int getUserIdWithLogin(User user){

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
