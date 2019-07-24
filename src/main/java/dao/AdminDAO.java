package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.items.Level;


import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;


    //todo
    public void addLevel() {

    }

    private int getClassId() throws DBException {

        String query = "SELECT class_id + 1 FROM classes ORDER BY my_id DESC LIMIT 1";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            int result = rs.getInt("class_name");
            dbCreator.connectToDatabase().close();
            return result;

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createNewClass()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in createNewClass()");
        }

    }

    //todo
    public void createNewClass(String className) throws DBException {

        String query = "INSERT INTO classes(class_id, user_id, class_name) VALUES (?,?,?)";

        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, getClassId());
            statement.setInt(2, 0);
            statement.setString(3, className);
            statement.executeUpdate();
            dbCreator.connectToDatabase().close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createNewClass()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in createNewClass()");
        }

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



    @Override
    public List<Level> getLevelList() throws SQLException {
        List<Level> levels = new ArrayList();
        Connection con = dbCreator.connectToDatabase();

        con.setAutoCommit(false);
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM level_of_exp;");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int maxValue = resultSet.getInt("max_value");

            Level level = new Level(id, name, maxValue);
            levels.add(level);

        }
        return levels;
    }
}
