package dao;

import model.users.User;

import java.util.ArrayList;
import java.util.Optional;

public interface ILoginDAO {


    Optional<User> getUserByLogin(String login, String password);
}
