package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.users.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class StudentController implements HttpHandler {


    Student student;


    public void handle(HttpExchange httpExchange) throws IOException {
        int id;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/students/achievements")) {
            } else {
                profile(httpExchange);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException in StudentController handle()");
        }

    }

    private void profile(HttpExchange httpExchange) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        String firstName = "Jacke";
        String lastName = "Placke";
        String phoneNumber = "9840392/3";
        String email = "jacek@placek";
        // fill the model with values
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNumber", phoneNumber);
        model.with("email", email);
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
