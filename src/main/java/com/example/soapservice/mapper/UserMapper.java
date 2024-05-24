package com.example.soapservice.mapper;


import com.example.guides.gs_producing_web_service.User;
import com.example.soapservice.persistence.entity.UserData;
import com.example.soapservice.service.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserData userEntityToUserData(UserEntity user);
	User userEntityToUser(UserEntity userEntity);
	UserEntity userDataToUserEntity(UserData userData);
	UserEntity userToUserEntity(User user);
}
