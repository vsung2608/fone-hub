package com.example.fone_hub.entity;

import com.example.fone_hub.enums.LinkedStatus;
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
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "image")
    String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    LinkedStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;
}

