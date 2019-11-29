package dao;

import model.items.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;

    public AdminDAO(){
        dbCreator = new DBCreator();
    }

    @Override
    public void addLevel(String name, int maxValue) throws DBException {
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("insert into level_of_exp (name, max_value) values (?, ?)");
            stm.setString(1, name);
            stm.setInt(2, maxValue);
            stm.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(int id)");
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
