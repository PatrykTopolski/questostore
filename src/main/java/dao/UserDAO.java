package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.Admin;
import model.users.Student;
import model.users.Mentor;
import model.users.User;

import java.sql.*;
import java.util.List;


public class UserDAO implements IUserDAO {
    DBCreator dbCreator;

    public UserDAO() {
        dbCreator = new DBCreator();
    }

    public User seeProfile(int id) throws DBException {
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select usertype from users where id=? ");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            String userType = "";
            if (result.next()) {
                userType = result.getString("usertype");
            }
            if (userType.equals("student")) {
                return getFullStudentObject(id);
            } else if (userType.equals(("mentor"))) {
                return getFullMentor(id);
            } else if (userType.equals("admin")) {
                return getFullAdmin(id);
            }
            throw new DBException("Wrong user type or user doesn't exist");
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in seeProfile()");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException("Unidentified exception occurred in seeProfile()");
        }
    }


    private Student getFullStudentObject(int id) throws DBException {
        try {
            DBCreator creator = new DBCreator();
            Connection connection = creator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from users left  join  studentpersonals on users.id=studentpersonals.user_id  where id= ? ");
            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();
            Student student;
            if (result.next()) {
                int user_id = result.getInt("id");
                String login = result.getString(("login"));
                String password = result.getString("password");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNumber = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                int classID = result.getInt("class_id");
                int experiencePoints = result.getInt("experience_points");
                int coolcoins = result.getInt("coolcoins");
                QuestDAO questDAO = new QuestDAO();
                ArtifactDAO artifactDAO = new ArtifactDAO();
                List<Quest> questList = questDAO.getUsersQuests(user_id);
                List<Artifact> artifactList = artifactDAO.getUsersArtifacts(user_id);

                student = new Student(user_id, login, password, firstName, lastName, phoneNumber, email, address, "student", coolcoins, classID, questList, artifactList, experiencePoints);
                return student;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in seeProfile()");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException("Unidentified exception occurred in seeProfile()");
        }
    }

    public Mentor getFullMentor(int id) throws DBException {
        try {
            // this method do the same as methods in mentor class?
            // getMentorById and getMentorByFullName?
            DBCreator creator = new DBCreator();
            Connection connection = creator.connectToDatabase();
            System.out.println("connected");
            PreparedStatement stm = connection.prepareStatement("select * from users left  join  mentorspersonals on users.id=mentorspersonals.user_id  where id= ? ");
            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();
            Mentor mentor;
            if (result.next()) {
                int user_id = result.getInt("id");
                String login = result.getString(("login"));
                String password = result.getString("password");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNumber = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                mentor = new Mentor(user_id, login, password, firstName, lastName, phoneNumber, email, address);
                return mentor;
            }
            throw new DBException("No mentor with id: " + id);
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in seeProfile()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in seeProfile()");
        }
    }

    private Admin getFullAdmin(int id) throws DBException {
        try {
            DBCreator creator = new DBCreator();
            Connection connection = creator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from users left  join  adminpersonals on users.id=adminpersonals.user_id  where id= ? ");
            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();
            Admin admin;
            if (result.next()) {
                int user_id = result.getInt("id");
                String login = result.getString(("login"));
                String password = result.getString("password");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNumber = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                admin = new Admin(user_id, login, password, firstName, lastName, phoneNumber, email, address);
                return admin;
            }
            throw new DBException("No admin with id: " + id);
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in seeProfile()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in seeProfile()");
        }
    }
}




