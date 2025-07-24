package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.OrderDetail;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.OrderStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    Date date;
    Long total;
    Long quantity;
    String phone;
    String address;
    OrderStatus status;
    User user;
    List<OrderDetail> items;
}
