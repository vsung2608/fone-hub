package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class GoodsInformation {
    String origin;
    LocalDate launchDate;
    Integer warrantyPeriod;
}
