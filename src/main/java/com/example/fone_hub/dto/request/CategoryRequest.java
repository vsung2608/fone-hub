package com.example.fone_hub.dto.request;

import jakarta.persistence.Column;

public record CategoryRequest (
        String name,
        String description
) {
}
