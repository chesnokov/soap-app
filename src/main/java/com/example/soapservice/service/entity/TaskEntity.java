package com.example.soapservice.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskEntity {
	private Long id;

	private String name;

	private String description;

	private LocalDate creationDate;

	private LocalDate deadLine;
}
