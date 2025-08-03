package pe.edu.vallegrande.vgmsdistribution.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsdistribution.application.services.impl.ZonesServiceImpl;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Zones;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.ZoneCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.ZonesResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v2/zones")
@RequiredArgsConstructor
public class ZonesRest {

    private final ZonesServiceImpl zonesService;

    @GetMapping
    public Flux<Zones> getAllZ() {
        return zonesService.getAllZ();
    }

    @GetMapping("/active")
    public Flux<Zones> getAllActiveZ() {
        return zonesService.getAllActiveZ();
    }

    @GetMapping("/inactive")
    public Flux<Zones> getAllInactiveZ() {
        return zonesService.getAllInactiveZ();
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<Zones> getByOrganizationZ(@PathVariable String organizationId) {
        return zonesService.getByOrganizationZ(organizationId);
    }

    @GetMapping("/{id}")
    public Mono<Zones> getByIdZ(@PathVariable String id) {
        return zonesService.getByIdZ(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ZonesResponse> saveZ(@RequestBody ZoneCreateRequest request) {
        return zonesService.saveZ(request);
    }

    @PutMapping("/{id}")
    public Mono<Zones> updateZ(@PathVariable String id, @RequestBody Zones zones) {
        return zonesService.updateZ(id, zones);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteZ(@PathVariable String id) {
        return zonesService.deleteZ(id);
    }

    @PatchMapping("/{id}/activate")
    public Mono<Zones> activateZ(@PathVariable String id) {
        return zonesService.activateZ(id);
    }

    @PatchMapping("/{id}/desactivate")
    public Mono<Zones> deactivateZ(@PathVariable String id) {
        return zonesService.deactivateZ(id);
    }
}