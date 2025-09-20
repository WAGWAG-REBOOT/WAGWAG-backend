package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.User;
import com.noname.wagwag.repository.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    boolean existsByUserName(String userName);

    List<User> findByDongCode(String dongCode);
    List<User> findBySocialType(SocialType socialType);
    List<User> findByUserState(String userState);

    @Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
    List<User> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    @Query("SELECT u FROM User u WHERE u.dongCode = :dongCode AND u.userState = :userState")
    List<User> findByDongCodeAndUserState(@Param("dongCode") String dongCode, @Param("userState") String userState);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.socialType = :socialType")
    Long countBySocialType(@Param("socialType") SocialType socialType);
}



