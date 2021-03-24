package Server.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest implements Request {

    private final InputStream in;
    private final String req;
    private final String uri;


    //-------------------------------------Constructors(CON)-----------------------------------------//

    public HttpRequest(InputStream in) throws IOException {
        this.in = in;
        this.req = convertStreamToString();
        this.uri = parseURI();
    }

    //---------------------------------------END_OF_CON-----------------------------------------------//
    //                                                                                                //
    //----------------------------------Private_Methods(PRIVY)----------------------------------------//

    private String parseURI() {
        if (this.req.isEmpty()) {
            return "";
        }
        int firstURIIndex = req.indexOf('/');
        int lastURIIndex = req.indexOf(" H");

        // "GET /ex?p=s%20s HTTP/1.1" - example
        return req.substring(firstURIIndex, lastURIIndex);
    }

    private String convertStreamToString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.in));

        StringBuilder fullReq = new StringBuilder();
        String line;

        while (true) {
            line = reader.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            fullReq.append(line);
            fullReq.append(System.getProperty("line.separator"));
        }


        return fullReq.toString();
    }

    //--------------------------------------END_OF_PRIVY----------------------------------------------//

    //------------------------------------Public_Methods(PUB)-----------------------------------------//
    @Override
    public String getParam(String name) {
        return null;
    }

    @Override
    public String getAsText() {
        return null;
    }

    @Override
    public String getURI() {
        return null;
    }
    //---------------------------------------END_OF_PUB-----------------------------------------------//

}
