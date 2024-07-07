package com.petstation.petstation.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class AnimalDTO {
	
    private int id;
    private String name;
    private String description;
    private String imageURL;
    private String category;
    private int categoryId;
    private int age;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    private String animalStatus;

}
