package com.example.fone_hub.entity.product_spec;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class SelfieCamera {
    private String selfieCameraType;
    private String selfieCameraResolution;
    @Lob
    private String selfieCameraVideoRecording;
    @Lob
    private String selfieCameraFeatures;
}

