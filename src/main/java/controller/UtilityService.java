package controller;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class UtilityService {

    public static void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void sendRedirect(HttpExchange httpExchange, String url) throws IOException{
        httpExchange.getResponseHeaders().set("Location", url);
        httpExchange.sendResponseHeaders(303, -1);
    }
}
