package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NegativeOrZero;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    //get users based on userName
    User findByUserName(String userName);
   @Transactional
   // used @Transactional with derived queries
   //@Modifying  used with JPQL and native queries
   void deleteByUserName(String userName);

  List<User> findByRoleDescriptionIgnoreCase(String description);
}
