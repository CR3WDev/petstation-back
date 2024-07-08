package com.petstation.petstation.mappers;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.dtos.ImageDTO;
import com.petstation.petstation.models.Category;
import com.petstation.petstation.models.Image;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {
	
		@Autowired
	    private ModelMapper modelMapper;

	    public ImageDTO toImageDTO(Image imageModel) {
	        return modelMapper.map(imageModel, ImageDTO.class);
	    }
	    
	    public Image toImage(ImageDTO imageDTO) {
	        return modelMapper.map(imageDTO, Image.class);
	    }


}
