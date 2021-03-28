package Server.Servlet;

import java.util.Map;

public class ServletsMap {
    private Map<String, Servlet> servlets;

    public ServletsMap(Map<String, Servlet> servlets) {
        this.servlets = servlets;
    }

    public void callInit() {
        servlets.forEach((k, v) -> v.init());
    }

    public void callDestroy() {
        servlets.forEach((k, v) -> v.destroy());
    }

    public Servlet getServlet (String uri) {
        return servlets.get(uri);
    }
}
