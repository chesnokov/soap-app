package com.example.soapservice.persistence.repository;

import com.example.soapservice.persistence.entity.TaskData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskData, Long> {
}
