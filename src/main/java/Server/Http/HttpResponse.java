package Server.Http;

import java.io.OutputStream;

public class HttpResponse implements Response {

    private final OutputStream out;

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    @Override
    public void sendStatRes(String resURI) {

    }
}
