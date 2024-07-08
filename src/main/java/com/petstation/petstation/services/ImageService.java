package com.petstation.petstation.services;

import com.petstation.petstation.dtos.ImageDTO;
import com.petstation.petstation.exceptions.NotFoundException;
import com.petstation.petstation.mappers.ImageMapper;
import com.petstation.petstation.models.Image;
import com.petstation.petstation.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String FOLDER_PATH;

    @Autowired
    private ImageMapper imageMapper;

    public ImageDTO uploadImageToFileSystem(MultipartFile image) throws IOException {
        String imagePath = FOLDER_PATH + image.getOriginalFilename();

        Image imageData = imageRepository.save(Image.builder()
                .name(image.getOriginalFilename())
                .type(image.getContentType())
                .imageURL(imagePath).build());

        image.transferTo(new File(imagePath));

        return imageMapper.toImageDTO(imageData);
    }

    public byte[] downloadImageFromFileSystem(int imageId) throws IOException {
        Optional<Image> imageData = imageRepository.findById(imageId);

        if(imageData.isEmpty()) {
            throw new NotFoundException("Image Not Found!");
        }

        String filePath = imageData.get().getImageURL();
        return Files.readAllBytes(new File(filePath).toPath());
    }
}

