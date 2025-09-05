package co.com.bancolombia.api.config;

import co.com.bancolombia.model.auth.gateways.AuthJwtGateway;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthJwtGateway {

    @Value("${jwt.secret:OauQo3_8TS-2gngE4XDGRHRKIVxKfG4-rF7_JwIZ_Ig}")
    private String secret;
    @Value("${jwt.expiration:3600000}")
    private Integer expiration;

    private static final Logger log = Logger.getLogger(JwtProvider.class.getName());

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }

    private io.jsonwebtoken.Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractClaim(String claimName, String token) {
        return parse(token).get(claimName, String.class);
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            log.severe("token expired");
        } catch (UnsupportedJwtException e) {
            log.severe("token unsupported");
        } catch (MalformedJwtException e) {
            log.severe("token malformed");
        } catch (SignatureException e) {
            log.severe("bad signature");
        } catch (IllegalArgumentException e) {
            log.severe("illegal args");
        }
        return false;
    }

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}