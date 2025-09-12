package co.com.bancolombia.model.auth.gateways;

public interface AuthJwtGateway {

    boolean validate(String token);

    String extractClaim(String claimName, String token);
}
