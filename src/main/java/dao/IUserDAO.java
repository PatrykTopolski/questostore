package dao;

import model.users.User;
import java.sql.SQLException;


public interface IUserDAO {
    //this interface contains methods which allow to see user's profile

    User seeProfile(int id) throws DBException;
    void updateMyProfile();

}
