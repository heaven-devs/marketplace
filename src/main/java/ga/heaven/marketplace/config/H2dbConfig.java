package ga.heaven.marketplace.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2dbConfig {
    @Value("${db.port}")
    private String h2TcpPort;
    
    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * @return h2 db Server
     * @throws SQLException if something went wrong during startup the server.
     * @see Server
     */
    @Bean
//            (initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }
}
