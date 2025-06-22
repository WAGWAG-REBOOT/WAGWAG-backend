package com.noname.wagwag.repository;

import com.noname.wagwag.repository.entity.User;
import com.noname.wagwag.repository.entity.UserRole;
import com.noname.wagwag.repository.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser(User user);
}


