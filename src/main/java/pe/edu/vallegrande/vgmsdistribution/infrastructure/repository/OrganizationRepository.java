package pe.edu.vallegrande.vgmsdistribution.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.vgmsdistribution.domain.models.Organization;

public interface OrganizationRepository extends ReactiveMongoRepository<Organization, String> {
}