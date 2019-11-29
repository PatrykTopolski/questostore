package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.LoginDAO;
import dao.SessionDAO;
import helpers.CookieHelper;
import helpers.DataParser;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.List;
import java.util.UUID;

public class LoginController implements HttpHandler {
    private LoginDAO loginDao = new LoginDAO();
    private SessionDAO sessionDao = new SessionDAO();
    private CookieHelper cookieHelper = new CookieHelper();


    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            if (cookieStr != null){
                String sessionId = cookieHelper.removeQuotationFromSessionId(cookieStr);
                try {
                    loginDao.deleteSession(sessionId);
                }catch (DBException exc){
                    System.out.println("DB exception caught in log out sequence, in handle method");
                }
                HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
                cookie.setMaxAge(0);
                cookie.setVersion(1);
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            }
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            String response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);
        }

        if (method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            //First element on list is login  second is password
            List<String> inputs = DataParser.parseFormDataToList(formData);

            if (loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).isPresent()){
                String userType = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getUserType();
                int userId = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getId();
                String url = "";
                url= "/" + userType;

                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                HttpCookie cookie = new HttpCookie("sessionId", randomUUIDString);  //cookie is version 1

                try{
                    sessionDao.addSession(randomUUIDString, userId);
                }catch (DBException exc ){
                    exc.printStackTrace();
                    System.out.println("DB exception caought in loginController");
                }
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                UtilityService.sendRedirect(httpExchange, url);
            } else {
                String response = "<html><body>" +
                        "<h1> Sorry there is no such user " +
                        "!</h1></body><html>";
                UtilityService.sendResponse(httpExchange, response);
            }
        }
    }
}
