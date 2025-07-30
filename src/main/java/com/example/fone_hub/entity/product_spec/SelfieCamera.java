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
public class SelfieCamera {
    private String selfieCameraType;
    private String selfieCameraResolution;
    @Lob
    private String selfieCameraVideoRecording;
    @Lob
    private String selfieCameraFeatures;
}

