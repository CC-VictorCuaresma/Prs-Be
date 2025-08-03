package pe.edu.vallegrande.vgmsdistribution.application.services;

import pe.edu.vallegrande.vgmsdistribution.domain.models.DistributionSchedule;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.DistributionScheduleCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.DistributionScheduleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DistributionScheduleService {

    Flux<DistributionSchedule> getAll();
    Flux<DistributionSchedule> getAllActive();
    Flux<DistributionSchedule> getAllInactive();
    Mono<DistributionSchedule> getById(String id);
    Mono<DistributionScheduleResponse> save(DistributionScheduleCreateRequest request);
    Mono<DistributionSchedule> update(String id, DistributionSchedule schedule);
    Mono<Void> delete(String id);
    Mono<DistributionSchedule> activate(String id);
    Mono<DistributionSchedule> deactivate(String id);
}
