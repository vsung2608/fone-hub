package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;

@Embeddable
public class Storage {
    private int rom;
    private boolean hasExternalMemory;
    private String contactStorage;
}

