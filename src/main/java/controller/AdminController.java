package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAO;
import dao.DBException;
import dao.MentorDAO;
import dao.UserDAO;
import model.items.Level;
import model.users.Admin;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

public class AdminController implements HttpHandler {
    List mentorsList = new ArrayList();
    MentorDAO mentorDao = new MentorDAO();
    UserDAO userDAO = new UserDAO();

    private Admin admin;

    public void handle(HttpExchange httpExchange) throws IOException {

        int id = 1;
        try {
            String uri = httpExchange.getRequestURI().toString();
            String[] parsedUri = parseResponseURi(uri);

            if (uri.equals("/admin/classes")) {
            }

            if (uri.equals("/admin/levels")) {
                showLevels(httpExchange);
            }

            if (uri.equals("/admin/mentors")) {
                showMentors(httpExchange);
            }

            if (uri.equals("/admin")) {
                showProfile(httpExchange, id);

            }
            if(uri.equals("/admin/createMentor")){
                addNewMentor(httpExchange);
            }
            if (parsedUri.length > 2 && parsedUri[2].equals("update")){
                updateProfile(parsedUri[3], httpExchange);
            }
            else {
                this.showProfile(httpExchange, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IOException in AdminController handle()");
        }
    }

    private void showLevels(HttpExchange httpExchange) throws IOException {
        String response = "";


        AdminDAO adminDAO = new AdminDAO();
        List<Level> levels = new ArrayList<>();
        try {
            levels = adminDAO.getLevelList();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }


        System.out.println("name" + levels.get(0).getName());


        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/html/admin/levelsPage.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values;
        model.with("levels", levels);


        System.out.println("fillet model with data");
        // render a template to a string
        response = template.render(model);

        System.out.println("model render complete ");
        // send the results to a the client



        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private void showProfile(HttpExchange httpExchange, int id){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        User user = getUser(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        System.out.println("admin first name" + firstName);
        System.out.println("admin last name" + lastName);


        // fill the model with values
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNum", phoneNumber);
        model.with("email", email);
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }



    private void showMentors(HttpExchange httpExchange) {
        // create a list with mentors from dao
        try {
            mentorsList = mentorDao.getAllMentors();
        }catch (DBException dbexc) {
            System.out.println("this is db exception");
        }

        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorList.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentorsList);

        String response = template.render(model);
        sendResponse(httpExchange, response);
    }




    private List<Mentor> getMentorsList(){
        try {
            mentorsList = mentorDao.getAllMentors();
        }catch (DBException exc) {
            System.out.println("This is DB Exception caught in Admin DAO");
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

    private void addNewMentor(HttpExchange httpExchange)throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")){
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/admin/createUpdateMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            response = template.render(model);
            sendResponse(httpExchange, response);

        }

        if (method.equals("POST")){
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);

            User user = null;
            Mentor mentor = null;



            String name = inputs.get("name");
            String surname = inputs.get("surname");
            String login = inputs.get("login");
            String password = inputs.get("password");
            String email = inputs.get("email");
            String adress = inputs.get("adress");
            String phone = inputs.get("phone");
            String mentorsClass = inputs.get("classes");
            System.out.println(mentorsClass);

            user = new Mentor(login, password, "mentor");
            //default value of coolcoins and experience points is 0 as we create new student
            mentor = new Mentor(0, login, password, name, surname, phone, email, adress);


            try {
                mentorDao.addMentor(user, mentor);
            }catch (DBException dbexc){
                dbexc.printStackTrace();
                System.out.println("db exception caught in admin controller in database add mentor ");
            }


        String url = "/admin/mentors";
        httpExchange.getResponseHeaders().set("Location", url);
        httpExchange.sendResponseHeaders(303, -1);

        }
    }

    private void delete() {

    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String[] parseResponseURi(String uri){


        System.out.println("PARSING DATA");
        String[] splitedUri = uri.split("/");

        for (String element: splitedUri
             ) {
            System.out.println(element);
        }
        return splitedUri;

    }


    private void updateProfile(String id, HttpExchange httpExchange) throws IOException{
        System.out.println("PROFILE UPDATE" + id);

        String response = "";
        String method = httpExchange.getRequestMethod();


        if (method.equals("GET")){
            Mentor mentor = new Mentor("null", "null", "null");
            try {

                mentor = userDAO.getFullMentor(Integer.parseInt(id));

            }catch (DBException e){
                e.printStackTrace();
            }
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/admin/UpdateMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            model.with("firstName", mentor.getFirstName());
            model.with("lastName", mentor.getLastName());
            model.with("login", mentor.getLogin());
            model.with("password", mentor.getPassword());
            model.with("phone", mentor.getPhoneNum());
            model.with("email", mentor.getEmail());
            model.with("address", mentor.getAddress());

            response = template.render(model);
            sendResponse(httpExchange, response);

        }

        if (method.equals("POST")){
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);

            User user = null;
            Mentor mentor = null;



            String name = inputs.get("name");
            String surname = inputs.get("surname");
            String login = inputs.get("login");
            String password = inputs.get("password");
            String email = inputs.get("email");
            String adress = inputs.get("adress");
            String phone = inputs.get("phone");
            String mentorsClass = inputs.get("classes");
            System.out.println(mentorsClass);


            mentor = new Mentor(Integer.valueOf(id), login, password, name, surname, phone, email, adress);


            try {
                mentorDao.updateMentorByID(mentor);
            }catch (DBException dbexc){
                dbexc.printStackTrace();
                System.out.println("db exception caught in admin controller in database add mentor ");
            }


            String url = "/admin/mentors";
            httpExchange.getResponseHeaders().set("Location", url);
            httpExchange.sendResponseHeaders(303, -1);

        }
    }

}





