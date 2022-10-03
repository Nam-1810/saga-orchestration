package vinsguru.order.service;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.OrchestratorResponseDTO;
import reactor.core.publisher.Mono;
import vinsguru.order.repository.PurchaseOrderRepository;

@Service
public class OrderEventUpdateService {

    @Autowired
    private PurchaseOrderRepository repository;

    public Mono<Void> updateOrder(final OrchestratorResponseDTO responseDTO){
        return this.repository.findById(getIdAsByte(responseDTO.getOrderId()))
        		.doOnNext(p -> System.out.println("responseDTO: " + responseDTO))
                .doOnNext(p -> p.setStatus(responseDTO.getStatus()))
                .flatMap(this.repository::save)
                .then();
    }
    
    public byte[] getIdAsByte(UUID uuid)
    {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }


}
