package mardini.project.kaskos.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = secretKey.getBytes();
        String algorithm = MacAlgorithm.HS256.getName();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, algorithm);
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] bytes = secretKey.getBytes();
        ImmutableSecret immutableSecret = new ImmutableSecret<>(bytes);
        return new NimbusJwtEncoder(immutableSecret);
    }
}
