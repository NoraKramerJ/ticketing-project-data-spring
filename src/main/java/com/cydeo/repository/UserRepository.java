package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NegativeOrZero;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {


//if I pass true to the method, will return me all the users if deleted,
    // if I pass false to the method, will return me all the users if they are not deleted
    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);


    //get users based on userName
    User findByUserNameAndIsDeleted(String username,Boolean deleted);
   @Transactional
   // used @Transactional with derived queries
   //@Modifying  used with JPQL and native queries
   void deleteByUserName(String userName);

  List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, boolean deleted);
}
