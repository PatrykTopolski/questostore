package dao;

import model.users.Mentor;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO implements IMentorDAO {
    private Connection connection;
    private DBCreator dbCreator;

    public MentorDAO() {
        dbCreator = new DBCreator();
    }

    public void addMentor(User user, Mentor mentor) throws DBException {
        createUser(user);
        int userID = getUserIdWithLogin(user);
        mentor.setId(userID);
        createMentor(mentor);
    }

    public List<Mentor> getAllMentors() throws DBException{
        List<Mentor> mentorsList = new ArrayList();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from mentorspersonals ORDER BY user_id");
            ResultSet result = stm.executeQuery();
            connection.close();
            Mentor mentor = null;

            while (result.next()) {
                int id = result.getInt("user_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                mentor = new Mentor(id, firstName, lastName, phoneNum, email, address);
                mentorsList.add(mentor);
            }
            return mentorsList;
        } catch (SQLException e) {
            throw new DBException("SQLException occured in getMentor(int id)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(int id)");
        }
    }

    public void updateMentorByID(Mentor mentor) throws DBException {
        try {
            String query = "UPDATE mentorsPersonals SET first_name = ?, last_name = ?, phone_number = ?, email = ?, address = ? WHERE user_id = ?;" +
                    "UPDATE users set login = ?, password = ? WHERE id = ? ";

            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mentor.getFirstName());
            statement.setString(2, mentor.getLastName());
            statement.setString(3, mentor.getPhoneNum());
            statement.setString(4, mentor.getEmail());
            statement.setString(5, mentor.getAddress());
            statement.setInt(6, mentor.getId());
            statement.setString(7, mentor.getLogin());
            statement.setString(8, mentor.getPassword());
            statement.setInt(9, mentor.getId());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateMentorByID(Mentor mentor)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in updateMentorByID(Mentor mentor)");
        }
    }


    public void updateMentorByFullName(Mentor mentor) throws DBException {
        try {
            String query = "UPDATE mentorsPersonals SET phone_number = ?, email = ?, adress = ? WHERE first_name = ? AND last_name = ?";
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mentor.getPhoneNum());
            statement.setString(2, mentor.getEmail());
            statement.setString(3, mentor.getAddress());
            statement.setString(4, mentor.getFirstName());
            statement.setString(5, mentor.getLastName());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateMentorByFullName(Mentor mentor)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in updateMentorByFullName(Mentor mentor)");
        }
    }

    public Mentor getMentorById(int id) throws DBException {
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from mentorspersonals left join classes on " +
                    "mentorspersonals.user_id = classes.user_id where mentorspersonals.user_id = ?;");

            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            connection.close();
            Mentor mentor;

            if (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                int classID = result.getInt("class_id");
                mentor = new Mentor(id, firstName, lastName, phoneNum, email, address, classID);
                return mentor;
            }
            throw new DBException("No mentor with id: " + id);
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getMentor(int id)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getMentor(int id)");
        }
    }

    public Mentor getMentorByFullName(String firstName, String lastName) throws DBException {
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM mentorspersonals WHERE first_name = ? AND last_name = ?");
            stm.setString(1, firstName);
            stm.setString(2, lastName);
            ResultSet result = stm.executeQuery();

            int userID = 0;
            if (result.next()) {
                userID = result.getInt("user_id");
            }
            stm = connection.prepareStatement("select * from mentorspersonals left join classes on " + "mentorspersonals.user_id = classes.user_id where mentorspersonals.user_id = ?;");
            stm.setInt(1, userID);
            result = stm.executeQuery();

            if (result.next()) {
                int mentorID = result.getInt("user_id");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                int classID = result.getInt("class_id");
                return new Mentor(mentorID, firstName, lastName, phoneNum, email, address, classID);
            }
            connection.close();
            throw new DBException("No mentor: " + firstName + " " + lastName);
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getMentor(String firstName, String lastName)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getMentor(String firstName, String lastName)");
        }
    }

    private void createUser(User user) throws DBException {
        String query = "INSERT INTO users(login, password, usertype) VALUES (?,?,?)";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());
            statement.executeUpdate();
            dbCreator.connectToDatabase().close();
        } catch (SQLException e){
            throw new DBException("SQLException occurred in createUser(User user)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createUser(User user)");
        }
    }

    private void createMentor(Mentor mentor) throws DBException {
        String query = "INSERT INTO mentorspersonals(user_id, first_name, last_name, phone_number, email, address) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mentor.getId());
            statement.setString(2, mentor.getFirstName());
            statement.setString(3, mentor.getLastName());
            statement.setString(4, mentor.getPhoneNum());
            statement.setString(5, mentor.getEmail());
            statement.setString(6, mentor.getAddress());
            statement.executeUpdate();
            dbCreator.connectToDatabase().close();
        }  catch (SQLException e){
            throw new DBException("SQLException occurred in createMentor(Mentor mentor)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createMentor(Mentor mentor)");
        }
    }

    private int getUserIdWithLogin(User user) throws DBException {
        String query = "SELECT * FROM users WHERE login = ?;";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("id");
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getUserIdWithLogin(User user)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getUserIdWithLogin(User user)");
        }
    }
}
