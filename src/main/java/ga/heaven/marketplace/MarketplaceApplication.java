package ga.heaven.marketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
  public static void main(String[] args) {
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
