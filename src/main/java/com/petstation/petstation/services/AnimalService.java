package com.petstation.petstation.services;

import java.util.List;
import java.util.Optional;

import com.petstation.petstation.dtos.RequestListDTO;
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

	public AnimalDTO save(AnimalDTO animalDTO) {
		animalDTO.setAnimalStatus(AnimalStatus.AVALIABLE.getDescription());
		animalDTO.setId(0);
		Animal animalSaved = animalRepository.save(animalMapper.toAnimal(animalDTO));
		return animalMapper.toAnimalDTO(animalSaved);
	}
	
    public AnimalDTO findById(int id) {
    	Optional<Animal> animal = animalRepository.findById(id);
    	
    	if(animal.isEmpty()) {
    		throw new NotFoundException("Animal Not Found!");
    	}
    	
    	return animalMapper.toAnimalDTO(animal.get());
    }

    public void deleteById(int id) {
    	Optional<Animal> animal = animalRepository.findById(id);
    	
    	if(animal.isEmpty()) {
    		throw new NotFoundException("Animal Not Found");
    	}
    	
    	animalRepository.deleteById(id);
    }

    public AnimalDTO update(AnimalDTO animalDTO) {
    	Optional<Animal> animal = animalRepository.findById(animalDTO.getId());
    	
    	if(animal.isEmpty()) {
    		throw new NotFoundException("Animal Not Found");
    	}
    	
    	Animal animalSaved = animalRepository.save(animalMapper.toAnimal(animalDTO));

        return animalMapper.toAnimalDTO(animalSaved);
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

		return animalMapper.toAnimalDTO(animalSaved);
	}
    
    public List<AnimalDTO> finAll() {
        return animalMapper.toAnimalDTOList(animalRepository.findAll());
    }

	public Page<AnimalDTO> findAllByPage(RequestListDTO requestListDTO) {
		PageRequest pageRequest = PageRequestUtils.createPageRequest(requestListDTO);

		Specification<Animal> spec = PageRequestUtils.createSpecification(requestListDTO);

		Page<Animal> animalModelPage = animalRepository.findAll(spec, pageRequest);
		return animalModelPage.map(animalMapper::toAnimalDTO);
	}

}
