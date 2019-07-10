package dao;

import model.users.Admin;
import model.users.Student;
import model.users.Mentor;
import model.users.User;
import java.sql.*;


public class UserDAO implements IUserDAO{
    //this class contains methods which allow to see user's profile

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method
    DBCreator dbCreator;

    public UserDAO(){
        dbCreator = new DBCreator();
    }


    //todo mentor and admin profile
    public User seeProfile(int id) throws SQLException {
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select usertype from users where id=? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();
        String userType = new String();
        if(result.next()){
            userType = result.getString("usertype");
        }

        if (userType.equals("codecooler")){
            System.out.println("i am student");
            Student student = getFullCodecoolerObject(id);
            return student;
        }else if(userType.equals(("mentor"))){
            System.out.println("im mentor");
            Mentor mentor = getFullMentor(id);
            return mentor;
        }else if (userType.equals("admin")){
            System.out.println("i am admin");
            Admin admin = getFullADmin(id);
            return admin;
        }

        return null;
    }


    //todo
    public void updateMyProfile() {

    }


    private Student getFullCodecoolerObject(int id) throws SQLException{
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  studentpersonals on users.id=studentpersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Student student;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");
            int classID = result.getInt("class_id");
            int experiencePoints = result.getInt("experience_points");
            int coolcoins = result.getInt("coolcoins");

            student = new Student(user_id, login, password, firstName, lastName,phoneNumber, email, address, classID, experiencePoints, coolcoins);
            return student;
        }
        return null;
    }

    private Mentor getFullMentor(int id) throws SQLException{
        // this method do the same as methods in mentor class?
        // getMentorById and getMentorByFullName?
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  mentorspersonals on users.id=mentorspersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Mentor mentor;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");

            mentor = new Mentor(user_id, login, password, firstName, lastName, phoneNumber, email, address);
            return mentor ;
        }
        return null;
    }

    private Admin getFullADmin(int id) throws SQLException{
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  mentorspersonals on users.id=mentorspersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Admin admin;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");

            admin = new Admin(user_id, login, password, firstName, lastName, phoneNumber, email, address);
            return admin ;
        }

        return null;
    }
}
