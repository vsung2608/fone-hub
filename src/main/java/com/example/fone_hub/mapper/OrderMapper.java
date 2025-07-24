package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.response.OrderResponse;
import com.example.fone_hub.entity.Order;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);
}