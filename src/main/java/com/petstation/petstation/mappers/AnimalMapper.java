package com.petstation.petstation.mappers;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.petstation.petstation.dtos.AnimalDTO;
import com.petstation.petstation.models.Animal;

@Component
public class AnimalMapper {
	
		@Autowired
	    private ModelMapper modelMapper;

		@Autowired
		private CategoryService categoryService;

	    public AnimalDTO toAnimalDTO(Animal animalModel) {
			LocalDate now = LocalDate.now();
			AnimalDTO animalDTO = modelMapper.map(animalModel, AnimalDTO.class);
			animalDTO.setAge(Period.between(animalDTO.getBirthdate(), now).getYears());
			CategoryDTO categoryDTO = categoryService.findById(animalDTO.getCategoryId());
			animalDTO.setCategory(categoryDTO.getName());
	        return animalDTO;
	    }
	    
	    public Animal toAnimal(AnimalDTO animalDTO) {
	        return modelMapper.map(animalDTO, Animal.class);
	    }

	    public List<AnimalDTO> toAnimalDTOList(List<Animal> animalList) {
	        return animalList.stream()
	                .map(this::toAnimalDTO)
	                .collect(Collectors.toList());
	    }
	    
	    public Page<AnimalDTO> toAnimalDTOPage(Page<Animal> animalPage) {
	        List<AnimalDTO> animalDTOList = animalPage.getContent().stream()
	                .map(this::toAnimalDTO)
	                .collect(Collectors.toList());
	        return new PageImpl<>(animalDTOList, animalPage.getPageable(), animalPage.getTotalElements());
	    }

}