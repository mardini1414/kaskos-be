package mardini.project.kaskos.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    public String generateJwt(String email);
}
