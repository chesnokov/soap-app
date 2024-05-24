package com.example.soapservice.service;


import com.example.soapservice.service.entity.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {
	List<TaskEntity> getAllTasks();
	Optional<TaskEntity> getTaskById(Long id);
	TaskEntity addTask(TaskEntity task);
	Optional<TaskEntity> updateTask(Long id, TaskEntity task);
	void deleteTask(Long id);
}
