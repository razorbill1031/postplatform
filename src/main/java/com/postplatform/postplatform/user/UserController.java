package com.postplatform.postplatform.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
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
            @NotBlank(message = "이메일은 필수 입력입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수 입력입니다.")
            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
            String password,

            @NotBlank(message = "닉네임은 필수 입력입니다.")
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