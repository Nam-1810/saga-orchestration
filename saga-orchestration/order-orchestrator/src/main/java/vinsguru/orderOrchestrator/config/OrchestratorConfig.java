package vinsguru.orderOrchestrator.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dto.OrchestratorRequestDTO;
import dto.OrchestratorResponseDTO;
import reactor.core.publisher.Flux;
import vinsguru.orderOrchestrator.service.OrchestratorService;

@Configuration
public class OrchestratorConfig {

	@Autowired
	private OrchestratorService orchestratorService;

	@Bean
	public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor() {
		return flux -> flux
				.flatMap(dto -> this.orchestratorService.orderProduct(dto));
	}

}
