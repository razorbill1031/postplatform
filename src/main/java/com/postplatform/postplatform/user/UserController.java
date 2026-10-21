package com.postplatform.postplatform.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
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

    //로그인
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(
                request.email(),
                request.password()
        );

        //로그인한 회원을 세션에 저장
        session.setAttribute("loginUserId", user.getId());

        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }

    public record LoginRequest(
            @NotBlank(message = "이메일은 필수 입력입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수 입력입니다.")
            String password
    ) {
    }

    public record LoginResponse(
            Long id,
            String email,
            String nickname
    ) {
    }

    //로그인 상태 확인
    @GetMapping("/me")
    public LoginResponse me(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        User user = userService.findById(loginUserId);

        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}