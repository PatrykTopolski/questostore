import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.StudentController;

import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8030), 0);

        // set routes
        //server.createContext();
//        server.createContext("admin/showClasses", (HttpHandler) new AdminMentorsListHandler());
        server.createContext("/static", new Static());
        server.createContext("/student", new StudentController());
        server.createContext("/admin", new AdminController());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }

}
