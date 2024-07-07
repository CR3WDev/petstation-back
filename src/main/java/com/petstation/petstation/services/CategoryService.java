package com.petstation.petstation.services;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.dtos.RequestListDTO;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.mappers.CategoryMapper;
import com.petstation.petstation.models.Category;
import com.petstation.petstation.repositories.CategoryRepository;
import com.petstation.petstation.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;

	public CategoryDTO save(CategoryDTO categoryDTO) {
		Category categorySaved = categoryRepository.save(categoryMapper.toCategory(categoryDTO));
		return categoryMapper.toCategoryDTO(categorySaved);
	}
	
    public CategoryDTO findById(int id) {
    	Optional<Category> category = categoryRepository.findById(id);
    	
    	if(category.isEmpty()) {
    		throw new NotFoundException("Category Not Found!");
    	}
    	
    	return categoryMapper.toCategoryDTO(category.get());
    }

    public void deleteById(int id) {
    	Optional<Category> category = categoryRepository.findById(id);
    	
    	if(category.isEmpty()) {
    		throw new NotFoundException("Category Not Found");
    	}
    	
    	categoryRepository.deleteById(id);
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
    	Optional<Category> category = categoryRepository.findById(categoryDTO.getId());
    	
    	if(category.isEmpty()) {
    		throw new NotFoundException("Category Not Found");
    	}
    	
    	Category categorySaved = categoryRepository.save(categoryMapper.toCategory(categoryDTO));

        return categoryMapper.toCategoryDTO(categorySaved);
    }

	public Page<CategoryDTO> findAllByPage(RequestListDTO requestListDTO) {
		PageRequest pageRequest = PageRequestUtils.createPageRequest(requestListDTO);

		Specification<Category> spec = PageRequestUtils.createSpecification(requestListDTO);

		Page<Category> categoryModelPage = categoryRepository.findAll(spec, pageRequest);

		return categoryModelPage.map(categoryMapper::toCategoryDTO);
	}

	public List<CategoryDTO> findAll() {
		return categoryMapper.toCategoryDTOList(categoryRepository.findAll());
	}

}
