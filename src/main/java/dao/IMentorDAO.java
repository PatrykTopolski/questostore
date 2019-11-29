package dao;


import model.users.Mentor;
import model.users.User;
import java.sql.SQLException;

public interface IMentorDAO {
    void addMentor(User user, Mentor mentor) throws DBException;
    void updateMentorByID(Mentor mentor) throws DBException;
    void updateMentorByFullName(Mentor mentor) throws DBException;
    Mentor getMentorById(int id) throws SQLException, DBException;
    Mentor getMentorByFullName(String firstName, String lastName) throws DBException;
}
