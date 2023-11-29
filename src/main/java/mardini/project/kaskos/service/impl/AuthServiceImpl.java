package mardini.project.kaskos.service.impl;

import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.repository.UserRepository;
import mardini.project.kaskos.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    @Override
    public String generateJwt(String email) {
        MacAlgorithm algorithm = MacAlgorithm.HS256;
        Instant now = Instant.now();
        Instant oneDay = now.plusSeconds(60 * 60 * 24);
        JwsHeader jwsHeader = JwsHeader.with(algorithm).build();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(oneDay)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
    }
}
