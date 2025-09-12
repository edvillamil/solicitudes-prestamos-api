package co.com.bancolombia.api.config;

//import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Decoders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtDecoderConfig {

    // Puedes leerlo de application.yaml con @Value("${jwt.secret}")
    private final String secretBase64Url = "OauQo3_8TS-2gngE4XDGRHRKIVxKfG4-rF7_JwIZ_Ig";

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        byte[] secretBytes = Decoders.BASE64URL.decode(secretBase64Url);
        SecretKeySpec key = new SecretKeySpec(secretBytes, "HmacSHA256");
        return NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)   // HS256/HS384/HS512 según tu token
                .build();
    }
}
