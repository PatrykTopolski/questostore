package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import helpers.CookieHelper;
import helpers.DataParser;
import model.items.Artifact;
import model.items.Quest;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.io.*;
import java.util.List;
import java.util.Map;

public class StudentController implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private CookieHelper cookieHelper = new CookieHelper();
    private QuestDAO questDAO = new QuestDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private StudentDAO studentDAO = new StudentDAO();


    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/student/artifacts")) {
                showArtifacts(httpExchange);
            }
            else if (uri.equals("/student/quests")) {
                showQuests(httpExchange);
            }
            else {
                profile(httpExchange);
            }
        } catch (IOException e) {
            System.out.println("IOException in StudentController");
        } catch (DBException e) {
            System.out.println("DBException in StudentController");
        } catch (Exception e) {
            System.out.println("Unidentified Exception in StudentController");
        }
    }

    private void showQuests(HttpExchange httpExchange) throws DBException, IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/quests.twig");
        JtwigModel model = JtwigModel.newModel();
        QuestDAO questDAO = new QuestDAO();
        List<Quest> questList = questDAO.getQuestsList();
        model.with("questList", questList);
        String response = template.render(model);
        UtilityService.sendResponse(httpExchange, response);
    }

    private void showArtifacts(HttpExchange httpExchange) throws DBException, IOException {
        String method = httpExchange.getRequestMethod();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/artifacts.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Artifact> artifactList = artifactDAO.getArtifactsList();
        int userId = cookieHelper.getUserIdBySessionID(httpExchange);
        User user = userDAO.seeProfile(userId);
        int userCoins = user.getAmountOfCoins();

        model.with("coins", userCoins);
        model.with("artifactList", artifactList);
        String response = template.render(model);
        if (method.equals("POST")) { buyArtifact(httpExchange); }
        UtilityService.sendResponse(httpExchange, response);
    }

    private void buyArtifact(HttpExchange httpExchange) throws IOException, DBException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = DataParser.parseFormData(formData);
        int artifactId = Integer.parseInt(inputs.get("artifact_id").toString());
        Artifact artifact = artifactDAO.getArtifact(artifactId);
        int artifactPrice = artifact.getPrice();
        int userId = cookieHelper.getUserIdBySessionID(httpExchange);
        User user = userDAO.seeProfile(userId);
        int userCoins = user.getAmountOfCoins();

        if (userCoins >= artifactPrice){
            artifactDAO.addUserArtifact(userId, artifactId);
            int newCoins = user.getAmountOfCoins() - artifact.getPrice();
            studentDAO.updateCoins(userId, newCoins);
        }
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
        String address = student.getAddress();

        int room = student.getRoomID();

        List<Quest> completedQuests = questDAO.getUsersQuests(userId);
        List<Artifact> purchasedArtifacts = artifactDAO.getUsersArtifacts(userId);

        model.with("purchasedArtifacts", purchasedArtifacts);
        model.with("completedQuests", completedQuests);
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("address", address);
        model.with("phoneNumber", phoneNumber);
        model.with("email", email);
        model.with("coolcoins", coolcoins);
        model.with("experience_points", experience);
        model.with("class", room);
        String response = template.render(model);
        UtilityService.sendResponse(httpExchange, response);
    }

}
