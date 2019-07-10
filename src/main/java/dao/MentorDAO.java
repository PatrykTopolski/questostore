package dao;

import model.users.Mentor;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MentorDAO implements IMentorDAO {
    //this class contains methods to process mentor: find by name o id,
    // create, update, assign to room

    Connection connection;
    DBCreator dbCreator;

    public MentorDAO() {
        dbCreator = new DBCreator();

    }

    public void addMentor(User user, Mentor mentor) throws DBException {
        createUser(user);
        int userID = getUserIdWithLogin(user);
        mentor.setId(userID);
        createMentor(mentor);
    }

    public void updateMentorByID(Mentor mentor) throws DBException {
        try {
            String query = "UPDATE mentorsPersonals SET first_name = ?, last_name = ?, phone_number = ?, email = ?, adress = ? WHERE id = ?";
            PreparedStatement statement = null;

            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);
            statement.setString(1, mentor.getFirstName());
            statement.setString(2, mentor.getLastName());
            statement.setString(3, mentor.getPhoneNum());
            statement.setString(4, mentor.getEmail());
            statement.setString(5, mentor.getAdress());
            statement.setInt(6, mentor.getId());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occured in updateMentorByID(Mentor mentor)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in updateMentorByID(Mentor mentor)");
        }

    }


    public void updateMentorByFullName(Mentor mentor) throws DBException {
        try {
            String query = "UPDATE mentorsPersonals SET phone_number = ?, email = ?, adress = ? WHERE first_name = ? AND last_name = ?";
            PreparedStatement statement = null;

            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);
            statement.setString(1, mentor.getPhoneNum());
            statement.setString(2, mentor.getEmail());
            statement.setString(3, mentor.getAdress());
            statement.setString(4, mentor.getFirstName());
            statement.setString(5, mentor.getLastName());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occured in updateMentorByFullName(Mentor mentor)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in updateMentorByFullname(Mentor mentor)");
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
            Mentor mentor = null;

            if (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                int classID = result.getInt("class_id");
                mentor = new Mentor(id, firstName, lastName, phoneNum, email, address, classID);
            }

            return mentor;
        } catch (SQLException e) {
            throw new DBException("SQLException occured in getMentor(int id)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(int id)");
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

            while (result.next()) {
                int mentorID = result.getInt("user_id");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                int classID = result.getInt("class_id");
                Mentor mentor = new Mentor(mentorID, firstName, lastName, phoneNum, email, address, classID);
                return mentor;
            }
            connection.close();

            return null;
        } catch (SQLException e) {
            throw new DBException("SQLException occured in getMentor(String firstName, String lastName)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(String firstName, String lastName)");
        }
    }

    //todo
    public void assignMentorToRoom() {

    }

    private void createUser(User user) throws DBException {
        String query = "INSERT INTO users(login, password, usertype) VALUES (?,?,?)";
        PreparedStatement statement = null;

        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());
            statement.executeUpdate();
            dbCreator.connectToDatabase().close();

        } catch (SQLException e){
            throw new DBException("SQLException occured in createUser(User user)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occured in createUser(User user)");
        }
    }

    private void createMentor(Mentor mentor) throws DBException {
        String query = "INSERT INTO mentorpersonals(user_id, first_name, last_name, phone_number, email, adress) VALUES(?,?,?,?,?,?);";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, mentor.getId());
            statement.setString(2, mentor.getFirstName());
            statement.setString(3, mentor.getLastName());
            statement.setString(4, mentor.getPhoneNum());
            statement.setString(5, mentor.getEmail());
            statement.setString(6, mentor.getAdress());
            statement.executeUpdate();

            dbCreator.connectToDatabase().close();

        }  catch (SQLException e){
            throw new DBException("SQLException occured in createMentor(Mentor mentor)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occured in createMentor(Mentor mentor)");
        }
    }

    private int getUserIdWithLogin(User user) throws DBException {
        ResultSet results = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM users WHERE login = ?;";
        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            results = statement.executeQuery();
            results.next();
            return results.getInt("id");

        } catch (SQLException e) {
            throw new DBException("SQLException occured in getUserIdWithLogin(User user)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getUserIdWithLogin(User user)");
        }

    }

}
