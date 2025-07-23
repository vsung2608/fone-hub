package com.example.fone_hub.entity;

import com.example.fone_hub.enums.Role;
import com.example.fone_hub.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "gender")
    String gender;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

    @Column(name = "create_date")
    Date createDate;

    @Column(name = "update_date")
    Date updateDate;

    @Column(name = "avatar")
    String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    Role roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_user")
    UserStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Order> orders;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Cart> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Address> addresses;
}

