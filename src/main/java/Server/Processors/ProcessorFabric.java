package Server.Processors;

import Server.Constants;
import Server.Servlet.ServletsMap;

public class ProcessorFabric {


    public static Processor createProcessor(String uri) {
        Processor proc;
        if(uri.startsWith(Constants.SERVLET_STRING)) {
            proc = new ServletProcessor();
        }
        else {
            proc = new StaticResProcessor();
        }
        return proc;
    }
}
