package com.wallet.keycloak.dtos.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor

public class ApiResponse<T> {
    public T data;
}
