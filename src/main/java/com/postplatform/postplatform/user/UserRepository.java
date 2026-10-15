package com.postplatform.postplatform.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //전달받은 메일 가진 회원이 존재하는지 확인 -> 중복 메일 확인하는데 사용
    boolean existsByEmail(String email);
}