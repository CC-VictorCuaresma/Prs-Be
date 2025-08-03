package pe.edu.vallegrande.vgmsdistribution.application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsdistribution.application.services.ZonesService;
import pe.edu.vallegrande.vgmsdistribution.domain.enums.Constants;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Zones;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.ZoneCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.ZonesResponse;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.exception.CustomException;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.repository.ZonesRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ZonesServiceImpl implements ZonesService {

    private final ZonesRepository zonesRepository;


    @Override
    public Flux<Zones> getAllZ() {
        return zonesRepository.findAll();
    }

    @Override
    public Flux<Zones> getAllActiveZ() {
        return zonesRepository.findAllByStatus(Constants.ACTIVE.name());
    }

    @Override
    public Flux<Zones> getAllInactiveZ() {
        return zonesRepository.findAllByStatus(Constants.INACTIVE.name());
    }

    @Override
    public Flux<Zones> getByOrganizationZ(String organizationId) {
        return zonesRepository.findAllByOrganizationId(organizationId);
    }

    @Override
    public Mono<Zones> getByIdZ(String id) {
        return zonesRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(404, "Sucursal no encontrada", "ID: " + id)));
    }

    @Override
    public Mono<ZonesResponse> saveZ(ZoneCreateRequest request) {
        return generateNextCode().flatMap(generatedCode -> {
        Zones zones = Zones.builder()
                .zoneCode(generatedCode)
                .zoneName(request.getZoneName())
                .organizationId(request.getOrganizationId())
                .description(request.getDescription())
                .status(Constants.ACTIVE.name())
                .createdAt(LocalDateTime.now())
                .build();

        return zonesRepository.save(zones)
                .map(this::toResponse);
    });
    }

    private Mono<String> generateNextCode() {
        return zonesRepository.findAll() // O si tienes muchos registros, usa `findTopByOrderByCodeDesc()`
                .sort((s1, s2) -> s2.getZoneCode().compareTo(s1.getZoneCode())) // ordenar descendente
                .next()
                .map(last -> {
                    String lastCode = last.getZoneCode(); // Ejemplo: "ST007"
                    int number = Integer.parseInt(lastCode.replace("ZN", ""));
                    return String.format("ZN0%03d", number + 1);
                })
                .defaultIfEmpty("ZN0001"); // Si es el primero
    }
    @Override
    public Mono<Zones> updateZ(String id, Zones zonesUpdate) {
        return getByIdZ(id)
                .flatMap(zones -> {
                    zones.setZoneName(zonesUpdate.getZoneName());
                    zones.setDescription(zonesUpdate.getDescription());
                    zones.setOrganizationId(zonesUpdate.getOrganizationId());
                    return zonesRepository.save(zones);
                });
    }

    @Override
    public Mono<Void> deleteZ(String id) {
        return zonesRepository.deleteById(id);
    }

    @Override
    public Mono<Zones> activateZ(String id) {
        return getByIdZ(id).flatMap(zones -> {
            zones.setStatus(Constants.ACTIVE.name());
            return zonesRepository.save(zones);
        });
    }

    @Override
    public Mono<Zones> deactivateZ(String id) {
        return getByIdZ(id).flatMap(zones -> {
            zones.setStatus(Constants.INACTIVE.name());
            return zonesRepository.save(zones);
        });
    }

    private ZonesResponse toResponse(Zones zones) {
        return ZonesResponse.builder()
                .zoneId(zones.getZoneId())
                .zoneName(zones.getZoneName())
                .zoneCode(zones.getZoneCode())
                .description(zones.getDescription())
                .organizationId(zones.getOrganizationId())
                .build();
    }
}