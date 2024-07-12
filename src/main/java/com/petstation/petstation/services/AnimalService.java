package com.petstation.petstation.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.dtos.RequestListDTO;
import com.petstation.petstation.exceptions.InvalidDateException;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.petstation.petstation.dtos.AnimalDTO;
import com.petstation.petstation.enums.AnimalStatus;
import com.petstation.petstation.mappers.AnimalMapper;
import com.petstation.petstation.models.Animal;
import com.petstation.petstation.repositories.AnimalRepository;

@Service
public class AnimalService {
	
	@Autowired
	private AnimalRepository animalRepository;
	
	@Autowired
	private AnimalMapper animalMapper;

	@Autowired
	private CategoryService categoryService;

	public AnimalDTO save(AnimalDTO animalDTO) {
		animalDTO.setAnimalStatus(AnimalStatus.AVALIABLE.getDescription());
		LocalDate today = LocalDate.now();

		if(animalDTO.getBirthdate().isBefore(today)) {
			throw new InvalidDateException("Invalid Birthdate!");
		}

		Animal animalSaved = animalRepository.save(animalMapper.toAnimal(animalDTO));
		return transformToDTO(animalSaved);
	}
	
    public AnimalDTO findById(int id) {
		Animal animal = animalRepository.findById(id)
				.orElseThrow(()->new NotFoundException("Animal Not Found"));

		return transformToDTO(animal);
    }

    public void deleteById(int id) {
		animalRepository.findById(id)
				.orElseThrow(()->new NotFoundException("Animal Not Found"));
    	
    	animalRepository.deleteById(id);
    }

    public AnimalDTO update(AnimalDTO animalDTO) {
    	Animal animal = animalRepository.findById(animalDTO.getId())
				.orElseThrow(()->new NotFoundException("Animal Not Found"));
		LocalDate today = LocalDate.now();

		if(animal.getBirthdate().isAfter(today)) {
			throw new InvalidDateException("Invalid Birthdate!");
		}

    	Animal animalSaved = animalRepository.save(animalMapper.toAnimal(animalDTO));
        return transformToDTO(animalSaved);
    }

	public AnimalDTO updateStatus(int id, String animalStatus) {
		Animal animal = animalRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Animal Not Found"));

		switch (animalStatus) {
			case "avaliable": {
				animal.setAnimalStatus(AnimalStatus.AVALIABLE.getDescription());
				break;
			}
			case "adopted" : {
				animal.setAnimalStatus(AnimalStatus.ADOPTED.getDescription());
				break;
			}
			default: {
				throw new NotFoundException("Animal Status Not Found");
			}
		}

		Animal animalSaved = animalRepository.save(animal);

		return transformToDTO(animalSaved);
	}
    
    public List<AnimalDTO> finAll() {
        return animalMapper.toAnimalDTOList(animalRepository.findAll());
    }

	public Page<AnimalDTO> findAllByPage(RequestListDTO requestListDTO) {
		PageRequest pageRequest = PageRequestUtils.createPageRequest(requestListDTO);
		Specification<Animal> spec = PageRequestUtils.createSpecification(requestListDTO);

		Page<Animal> animalModelPage = animalRepository.findAll(spec, pageRequest);
		return animalModelPage.map(this::transformToDTO);
	}

	public AnimalDTO transformToDTO(Animal animal) {
		AnimalDTO animalDTO = animalMapper.toAnimalDTO(animal);
		CategoryDTO categoryDTO = categoryService.findById(animal.getCategory().getId());
		animalDTO.setCategory(categoryDTO.getName());
		animalDTO.setAge(Period.between(animalDTO.getBirthdate(), LocalDate.now()).getYears());
		return animalDTO;
	}

}
