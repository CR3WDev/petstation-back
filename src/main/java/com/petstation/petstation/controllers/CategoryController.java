package com.petstation.petstation.controllers;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.dtos.RequestListDTO;
import com.petstation.petstation.dtos.ResponseListDTO;
import com.petstation.petstation.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.save(categoryDTO));
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseListDTO<CategoryDTO>> listByPage(@Valid @RequestBody RequestListDTO requestListDTO) {
			Page<CategoryDTO> categoryPageDTO = categoryService.findAllByPage(requestListDTO);
			ResponseListDTO<CategoryDTO> responseListDTO = new ResponseListDTO<CategoryDTO>(categoryPageDTO.getContent(),categoryPageDTO.getTotalElements());
			return ResponseEntity.ok().body(responseListDTO);
    	
    }

    @GetMapping("/dropdown")
    public ResponseEntity<ResponseListDTO<CategoryDTO>> listDropdown() {
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        ResponseListDTO<CategoryDTO> responseListDTO = new ResponseListDTO<CategoryDTO>(categoryDTOList,categoryDTOList.size());
        return ResponseEntity.ok().body(responseListDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable int id) {       			
    			return ResponseEntity.ok().body(categoryService.findById(id));
    }
    
    @PutMapping()
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
    	return ResponseEntity.ok().body(categoryService.update(categoryDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteById(@PathVariable int id) {
    	categoryService.deleteById(id);
    	return ResponseEntity.ok().build();
    }

}
