package vinsguru.order.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.OrderRequestDTO;
import dto.OrderResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vinsguru.order.entity.PurchaseOrder;
import vinsguru.order.service.OrderService;

@RestController
@RequestMapping("order")	
public class OrderController {
	
	@Autowired
	private OrderService service;

	@PostMapping("/create")
	public Mono<PurchaseOrder> createOrder(@RequestBody Mono<OrderRequestDTO> mono) {
	
		return mono.flatMap(this.service::createOrder);
	}

	@GetMapping("/all")
	public Flux<OrderResponseDTO> getOrders() {
		return this.service.getAll();
	}
	
}
