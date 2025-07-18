package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;

@Embeddable
public class Cpu {
    private String cpuModel;
    private String coreType;
    private int coreCount;
    private String gpuModel;
    private int ram;
}
