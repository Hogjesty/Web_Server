package Server.Processors;

import Server.Http.Request;
import Server.Http.Response;

public interface Processor {
    void process(Request req, Response res);
}
