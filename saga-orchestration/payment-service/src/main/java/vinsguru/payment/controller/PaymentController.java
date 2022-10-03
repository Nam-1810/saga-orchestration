package vinsguru.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.PaymentRequestDTO;
import dto.PaymentResponseDTO;
import vinsguru.payment.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {
	@Autowired
	private PaymentService service;

	@PostMapping("/debit")
	public PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO) {
		return this.service.debit(requestDTO);
	}

	@PostMapping("/credit")
	public void credit(@RequestBody PaymentRequestDTO requestDTO) {
		this.service.credit(requestDTO);
	}
}
