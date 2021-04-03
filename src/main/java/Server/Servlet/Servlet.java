package Server.Servlet;

import Server.Http.Request;
import Server.Http.Response;

import java.io.IOException;

public interface Servlet {
    void init();
    void destroy();
    void service(Request req, Response res) throws IOException;
}
