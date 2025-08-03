package pe.edu.vallegrande.vgmsdistribution.application.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsdistribution.application.services.FareService;
import pe.edu.vallegrande.vgmsdistribution.domain.enums.Constants;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Fare;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.FareCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.FareResponse;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.exception.CustomException;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.repository.FareRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@Slf4j
public class FareServiceImpl implements FareService {

    @Autowired
    private FareRepository fareRepository;

    @Override
    public Flux<Fare> getAllF() {
        return fareRepository.findAll()
                .doOnNext(fare -> log.info("Fare retrieved: {}", fare));
    }

    @Override
    public Flux<Fare> getAllActiveF() {
        return fareRepository.findAllByStatus(Constants.ACTIVE.name());
    }

    @Override
    public Flux<Fare> getAllInactiveF() {
        return fareRepository.findAllByStatus(Constants.INACTIVE.name());
    }

    @Override
    public Mono<Fare> getByIdFMono(String id) {
        return fareRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "Fare not found",
                        "The requested fare with id " + id + " was not found")));
    }

   @Override
public Mono<FareResponse> saveF(FareCreateRequest request) {
    return generateNextFareCode() // ← Aquí lo usamos
        .flatMap(generatedCode -> 
            fareRepository.existsByFareCode(generatedCode)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new CustomException(
                                HttpStatus.BAD_REQUEST.value(),
                                "Fare code already exists",
                                "The fare code " + generatedCode + " is already registered"));
                    }

                    Fare fare = Fare.builder()
                            .organizationId(request.getOrganizationId())
                            .fareCode(generatedCode) // ← Se usa el código generado
                            .fareName(request.getFareName())
                            .fareType(request.getFareType())
                            .fareAmount(request.getFareAmount())
                            .status(Constants.ACTIVE.name())
                            .createdAt(Instant.now())
                            .build();

                    return fareRepository.save(fare)
                            .map(savedFare -> FareResponse.builder()
                                    .id(savedFare.getId())
                                    .organizationId(savedFare.getOrganizationId())
                                    .fareCode(savedFare.getFareCode())
                                    .fareName(savedFare.getFareName())
                                    .fareType(savedFare.getFareType())
                                    .fareAmount(savedFare.getFareAmount())
                                    .status(savedFare.getStatus())
                                    .createdAt(savedFare.getCreatedAt())
                                    .build());
                })
        );
}

private static final String FARE_PREFIX = "TAR";

private Mono<String> generateNextFareCode() {
    return fareRepository.findTopByOrderByFareCodeDesc()
            .map(last -> {
                String lastCode = last.getFareCode(); // ej. "TAR003"
                int number = 0;
                try {
                    number = Integer.parseInt(lastCode.replace(FARE_PREFIX, ""));
                } catch (NumberFormatException e) {
                    // Si el código no sigue el patrón, asumimos 0
                }
                return String.format(FARE_PREFIX + "%03d", number + 1);
            })
            .defaultIfEmpty(FARE_PREFIX + "001");
}

    

    @Override
    public Mono<Fare> updateF(String id, Fare fare) {
        return fareRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "Fare not found",
                        "Cannot update non-existent fare with id " + id)))
                .flatMap(existingFare -> {
                    existingFare.setFareName(fare.getFareName());
                    existingFare.setFareType(fare.getFareType());
                    existingFare.setFareAmount(fare.getFareAmount());
                    return fareRepository.save(existingFare);
                });
    }

    @Override
    public Mono<Void> deleteF(String id) {
        return fareRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "Fare not found",
                        "Cannot delete non-existent fare with id " + id)))
                .flatMap(fareRepository::delete);
    }

    @Override
    public Mono<Fare> activateF(String id) {
        return changeStatus(id, Constants.ACTIVE.name());
    }

    @Override
    public Mono<Fare> deactivateF(String id) {
        return changeStatus(id, Constants.INACTIVE.name());
    }

    private Mono<Fare> changeStatus(String id, String status) {
        return fareRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(),
                        "Fare not found",
                        "Cannot change status of non-existent fare with id " + id)))
                .flatMap(fare -> {
                    fare.setStatus(status);
                    return fareRepository.save(fare);
                });
    }
}
