package Server.Processors;

import Server.Constants;
import Server.Http.Request;
import Server.Http.Response;
import Server.Servlet.Servlet;
import Server.Servlet.ServletsMap;

public class ServletProcessor implements Processor {
    private ServletsMap map;

    public ServletProcessor(ServletsMap map) {
        this.map = map;
    }

    @Override
    public void process(Request req, Response res) {
        String servletName = parseServletName(req.getURI());
        Servlet servlet = map.getServlet(servletName);
        if (servlet == null) return;
        servlet.service(req, res);
    }

    private String parseServletName(String uri) {
        String name = uri.substring(uri.indexOf(Constants.SERVLET_STRING));
        if (name.contains("?")) {
            name = name.substring(0, name.indexOf("?"));
        }
        return name;
    }
}
