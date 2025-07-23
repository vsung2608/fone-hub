package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Storage {
    private int rom;
    private boolean hasExternalMemory;
    private String contactStorage;
}

