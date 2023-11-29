package mardini.project.kaskos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.dto.ApiResponse;
import mardini.project.kaskos.dto.MessageResponse;
import mardini.project.kaskos.dto.UserRequest;
import mardini.project.kaskos.dto.UserResponse;
import mardini.project.kaskos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid UserRequest userRequest){
        userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.builder().message("user added successfully").build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder().data(users).build());
    }

    @GetMapping(path = "authenticated")
    public ResponseEntity<ApiResponse> getAuthenticatedUser(Authentication authentication){
        UserResponse user = userService.getAuthenticatedUser(authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder().data(user).build());
    }
}
