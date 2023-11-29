package mardini.project.kaskos.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mardini.project.kaskos.entity.User;
import mardini.project.kaskos.repository.UserRepository;
import mardini.project.kaskos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.UUID;

public class OauthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        saveUser(oAuth2User);
        String email = oAuth2User.getAttribute("email");
        String token = authService.generateJwt(email);
        response.sendRedirect("http://localhost:5173/oauth/google?token=" + token);
    }

    private void saveUser(OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        boolean isExists = userRepository.existsByEmail(email);
        if (!isExists) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(UUID.randomUUID().toString());
            userRepository.save(user);
        }
    }
}
