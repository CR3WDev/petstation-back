package com.petstation.petstation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.petstation.petstation.dtos.AnimalDTO;
import com.petstation.petstation.dtos.RequestListDTO;
import com.petstation.petstation.dtos.ResponseListDTO;
import com.petstation.petstation.services.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {
	
    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalDTO> save(@RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok().body(animalService.save(animalDTO));
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseListDTO<AnimalDTO>> listByPage(@Valid @RequestBody RequestListDTO requestListDTO) {
			Page<AnimalDTO> animalPageDTO = animalService.findAllByPage(requestListDTO);
			ResponseListDTO<AnimalDTO> responseListDTO = new ResponseListDTO<AnimalDTO>(animalPageDTO.getContent(),animalPageDTO.getTotalElements());
			return ResponseEntity.ok().body(responseListDTO);
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getById(@PathVariable int id) {       			
    			return ResponseEntity.ok().body(animalService.findById(id));
    }
    
    @PutMapping()
    public ResponseEntity<AnimalDTO> update(@RequestBody AnimalDTO animalDTO) {
    	return ResponseEntity.ok().body(animalService.update(animalDTO));
    }

    @PutMapping("/{id}/{animalStatus}")
    public ResponseEntity<AnimalDTO> updateStatus(@PathVariable int id, @PathVariable String animalStatus) {
        return ResponseEntity.ok().body(animalService.updateStatus(id,animalStatus));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<AnimalDTO> deleteById(@PathVariable int id) {
    	animalService.deleteById(id);
    	return ResponseEntity.ok().build();
    }

}
