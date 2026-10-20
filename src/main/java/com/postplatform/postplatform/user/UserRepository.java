package com.postplatform.postplatform.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //전달받은 메일 가진 회원이 존재하는지 확인 -> 중복 메일 확인하는데 사용
    boolean existsByEmail(String email);

    //이메일로 회원 조회
    Optional<User> findByEmail(String email);
}