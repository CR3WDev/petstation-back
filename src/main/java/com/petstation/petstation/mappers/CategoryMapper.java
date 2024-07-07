package com.petstation.petstation.mappers;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
	
		@Autowired
	    private ModelMapper modelMapper;

	    public CategoryDTO toCategoryDTO(Category categoryModel) {
	        return modelMapper.map(categoryModel, CategoryDTO.class);
	    }
	    
	    public Category toCategory(CategoryDTO categoryDTO) {
	        return modelMapper.map(categoryDTO, Category.class);
	    }

	    public List<CategoryDTO> toCategoryDTOList(List<Category> categoryList) {
	        return categoryList.stream()
	                .map(this::toCategoryDTO)
	                .collect(Collectors.toList());
	    }
	    
	    public Page<CategoryDTO> toAnimalDTOPage(Page<Category> categoryPage) {
	        List<CategoryDTO> categoryDTOList = categoryPage.getContent().stream()
	                .map(this::toCategoryDTO)
	                .collect(Collectors.toList());
	        return new PageImpl<>(categoryDTOList, categoryPage.getPageable(), categoryPage.getTotalElements());
	    }

}
