package com.example.fone_hub.dto.request;

import java.util.List;

import com.example.fone_hub.enums.Sort;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterRequest {
    String name;
    Long minPrice;
    Long maxPrice;
    Integer page;
    Integer size;
    List<String> brands;
    List<String> categories;
    Sort typeSort;
}
