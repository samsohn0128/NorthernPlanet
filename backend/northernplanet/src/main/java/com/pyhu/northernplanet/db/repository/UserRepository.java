package com.pyhu.northernplanet.db.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.User;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findByUserId(int user_id);

  Optional<User> findByOauthId(String oauthId);
}
