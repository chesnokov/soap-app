package com.example.soapservice.endpoint;

import com.example.guides.gs_producing_web_service.AddUserRequest;
import com.example.guides.gs_producing_web_service.AddUserResponse;
import com.example.guides.gs_producing_web_service.DeleteUserRequest;
import com.example.guides.gs_producing_web_service.DeleteUserResponse;
import com.example.guides.gs_producing_web_service.GetUserByIdRequest;
import com.example.guides.gs_producing_web_service.GetUserByIdResponse;
import com.example.guides.gs_producing_web_service.GetUsersRequest;
import com.example.guides.gs_producing_web_service.GetUsersResponse;
import com.example.guides.gs_producing_web_service.UpdateUserRequest;
import com.example.guides.gs_producing_web_service.UpdateUserResponse;
import com.example.guides.gs_producing_web_service.User;
import com.example.soapservice.mapper.UserMapper;
import com.example.soapservice.persistence.entity.UserData;
import com.example.soapservice.persistence.repository.TaskRepository;
import com.example.soapservice.persistence.repository.UserRepository;
import com.example.soapservice.service.UserService;
import com.example.soapservice.service.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;

@Endpoint
@Getter
@Setter
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://example.com/guides/gs-producing-web-service";

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
	@ResponsePayload
	public GetUsersResponse getUsers(@RequestPayload GetUsersRequest request) {

		List<UserEntity> users = userService.getAllUsers();
		List<User> resultUsers = users.stream().
				map(userMapper::userEntityToUser).toList();
		GetUsersResponse response = new GetUsersResponse();
		response.getUser().addAll(resultUsers);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
	@ResponsePayload
	public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
		Optional<UserEntity> user = userService.getUserById(request.getId());
		GetUserByIdResponse response = new GetUserByIdResponse();
		user.ifPresent(userEntity -> response.getUser().add(userMapper.userEntityToUser(userEntity)));
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
	@ResponsePayload
	public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
		AddUserResponse response = new AddUserResponse();
		UserEntity userEntity = userMapper.userToUserEntity(request.getUser());
		userEntity = userService.addUser(userEntity);
		User resultUser = userMapper.userEntityToUser(userEntity);
		response.setUser(resultUser);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
	@ResponsePayload
	public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
		UserEntity user = userMapper.userToUserEntity(request.getUser());
		Optional<UserEntity> updatedUser = userService.updateUser(request.getId(), user);
		UpdateUserResponse response = new UpdateUserResponse();
		if(!updatedUser.isEmpty()) {
			User resultUser = userMapper.userEntityToUser(updatedUser.get());
			response.setUser(resultUser);
		}
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
	@ResponsePayload
	public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
		userService.deleteUser(request.getId());
		return new DeleteUserResponse();
	}
}
