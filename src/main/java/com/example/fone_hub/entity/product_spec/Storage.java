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
public class Storage {
    private int rom;
    private String hasExternalMemory;
    private String contactStorage;
}

