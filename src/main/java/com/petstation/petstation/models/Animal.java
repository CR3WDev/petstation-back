package com.petstation.petstation.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "animal")
@Entity
public class Animal {
	
	   	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    private String name;
	    private String description;

		@OneToOne
		@JoinColumn(name = "image_id", nullable = false)
	    private Image image;

		@JsonFormat(pattern = "dd/MM/yyyy")
		private LocalDate birthdate;

		@Column(name = "animal_status")
	    private String animalStatus;

		@ManyToOne
		@JoinColumn(name = "category_id", nullable = false)
		private Category category;

}
