package Server.Http;

import Server.Constants;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpResponse implements Response {

    private final OutputStream out;

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    @Override
    public void sendStatRes(String resURI) throws IOException {
        byte[] bytes = new byte[Constants.BUFFER_SIZE];
        FileInputStream fis = null;

        try {

            File file = new File(Constants.WEB_ROOT, resURI);
            fis = new FileInputStream(file);
            String httpHeader = Constants.HTTP_HEADER;
            this.out.write(httpHeader.getBytes(StandardCharsets.UTF_8));
            int size;
            while (true) {
                size = fis.read(bytes, 0, bytes.length);
                if (size == -1) {
                    break;
                }
                this.out.write(bytes, 0, size);
            }
            out.flush();

        } catch (FileNotFoundException e) {
            sendError(404, "File Not Found");

        } finally {

            if (fis != null) {
                fis.close();
            }

        }
    }

    @Override
    public void sendHtml(String html) throws IOException {
        this.out.write(Constants.HTTP_HEADER.getBytes(StandardCharsets.UTF_8));
        this.out.write(html.getBytes(StandardCharsets.UTF_8));
        this.out.flush();
    }

    @Override
    public void sendError(int code, String msg) {
        String html = "<h1>" + msg + "<br>Error: " + code + "</h1>";
        String error = Constants.HTTP_ERROR_HEADER +
                "Content-Length: " + html.length() + "\r\n\r\n" + html;

        try {
            this.out.write(error.getBytes(StandardCharsets.UTF_8));
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
