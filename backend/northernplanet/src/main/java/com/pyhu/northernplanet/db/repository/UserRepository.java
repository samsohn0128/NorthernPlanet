package com.pyhu.northernplanet.db.repository;


import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUserId(Long user_id);


  Optional<User> findByOauthId(String oauthId);

  @Query(value = "select user_id from users where oauth_id=?;", nativeQuery = true)
  Long findUserIdByOauthId(String oauthId);

  @Modifying
  @Transactional
  @Query(value="UPDATE user SET name=?2 WHERE user_id=?1",nativeQuery=true)
  void updateUserNameByUserId(Long userId, String name);
}
