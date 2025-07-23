package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class Battery{
    private String batteryType;
    private String batteryLife;

    @Lob
    private String additionalInfo;
}

