package Server.Http;

public interface Request {

    String getParam(String name);

    String getAsText();

    String getURI();
}
