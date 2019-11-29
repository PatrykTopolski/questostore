package dao;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
         DBCreator db = new DBCreator();
         db.connectToDatabase();
         db.executeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
