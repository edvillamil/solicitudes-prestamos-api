package co.com.bancolombia.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "microservices.usuarios")
public class UsuariosMsProperties {
    /**
     * Base URL del microservicio de usuarios, ej: http://usuarios-ms:8080/api
     */
    private String baseUrl = "http://localhost:8081/api";

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
}

