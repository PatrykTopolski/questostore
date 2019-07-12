package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.MentorDAO;
import dao.UserDAO;
import model.users.Admin;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminController implements HttpHandler {
    List mentorsList = new ArrayList();
    MentorDAO mentorDao = new MentorDAO();
    UserDAO userDAO = new UserDAO();

    private Admin admin;

    public void handle(HttpExchange httpExchange) throws IOException {

        int id = 1;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/admin/classes")) {
            }

            if (uri.equals("/admin/levels")) {
            }

            if (uri.equals("/admin/mentors")) {
                showMentors(httpExchange);
            }

            if (uri.equals("/admin")) {
                showProfile(httpExchange, id);

            } else {
                this.showProfile(httpExchange, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IOException in StudentController handle()");
        }
    }

    private void showProfile(HttpExchange httpExchange, int id){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        User user = getUser(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        System.out.println("first name" + firstName);
        System.out.println("last name" + lastName);


        // fill the model with values
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNumber", phoneNumber);
        model.with("email", email);
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }



    private void showMentors(HttpExchange httpExchange) {
        // create a list with mentors from dao
        try {
            mentorsList = getMentorsNames(mentorDao.getAllMentors());
        }catch (DBException dbexc) {
            System.out.println("this is db exception");
        }

        // client's address
        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorList.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values

        model.with("listName", mentorsList);


        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        sendResponse(httpExchange, response);
    }




    private List<Mentor> getMentorsList(){
        try {
            mentorsList = mentorDao.getAllMentors();
        }catch (DBException exc) {
            System.out.println("This is DB Exception");
        }
        return mentorsList;
    }

    private List<String> getMentorsNames(List<Mentor> mentorsList){
        List<String> mentorsNames = new ArrayList<>();
        for (Mentor mentor: mentorsList
             ) {
            String firstName = mentor.getFirstName();
            String lastName = mentor.getLastName();
            String fullName = firstName + " " + lastName;
            mentorsNames.add(fullName);
        }
        return mentorsNames;

    }

    private void sendResponse(HttpExchange httpExchange, String response) {
       try{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();}
       catch (IOException IOExc) {
           System.out.println("Exception in admin controller");
       }
    }


    //todo should we throw some new exception here?
    private User getUser(int id){
        User user = null;
        try {
            user = userDAO.seeProfile(id);
        }catch (DBException dbExc){
            System.out.println("There was no such user in DB");

        }
        return user;
    }

    private void update() {

    }

    private void delete() {

    }
}





