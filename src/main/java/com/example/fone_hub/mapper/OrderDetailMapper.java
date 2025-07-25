package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.response.OrderDetailResponse;
import com.example.fone_hub.entity.OrderDetail;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetail order);
}