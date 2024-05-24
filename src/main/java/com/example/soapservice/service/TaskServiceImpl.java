package com.example.soapservice.service;


import com.example.soapservice.mapper.TaskMapper;
import com.example.soapservice.persistence.entity.TaskData;
import com.example.soapservice.persistence.repository.TaskRepository;
import com.example.soapservice.service.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;
	@Override
	public List<TaskEntity> getAllTasks() {
		List<TaskData> taskData = taskRepository.findAll();
		return taskData.stream()
				.map( taskMapper::taskDataToTaskEntity).toList();
	}

	@Override
	public Optional<TaskEntity> getTaskById(Long id) {
		Optional<TaskData> taskData = taskRepository.findById(id);
		return taskData.map(taskMapper::taskDataToTaskEntity);
	}

	@Override
	public TaskEntity addTask(TaskEntity task) {
		TaskData taskData = taskMapper.taskEntityToTaskData(task);
		taskData = taskRepository.save(taskData);
		return taskMapper.taskDataToTaskEntity(taskData);
	}

	@Override
	public Optional<TaskEntity> updateTask(Long id, TaskEntity task) {
		Optional<TaskData> taskData = taskRepository.findById(id);
		if(taskData.isEmpty()) {
			return Optional.empty();
		} else {
			TaskData updatedTask = taskMapper.taskEntityToTaskData(task);
			updatedTask.setId(id);
			updatedTask = taskRepository.save(updatedTask);
			return Optional.ofNullable(taskMapper.taskDataToTaskEntity(updatedTask));
		}
	}

	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
}
