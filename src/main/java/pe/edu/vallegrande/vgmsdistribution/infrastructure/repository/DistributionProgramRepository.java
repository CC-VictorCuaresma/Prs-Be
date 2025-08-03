package pe.edu.vallegrande.vgmsdistribution.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vgmsdistribution.domain.models.DistributionProgram;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.DistributionProgramResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface DistributionProgramRepository extends ReactiveMongoRepository<DistributionProgram, String> {

    Flux<DistributionProgram> findAllByStatus(String status);

    Mono<DistributionProgram> findFirstByProgramCode(String programCode);

    Flux<DistributionProgramResponse> findByProgramCode(String programCode);

    Mono<DistributionProgram> findTopByOrderByProgramCodeDesc(); // <- CORRECTO
}
