package com.cydeo.repository;


import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    //give me the roles based on description


    Role findByDescription(String description);
}
