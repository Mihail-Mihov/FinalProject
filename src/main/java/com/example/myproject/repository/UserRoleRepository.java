package com.example.myproject.repository;

import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {


    UserRoleEntity findByRole(UserRoleEnum role);
}
