package  dao;

import java.sql.SQLException;
import model.users.Student;
import model.users.User;
import java.util.List;

public interface IStudentDAO {

    //this interface contains methods to create and update codecooler
    // and show his level

    void createStudent(User user, Student codecooler) throws DBException;
    List<Student> getStudentListFromRoom(int roomId) throws DBException; //what about this exception?
    int getExperiencePoints(int id) throws SQLException, DBException;
}
