package com.itmo.programming.mapper.toentity;

import com.itmo.programming.dto.ColorDTO;
import com.itmo.programming.model.Color;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface ColorDTOMapper {
    ColorDTOMapper INSTANCE = Mappers.getMapper(ColorDTOMapper.class);

    Color toEntity(ColorDTO colorDTO);
}
