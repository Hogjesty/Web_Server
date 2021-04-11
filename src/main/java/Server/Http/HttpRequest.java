package Server.Http;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {

    private final String req;
    private final String uri;
    private final Map<String, String> params;


    public HttpRequest(InputStream in) throws IOException {
        this.req = convertStreamToString(in);
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

    private String convertStreamToString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder fullReq = new StringBuilder();
        loop(reader, fullReq);
        if (fullReq.indexOf("POST ") == 0) {
            // 223322 - content length
            byte[] bytes = new byte[10];
            int i = in.read(bytes, 0, 10);
            fullReq.append(new String(bytes, StandardCharsets.UTF_8));
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

    private void loop (BufferedReader reader, StringBuilder sb) throws IOException {
        String line;
        while (true) {
            line = reader.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
    }
}
