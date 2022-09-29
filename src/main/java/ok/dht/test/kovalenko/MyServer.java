package ok.dht.test.kovalenko;

import ok.dht.ServiceConfig;
import one.nio.http.HttpServer;
import one.nio.http.HttpServerConfig;
import one.nio.http.HttpSession;
import one.nio.http.Request;
import one.nio.http.Response;
import one.nio.net.Session;
import one.nio.server.SelectorThread;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class MyServer extends HttpServer {

    public MyServer(HttpServerConfig config, Object... routers) throws IOException {
        super(config, routers);
    }

    public static void main(String[] args)
            throws IOException, ExecutionException, InterruptedException, TimeoutException {
        int port = 19234;
        String url = "http://localhost:" + port;
        ServiceConfig cfg = new ServiceConfig(
                port,
                url,
                Collections.singletonList(url),
                Paths.get("/home/pavel/IntelliJIdeaProjects/tables/data_bigtables/")
        );
        new MyService(cfg).start().get(1, TimeUnit.SECONDS);
        System.err.println("Socket is ready: " + url);
    }

    @Override
    public void handleDefault(Request request, HttpSession session) throws IOException {
        Response response = new Response(Response.BAD_REQUEST, Response.EMPTY);
        session.sendResponse(response);
    }

    @Override
    public synchronized void stop() {
        for (SelectorThread selectorThread : selectors) {
            for (Session session : selectorThread.selector) {
                session.close();
            }
        }
    }
}
