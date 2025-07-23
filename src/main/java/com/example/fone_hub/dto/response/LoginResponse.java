package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    boolean login;
    User user;
    String message;
}
