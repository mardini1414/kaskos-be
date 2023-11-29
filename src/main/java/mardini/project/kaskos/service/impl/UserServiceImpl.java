package mardini.project.kaskos.service.impl;

import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.dto.UserRequest;
import mardini.project.kaskos.dto.UserResponse;
import mardini.project.kaskos.entity.User;
import mardini.project.kaskos.repository.UserRepository;
import mardini.project.kaskos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(UserRequest userRequest){
        boolean isExists = userRepository.existsByEmail(userRequest.getEmail());
        if(isExists){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User already exists");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        String password = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAllProjectedBy();
    }

    @Override
    public UserResponse getAuthenticatedUser(Authentication authentication) {
        return userRepository.findTop1ByEmail(authentication.getName());
    }


}
