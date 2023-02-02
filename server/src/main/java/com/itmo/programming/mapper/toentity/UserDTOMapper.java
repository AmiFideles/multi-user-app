package com.itmo.programming.mapper.toentity;

import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    User toUser(UserDTO userDTO);
}
