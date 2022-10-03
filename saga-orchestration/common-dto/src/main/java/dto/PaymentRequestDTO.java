package dto;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentRequestDTO{
    private Integer userId;
    private UUID orderId;
    private Double amount;
}