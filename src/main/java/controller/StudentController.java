package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import helpers.CookieHelper;
import model.items.Artifact;
import model.items.Quest;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class StudentController implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private CookieHelper cookieHelper = new CookieHelper();
    private QuestDAO questDAO = new QuestDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();


    public void handle(HttpExchange httpExchange) throws IOException {
        int id;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/student/artifacts")) {
                artifacts(httpExchange);
            } if (uri.equals("/student/quests")){
                quests(httpExchange);
            } else {
                profile(httpExchange);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException in StudentController");
        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("DBException in StudentController");
        }

    }

    private void quests(HttpExchange httpExchange) throws DBException, IOException {


        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/quests.twig");
        JtwigModel model = JtwigModel.newModel();

        QuestDAO questDAO = new QuestDAO();
        List<Quest> questList = questDAO.getQuestsList();

        model.with("questList", questList);


        String response = template.render(model);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void artifacts(HttpExchange httpExchange) throws DBException, IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/artifacts.twig");
        JtwigModel model = JtwigModel.newModel();

        ArtifactDAO artifactDAO = new ArtifactDAO();
        List<Artifact> artifactList = artifactDAO.getArtifactsList();


        model.with("artifactList", artifactList);


        String response = template.render(model);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();


    }

    private void profile(HttpExchange httpExchange) throws DBException, IOException {
        int userId = cookieHelper.getUserIdBySessionID(httpExchange);
        User student = userDAO.seeProfile(userId);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/profile.twig");
        JtwigModel model = JtwigModel.newModel();
        int coolcoins = student.getAmountOfCoins();
        int experience = student.getLvlOfExp();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String phoneNumber = student.getPhoneNum();
        String email = student.getEmail();



        List<Quest> completedQuests = questDAO.getUsersQuests(userId);
        List<Artifact> purchasedArtifacts = artifactDAO.getUsersArtifacts(userId);

        model.with("purchasedArtifacts", purchasedArtifacts);
        model.with("completedQuests", completedQuests);
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNumber", phoneNumber);
        model.with("email", email);
        model.with("coolcoins", coolcoins);
        model.with("experience_points", experience);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    private void update() {

    }

    private void delete() {

    }


}
