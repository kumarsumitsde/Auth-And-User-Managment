package com.auth_service.auth_service.util;

public class ActivationTokenUtil {

    public static String generateActivationToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
