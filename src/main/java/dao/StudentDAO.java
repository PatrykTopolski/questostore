package dao;

import model.users.Student;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StudentDAO implements IStudentDAO {

    //this class contains methods to create and update student
    // and show his level

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method


    private Connection connection;
    private DBCreator dbCreator;


    public StudentDAO() {
        dbCreator = new DBCreator();
    }

    public void updateStudent(Student student) throws DBException {
        try {
            String query = "UPDATE studentpersonals SET first_name = ?, last_name = ?, phone_number = ?, email = ?, address = ? WHERE user_id = ?;" +
                    "UPDATE users SET login = ?, password = ? WHERE id = ?";

            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPhoneNum());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getAddress());
            statement.setInt(6, student.getId());

            statement.setString(7, student.getLogin());
            statement.setString(8, student.getPassword());
            statement.setInt(9, student.getId());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateMentorByID(Mentor mentor)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in updateMentorByID(Mentor mentor)");
        }

    }

    public void createStudent(User user, Student student) throws  DBException {
        createUser(user);
        int userID = getUserIdWithLogin(user);
        student.setId(userID);
        createStudent(student);

    }


    public List<Student> getStudentListFromRoom(int roomId) throws DBException{
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM studentpersonals WHERE class_id = ?");
            stm.setInt(1, roomId);
            ResultSet result = stm.executeQuery();
            List<Student> resultList = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt("user_id");
                String firstName = result.getString("first_name");
               // System.out.println("student first name" + firstName);
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                Student student = new Student(id, firstName, lastName, phoneNum, email, address, roomId);
                resultList.add(student);
            }

            stm.close();
            result.close();
            connection.close();
            return resultList;
        }
        catch (SQLException e) {
            throw new DBException("SQLException occurred in getStudent(int roomId))");
        }
        catch (Exception e){
            throw new DBException("Unidentified exception occurred in getStudent(int roomId)");
        }
    }




    public int getExperiencePoints(int id) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select experience_points from studentpersonals where user_id = ? ");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getInt("experience_points");
            }

            throw new DBException("No entry with id: " + id);

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getExperiencePoints()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getExperiencePoints()");
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
            connection.close();

        } catch (SQLException e) {

            throw new DBException("SQLException occurred in createUser()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createUser()");
        }

    }


    private void createStudent(Student student) throws DBException {
        String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience_points," +
                " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, student.getAddress());
            statement.setInt(2, student.getRoomID());
            statement.setInt(3, student.getAmountOfCoins());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getLvlOfExp());
            statement.setString(6, student.getFirstName());
            statement.setString(7, student.getLastName());
            statement.setString(8, student.getPhoneNum());
            statement.setInt(9, student.getId());

            statement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createStudent()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createStudent()");
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
            int id = results.getInt("id");
            connection.close();
            return id;


        } catch (SQLException e) {

            throw new DBException("Did not find user with this login SLQ Exception");

        } catch (Exception e){

            throw new DBException("Did not find user with this login");
        }

    }


}
