package dao;

import java.sql.Connection;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;

    AdminDAO(){
        dbCreator = new DBCreator();
    }


    //todo
    public void addLevel() {

    }
    //todo
    public void createNewClass() {

    }
}
