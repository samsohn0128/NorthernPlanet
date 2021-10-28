package com.pyhu.northernplanet.db.repository;


import com.pyhu.northernplanet.db.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

  Users findByEmail(String email);

  Users findByUserId(int user_id);

  Optional<Users> findByOauthId(String oauthId);

  @Query(value = "select user_id from users where oauth_id=?;", nativeQuery = true)
  Long findUserIdByOauthId(int oauthId);
}
