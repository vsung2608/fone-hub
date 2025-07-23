package com.example.fone_hub.entity;

import com.example.fone_hub.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "image")
    String image;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    StatusEnum status;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<Product> products;

    public Category(String name) {
        this.name = name;
    }

}
