package dto;

import java.util.UUID;

import enums.InventoryStatus;
import lombok.Data;

@Data
public class InventoryResponseDTO {

	private UUID orderId;
	

    private Integer userId;

    private Integer productId;
    private InventoryStatus status;


}