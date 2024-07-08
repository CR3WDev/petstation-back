package com.petstation.petstation.services;

import com.petstation.petstation.dtos.CategoryDTO;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.mappers.CategoryMapper;
import com.petstation.petstation.models.Category;
import com.petstation.petstation.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;


    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    public void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("teste");

        category = new Category();
        category.setId(1);
        category.setName("teste");
    }

    @Test
    @DisplayName("Should save a category")
    void save() {
        when(categoryMapper.toCategory(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        CategoryDTO savedCategory = categoryService.save(categoryDTO);

        assertEquals(categoryDTO, savedCategory);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toCategory(categoryDTO);
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    @DisplayName("Should find category by ID")
    void findById() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        CategoryDTO foundCategory = categoryService.findById(categoryDTO.getId());

        assertEquals(categoryDTO.getId(), foundCategory.getId());
        assertEquals(categoryDTO.getName(), foundCategory.getName());
        verify(categoryRepository).findById(categoryDTO.getId());
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    @DisplayName("Should throw NotFoundException when category not found by ID")
    void findByIdNotFound() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.findById(categoryDTO.getId()));
        verify(categoryRepository).findById(categoryDTO.getId());
    }

    @Test
    @DisplayName("Should delete category by ID")
    void deleteById() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.of(category));

        categoryService.deleteById(categoryDTO.getId());

        verify(categoryRepository).findById(categoryDTO.getId());
        verify(categoryRepository).deleteById(categoryDTO.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when deleting category not found by ID")
    void deleteByIdNotFound() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.deleteById(categoryDTO.getId()));
        verify(categoryRepository).findById(categoryDTO.getId());
    }

    @Test
    @DisplayName("Should update category")
    void update() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.of(category));
        when(categoryMapper.toCategory(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        CategoryDTO updatedCategory = categoryService.update(categoryDTO);

        assertEquals(categoryDTO, updatedCategory);
        verify(categoryRepository).findById(categoryDTO.getId());
        verify(categoryRepository).save(category);
        verify(categoryMapper).toCategory(categoryDTO);
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    @DisplayName("Should throw NotFoundException when updating category not found")
    void updateNotFound() {
        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.update(categoryDTO));
        verify(categoryRepository).findById(categoryDTO.getId());
    }

    @Test
    @DisplayName("Should find all categories")
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(categoryMapper.toCategoryDTOList(anyList())).thenReturn(Collections.singletonList(categoryDTO));

        List<CategoryDTO> categoryDTOList = categoryService.findAll();

        assertEquals(1, categoryDTOList.size());
        assertEquals(categoryDTO, categoryDTOList.get(0));
        verify(categoryRepository).findAll();
        verify(categoryMapper).toCategoryDTOList(anyList());
    }
}