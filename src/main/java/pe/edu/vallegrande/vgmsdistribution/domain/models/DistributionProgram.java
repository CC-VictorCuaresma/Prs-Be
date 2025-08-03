package pe.edu.vallegrande.vgmsdistribution.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "distribution_programs")
public class DistributionProgram {

    @Id
    private String id;

    private String organizationId;
    private String programCode;
    private String scheduleId;
    private String routeId;

    private LocalDate programDate;
    private String plannedStartTime;
    private String plannedEndTime;
    private String actualStartTime;
    private String actualEndTime;

    private String status; // PLANNED, IN_PROGRESS, COMPLETED, CANCELLED
    private String responsibleUserId;
    private String observations;

    private Instant createdAt;
}
