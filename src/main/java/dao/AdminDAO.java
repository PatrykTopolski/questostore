package dao;

import model.users.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;

    AdminDAO() {
        dbCreator = new DBCreator();
    }


    //todo
    public void addLevel() {

    }

    //todo
    public void createNewClass() {

    }

    public List<String> getAllClasses() throws DBException {

        try {
            Connection connection = dbCreator.connectToDatabase();

            PreparedStatement stm = connection.prepareStatement("SELECT class_name FROM classes");

            ResultSet result = stm.executeQuery();
            List<String> resultList = new LinkedList<>();

            while (result.next()) {
                String className = result.getString("class_name");
                resultList.add(className);

            }

            return resultList;

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getAllClasses())");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getAllClasses()");

        }
    }
}
