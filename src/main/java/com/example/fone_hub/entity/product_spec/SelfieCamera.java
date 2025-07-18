package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
public class SelfieCamera {
    private String selfieCameraType;
    private String resolution;
    @Lob
    private String videoRecording;
    @Lob
    private String features;
}

