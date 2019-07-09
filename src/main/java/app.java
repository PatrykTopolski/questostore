import com.sun.net.httpserver.HttpServer;
import controller.helpers.AdminMentorsListHandler;

import java.net.InetSocketAddress;

public class app {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("admin/showMentors", new AdminMentorsListHandler());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }

}
