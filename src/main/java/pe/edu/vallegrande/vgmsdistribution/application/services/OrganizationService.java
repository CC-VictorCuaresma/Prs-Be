package pe.edu.vallegrande.vgmsdistribution.application.services;

import pe.edu.vallegrande.vgmsdistribution.domain.models.Organization;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.OrganizationCreateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrganizationService {
    Flux<Organization> getAllO();
    Mono<Organization> getByIdO(String id);
    Mono<Organization> saveO(OrganizationCreateRequest request);
    Mono<Organization> updateO(String id, Organization organization);
    Mono<Void> deleteO(String id);
    Mono<Organization> activateO(String id);
    Mono<Organization> deactivateO(String id);
}