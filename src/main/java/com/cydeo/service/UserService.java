package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

 List<UserDTO> listAllUsers();

 UserDTO findByUserName( String userName);
 void save(UserDTO user);
 void deleteByUserName( String userName);
 UserDTO update(UserDTO user);

 //created new method delete to save the object in the db and to only delete on the UI part
 void delete(String userName);

    List<UserDTO> listAllByRole(String role);
}
