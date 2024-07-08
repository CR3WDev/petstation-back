package com.petstation.petstation.services;

import com.petstation.petstation.dtos.AnimalDTO;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.mappers.AnimalMapper;
import com.petstation.petstation.models.Animal;
import com.petstation.petstation.repositories.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @InjectMocks
    private AnimalService animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    private AnimalDTO animalDTO;
    private Animal animal;

    @BeforeEach
    void setUp() {
        animalDTO = new AnimalDTO();
        animalDTO.setId(1);
        animalDTO.setName("Rex");
        animalDTO.setDescription("Friendly dog");
        animalDTO.setCategory("Dog");
        animalDTO.setCategoryId(1);
        animalDTO.setImageId(1);
        animalDTO.setAge(5);
        animalDTO.setBirthdate(LocalDate.of(2019, 5, 15));
        animalDTO.setAnimalStatus("Available");

        animal = new Animal();
        animal.setId(1);
        animal.setName("Rex");
        animal.setDescription("Friendly dog");
        animal.setCategory(null);
        animal.setImage(null);
        animal.setBirthdate(LocalDate.of(2019, 5, 15));
        animal.setAnimalStatus("Available");
    }

    @Test
    @DisplayName("Should save an animal")
    void save() {
        when(animalMapper.toAnimal(any(AnimalDTO.class))).thenReturn(animal);
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        when(animalMapper.toAnimalDTO(any(Animal.class))).thenReturn(animalDTO);

        AnimalDTO savedAnimalDTO = animalService.save(animalDTO);

        assertEquals(animalDTO.getId(), savedAnimalDTO.getId());
        assertEquals(animalDTO.getName(), savedAnimalDTO.getName());
        assertEquals(animalDTO.getDescription(), savedAnimalDTO.getDescription());
        assertEquals(animalDTO.getCategory(), savedAnimalDTO.getCategory());
        assertEquals(animalDTO.getCategoryId(), savedAnimalDTO.getCategoryId());
        assertEquals(animalDTO.getImageId(), savedAnimalDTO.getImageId());
        assertEquals(animalDTO.getAge(), savedAnimalDTO.getAge());
        assertEquals(animalDTO.getBirthdate(), savedAnimalDTO.getBirthdate());
        assertEquals(animalDTO.getAnimalStatus(), savedAnimalDTO.getAnimalStatus());

        verify(animalMapper).toAnimal(any(AnimalDTO.class));
        verify(animalRepository).save(any(Animal.class));
        verify(animalMapper).toAnimalDTO(any(Animal.class));
    }

    @Test
    @DisplayName("Should find animal by ID")
    void findById() {
        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.of(animal));
        when(animalMapper.toAnimalDTO(any(Animal.class))).thenReturn(animalDTO);

        AnimalDTO foundAnimalDTO = animalService.findById(animalDTO.getId());

        assertEquals(animalDTO.getId(), foundAnimalDTO.getId());
        assertEquals(animalDTO.getName(), foundAnimalDTO.getName());
        assertEquals(animalDTO.getDescription(), foundAnimalDTO.getDescription());
        assertEquals(animalDTO.getCategory(), foundAnimalDTO.getCategory());
        assertEquals(animalDTO.getCategoryId(), foundAnimalDTO.getCategoryId());
        assertEquals(animalDTO.getImageId(), foundAnimalDTO.getImageId());
        assertEquals(animalDTO.getAge(), foundAnimalDTO.getAge());
        assertEquals(animalDTO.getBirthdate(), foundAnimalDTO.getBirthdate());
        assertEquals(animalDTO.getAnimalStatus(), foundAnimalDTO.getAnimalStatus());

        verify(animalRepository).findById(animalDTO.getId());
        verify(animalMapper).toAnimalDTO(any(Animal.class));
    }

    @Test
    @DisplayName("Should throw NotFoundException when animal not found by ID")
    void findByIdNotFound() {
        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> animalService.findById(animalDTO.getId()));

        verify(animalRepository).findById(animalDTO.getId());
        verifyNoMoreInteractions(animalMapper);
    }

    @Test
    @DisplayName("Should delete animal by ID")
    void deleteById() {
        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.of(animal));

        animalService.deleteById(animalDTO.getId());

        verify(animalRepository).findById(animalDTO.getId());
        verify(animalRepository).deleteById(animalDTO.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when deleting animal not found by ID")
    void deleteByIdNotFound() {
        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> animalService.deleteById(animalDTO.getId()));

        verify(animalRepository).findById(animalDTO.getId());
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when updating animal not found")
    void updateNotFound() {
        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> animalService.update(animalDTO));

        verify(animalRepository).findById(animalDTO.getId());
        verifyNoMoreInteractions(animalRepository);
        verifyNoInteractions(animalMapper);
    }

}
