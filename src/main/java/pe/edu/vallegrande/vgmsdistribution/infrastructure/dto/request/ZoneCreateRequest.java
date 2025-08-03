package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneCreateRequest {

    private String organizationId;
    private String zoneCode;
    private String zoneName;
    private String description;
}
