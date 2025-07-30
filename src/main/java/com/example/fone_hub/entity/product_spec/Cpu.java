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
public class Cpu {
    private String cpuModel;
    private String coreType;
    private int coreCount;
    private String gpuModel;
    private int ram;
}
