package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Display {
    private String size;
    private String technology;
    private String standard;
    private String displayResolution;
    private String brightness;
    private String contrastRatio;
    private String glassMaterial;
    private String touchType;
}
