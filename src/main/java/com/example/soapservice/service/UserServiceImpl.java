package com.example.soapservice.service;

import com.example.soapservice.mapper.UserMapper;
import com.example.soapservice.persistence.entity.TaskData;
import com.example.soapservice.persistence.entity.UserData;
import com.example.soapservice.persistence.repository.TaskRepository;
import com.example.soapservice.persistence.repository.UserRepository;
import com.example.soapservice.service.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final UserMapper userMapper;


	@Override
	public List<UserEntity> getAllUsers() {
		List<UserData> userData = userRepository.findAll();
		return userData.stream()
				.map( userMapper::userDataToUserEntity).toList();
	}

	@Override
	public Optional<UserEntity> getUserById(Long id) {
		Optional<UserData> userData = userRepository.findById(id);
		return userData.map(userMapper::userDataToUserEntity);
	}

	@Override
	public UserEntity addUser(UserEntity user) {
		UserData userData = userMapper.userEntityToUserData(user);
		final UserData saveduserData = userRepository.save(userData);
		List<TaskData> tasks = userData.getTasks().stream().map(t ->  {
			t.setUser(saveduserData);
			return taskRepository.save(t); }).toList();

		saveduserData.getTasks().clear();
		saveduserData.getTasks().addAll(tasks);

		UserData resultUserData = userRepository.save(saveduserData);
		return userMapper.userDataToUserEntity(resultUserData);
	}

	@Override
	public Optional<UserEntity> updateUser(Long id, UserEntity user) {
		Optional<UserData> userData = userRepository.findById(id);
		if(userData.isEmpty()) {
			return Optional.empty();
		} else {
			final UserData updatedUser = userMapper.userEntityToUserData(user);
			List<TaskData> tasks = updatedUser.getTasks().stream().map(t ->  {
				t.setUser(updatedUser);
				return taskRepository.save(t); }).toList();

			updatedUser.getTasks().clear();
			updatedUser.getTasks().addAll(tasks);

			UserData savedUser = userRepository.save(updatedUser);
			UserEntity resultUser = userMapper.userDataToUserEntity(savedUser);
			return Optional.ofNullable(resultUser);
		}
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
