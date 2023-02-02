package com.itmo.programming.mapper.toentity;

import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper(uses = {ColorDTOMapper.class, CoordinatesDTOMapper.class, LocationDTOMapper.class, UserDTOMapper.class})
public interface PersonDTOMapper {
    PersonDTOMapper INSTANCE = Mappers.getMapper(PersonDTOMapper.class);

    @Mapping(source = "personDTO.coordinatesDTO", target = "coordinates")
    @Mapping(source = "personDTO.locationDTO", target = "location")
    @Mapping(source = "personDTO.colorDTO", target = "color")
    @Mapping(source = "personDTO.userDTO", target = "user")
    Person toEntity(PersonDTO personDTO);

}
