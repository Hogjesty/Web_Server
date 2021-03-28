package Server;

public class Constants {
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    public static final String SERVER_NAME = "127.0.0.1";
    public static final String SERVLET_STRING = "/servlet/";
    public static final String ROOT = "/index.html";
    public static final String WEB_ROOT = System.getProperty("user.dir") + "/src/main/resources";
    public static final int BUFFER_SIZE = 1024;
    public static final String HTTP_HEADER = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
    public static final String HTTP_ERROR_HEADER =
            "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\n";
//    public static final
//    public static final
//    public static final
//    public static final
//    public static final
//    public static final
//    public static final
//    public static final
}
