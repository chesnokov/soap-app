package com.example.soapservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "task")
public class TaskData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private UserData user;

	@Column(name = "name")
	private String name;

	@Column(name="description")
	private String description;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@Column(name = "dead_line")
	private LocalDate deadLine;
}
