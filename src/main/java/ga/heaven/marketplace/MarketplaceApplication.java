package ga.heaven.marketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
//import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "MARKETPLACE HEAVEN API",
                version = "0.0",
                description = "Marketplace cross-origin endpoints",
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                ),
                contact = @Contact(
                        name = "JD10 HEAVEN-DEVS TEAM",
                        email = "skypro@heaven.ga ",
                        url = "heaven.ga"
                )
        ),
        servers = {
                @Server(
                        description = "(local)",
                        url = "/"),
        }
)
public class MarketplaceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketplaceApplication.class);
    
    public static void main(String[] args) {
        startH2Server();
        SpringApplication.run(MarketplaceApplication.class, args);
    }
    
    private static void startH2Server() {
        try {
            org.h2.tools.Server h2Server = org.h2.tools.Server.createTcpServer().start();
            if (h2Server.isRunning(true)) {
                LOGGER.info("H2 server was started and is running.");
            } else {
                throw new RuntimeException("Could not start H2 server.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to start H2 server: ", e);
        }
    }
}
