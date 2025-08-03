package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionProgramCreateRequest {

    private String organizationId;
    private String programCode;
    private String scheduleId;
    private String routeId;

    private String programDate; // formato esperado: "yyyy-MM-dd"
    private String plannedStartTime; // formato: "HH:mm"
    private String plannedEndTime;   // formato: "HH:mm"
    private String actualStartTime;  // formato: "HH:mm"
    private String actualEndTime;    // formato: "HH:mm"

    private String status; // PLANNED, IN_PROGRESS, COMPLETED, CANCELLED
    private String responsibleUserId;
    private String observations;
}
