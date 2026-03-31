package com.roomease.Mapper;

import com.roomease.DTO.OauthUserDto;
import com.roomease.Entity.OauthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OauthUserMapper {

    OauthUserDto toDto(OauthUser user);
}
