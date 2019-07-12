package dao;


import model.users.User;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {

         DBCreator db = new DBCreator();
         db.connectToDatabase();
         db.executeStatement();


         UserDAO userDAO = new UserDAO();

         User user = userDAO.seeProfile(3);

         user.getLogin();


        } catch (SQLException e){
            e.printStackTrace();
        } catch (DBException e){
            e.printStackTrace();
        }
    }
}
