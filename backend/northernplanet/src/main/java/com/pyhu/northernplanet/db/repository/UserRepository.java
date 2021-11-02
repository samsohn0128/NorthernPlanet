package com.pyhu.northernplanet.db.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  User findByUserId(int user_id);


  Optional<User> findByOauthId(String oauthId);

  @Query(value = "select user_id from users where oauth_id=?;", nativeQuery = true)
  Long findUserIdByOauthId(int oauthId);
}
