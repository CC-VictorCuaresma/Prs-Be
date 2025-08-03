package pe.edu.vallegrande.vgmsdistribution.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "organizations")
public class Organization {

    @Id
    private String organizationId;
    private String organizationCode;
    private String organizationName;
    private String address;
    private Long phone;
    private String legalRepresentative;
    private LocalDateTime created_at;

    @Builder.Default
    private String status = "ACTIVE";
    private LocalDateTime updated_at;

}
