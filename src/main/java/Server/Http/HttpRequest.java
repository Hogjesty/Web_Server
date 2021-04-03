package Server.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {

    private final InputStream in;
    private final String req;
    private final String uri;
    private final Map<String, String> params;


    public HttpRequest(InputStream in) throws IOException {
        this.in = in;
        this.req = convertStreamToString();
        this.uri = parseURI();
        this.params = addParamsToMap();
    }


    @Override
    public String getParam(String name) {
        return this.params.get(name);
    }

    @Override
    public String getAsText() {
        return this.req;
    }

    @Override
    public String getURI() {
        return this.uri;
    }


    private String parseURI() {
        if (this.req.isEmpty()) {
            return "";
        }
        // "GET /ex?p=s%20s HTTP/1.1" - example
        return req.substring(req.indexOf('/'), req.indexOf(" H"));
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

    private Map<String, String> addParamsToMap() {
        Map<String, String> map = new HashMap<>();

        if (!this.req.contains("GET ") || !this.uri.contains("?"))
            return map;


        String params = this.uri.substring(this.uri.indexOf('?') + 1);
        String[] keyValuePairs = params.split("&");
        for (String str : keyValuePairs) {
            String[] keyValuePair = str.split("=");
            if (keyValuePair.length == 2) {
                String key = keyValuePair[0];
                String value = keyValuePair[1];
                map.put(key, value);
            }
        }
        return map;
    }

}
