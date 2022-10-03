package vinsguru.order.eventhandlers;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dto.OrchestratorRequestDTO;
import dto.OrchestratorResponseDTO;
import reactor.core.publisher.Flux;
import vinsguru.order.service.OrderEventUpdateService;

@Configuration
public class OrderEventHandler {

    @Autowired
    private Flux<OrchestratorRequestDTO> flux;

    @Autowired
    private OrderEventUpdateService service;

    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> supplier(){
        return () -> flux.doOnNext(c -> System.out.println("Supplying:: " + c));
    };

    @Bean
    public Consumer<Flux<OrchestratorResponseDTO>> consumer(){
        return f -> f
               .doOnNext(c -> System.out.println("Consuming :: " + c))
               .flatMap(responseDTO -> this.service.updateOrder(responseDTO))
               .subscribe();
    };

}