package vinsguru.order.entity;


import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import enums.OrderStatus;
import lombok.Data;
import lombok.ToString;

@Table("purchase_order")
@Data
@ToString
public class PurchaseOrder implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private UUID id;
    
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;
    

}
