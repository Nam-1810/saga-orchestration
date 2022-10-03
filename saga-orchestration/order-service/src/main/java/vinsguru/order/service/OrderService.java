package vinsguru.order.service;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import dto.OrchestratorRequestDTO;
import dto.OrderRequestDTO;
import dto.OrderResponseDTO;
import enums.OrderStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import vinsguru.order.entity.PurchaseOrder;
import vinsguru.order.repository.PurchaseOrderRepository;

@Service
public class OrderService {
	
    private static final Map<Integer, Double> PRODUCT_PRICE =  Map.of(
            1, 100d,
            2, 200d,
            3, 300d
    );

	@Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
    private Sinks.Many<OrchestratorRequestDTO> sink;
	

    @Resource
    R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<PurchaseOrder> createOrder(OrderRequestDTO orderRequestDTO){
        return this.r2dbcEntityTemplate.insert(this.dtoToEntity(orderRequestDTO))
                .doOnNext(e -> orderRequestDTO.setOrderId(e.getId()))
                .doOnNext(e -> this.emitEvent(orderRequestDTO));
    }
    
    public Mono<PurchaseOrder> findAllById(UUID uuid){
    	
        return this.purchaseOrderRepository.findById(getIdAsByte(uuid));
    }
    
    public byte[] getIdAsByte(UUID uuid)
    {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        System.out.println("byte: " + bb.array());
        return bb.array();
    }
    
    
    public Flux<OrderResponseDTO> getAll() {
        return this.purchaseOrderRepository.findAll()
                .map(this::entityToDto);
    }
    
    private void emitEvent(OrderRequestDTO orderRequestDTO){
    	this.sink.tryEmitNext(this.getOrchestratorRequestDTO(orderRequestDTO));
    			
    }
    
    private PurchaseOrder dtoToEntity(final OrderRequestDTO dto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(UUID.randomUUID());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(PRODUCT_PRICE.get(purchaseOrder.getProductId()));
        return purchaseOrder;
    }

    private OrderResponseDTO entityToDto(final PurchaseOrder purchaseOrder){
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(purchaseOrder.getId());
        dto.setProductId(purchaseOrder.getProductId());
        dto.setUserId(purchaseOrder.getUserId());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setAmount(purchaseOrder.getPrice());
        return dto;
    }

    public OrchestratorRequestDTO getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO){
        OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
        requestDTO.setUserId(orderRequestDTO.getUserId());
        requestDTO.setAmount(PRODUCT_PRICE.get(orderRequestDTO.getProductId()));
        requestDTO.setOrderId(orderRequestDTO.getOrderId());
        requestDTO.setProductId(orderRequestDTO.getProductId());
        return requestDTO;
    }


}
