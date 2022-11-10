package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
      List<User> userList=userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String userName) {
        return userMapper.convertToDto(userRepository.findByUserName(userName));
    }

    @Override
    public void save(UserDTO user) {

        userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public void deleteByUserName(String userName) {
      userRepository.deleteByUserName(userName);
    }

    @Override
    public UserDTO update(UserDTO user) {
      // find the current user to get the id first to assign it to the updated one
        User user1=userRepository.findByUserName(user.getUserName());// has id
        // Map update user dto to entity object
        User convertedUser=userMapper.convertToEntity(user); // no id

        // set id to the converted object
        convertedUser.setId(user1.getId());
        // save the updated user in the db
        userRepository.save(convertedUser);
        return findByUserName(user.getUserName());


    }

    @Override
    public void delete(String userName) {
        //go to db and get that user with userName
        //change the isDeleted field to true
        // then save the object to db
       User user= userRepository.findByUserName(userName);
       user.setDeleted(true);
       userRepository.save(user);

    }
}
