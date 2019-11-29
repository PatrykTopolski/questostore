package dao;

import model.users.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginDAO implements ILoginDAO {
    private DBCreator dbCreator = new DBCreator();

    public void deleteSession(String sessionId) throws DBException{
        Connection connection;
        try {
            String query = "DELETE FROM sessions WHERE sessionid LIKE ?";
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, sessionId);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateMentorByID(Mentor mentor)");
        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in updateMentorByID(Mentor mentor)");
        }
    }

    public Optional<User> getUserByLogin(String login, String password) {
        Optional<User> user = Optional.empty();
        try {
            Connection con = dbCreator.connectToDatabase();

            String query = "SELECT * FROM users WHERE login LIKE ? AND password LIKE ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            String userLogin = "";
            String userPass = "";
            String userType = "";
            int userId = 0;
            while (resultSet.next()) {
                userId = resultSet.getInt("id");
                userLogin = resultSet.getString("login");
                userPass = resultSet.getString("password");
                userType = resultSet.getString("usertype");
            }

            if (userType.equals("mentor")){
                user = Optional.of(new Mentor(userId, userLogin, userPass, userType));
            } else if (userType.equals("student")){
                user = Optional.of(new Student(userId, userLogin, userPass, userType));
            } else if (userType.equals("admin")){
                user = Optional.of(new Admin(userId, userLogin, userPass, userType));
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }
}
