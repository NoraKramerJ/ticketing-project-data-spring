package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;

public class RoleMapper {

private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //this method will convert RoleDTO to role entity
    public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto,Role.class);

    }


// this method will convert Role entity to RoleDto
    public RoleDTO convertToDto(Role entity){
return modelMapper.map(entity, RoleDTO.class);

    }
}
