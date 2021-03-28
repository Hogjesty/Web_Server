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

            String errorMsg = Constants.HTTP_ERROR_HEADER +
                    "Content-Length: 23\r\n\r\n" + "<h1>File Not Found</h1>";
            this.out.write(errorMsg.getBytes(StandardCharsets.UTF_8));
            this.out.flush();

        } finally {

            if (fis != null) {
                fis.close();
            }

        }
    }
}
