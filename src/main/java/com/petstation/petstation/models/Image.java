package com.petstation.petstation.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;

    @Column(name = "image_url")
    private String imageURL;
}
