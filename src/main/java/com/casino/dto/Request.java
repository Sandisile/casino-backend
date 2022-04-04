package com.casino.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Request {
    @NonNull
    private String transactionId;
    @NonNull
    private Double amount;
}
