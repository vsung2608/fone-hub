package com.example.fone_hub.entity;

import com.example.fone_hub.enums.LinkedStatus;
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

    @Column(name = "name_brand")
    String name;

    @Column(name = "image")
    String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    LinkedStatus status;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;
}

