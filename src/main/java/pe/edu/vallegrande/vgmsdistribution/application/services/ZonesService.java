package pe.edu.vallegrande.vgmsdistribution.application.services;

import pe.edu.vallegrande.vgmsdistribution.domain.models.Zones;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.ZoneCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.ZonesResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ZonesService {
    Flux<Zones> getAllZ();
    Flux<Zones> getAllActiveZ();
    Flux<Zones> getAllInactiveZ();
    Flux<Zones> getByOrganizationZ(String organizationId);
    Mono<Zones> getByIdZ(String id);
    Mono<ZonesResponse> saveZ(ZoneCreateRequest request);
    Mono<Zones> updateZ(String id, Zones zones);
    Mono<Void> deleteZ(String id);
    Mono<Zones> activateZ(String id);
    Mono<Zones> deactivateZ(String id);
}