package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<RoleDTO> listAllRoles() {

        //
      List<Role>roleList= roleRepository.findAll();

       return roleList;
    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}
