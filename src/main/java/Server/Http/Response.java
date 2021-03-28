package Server.Http;

import java.io.IOException;

public interface Response {
    void sendStatRes(String resURI) throws IOException;
}
