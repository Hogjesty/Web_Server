package MyApp;

import MyApp.Web.NotesServlet;
import Server.Server;
import Server.Servlet.ServletsMap;

import java.io.IOException;


public class RunServer {
    public static void main(String[] args) {
        Server server = new Server(5555, prepareServletsMap());
        try {
            server.await();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static ServletsMap prepareServletsMap() {
        ServletsMap map = new ServletsMap();
        map.addServlet(NotesServlet.SERVLET_NAME, new NotesServlet());

        return map;
    }
}
