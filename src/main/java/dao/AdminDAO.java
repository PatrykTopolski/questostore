package dao;

import model.items.Artifact;
import model.items.Level;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;

    public AdminDAO(){
        dbCreator = new DBCreator();
    }


    //todo
    public void addLevel() {

    }
    //todo
    public void createNewClass() {

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
