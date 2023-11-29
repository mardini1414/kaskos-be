package mardini.project.kaskos.service;

import mardini.project.kaskos.dto.UserRequest;
import mardini.project.kaskos.dto.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    public void create(UserRequest userRequest);

    public List<UserResponse> findAll();

    public UserResponse getAuthenticatedUser(Authentication authentication);

}
