package controller.helpers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.MentorDAO;
import model.users.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class AdminController implements HttpHandler {
    MentorDAO dao = new MentorDAO();
    List<Mentor> mentorsList = new ArrayList<>();

    public void handle(HttpExchange httpExchange) throws IOException {

        // create a list with mentors from dao
        mentorsList = getMentorsList();

        // client's address
        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorList.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        for (Mentor mentor : mentorsList){
            String firstName = mentor.getFirstName();
            String lastName = mentor.getLastName();
            String fullName = firstName + lastName;
            model.with("fullName", fullName);
        }


        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        sendResponse(httpExchange, response);


    }

    private List<Mentor> getMentorsList(){
        try {
            mentorsList = dao.getAllMentors();
        }catch (DBException exc) {
            System.out.println("This is DB Exception");
        }
        return mentorsList;
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
