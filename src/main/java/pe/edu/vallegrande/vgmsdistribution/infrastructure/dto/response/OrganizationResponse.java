package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrganizationResponse {
    private String organization_id;  // MongoDB _id
    private String organization_code;
    private String organization_name;
    private String address;
    private Long phone;
    private String legalRepresentative;
    private LocalDateTime created_at;
    private String status;
}
