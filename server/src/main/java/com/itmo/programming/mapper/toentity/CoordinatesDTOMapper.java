package com.itmo.programming.mapper.toentity;

import com.itmo.programming.dto.CoordinatesDTO;
import com.itmo.programming.model.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface CoordinatesDTOMapper {
    CoordinatesDTOMapper INSTANCE = Mappers.getMapper(CoordinatesDTOMapper.class);

    Coordinates toEntity(CoordinatesDTO coordinatesDTO);
}
