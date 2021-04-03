package Server.Http;

import java.io.IOException;

public interface Response {
    void sendStatRes(String resURI) throws IOException;
    void sendHtml (String html) throws IOException;
    void sendError (int code, String msg);
}
