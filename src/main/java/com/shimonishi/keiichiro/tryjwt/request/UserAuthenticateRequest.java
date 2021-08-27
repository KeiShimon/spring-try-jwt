package com.shimonishi.keiichiro.tryjwt.request;

import lombok.Data;

@Data
public class UserAuthenticateRequest {
    private final String username;
    private final String password;
}
