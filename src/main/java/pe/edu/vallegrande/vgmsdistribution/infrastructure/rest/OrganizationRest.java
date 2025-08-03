package pe.edu.vallegrande.vgmsdistribution.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Organization;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.OrganizationCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.application.services.OrganizationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v2/organizations")
@RequiredArgsConstructor
public class OrganizationRest {

    private final OrganizationService organizationService;

    @GetMapping
    public Flux<Organization> getAllO() {
        return organizationService.getAllO();
    }

    @GetMapping("/{id}")
    public Mono<Organization> getByIdO(@PathVariable String id) {
        return organizationService.getByIdO(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Organization> createO(@RequestBody OrganizationCreateRequest request) {
        return organizationService.saveO(request);
    }

    @PutMapping("/{id}")
    public Mono<Organization> updateO(@PathVariable String id, @RequestBody Organization organization) {
        return organizationService.updateO(id, organization);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteO(@PathVariable String id) {
        return organizationService.deleteO(id);
    }

    @PatchMapping("/{id}/activate")
    public Mono<Organization> activateO(@PathVariable String id) {
        return organizationService.activateO(id);
    }

    @PatchMapping("/{id}/desactivate")
    public Mono<Organization> deactivateO(@PathVariable String id) {
        return organizationService.deactivateO(id);
    }

}