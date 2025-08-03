package pe.edu.vallegrande.vgmsdistribution.application.services;

import pe.edu.vallegrande.vgmsdistribution.domain.models.Fare;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.FareCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.FareResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FareService {

    Flux<Fare> getAllF();
    Flux<Fare> getAllActiveF();
    Flux<Fare> getAllInactiveF();
    Mono<Fare> getByIdFMono(String id);
    Mono<FareResponse> saveF(FareCreateRequest fareRequest);
    Mono<Fare> updateF(String id, Fare fare);
    Mono<Void> deleteF(String id);
    Mono<Fare> activateF(String id);
    Mono<Fare> deactivateF(String id);
}
