package com.mobi.ecommerce.user;

import com.mobi.ecommerce.role.RoleType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(ur) > 0 THEN true ELSE false END FROM User_Role ur WHERE ur.role.name = :roleName")

    /*
    * SELECT COUNT(*) > 0
    FROM user_role ur
    JOIN role r ON ur.role_id = r.id
    WHERE r.name = 'ADMIN';
    * */
    boolean existsByRole(@Param("roleName") RoleType roleName);}
