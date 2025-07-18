package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;

@Embeddable
public class Design {
    private String dimensions;
    private int weight;
    private String waterResistance;
    private String material;
}

