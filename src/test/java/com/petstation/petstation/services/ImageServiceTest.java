package com.petstation.petstation.services;

import com.petstation.petstation.dtos.ImageDTO;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.mappers.ImageMapper;
import com.petstation.petstation.models.Image;
import com.petstation.petstation.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private MultipartFile multipartFile;

    private ImageDTO imageDTO;
    private Image image;

    @Value("${file.upload-dir}")
    private String FOLDER_PATH;

    @BeforeEach
    void setUp() {
        imageDTO = new ImageDTO();
        imageDTO.setId(1);
        imageDTO.setName("test_image");
        imageDTO.setType("image/jpeg");

        image = new Image();
        image.setId(1);
        image.setName("test_image");
        image.setType("image/jpeg");
        image.setImageURL(FOLDER_PATH + "test_image.jpg");
    }

    @Test
    @DisplayName("Should upload image to file system")
    void uploadImageToFileSystem() throws IOException {
        when(multipartFile.getOriginalFilename()).thenReturn("test_image.jpg");
        when(multipartFile.getContentType()).thenReturn("image/jpeg");

        when(imageRepository.save(any(Image.class))).thenReturn(image);

        when(imageMapper.toImageDTO(any(Image.class))).thenReturn(imageDTO);

        doNothing().when(multipartFile).transferTo(any(File.class));

        ImageDTO uploadedImageDTO = imageService.uploadImageToFileSystem(multipartFile);

        assertNotNull(uploadedImageDTO);
        assertEquals(imageDTO.getId(), uploadedImageDTO.getId());
        assertEquals(imageDTO.getName(), uploadedImageDTO.getName());
        assertEquals(imageDTO.getType(), uploadedImageDTO.getType());

        verify(imageRepository).save(any(Image.class));
        verify(imageMapper).toImageDTO(any(Image.class));
        verify(multipartFile).transferTo(any(File.class));
    }

    @Test
    @DisplayName("Should throw NotFoundException when downloading non-existing image")
    void downloadImageFromFileSystemNotFound() {
        when(imageRepository.findById(imageDTO.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> imageService.downloadImageFromFileSystem(imageDTO.getId()));
        verify(imageRepository).findById(imageDTO.getId());
    }
}
