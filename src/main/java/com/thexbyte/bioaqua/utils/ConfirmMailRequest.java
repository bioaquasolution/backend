package com.thexbyte.bioaqua.utils;


import lombok.Data;

@Data
public class ConfirmMailRequest {
    String email;
    String code;
}