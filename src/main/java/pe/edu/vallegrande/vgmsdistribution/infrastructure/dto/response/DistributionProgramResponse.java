package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionProgramResponse {

    private String id;
    private String organizationId;
    private String programCode;
    private String scheduleId;
    private String routeId;

    private String programDate;         // Formato: yyyy-MM-dd
    private String plannedStartTime;    // Formato: HH:mm
    private String plannedEndTime;
    private String actualStartTime;
    private String actualEndTime;

    private String status;
    private String responsibleUserId;
    private String observations;
    private String createdAt;           // Formato ISO (puede ser convertido desde Instant)
}
