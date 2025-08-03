package pe.edu.vallegrande.vgmsdistribution.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "zones")
public class Zones {
    @Id
    private String zoneId;
    private String organizationId;
    private String zoneCode;
    private String zoneName;
    private String description; // Asociación con organización
    private String status;
    private LocalDateTime createdAt;
}
