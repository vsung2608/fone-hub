package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInformation {
    String origin;
    LocalDate launchDate;
    Integer warrantyPeriod;
}
