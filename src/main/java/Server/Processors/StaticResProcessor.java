package Server.Processors;

import Server.Constants;
import Server.Http.Request;
import Server.Http.Response;

public class StaticResProcessor implements Processor{


    @Override
    public void process(Request req, Response res) {
        String uri = req.getURI();

        if (uri.isEmpty() || uri.equals("/")) {
            uri = Constants.ROOT;
        }
        res.sendStatRes(uri);
    }
}
