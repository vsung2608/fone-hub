package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.Address;
import com.example.fone_hub.entity.Cart;
import com.example.fone_hub.enums.StatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String password;
    String fullName;
    String gender;
    String phone;
    String email;
    String avatar;
    Date createDate;
    Date updateDate;
    String roles;
    StatusEnum status;
    List<Cart> carts;
    List<Address> addresses;
}
