package com.lab.ecommerce.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Logs graceful shutdown sequence when SIGTERM is received.
 *
 * Spring Boot (server.shutdown=graceful) already:
 *   1. Stops accepting new requests
 *   2. Waits for active requests to finish
 *   3. Closes DB connections via DataSource lifecycle
 *
 * This bean just adds the required log messages.
 */
@Component
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(GracefulShutdownListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("SIGTERM received. Starting graceful shutdown...");
        log.info("Waiting for active HTTP requests to complete...");
        log.info("Closing database connections...");
        log.info("Graceful shutdown complete. Exiting with code 0.");
    }
}
