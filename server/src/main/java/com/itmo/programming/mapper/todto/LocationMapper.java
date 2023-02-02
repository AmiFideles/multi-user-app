package com.itmo.programming.mapper.todto;


import com.itmo.programming.dto.LocationDTO;
import com.itmo.programming.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO toDTO(Location location);
}
