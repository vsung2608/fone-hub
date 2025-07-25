package com.example.fone_hub.dto.request;

import com.example.fone_hub.entity.OrderDetail;
import com.example.fone_hub.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long id;
    Date orderDate;
    Long total;
    User user;
    List<OrderDetail> orderDetails;
}