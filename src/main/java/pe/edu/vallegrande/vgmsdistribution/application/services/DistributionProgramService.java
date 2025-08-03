package pe.edu.vallegrande.vgmsdistribution.application.services;

import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.DistributionProgramCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.DistributionProgramResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DistributionProgramService {

    Flux<DistributionProgramResponse> getAll();
    Mono<DistributionProgramResponse> getById(String id);
    Mono<DistributionProgramResponse> save(DistributionProgramCreateRequest request);
    Mono<DistributionProgramResponse> update(String id, DistributionProgramCreateRequest request);
    Mono<Void> delete(String id);

    // Cambios de estado
    Mono<DistributionProgramResponse> changeStatus(String id, String status);
}
