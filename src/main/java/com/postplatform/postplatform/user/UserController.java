package com.postplatform.postplatform.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@RequestBody SignupRequest request) {
        User user = userService.register(
                request.email(),
                request.password(),
                request.nickname()
        );

        return new SignupResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }

    public record SignupRequest(
            String email,
            String password,
            String nickname
    ) {
    }

    public record SignupResponse(
            Long id,
            String email,
            String nickname
    ) {
    }
}