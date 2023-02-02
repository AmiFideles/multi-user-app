package com.itmo.programming.mapper.todto;


import com.itmo.programming.dto.CoordinatesDTO;
import com.itmo.programming.model.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface CoordinatesMapper {
    CoordinatesMapper INSTANCE = Mappers.getMapper(CoordinatesMapper.class);

    CoordinatesDTO toDTO(Coordinates coordinates);

}
