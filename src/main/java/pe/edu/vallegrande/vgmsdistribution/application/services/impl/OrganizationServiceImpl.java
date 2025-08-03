package pe.edu.vallegrande.vgmsdistribution.application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import pe.edu.vallegrande.vgmsdistribution.application.services.OrganizationService;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Organization;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.OrganizationCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.repository.OrganizationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    public Flux<Organization> getAllO() {
        return repository.findAll();
    }

    @Override
    public Mono<Organization> getByIdO(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la organización con ID: " + id)));
    }

    @Override
    public Mono<Organization> saveO(OrganizationCreateRequest request) {
        return generateNextCode().flatMap(generateCode ->{
        Organization organization = Organization.builder()
                .organizationName(request.getOrganizationName())
                .organizationCode(generateCode)
                .address(request.getAddress())
                .legalRepresentative(request.getLegalRepresentative())
                .phone(request.getPhone())
                .status(request.getStatus())
                .created_at(LocalDateTime.now())
                .build();

        return repository.save(organization);
    });}

    private Mono<String> generateNextCode() {
        return repository.findAll() // O si tienes muchos registros, usa `findTopByOrderByCodeDesc()`
                .sort((s1, s2) -> s2.getOrganizationCode().compareTo(s1.getOrganizationCode())) // ordenar descendente
                .next()
                .map(last -> {
                    String lastCode = last.getOrganizationCode(); // Ejemplo: "ST007"
                    int number = Integer.parseInt(lastCode.replace("JASS", ""));
                    return String.format("JASS%03d", number + 1);
                })
                .defaultIfEmpty("JASS001"); // Si es el primero
    }
    @Override
    public Mono<Organization> updateO(String id, Organization organization) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la organización con ID: " + id)))
                .flatMap(existing -> {
                    organization.setOrganizationId(id);  // CORRECCIÓN AQUÍ
                    return repository.save(organization);
                });
    }

    @Override
    public Mono<Void> deleteO(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la organización con ID: " + id)))
                .flatMap(repository::delete);
    }

    @Override
    public Mono<Organization> activateO(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la organización con ID: " + id)))
                .flatMap(org -> {
                    org.setStatus("ACTIVE");
                    return repository.save(org);
                });
    }

    @Override
    public Mono<Organization> deactivateO(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la organización con ID: " + id)))
                .flatMap(org -> {
                    org.setStatus("INACTIVE");
                    return repository.save(org);
                });
    }
}