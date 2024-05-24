package com.example.soapservice.mapper;

import com.example.guides.gs_producing_web_service.Task;
import com.example.soapservice.persistence.entity.TaskData;
import com.example.soapservice.service.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	TaskEntity taskToTaskEntity(Task restTask);
	TaskEntity taskDataToTaskEntity(TaskData taskData);
	Task taskEntityToTask(TaskEntity task);
	TaskData taskEntityToTaskData(TaskEntity task);
}
