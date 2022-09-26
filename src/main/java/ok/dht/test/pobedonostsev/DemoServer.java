package ok.dht.test.pobedonostsev;

import ok.dht.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public final class DemoServer {

    private static final Logger LOG = LoggerFactory.getLogger(DemoServer.class);

    private DemoServer() {
        // Only main method
    }

    public static void main(String[] args) throws Exception {
        int port = 19234;
        String url = "http://localhost:" + port;
        ServiceConfig cfg = new ServiceConfig(
                port,
                url,
                Collections.singletonList(url),
                Path.of("server")
        );
        DemoService service = new DemoService(cfg);
        service.start().get(1, TimeUnit.SECONDS);
        LOG.debug("Socket is ready: " + url);
    }
}
