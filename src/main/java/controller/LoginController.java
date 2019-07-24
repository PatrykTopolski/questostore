package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.LoginDAO;
import dao.SessionDAO;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginController implements HttpHandler {

    private LoginDAO loginDao = new LoginDAO();
    private SessionDAO sessionDao = new SessionDAO();


    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        //String uriStr = httpExchange.getRequestURI().toString();
        //this usriStr can be use to debug

        if (method.equals("GET")) {
            //check again is any cookie and destroy it
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            if (cookieStr != null){

                String sessionId = removeQuotationFromSessionId(cookieStr);

                System.out.println("cookie str in removing sq: " + cookieStr);
                try {
                    loginDao.deleteSession(sessionId);
                }catch (DBException exc){
                    System.out.println("DB exception caought in log out sequence, in handle method");
                }

                //delete cookie
                HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
                cookie.setMaxAge(0);
                cookie.setVersion(1);
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            }

            //render login template
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            String response = template.render(model);


            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }



        if (method.equals("POST")){

            //get data from form
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            List<String> parsedForm = parseFormData(formData);

            //First element on list is login  second is password
            List<String> inputs = this.parseFormData(formData);
            System.out.println(inputs);

            //check is user in database
            if (loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).isPresent()){
                System.out.println("user exist");
                String userType = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getUserType();
                int userId = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getId();
                String url = "";
                url= "/" + userType;

                //create cookie with session ID
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                HttpCookie cookie = new HttpCookie("sessionId", randomUUIDString);  //cookie is version 1


                //save sessionId to sessions table
                try{
                    sessionDao.addSession(randomUUIDString, userId);
                }catch (DBException exc ){
                    exc.printStackTrace();
                    System.out.println("DB exception caought in loginController");
                }


                //prepare response with redirect to other page
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                httpExchange.getResponseHeaders().set("Location", url);
                httpExchange.sendResponseHeaders(303, -1);

            }

            else {
                //todo make template with info about wrong user

                String response = "<html><body>" +
                        "<h1> Sorry there is no such user " +
                        "!</h1></body><html>";

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

        }

    }

    private String removeQuotationFromSessionId(String cookieString){
        String[] cookieValues = cookieString.split("=");
        String sessionIdwrong = cookieValues[1].trim();
        StringBuilder sb = new StringBuilder(sessionIdwrong);
        sb.deleteCharAt(sessionIdwrong.length()-1);
        sb.deleteCharAt(0);
        String sessionId = sb.toString();
        return sessionId;

    }

    private List<String> parseFormData(String formData) throws UnsupportedEncodingException {
        List<String> result = new ArrayList<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            result.add(value);
        }
        return result;
    }


    private void update() {

    }

    private void delete() {

    }
}
