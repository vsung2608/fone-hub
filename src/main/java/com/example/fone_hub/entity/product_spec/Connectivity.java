package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connectivity {
    private String simType;
    private Integer simSlotCount;
    private String networkSupport;
    private String chargingPort;
    private String audioJack;
    private String wifi;
    private String bluetooth;
    private String otherConnection;

    @Lob
    private String gpsSystems;
}

