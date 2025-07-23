package com.example.fone_hub.entity;

import com.example.fone_hub.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "total")
    Long total;

    @Column(name = "quantity_product")
    Long quantity;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order")
    OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    List<OrderDetail> items;
}
