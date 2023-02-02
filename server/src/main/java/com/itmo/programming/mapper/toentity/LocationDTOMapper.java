package com.itmo.programming.mapper.toentity;

import com.itmo.programming.dto.LocationDTO;
import com.itmo.programming.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface LocationDTOMapper {
    LocationDTOMapper INSTANCE = Mappers.getMapper(LocationDTOMapper.class);

    Location toEntity(LocationDTO locationDTO);
}
