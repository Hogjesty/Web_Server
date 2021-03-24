package Server;

import Server.Http.HttpRequest;
import Server.Http.HttpResponse;
import Server.Http.Request;
import Server.Http.Response;
import Server.Processors.Processor;
import Server.Processors.ProcessorFabric;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void await() throws IOException {
        ServerSocket ss = null;

        ss = new ServerSocket(this.port, 1, InetAddress.getByName(Constants.SERVER_NAME));

        System.out.println("Server is waiting for requests on port " + port);

        boolean isShutDown = false;
        while (!isShutDown) {
            Socket socket = ss.accept();
            isShutDown = processReq(socket);
            socket.close();
        }
        ss.close();
    }

    private boolean processReq(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        Request req = new HttpRequest(in);
        Response res = new HttpResponse(out);

        System.out.println(req.getAsText());


        String uri = req.getURI();
        if (uri == null)
            return false;

        if (uri.equals(Constants.SHUTDOWN_COMMAND))
            return true;

        Processor proc = ProcessorFabric.createProcessor(uri);
        proc.process(req, res);

//        Request;
//        Response;
        return false;
    }

}
