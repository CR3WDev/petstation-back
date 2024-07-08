package com.petstation.petstation.controllers;

import com.petstation.petstation.dtos.ImageDTO;
import com.petstation.petstation.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageDataController {

    @Autowired
    private ImageService imageService;

    @PostMapping("")
    public ResponseEntity<ImageDTO> uploadImageToFIleSystem(@RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(imageService.uploadImageToFileSystem(image));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable int imageId) throws IOException {
        byte[] imageData = imageService.downloadImageFromFileSystem(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
