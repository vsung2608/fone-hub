package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class GoodsInformation {
    String origin;
    LocalDate launchDate;
    Integer warrantyPeriod;
}
