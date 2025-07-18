package com.example.fone_hub.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Invalid message key"),
    USER_EXISTED(1002, "User Existed"),
    USER_NOT_EXISTED(1003, "User Not Existed"),
    USERNAME_INVALID(1004, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1005, "Password must be at least 8 characters"),
    ENTITY_EXIST(1006, "Entity is Existed"),
    ENTITY_NOT_EXIST(1007, "Entity is not Existed"),
    UPLOAD_IMAGE_ERROR(1008, "Upload image error"),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

}
