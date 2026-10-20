package com.postplatform.postplatform.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String email, String password, String nickname) {

        //중복 메일 사용 유무 확인
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, nickname);

        return userRepository.save(user);
    }

    //로그인
    @Transactional(readOnly = true)
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidLoginException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidLoginException();
        }

        return user;
    }
}