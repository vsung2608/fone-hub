package com.example.fone_hub.entity;

import com.example.fone_hub.entity.product_spec.*;
import com.example.fone_hub.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    Long price;

    @Column(name = "discount")
    Long discount;

    @Column(name = "color")
    String color;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "create_date")
    LocalDate createDate;

    @Column(name = "update_date")
    LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_product")
    ProductStatus status;

    @Builder.Default
    @Column(name = "quantity_sell")
    Long quantitySell = 0L;

    @ManyToOne
    @JsonBackReference(value = "product-category")
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JsonBackReference(value = "product-brand")
    @JoinColumn(name = "brand_id")
    Brand brand;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<ImageProduct> images;

    @Embedded
    GoodsInformation goodsInformation;

    @Embedded
    Design design;

    @Embedded
    Cpu cpu;

    @Embedded
    Display display;

    @Embedded
    Storage storage;

    @Embedded
    Camera camera;

    @Embedded
    SelfieCamera selfieCamera;

    @Embedded
    Connectivity connectivity;

    @Embedded
    Battery battery;
}