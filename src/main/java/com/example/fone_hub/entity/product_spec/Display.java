package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;

@Embeddable
public class Display {
    private String size;
    private String technology;
    private String standard;
    private String resolution;
    private String brightness;
    private String contrastRatio;
    private String glassMaterial;
    private String touchType;
}
