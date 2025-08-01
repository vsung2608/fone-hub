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
public class Camera {
    private String rearCameraType;
    private String camera1Type;
    private String camera1Resolution;
    private String camera2Type;
    private String camera2Resolution;
    @Lob
    private String cameraVideoRecording;
    @Lob
    private String cameraFeatures;
}
