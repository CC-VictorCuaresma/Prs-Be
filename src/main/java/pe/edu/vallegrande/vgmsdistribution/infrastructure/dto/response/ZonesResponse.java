package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ZonesResponse {
    private String zoneId;
    private String organizationId;
    private String zoneCode;
    private String zoneName;
    private String description; // Asociación con organización
    private String status;
    private LocalDateTime createdAt;
}